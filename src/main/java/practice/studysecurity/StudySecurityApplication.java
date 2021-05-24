package practice.studysecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import practice.studysecurity.domain.Account;
import practice.studysecurity.repository.UserRepository;

@SpringBootApplication
public class StudySecurityApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudySecurityApplication(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(StudySecurityApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(new Account("user", passwordEncoder.encode("1111")));
        userRepository.save(new Account("manager", passwordEncoder.encode("1111"), "ROLE_MANAGER"));
    }

}
