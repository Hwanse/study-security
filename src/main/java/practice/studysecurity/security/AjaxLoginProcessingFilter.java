package practice.studysecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;
import practice.studysecurity.controller.AccountDto;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public AjaxLoginProcessingFilter() {
    // 아래 정의한 URL 매칭되는 요청이 들어오면 이 필터를 거친
    super(new AntPathRequestMatcher("/api/login"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    throws AuthenticationException, IOException, ServletException {

    if (!isAjax(httpServletRequest)) {
      throw new IllegalStateException("Authentication is not supported.");
    }

    AccountDto accountDto = objectMapper.readValue(httpServletRequest.getReader(), AccountDto.class);

    if (StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
      throw new IllegalArgumentException("username and password must be provided.");
    }

    AjaxAuthenticationToken token = new AjaxAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());
    return getAuthenticationManager().authenticate(token);
  }

  private boolean isAjax(HttpServletRequest request) {
    // 아래 헤더 확인 조건은 클라이언트와 컨벤션을 상의하여 결정하면 된다
    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
      return true;
    }
    return false;
  }
}
