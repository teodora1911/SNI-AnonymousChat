package org.unibl.etf.sni.anonymouschat.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MessageRequest implements Serializable {

    @NotNull
    private UUID id;
    @NotNull
    @Min(0)
    private Integer segmentId;
    @NotNull
    private Integer segmentsNumber;
    @NotNull
    private String sender;
    @NotNull
    @NotBlank
    private String receiver;
    @NotBlank
    @NotNull
    private String content;
    private String extension;
}
