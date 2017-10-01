package com.money.spier.api.infrastructure.web.controllers;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.facades.ExpenseCreateFacade;
import com.money.spier.api.core.facades.ExpenseRetrieveFacade;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("prototype")
@RequestMapping("/expenses")
public class ExpenseController {

  @Autowired
  private ExpenseCreateFacade createFacade;

  @Autowired
  private ExpenseRetrieveFacade retrieveFacade;

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(
      @RequestBody Expense expense, @RequestParam("userName") String username) {

    //TODO: can we pass userName inside the body of request?

    expense.setCreationDate(LocalDateTime.now());
    createFacade.create(username, expense);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<Expense>> get(@RequestParam("userName") String username) {
    //TODO: should domain model be created to translate for example expense's user to username only?
    //or create response bean
    // because now I retrieve looped result
    return new ResponseEntity<>(retrieveFacade.retrieve(username), HttpStatus.OK);
  }

}
