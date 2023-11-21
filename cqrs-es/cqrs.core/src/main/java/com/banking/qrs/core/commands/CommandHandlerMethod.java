package com.banking.qrs.core.commands;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handle(T command);
}
