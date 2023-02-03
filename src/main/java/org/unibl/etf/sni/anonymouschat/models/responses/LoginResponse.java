package org.unibl.etf.sni.anonymouschat.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private Integer id;
    private String username;
    private String token;
}
