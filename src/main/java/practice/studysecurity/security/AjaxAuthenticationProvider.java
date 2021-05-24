package practice.studysecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PasswordEncoder passwordEncode;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String loginId = authentication.getName();
    String password = String.valueOf(authentication.getCredentials());

    AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);

    if (!passwordEncode.matches(password, accountContext.getPassword())) {
      throw new BadCredentialsException("Invalid Password.");
    }

    return new AjaxAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(AjaxAuthenticationToken.class);
  }

}
