package org.unibl.etf.sni.anonymouschat.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest implements Serializable {

    @NotBlank
    private String refreshToken;
}
