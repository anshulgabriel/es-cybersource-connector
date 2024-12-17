package au.com.escybersourceconnector.operationhandlers;

import au.com.es.paymentsassist.resultcodes.IpsiResultCodes;
import au.com.es.salad.errorhandlers.ErrorData;
import au.com.es.salad.logging.LogUtils;
import au.com.es.salad.transform.Transformer;
import au.com.escybersourceconnector.exceptions.RequestValidationRuntimeException;
import au.com.escybersourceconnector.models.context.ExchangeContext;
import au.com.escybersourceconnector.models.request.CyberSourceRequest;
import au.com.escybersourceconnector.models.response.CyberSourceResponse;
import au.com.escybersourceconnector.models.response.DownStreamResponse;
import au.com.escybersourceconnector.transformers.OperationRequestTransformer;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

import static au.com.es.salad.utils.ErrorTexts.ERROR_PROCESSING_OPERATION;

@SuppressWarnings("unused")

public interface OperationHandler {

    Validator getValidator();

    Logger getLogger();

    Class<?> getRequestClass();

    Mono<ServerResponse> processRequest(final AtomicReference<ExchangeContext> atomicExchangeContext);

    default Predicate<Errors> hasFieldErrors(final AtomicReference<ExchangeContext> atomicExchangeContext) {
        return Errors::hasFieldErrors;
    }

    default Mono<CyberSourceRequest> preProcessRequest(@NonNull AtomicReference<ExchangeContext> atomicExchangeContexts) {
        OperationRequestTransformer operationRequestTransformer = new OperationRequestTransformer();
        return atomicExchangeContexts.get().getServerRequest().bodyToMono(ExchangeContext.class).flatMap(body -> {
            atomicExchangeContexts.updateAndGet(context ->
                    context.withRequestModel(body.getRequestModel())
                            .withTraceId(body.getTraceId())
            );
            CyberSourceRequest cyberSourceRequest = operationRequestTransformer.transform(atomicExchangeContexts);
            atomicExchangeContexts.updateAndGet(context -> context.withCyberSourceRequest(cyberSourceRequest));
            return Mono.just(cyberSourceRequest);
        });
    }

    default Mono<ServerResponse> handleRequest(final AtomicReference<ExchangeContext> atomicExchangeContext) {
        Mono<CyberSourceRequest> mono = preProcessRequest(atomicExchangeContext);
        return mono.flatMap(requestData -> Optional.ofNullable(this.getValidator()).map(validator -> {
                            getLogger().info("--- Validating request ---");
                            LogUtils.info(getLogger(), requestData.toString());
                            final Errors errors = new BeanPropertyBindingResult(requestData, CyberSourceRequest.class.getName());
                            validator.validate(requestData, errors);
                            return errors;
                        }).filter(hasFieldErrors(atomicExchangeContext))
                        .map(errors -> onValidationErrors(errors, atomicExchangeContext))
                        .orElseGet(() -> {
                            return processRequest(atomicExchangeContext);
                        })
        ).onErrorResume(onPreProcessErrors(atomicExchangeContext));
    }

    default Function<Throwable, Mono<ServerResponse>> onPreProcessErrors(final AtomicReference<ExchangeContext> atomicExchangeContext) {
        return throwable -> {
            getLogger().debug("Request preprocess failed");
            final ExchangeContext exchangeContext = atomicExchangeContext.get();
            final DownStreamResponse downStreamResponse = DownStreamResponse.builder()
                    .resultCode("06").requestId(exchangeContext.getRequestId())
                    .message(IpsiResultCodes.getDescription("06"))
                    .errors(List.of(throwable.getMessage())).build();
            getLogger().info("--- pre-process error response ---");
            LogUtils.info(getLogger(), downStreamResponse);
            getLogger().error(throwable.getMessage(), throwable);
            return ServerResponse.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(downStreamResponse);

        };
    }

    default Mono<ServerResponse> onValidationErrors(final Errors errors, final AtomicReference<ExchangeContext> atomicExchangeContext) {

        return Optional.ofNullable(errorsTransformer()).map(transformer -> transformer.transform(errors)).map(errorDataList -> {
            final ExchangeContext exchangeContext = atomicExchangeContext.get();

            getLogger().error(ERROR_PROCESSING_OPERATION.formatted(exchangeContext.getEntityOperation()));

            final DownStreamResponse cyberSourceResponse = DownStreamResponse.builder()
                    .resultCode("06")
                    .requestId(exchangeContext.getRequestId())
                    .message(IpsiResultCodes.getDescription("06"))
                    .errors(List.of(errorDataList.toString())).build();
            getLogger().info("--- validation error response ---");
            LogUtils.info(getLogger(), cyberSourceResponse);
            return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue(cyberSourceResponse);
        }).orElseThrow(() -> new RequestValidationRuntimeException(errors.getAllErrors().toString()));

    }

    @SuppressWarnings("unchecked")
    default <R> Transformer<Errors, R> errorsTransformer() {
        return errors -> (R) errors.getFieldErrors().stream().map(ErrorData::from).toList();
    }
}
