package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDetails {
    private String bin;
    private  List<String> cardBrand;
    private  String issuedOrganization;
    private  String cardType;
    private  String cardSubtype;
    private  String country;
    private  String lastUpdated;
    private boolean submittedForUpdate;
    private String updateStatus;
}
