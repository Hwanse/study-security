package practice.studysecurity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationRestController {

  @PostMapping("/api/login")
  public String login(@RequestBody AccountDto accountDto) {
    return "";
  }

}
