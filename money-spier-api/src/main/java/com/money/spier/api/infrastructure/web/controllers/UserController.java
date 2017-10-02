package com.money.spier.api.infrastructure.web.controllers;

import com.money.spier.api.core.User;
import com.money.spier.api.core.services.UserService;
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
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService service;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<User> retrieve(@RequestParam("userName") String username) {
    User user = service.retrieve(username);
    return new ResponseEntity(user, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody User user) {
    service.create(user);
    return new ResponseEntity(HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@RequestParam("userName") String username) {
    service.delete(username);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  //TODO: implement full update
  @RequestMapping(method = RequestMethod.PATCH,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(
      @RequestParam("userName") String username, @RequestBody User user) {
    service.update(username, user);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
