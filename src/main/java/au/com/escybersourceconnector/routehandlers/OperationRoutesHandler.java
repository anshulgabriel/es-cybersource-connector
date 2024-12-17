package au.com.escybersourceconnector.routehandlers;

import au.com.es.paymentsassist.enums.Entity;
import au.com.es.paymentsassist.enums.Operation;
import au.com.es.paymentsassist.models.operationhandler.EntityOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class OperationRoutesHandler {

    final RouteExchangeHandler routeExchangeHandler;

    public Mono<ServerResponse> purchase(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.PURCHASE);
    }

    public Mono<ServerResponse> reversal(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.REVERSAL);
    }

    public Mono<ServerResponse> paymentRefund(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.REFUND);
    }

    public Mono<ServerResponse> captureRefund(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.CAPTURE);
    }

    public Mono<ServerResponse> credits(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.CREDIT);
    }

    public Mono<ServerResponse> paymentVoid(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.VOID_PURCHASE);
    }

    public Mono<ServerResponse> capturesVoid(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.VOID_CAPTURE);
    }

    public Mono<ServerResponse> refundVoid(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.VOID_REFUND);
    }

    public Mono<ServerResponse> timeOutReversal(ServerRequest serverRequest) {
        return executeFor(serverRequest, Operation.REVERSAL_TIMEOUT);
    }

    public Mono<ServerResponse> executeFor(ServerRequest serverRequest, Operation operation) {
        return routeExchangeHandler
                .handler()
                .apply(EntityOperation.of(
                                Entity.TRANSACTION,
                                operation
                        ),
                        serverRequest
                );
    }
}