package practice.studysecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import practice.studysecurity.security.common.AjaxLoginAuthenticationEntryPoint;
import practice.studysecurity.security.configs.AjaxLoginConfigurer;
import practice.studysecurity.security.handler.AjaxAccessDeniedHandler;
import practice.studysecurity.security.handler.AjaxAuthenticationFailureHandler;
import practice.studysecurity.security.handler.AjaxAuthenticationSuccessHandler;

/**
 * 시큐리티 설정 클래스 파일이 두 개 이상일 때 어떤 Configuration 이 먼저
 * 적용할지에 대한 순서를 정해주어야 한다. 순서를 정하지 않을 경우 Configuration 충돌이 일어난다
 */
@Order(0)
@EnableWebSecurity
@Slf4j
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {

  private final ObjectMapper objectMapper;

  public AjaxSecurityConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .antMatcher("/api/**")
      .authorizeRequests()
      .antMatchers("/api/messages").hasRole("MANAGER")
      .anyRequest().authenticated()
    .and()
      .exceptionHandling()
      .authenticationEntryPoint(ajaxLoginAuthenticationEntryPoint())
      .accessDeniedHandler(ajaxAccessDeniedHandler())
//    .and()
//      .addFilterAfter(ajaxLoginProcessingFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)
    ;

    http.csrf().disable();

    // ajaxLoginProcessingFilter 관련 설정부분을 DSL(도메인 특화 언어)로 재구성하여 설정하는 방식
    customConfigurerAjax(http);
  }

  private void customConfigurerAjax(HttpSecurity http) throws Exception {
    http
      .apply(new AjaxLoginConfigurer<>())
      .successHandlerAjax(authenticationSuccessHandler(objectMapper))
      .failureHandlerAjax(authenticationFailureHandler(objectMapper))
      .setAuthenticationManager(authenticationManagerBean())
      .loginProcessingUrl("/api/login");
  }


  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(ajaxAuthenticationProvider());
  }

  @Bean
  public AuthenticationProvider ajaxAuthenticationProvider() {
    return new AjaxAuthenticationProvider();
  }

  // 해당 필터에 AuthenticationManager 셋팅
  /*@Bean
  public AjaxLoginProcessingFilter ajaxLoginProcessingFilter(ObjectMapper objectMapper) throws Exception {
    AjaxLoginProcessingFilter ajaxLoginProcessingFilter = new AjaxLoginProcessingFilter();
    ajaxLoginProcessingFilter.setAuthenticationManager(authenticationManagerBean());
    ajaxLoginProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler(objectMapper));
    ajaxLoginProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler(objectMapper));
    return ajaxLoginProcessingFilter;
  }*/

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper) {
    return new AjaxAuthenticationSuccessHandler(objectMapper);
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper objectMapper) {
    return new AjaxAuthenticationFailureHandler(objectMapper);
  }

  @Bean
  public AccessDeniedHandler ajaxAccessDeniedHandler() {
    return new AjaxAccessDeniedHandler();
  }

  @Bean
  public AuthenticationEntryPoint ajaxLoginAuthenticationEntryPoint() {
    return new AjaxLoginAuthenticationEntryPoint();
  }

}
