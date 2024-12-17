package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestModel {
    private String requestId;
    private String traceId;
    private String operation;
    private String entity;
    private long currentDateTimeUtc;
    private PaymentInstrumentDecoded paymentInstrumentDecoded;
    private String cardToken;
    private String cardHolderName;
    private String cardExpiryDate;
    private String pan;
    private String cvv;
    private String gatewaySettingId;
    private String customerId;
    private String channel;
    private String transactionId;
    private String pfrTransactionId;
    private String hierarchyLevel;
    private int txnType;
    private CardDetails cardDetails;
    private String identifiedCardBrand;
    private String identifiedBinCountry;
    private String originalCardBrand;
    private String originalCardType;
    private String originalCardSubType;
    private String originalCardIssuer;
    private String originalCardCountry;
    private String originalPaymentInstrumentType;
    private String originalPaymentInstrumentSubType;
    private String originalDE48;
    private OriginalLfsData originalLfsData;
    private String originalGatewayType;
    private String originalGatewayRoute;
    private String originalTransactionId;
    private String initialTxnId;
    private String linkedGwTransactionId;
    private int originalTransactionTxnType;
    private LocalDateTime originalTransactionDate;
    private LocalDateTime linkedTransactionDate;
    private String originalTransactionDE61;
    private String originalMerchantReference;
    private OriginalTxnReference originalTxnReference;
    private String preAuthId;
    private String currency;
    private Long amount;
    private String orderId;
    private ThirdPartyToken thirdPartyToken;
    private String transactionDate;
    private String maskedPan;
    private String userName;
    private String password;
    private String virtualUser;
    private String hashedUserName;
    private String verifyId;
    private String threeDSID;
    private ThreeDs threeDs;
    private ThreeDsResponse threeDsResponse;
    private boolean threeDsOnly;
    private long startRequest;
    private long endRequest;
    private int requestTiming;
    private String merchReference;
    private Metadata metadata;
    private String requestorIp;
    private int ecm;
    private String dynamicDescriptor;
    private String accountNumber;
    private String dateOfBirth;
    private String driversLicense;
    @JsonProperty("LFSData")
    private Map<String, String> LFSData;
    private String secureToken;
    private String secretKeyForSecureToken;
    private boolean routeToBankSim;
    private String orderReference;
    private long txnTimeStamp;
}
