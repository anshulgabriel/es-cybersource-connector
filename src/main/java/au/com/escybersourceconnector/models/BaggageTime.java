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
public class BaggageTime {
    private long startTimeInMillis;
    private long remainingTimeoutInMillis;
    private long realtimeRemainingTime;
}
