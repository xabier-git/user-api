package cl.sentra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRequest {
    private String number;
    private String citycode;
    private String contrycode;

    // Getters and setters
}