package org.unibl.etf.sni.anonymouschat.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse implements Serializable {

    private String jwtToken;
}
