package com.banking.account.cmd;

import com.banking.account.cmd.api.command.*;
import com.banking.qrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Autowired
	private CommnadHandler commnadHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		commandDispatcher.registerHandler(OpenAccountCommand.class, commnadHandler::handle);
		commandDispatcher.registerHandler(DepostiFundsCommand.class, commnadHandler::handle);
		commandDispatcher.registerHandler(WithdrawFundsCommand.class, commnadHandler::handle);
		commandDispatcher.registerHandler(CloseAccountCommand.class, commnadHandler::handle);
	}
}
