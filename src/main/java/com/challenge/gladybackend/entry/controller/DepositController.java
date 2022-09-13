package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.dto.DepositDTO;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.mapper.DepositMapper;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.entry.validator.CreateDepositValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppInvalidActionException;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.service.DepositService;
import com.challenge.gladybackend.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("${app.api.root-path}/deposits")
public class DepositController {

    private final DepositService service;

    private final CreateDepositValidator createDepositValidator;

    private final IdValidator idValidator;

    @Autowired
    public DepositController(DepositService depositService, CreateDepositValidator createDepositValidator, IdValidator idValidator) {
        this.service = depositService;
        this.createDepositValidator = createDepositValidator;
        this.idValidator = idValidator;
    }

    /**
     * Get one deposit
     *
     * @param id Deposit ID
     * @return The deposit
     * @throws AppValidatorException ID value is invalid
     * @throws AppNotFoundException  The deposit is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepositDTO> get(@PathVariable int id) throws AppValidatorException, AppNotFoundException {
        log.info("Call get deposit: id={}", id);
        // Check id
        idValidator.isValidOrThrow(id);
        // Get the deposit and return DTO
        Deposit deposit = service.get(id);
        return ResponseUtils.response(DepositMapper.toDTO(deposit));
    }

    /**
     * Create a new deposit
     *
     * @param request The request with the data to create the new deposit
     * @return The new deposit
     * @throws AppValidatorException     Request is invalid
     * @throws AppInvalidActionException Company don't have enough money to make the deposit
     * @throws AppNotFoundException      Employee associated with the deposit is not found
     */
    @PostMapping
    public ResponseEntity<DepositDTO> create(@RequestBody CreateDepositRequest request)
        throws AppValidatorException, AppInvalidActionException, AppNotFoundException {
        log.info("Call create deposit: request={}", request);
        // Check request
        createDepositValidator.isValidOrThrow(request);
        // Create new deposit and return DTO
        Deposit deposit = service.create(request);
        return ResponseUtils.response(DepositMapper.toDTO(deposit));
    }

}
