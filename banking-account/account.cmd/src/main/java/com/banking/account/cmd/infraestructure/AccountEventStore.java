package com.banking.account.cmd.infraestructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.qrs.core.events.BaseEvent;
import com.banking.qrs.core.events.EventModel;
import com.banking.qrs.core.exceptions.AggregateNotFoundException;
import com.banking.qrs.core.exceptions.ConcurrencyException;
import com.banking.qrs.core.infraestructure.EventStore;
import com.banking.qrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != 1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event: events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if( null != persistedEvent && !persistedEvent.getId().isEmpty()){
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
        }

        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
