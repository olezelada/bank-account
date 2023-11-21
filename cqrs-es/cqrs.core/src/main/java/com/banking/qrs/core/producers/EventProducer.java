package com.banking.qrs.core.producers;

import com.banking.qrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
