package practice.studysecurity.controller;

public class AccountDto {

  private String username;

  private String password;

  private String email;

  private String age;

  private String role;

  public AccountDto() {
  }

  public AccountDto(String username, String password) {
    this(username, password, null, null, null);
  }

  public AccountDto(String username, String password, String email, String age, String role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.age = age;
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getAge() {
    return age;
  }

  public String getRole() {
    return role;
  }
}
