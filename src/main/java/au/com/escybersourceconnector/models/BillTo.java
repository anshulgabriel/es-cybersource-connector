package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillTo {
   private String firstName;
   private String lastName;
    private String address1;
 private String locality;
    private String administrativeArea;
    private String postalCode;
private String country;
    private String email;
    private String phoneNumber;
}
