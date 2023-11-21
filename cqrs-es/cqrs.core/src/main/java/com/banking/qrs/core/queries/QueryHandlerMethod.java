package com.banking.qrs.core.queries;

import com.banking.qrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity>handle(T query);
}
