package au.com.escybersourceconnector.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentData {
    private InstrumentMetaData instrumentMetaData;
    private PanData panData;
}
