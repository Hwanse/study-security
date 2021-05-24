package practice.studysecurity.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public ResponseEntity home() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

}
