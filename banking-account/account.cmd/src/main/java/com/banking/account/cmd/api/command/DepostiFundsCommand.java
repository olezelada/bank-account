package com.banking.account.cmd.api.command;

import com.banking.qrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepostiFundsCommand extends BaseCommand {
    private double amount;
}
