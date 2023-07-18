package com.fourtheorem.ecs.demo.eventsite.register.eventregister.repositories;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class DynamoDbRepo {

    //@Autowired
    //AmazonDynamoDB dynamoDB;

    DynamoDbClient ddb = DynamoDbClient.builder().build();

    private static String TABLE_NAME = "Eventsite";

    public String registerForEvent(String eventId,
                                   int memberId,
                                   String date) {

        AttributeValue updatedAttendees = getUpdatedAttendees(eventId, date, memberId);
        AttributeValueUpdate attributeValueUpdate = AttributeValueUpdate.builder().action(AttributeAction.PUT).value(updatedAttendees).build();

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(getEventKey(eventId, date))
                .attributeUpdates(Map.of("Attendees", attributeValueUpdate))
                .build();

        UpdateItemResponse updateItemResponse = ddb.updateItem(request);
        return updateItemResponse.responseMetadata().toString();
    }

    private String getPartitionKey(String eventId) {
        return String.format("EVENT_%s", eventId);
    }

    private Map<String, AttributeValue> getEventKey(String eventId, String date) {
        return Map.of(
                "PK", AttributeValue.builder().s(getPartitionKey(eventId)).build(),
                "SK", AttributeValue.builder().s(date).build()
        );
    }

    private AttributeValue getUpdatedAttendees(String eventId,
                                               String date,
                                               Integer memberId) {
        Map<String, AttributeValue> key = getEventKey(eventId, date);
        GetItemRequest attendeesGetReq = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .attributesToGet("Attendees")
                .build();

        //hoping permissions come from the execution role
        GetItemResponse item = ddb.getItem(attendeesGetReq);
        List<String> attendees = item.item().get("Attendees").ns();
        Set<String> currentAttendees = Set.of(attendees.toArray(new String[0]));
        HashSet<String> updatedAttendees = new HashSet<>();
        updatedAttendees.add(memberId.toString());
        updatedAttendees.addAll(currentAttendees);
        return AttributeValue.builder()
                .ns(updatedAttendees)
                .build();
    }


}
