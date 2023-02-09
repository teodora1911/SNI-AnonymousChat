package org.unibl.etf.sni.anonymouschat.models.dtos;

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
public class MessageInfo implements Serializable {
    private UUID id;
    private Integer segmentId;
    private Integer segmentsNumber;
    private String content;
    private String extension;
}
