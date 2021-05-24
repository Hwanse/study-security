package practice.studysecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.studysecurity.domain.Account;
import practice.studysecurity.repository.UserRepository;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;

  @Transactional
  @Override
  public void createUser(Account account) {
    userRepository.save(account);
  }

}
