package org.unibl.etf.sni.anonymouschat.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUserResponse {

    private String username;
    private String name;
    private String surname;
}
