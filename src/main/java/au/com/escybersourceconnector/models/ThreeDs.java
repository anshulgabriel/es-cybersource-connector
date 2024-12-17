package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThreeDs {
    private String threeDSId;
    private  boolean success;
    private  String cavv;
    private  String eci;
    private  String version;
    private  String xid;
    private  String dsTransactionId;
    private String verificationStatus;
    private String verificationStatusCode;
    private  String verificationStatusMessage;
    private String verifyStatus;
}
