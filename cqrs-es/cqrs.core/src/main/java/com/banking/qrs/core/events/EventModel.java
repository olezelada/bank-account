package com.banking.qrs.core.events;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collation = "eventStore")
public class EventModel {
    @Id
    private String id;
    private int version;
    private Date timeStamp;
    private String eventType;
    private BaseEvent eventData;
    private String aggregateType;
    private String aggregateIdentifier;
}
