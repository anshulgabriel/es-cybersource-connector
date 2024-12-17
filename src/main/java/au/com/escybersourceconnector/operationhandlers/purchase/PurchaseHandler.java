package au.com.escybersourceconnector.operationhandlers.purchase;

import au.com.es.paymentsassist.enums.Entity;
import au.com.es.paymentsassist.enums.Operation;
import au.com.escybersourceconnector.annotations.OperationComponent;
import au.com.escybersourceconnector.models.context.ExchangeContext;
import au.com.escybersourceconnector.models.response.CyberSourceResponse;
import au.com.escybersourceconnector.models.response.DownStreamResponse;
import au.com.escybersourceconnector.operationhandlers.OperationHandler;
import au.com.escybersourceconnector.routehandlers.component.CyberSourceEndpoints;
import au.com.escybersourceconnector.transformers.OperationResponseTransformer;
import au.com.escybersourceconnector.validators.PurchaseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;


@OperationComponent(entity = Entity.TRANSACTION,
        operation = Operation.PURCHASE)
@RequiredArgsConstructor
@Slf4j
public class PurchaseHandler implements OperationHandler {
    private final CyberSourceEndpoints cyberSourceEndpoints;
    private final PurchaseValidator purchaseValidator;
    private final WebClient cybersourceWebClient;
    @Autowired
    private OperationResponseTransformer operationResponseTransformer;

    @Override
    public Validator getValidator() {
        return purchaseValidator;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public Class<?> getRequestClass() {
        return ExchangeContext.class;
    }

    @Override
    public Mono<ServerResponse> processRequest(final AtomicReference<ExchangeContext> atomicExchangeContext) {
        final ExchangeContext exchangeContext = atomicExchangeContext.get();
        log.info("Processing request with context: {}", exchangeContext);
        return makeWebClientCall(exchangeContext);
    }

    private Mono<ServerResponse> makeWebClientCall(ExchangeContext exchangeContext) {
        return cybersourceWebClient.post()
                .uri(cyberSourceEndpoints.get(Operation.PURCHASE))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(exchangeContext.getCyberSourceRequest())
                .exchangeToMono(clientResponse -> handleResponse(clientResponse, exchangeContext));
    }

    private Mono<ServerResponse> handleResponse(ClientResponse clientResponse, ExchangeContext exchangeContext) {
        if (clientResponse.statusCode().is2xxSuccessful()) {
            return processSuccessResponse(clientResponse, exchangeContext);
        } else {
            return processErrorResponse(clientResponse, exchangeContext);
        }
    }

    private Mono<ServerResponse> processSuccessResponse(ClientResponse clientResponse, ExchangeContext exchangeContext) {
        return clientResponse.toEntity(CyberSourceResponse.class)
                .doOnNext(response -> log.info("Purchase successful: {}", response.getBody()))
                .flatMap(response -> {
                    DownStreamResponse downstreamResponse = operationResponseTransformer
                            .mapToSuccessResponse(clientResponse, response.getBody(), exchangeContext);
                    return ServerResponse.status(clientResponse.statusCode().value())
                            .bodyValue(downstreamResponse);
                });
    }

    private Mono<ServerResponse> processErrorResponse(ClientResponse clientResponse, ExchangeContext exchangeContext) {
        return clientResponse.toEntity(CyberSourceResponse.class)
                .doOnNext(response -> log.error("Error response received: {}", response))
                .flatMap(response -> {
                    DownStreamResponse downstreamResponse = operationResponseTransformer
                            .mapToErrorResponse(clientResponse, response.getBody(), exchangeContext);
                    return ServerResponse.status(clientResponse.statusCode().value())
                            .bodyValue(downstreamResponse);
                })
                .onErrorResume(WebClientResponseException.class, error -> {
                    log.error("Error processing purchase request: {}", error.getMessage(), error);
                    return ServerResponse.status(error.getStatusCode())
                            .bodyValue(error.getResponseBodyAsString());
                })
                .doOnError(error -> log.error("Unexpected error: {}", error.getMessage(), error));
    }

}
