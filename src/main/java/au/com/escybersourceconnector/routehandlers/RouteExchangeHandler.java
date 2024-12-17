package au.com.escybersourceconnector.routehandlers;

import au.com.es.paymentsassist.models.operationhandler.EntityOperation;
import au.com.escybersourceconnector.factories.OperationComponentsFactory;
import au.com.escybersourceconnector.models.context.ExchangeContext;
import au.com.escybersourceconnector.routehandlers.component.CyberSourceEndpoints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class RouteExchangeHandler {

    final CyberSourceEndpoints cyberSourceEndpoints;
    final WebClient epalWebClient;
    final WebClient epalSslWebClient;
    final OperationComponentsFactory operationComponentsFactory;


    private Long timeoutInMillis = 300000000L;


    public BiFunction<EntityOperation, ServerRequest, Mono<ServerResponse>> handler() {
        log.debug("Operation request/response exchange started");
        return (entityOperation, request) -> Mono.just(new AtomicReference<>(
                        ExchangeContext.builder()
                                .requestId(request.exchange()
                                        .getRequest()
                                        .getId())
                                .entityOperation(entityOperation)
                                .currentDateTimeUtc(ZonedDateTime.now(ZoneOffset.UTC))
                                .serverRequest(request)
                                .build()
                ))
                .flatMap(exchangeRequestAndResponse())
                .timeout(Duration.ofMillis(timeoutInMillis));
    }

    private Function<AtomicReference<ExchangeContext>, Mono<ServerResponse>> exchangeRequestAndResponse() {
        return atomicExchangeContext -> {

            final ExchangeContext exchangeContext = atomicExchangeContext.get();
            return operationComponentsFactory.operationHandler(exchangeContext.getEntityOperation())
                    .handleRequest(atomicExchangeContext);

        };
    }
}
