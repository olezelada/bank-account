package com.banking.qrs.core.infraestructure;

import com.banking.qrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvent(String aggregateId);
}
