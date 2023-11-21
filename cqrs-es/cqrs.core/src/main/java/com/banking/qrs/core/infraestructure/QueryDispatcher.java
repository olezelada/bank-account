package com.banking.qrs.core.infraestructure;

import com.banking.qrs.core.domain.BaseEntity;
import com.banking.qrs.core.queries.BaseQuery;
import com.banking.qrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
