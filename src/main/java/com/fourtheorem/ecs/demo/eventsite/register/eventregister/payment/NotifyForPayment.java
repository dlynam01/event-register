package com.fourtheorem.ecs.demo.eventsite.register.eventregister.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.protocols.jsoncore.JsonNode;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import java.util.Map;

@Component
public class NotifyForPayment {

    @Value("${payment.notification.url}")
    String queueUrl;

    SqsClient sqsClient = SqsClient.builder()
            .build();

    public String processPaymentCommand(int memberId, String eventId) {
        Map<String, MessageAttributeValue> messageAttribs = Map.of(
                "memberId", MessageAttributeValue.builder().stringValue(String.valueOf(memberId)).dataType("Number").build(),
                "eventId", MessageAttributeValue.builder().stringValue(eventId).dataType("Number").build()
        );
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody("{\"messageType\": \"PAYMENT_REQUEST\"}")
                .messageAttributes(messageAttribs)
                .build();
        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(messageRequest);
        return sendMessageResponse.messageId();
    }
}
