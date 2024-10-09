package com.notifyme.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notifyme.DTOs.KafkaWhatsAppDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.notifyme.constants.Constants.*;


@Component
@EnableKafka
public class KafkaWhatsAppConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaWhatsAppConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();


    @KafkaListener(topics = "wp", groupId = GROUP_ID)
    public void consume(String message) {
        try {
            KafkaWhatsAppDTO kafkaWhatsAppDTO = objectMapper.readValue(message, KafkaWhatsAppDTO.class);
            sendWhatsAppNotification(kafkaWhatsAppDTO);
        } catch (Exception e) {
            logger.error("KAFKA ERROR :: " + e.getMessage());
        }
    }

    public boolean sendWhatsAppNotification(KafkaWhatsAppDTO kafkaWhatsAppDTO) {
        try {
            Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("whatsapp:" + kafkaWhatsAppDTO.getUserMobileNumber()),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    kafkaWhatsAppDTO.getUserMessage()).create();

          logger.warn(message.getSid() + " :: " + kafkaWhatsAppDTO.getUserMobileNumber());
        } catch (Exception e) {
                logger.warn("TWILIO ERROR :: "+e.getMessage());
        }
        return true;
    }

}