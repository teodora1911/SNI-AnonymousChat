package org.unibl.etf.sni.anonymouschat.models.responses;

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
public class MessageResponse implements Serializable {

    private UUID uuid;
    private Integer segment;
    private String senderUsername;
    private String senderName;
    private String senderSurname;
    private String content;
}
