package practice.studysecurity.security.configs;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import practice.studysecurity.security.AjaxLoginProcessingFilter;

public final class AjaxLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
  AbstractAuthenticationFilterConfigurer<H, AjaxLoginConfigurer<H>, AjaxLoginProcessingFilter> {

  private AuthenticationManager authenticationManager;
  private AuthenticationSuccessHandler successHandler;
  private AuthenticationFailureHandler failureHandler;

  public AjaxLoginConfigurer() {
    super(new AjaxLoginProcessingFilter(), null);
  }

  /**
   * loginProcessingUrl Matcher 커스텀 구현
   */
  @Override
  protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
    return new AntPathRequestMatcher(loginProcessingUrl, "POST");
  }

  @Override
  public void init(H http) throws Exception {
    super.init(http);
  }

  @Override
  public void configure(H http) throws Exception {

    if (authenticationManager == null) {
      // 내부적으로 HttpSecurity 클래스에서 이미 AuthenticationManager 클래스 객체 정보를 공유객체로 저장하고있다.
      authenticationManager = http.getSharedObject(AuthenticationManager.class);
    }

    getAuthenticationFilter().setAuthenticationManager(authenticationManager);
    getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
    getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);

    SessionAuthenticationStrategy sessionAuthenticationStrategy = http
      .getSharedObject(SessionAuthenticationStrategy.class);
    if (sessionAuthenticationStrategy != null) {
      getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
    }

    RememberMeServices rememberMeServices = http
      .getSharedObject(RememberMeServices.class);
    if (rememberMeServices != null) {
      getAuthenticationFilter().setRememberMeServices(rememberMeServices);
    }

    http.setSharedObject(AjaxLoginProcessingFilter.class, getAuthenticationFilter());
    http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  public AjaxLoginConfigurer<H> successHandlerAjax(AuthenticationSuccessHandler authenticationSuccessHandler) {
    this.successHandler = authenticationSuccessHandler;
    return this;
  }

  public AjaxLoginConfigurer<H> failureHandlerAjax(AuthenticationFailureHandler authenticationFailureHandler) {
    this.failureHandler = authenticationFailureHandler;
    return this;
  }

  public AjaxLoginConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    return this;
  }

}
