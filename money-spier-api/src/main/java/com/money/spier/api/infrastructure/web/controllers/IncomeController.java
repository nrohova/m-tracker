package com.money.spier.api.infrastructure.web.controllers;

import com.money.spier.api.core.entities.Income;
import com.money.spier.api.core.services.IncomeService;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

  @Autowired
  private IncomeService service;

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity create(
      @Valid @RequestBody Income income,
      @RequestParam(value = "user", required = false) String username) {
    income.setCreationDate(LocalDateTime.now());
    service.create(username, income);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Set<Income>> get(@RequestParam("user") String username) {
    return new ResponseEntity<>(service.retrieve(username), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@PathVariable(name = "id") String incomeId) {
    service.delete(incomeId);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
