package com.banking.account.cmd.api.command;

import com.banking.qrs.core.commands.BaseCommand;

public class CloseAccountCommand extends BaseCommand {
    public CloseAccountCommand(String id) {
        super(id);
    }
}
