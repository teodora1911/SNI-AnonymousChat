package org.unibl.etf.sni.anonymouschat.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageRequest implements Serializable {

    @NotNull
    private UUID uuid;
    @NotNull
    @Min(0)
    private Integer segmentId;
    @NotNull
    private Integer segmentsNumber;
    @NotNull
    private Integer senderId;
    @NotNull
    @NotBlank
    private String receiverUsername;
    @NotBlank
    @NotNull
    private String content;
    private String extension;
}
