package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.CloseAccountCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.qrs.core.exceptions.AggregateNotFoundException;
import com.banking.qrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseAccountController {
    private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {
        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Se cerro la cuenta bancaria exitosamente"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("El cliente envio un request invalido con errores {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {id} ", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
