package com.money.spier.api.infrastructure.web.controllers;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.services.ExpenseService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

  @Autowired
  private ExpenseService service;

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(
      @RequestBody Expense expense, @RequestParam("userName") String username) {
    expense.setCreationDate(LocalDateTime.now());
    service.create(username, expense);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<Expense>> get(@RequestParam("userName") String username) {
    return new ResponseEntity<>(service.retrieve(username), HttpStatus.OK);
  }

}
