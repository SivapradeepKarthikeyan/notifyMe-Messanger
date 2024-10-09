package com.notifyme.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.protocol.types.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaWhatsAppDTO {

    private String userName;
    private String userMobileNumber;
    private String userMovieName;
    private String userMovieUrl;
    private String userMessage;

}

