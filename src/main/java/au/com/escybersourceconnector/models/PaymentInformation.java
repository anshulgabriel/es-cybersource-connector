package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInformation {
    private Card card;
    private TokenizedCard tokenizedCard;
}
