package au.com.escybersourceconnector.routes;


import au.com.es.salad.reactive.routing.RouteExtension;
import au.com.escybersourceconnector.routehandlers.OperationRoutesHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static au.com.escybersourceconnector.utils.Constants.BASE_API_PREFIX;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class OperationRoutes implements RouteExtension {

    final List<HandlerFilterFunction<ServerResponse, ServerResponse>> routeFilters;

    @Bean
    @RouterOperation(
            path = "cybersourceconnector/api/v1/purchase",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "purchase",
            operation = @Operation(
                    operationId = "purchase",
                    summary = "purchase processing",
                    description = "purchase request parser for cybersource",
                    tags = {"Purchase"},
                    requestBody = @RequestBody(
                            description = "Purchase request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/purchase/purchaseRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Purchase processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/purchase/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> purchase(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/purchase")),
                        operationRoutesHandler::purchase
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/payments/{id}/reversal",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "reversal",
            operation = @Operation(
                    operationId = "reversal",
                    summary = "Reversal processing",
                    description = "Reversal request parser for cybersource",
                    tags = {"Reversal"},
                    requestBody = @RequestBody(
                            description = "Reversal request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/reversal/reversalRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Payment Reversal processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/reversal/reversalResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> reversal(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/payments/{id}/reversal")),
                        operationRoutesHandler::reversal
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/payments/{id}/refund",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "refundPayment",
            operation = @Operation(
                    operationId = "refund",
                    summary = "Payment Refund processing",
                    description = "Payment Refund request parser for cybersource",
                    tags = {"Payment Refund"},
                    requestBody = @RequestBody(
                            description = "Payment Refund request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/payment/paymentRefundRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Payment Refund processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/payment/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> paymentRefund(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/payments/{id}/refund")),
                        operationRoutesHandler::paymentRefund
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/captures/{id}/refunds",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "refundCapture",
            operation = @Operation(
                    operationId = "refund",
                    summary = "Captures Refund processing",
                    description = "Captures Refund request parser for cybersource",
                    tags = {"refund Capture"},
                    requestBody = @RequestBody(
                            description = "Captures Refund request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/captures/captureRefundRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Captures Refund processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/captures/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> captureRefund(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/captures/{id}/refunds")),
                        operationRoutesHandler::captureRefund
                ),
                routeFilters
        );
    }


    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/credits",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "credits",
            operation = @Operation(
                    operationId = "credits",
                    summary = "credits processing",
                    description = "credits request parser for cybersource",
                    tags = {"credits"},
                    requestBody = @RequestBody(
                            description = "credits request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/credits/creditRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "credits processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/credits/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> credit(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/credits")),
                        operationRoutesHandler::credits
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/payments/{id}/voids",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "credits",
            operation = @Operation(
                    operationId = "void",
                    summary = "void puchase processing",
                    description = "void puchase request parser for cybersource",
                    tags = {"void"},
                    requestBody = @RequestBody(
                            description = "void puchase request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/puchase/void/purchaseVoidRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "credits processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/void/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> paymentVoid(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/payments/{id}/voids")),
                        operationRoutesHandler::paymentVoid
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/captures/{id}/voids",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "credits",
            operation = @Operation(
                    operationId = "void",
                    summary = "void captures processing",
                    description = "void captures request parser for cybersource",
                    tags = {"void"},
                    requestBody = @RequestBody(
                            description = "void captures request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/void/captures/capturesVoidRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "credits processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/void/captures/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> capturesVoid(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/captures/{id}/voids")),
                        operationRoutesHandler::capturesVoid
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/refunds/{id}/voids",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "credits",
            operation = @Operation(
                    operationId = "void",
                    summary = "void captures processing",
                    description = "void captures request parser for cybersource",
                    tags = {"void"},
                    requestBody = @RequestBody(
                            description = "void captures request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/void/captures/capturesVoidRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "credits processing success",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/void/captures/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> refundVoid(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/refunds/{id}/voids")),
                        operationRoutesHandler::refundVoid
                ),
                routeFilters
        );
    }

    @Bean
    @RouterOperation(
            path = "/cybersourceconnector/api/v1/reversals",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST,
            beanClass = OperationRoutesHandler.class,
            beanMethod = "credits",
            operation = @Operation(
                    operationId = "reversals",
                    summary = "timeout reversals processing",
                    description = "timeout reversals request parser for cybersource",
                    tags = {"void"},
                    requestBody = @RequestBody(
                            description = "timeout reversals request parser for cybersource",
                            required = true,
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "@jsons/reversal/timeOutReversalVoidRequest.json")
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "timeout reversals completed",
                                    content = @Content(
                                            mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/reversal/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "400",
                                    description = "Bad request: the request could not be understood by the server due to malformed syntax. The client should not repeat the request without modifications.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            ),
                            @ApiResponse(responseCode = "500",
                                    description = "Internal server error: the server encountered an unexpected condition which prevented it from fulfilling the request.",
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "@jsons/downStreamResponse.json")
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> timeOutReversal(OperationRoutesHandler operationRoutesHandler) {
        return routeWithFilters(
                route(
                        postMapping(BASE_API_PREFIX.formatted("/refunds/{id}/voids")),
                        operationRoutesHandler::timeOutReversal
                ),
                routeFilters
        );
    }
}
