package practice.studysecurity.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

  @GetMapping("/api/messages")
  public String messages() {
    return "messages ok";
  }

}
