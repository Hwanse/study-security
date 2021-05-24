package practice.studysecurity.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationRestController {

  @PostMapping("/api/login")
  public ResponseEntity login(@RequestBody AccountDto accountDto) {
    return ResponseEntity.ok().build();
  }

}
