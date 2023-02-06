package org.unibl.etf.sni.anonymouschat.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleMessageRequest {
    private String receiver;
    private String content;
}
