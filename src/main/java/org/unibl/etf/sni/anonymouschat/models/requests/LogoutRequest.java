package org.unibl.etf.sni.anonymouschat.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogoutRequest implements Serializable {

    @NotNull
    @Min(0)
    private Integer id;
}
