package practice.studysecurity.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import practice.studysecurity.security.AjaxLoginProcessingFilter;

/**
 * EnableWebSecurity 애노테이션의 역할 - WebSecurityConfiguration.class - SpringWebMvcImportSelector.class -
 * OAuth2ImportSelector.class - HttpSecurityConfiguration.class 웹 보안 설정과 관련이 있는 클래스들을 활성화 시켜주는 역할을
 * 수행\
 */
@Order(1)
@EnableWebSecurity // 시큐리티 Config 설정을 하기위해선 해당 애노테이션도 추가해주어야한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationDetailsSource authenticationDetailsSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 인증 정책 설정
    http.formLogin()
//            .loginPage("/loginPage")            // 사용자 정의 로그인 페이지
//      .defaultSuccessUrl("/")             // 로그인 성공 후 이동할 페이지
//            .failureUrl("/login")               // 로그인 실패 후 이동할 페이지
      .usernameParameter("userId")        // form 데이터에서 아이디 파라미터명 설정
      .passwordParameter("passwd")        // form 데이터에서 패스워드 파라미터명 설정
      .loginProcessingUrl("/login_proc")  // 로그인 Form Action URL 설정
      /*.successHandler(new AuthenticationSuccessHandler() {
          // 로그인 성공 후 핸들러
          @Override
          public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse,
                                              Authentication authentication)
              throws IOException, ServletException {
              System.out.println("authentication : " + authentication.getName());
              httpServletResponse.sendRedirect("/");
          }
      }).failureHandler(new AuthenticationFailureHandler() {
          // 로그인 실패 후 핸들러
          @Override
          public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse,
                                              AuthenticationException e)
              throws IOException, ServletException {
              System.out.println("exception : " + e.getMessage());
              httpServletResponse.sendRedirect("/login");
          }
      })*/
    .disable();

    // 스프링 시큐리티에서 로그아웃 처리는 원칙적으로 post 방식을 이용을 한다
    // 그러나 Get 방식으로 로그아웃 처리 구현을 하는 방법도 있다.
    http.logout()
      .logoutUrl("/logout")   // 로그아웃 처리 url
      .logoutSuccessUrl("/login")
      .addLogoutHandler(new LogoutHandler() {
        // 로그아웃 처리 과정 중에 수행할 커스텀 핸들러 추가
        @Override
        public void logout(HttpServletRequest httpServletRequest,
          HttpServletResponse httpServletResponse,
          Authentication authentication) {

        }
      })
      .logoutSuccessHandler(new LogoutSuccessHandler() {
        // 로그아웃 성공 후 처리할 커스텀 핸들러
        @Override
        public void onLogoutSuccess(HttpServletRequest httpServletRequest,
          HttpServletResponse httpServletResponse,
          Authentication authentication)
          throws IOException, ServletException {

        }
      })
      .deleteCookies("remember-me");

    http.rememberMe()
      .rememberMeParameter("remember")    // form-data 의 파라미터명은 remember-me
      .tokenValiditySeconds(3600)         // 토큰 만료기한 설정 (default: 14일)
      .alwaysRemember(true)               // 리멤버 미 기능이 활성화 되지 않아도 항상 실행
      .userDetailsService(userDetailsService); // 유저 정보를 조회하는 과정
  }

}
