package com.banking.account.cmd.domain;

import com.banking.account.cmd.api.command.OpenAccountCommand;
import com.banking.account.common.events.AccountClosedEvent;
import com.banking.account.common.events.AccountOpenedEvent;
import com.banking.account.common.events.FundsDepositedEvent;
import com.banking.account.common.events.FundsWithdrawnEvent;
import com.banking.qrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public double getBalance(){
        return this.balance;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("The funds cannot be deposited in this account");
        }

        if (amount <= 0) {
            throw new IllegalStateException("The funds of money cannot zero or less than zero");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());

    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();

    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("the account bank is closed");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build()
        );
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();

    }

    public void clouseAccount() {
        if (!this.active) {
            throw new IllegalStateException("the account is already closed");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());

    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

}
