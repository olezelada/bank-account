package com.banking.account.cmd.api.controllers;

import com.banking.account.cmd.api.command.DepostiFundsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.qrs.core.domain.BaseEntity;
import com.banking.qrs.core.exceptions.AggregateNotFoundException;
import com.banking.qrs.core.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "api/v1/depositFunds")
public class DepositFundsController {

    private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PutMapping(path="/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody DepostiFundsCommand command){
        try{

            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("El deposito de Dinero fuer exitoso"), HttpStatus.OK);

        }catch (IllegalStateException | AggregateNotFoundException e){
            logger.log(Level.WARNING, MessageFormat.format("El cliente envio un request invalido con errores {0}", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {id} ", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
