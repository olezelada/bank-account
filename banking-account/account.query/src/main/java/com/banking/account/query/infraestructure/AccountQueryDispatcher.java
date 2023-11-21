package com.banking.account.query.infraestructure;

import com.banking.qrs.core.domain.BaseEntity;
import com.banking.qrs.core.infraestructure.QueryDispatcher;
import com.banking.qrs.core.queries.BaseQuery;
import com.banking.qrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod handler) {
        var handlers = routes.computeIfAbsent(type, c-> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if(null == handlers || handlers.isEmpty()){
            throw new RuntimeException("Any query handler was register for this query");
        }

        if(handlers.size() > 1 ){
            throw new RuntimeException("No puede enviar un Query que tanga mas de dos queries");
        }

        return handlers.get(0).handle(query);
    }
}
