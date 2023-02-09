package org.unibl.etf.sni.anonymouschat.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    private String sender;
    private String receiver;
    private String payload;
}
