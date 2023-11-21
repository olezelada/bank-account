package com.banking.account.cmd.api.command;

public interface CommnadHandler {
    void handle(OpenAccountCommand command);
    void handle(DepostiFundsCommand command);
    void handle(WithdrawFundsCommand command);
    void handle(CloseAccountCommand command);
}
