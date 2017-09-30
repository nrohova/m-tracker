package com.money.spier.api.infrastructure.web.controllers;

import com.money.spier.api.core.User;
import com.money.spier.api.core.facades.UserCreateFacade;
import com.money.spier.api.core.facades.UserDeleteFacade;
import com.money.spier.api.core.facades.UserRetrieveFacade;
import com.money.spier.api.core.facades.UserUpdateFacade;
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
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserCreateFacade createFacade;

  @Autowired
  private UserDeleteFacade deleteFacade;

  @Autowired
  private UserUpdateFacade updateFacade;

  @Autowired
  private UserRetrieveFacade retrieveFacade;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<User> retrieve(@RequestParam("userName") String username) {
    User user = retrieveFacade.retrieve(username);
    return new ResponseEntity(user, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody User user) {
    createFacade.create(user);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@RequestParam("userName") String username) {
    deleteFacade.delete(username);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(method = RequestMethod.PATCH,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(
      @RequestParam("userName") String username, @RequestBody User user) {
    updateFacade.update(username, user);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
