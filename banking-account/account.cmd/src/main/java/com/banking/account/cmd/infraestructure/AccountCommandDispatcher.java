package com.banking.account.cmd.infraestructure;

import com.banking.qrs.core.commands.BaseCommand;
import com.banking.qrs.core.commands.CommandHandlerMethod;
import com.banking.qrs.core.infraestructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> hander) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(hander);
    }

    @Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        if(null == handlers || handlers.isEmpty()){
            throw new RuntimeException("The command handler does not register");
        }

        if(handlers.size() > 1){
            throw new RuntimeException("Cannot send a command with more than one handler");
        }

        handlers.get(0).handle(command);

    }
}
