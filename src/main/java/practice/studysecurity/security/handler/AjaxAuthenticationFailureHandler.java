package practice.studysecurity.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  public AjaxAuthenticationFailureHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException e) throws IOException, ServletException {

    String errMsg = "Invalid Username Or Password";

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
      errMsg = "Invalid Username or Password";
    } else if (e instanceof DisabledException) {
      errMsg = "Locked";
    } else if (e instanceof CredentialsExpiredException) {
      errMsg = "Expired password";
    }

    objectMapper.writeValue(response.getWriter(), errMsg);
  }
}
