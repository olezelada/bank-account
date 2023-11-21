package com.banking.qrs.core.handlers;

import com.banking.qrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
