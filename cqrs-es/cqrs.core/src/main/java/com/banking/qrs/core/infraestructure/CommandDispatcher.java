package com.banking.qrs.core.infraestructure;

import com.banking.qrs.core.commands.BaseCommand;
import com.banking.qrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> hander);
    void send(BaseCommand command);
}
