package practice.studysecurity.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Account {

  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String password;

  private String email;

  private String age;

  private String role;

  public Account(String username, String password) {
    this(username, password, "ROLE_USER");
  }

  public Account(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Account() {
  }
}
