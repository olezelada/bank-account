package com.banking.account.cmd.infraestructure;

import com.banking.account.cmd.domain.AccountAggregate;
import com.banking.qrs.core.domain.AggregateRoot;
import com.banking.qrs.core.handlers.EventSourcingHandler;
import com.banking.qrs.core.infraestructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;


    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvent(id);

        if(null != events && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x-> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }
}
