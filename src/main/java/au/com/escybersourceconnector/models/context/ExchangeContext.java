package au.com.escybersourceconnector.models.context;

import au.com.escybersourceconnector.models.RequestModel;
import au.com.escybersourceconnector.models.request.CyberSourceRequest;
import au.com.escybersourceconnector.models.response.CyberSourceResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import au.com.es.paymentsassist.models.operationhandler.EntityOperation;
import java.time.ZonedDateTime;

@Value
@Builder
@With
@AllArgsConstructor
public class ExchangeContext {
  String requestId;
  String traceId;
  EntityOperation entityOperation;
  ZonedDateTime currentDateTimeUtc;
  ServerRequest serverRequest;
  RequestModel requestModel;
  CyberSourceRequest cyberSourceRequest;
  CyberSourceResponse cyberSourceResponse;
  @With
  HttpStatus httpStatus;
  @With
  String ipsiResponseCode;
  long timeoutInMillis;
}
