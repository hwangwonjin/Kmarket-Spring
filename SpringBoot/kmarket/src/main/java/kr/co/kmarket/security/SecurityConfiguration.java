package kr.co.kmarket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private SecurityUserService service;

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/", "/index").permitAll(); // 모든 자원에 대해서 모든 사용자 접근 허용
		http.authorizeHttpRequests().antMatchers("/admin/*").hasAnyRole("2");
		http.authorizeHttpRequests().antMatchers("/write", "/view", "/modify").hasAnyRole("3", "4", "5"); 
		
		// 사이트 위변조 요청 방지
		http.csrf().disable();


		// 로그인 설정
		http.formLogin()
		.loginPage("/member/login")
		.defaultSuccessUrl("/index")
		.failureUrl("/member/login?success=111")
		.usernameParameter("uid")
		.passwordParameter("pass");

		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")).deleteCookies("JSESSIONID", "autoUid")
		.logoutSuccessUrl("/member/login?success=200");
		
		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);

		// 2023/02/11 이원정
		// 로그인 유지가 되지 않아 토큰 저장소 및 DB persistent_logins 테이블 추가했습니다.
		// 로그인 시 검사 - 애플리케이션 - 쿠키 - 'remember-me'라는 이름으로 토큰이 등록되고,
		// DB에 현재 로그인 토큰 정보가 저장됩니다.
		// 로그인 유지
		http.rememberMe()
		.rememberMeParameter("rememberMe")		// html checkbox name에 해당하는 값 (default: remember-me)
		.tokenValiditySeconds(86400*30)			// 한 달 (default: 14일)
		.alwaysRemember(false)					// 체크 박스 항상 실행 -> (default: false)
		.userDetailsService(service)			// 사용자 계정 조회 처리 설정 api
		.tokenRepository(tokenRepository());
		
		return http.build();
	}
	
	@Bean
    public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }

	// 로그인 유지 - 토큰 저장소
	@Bean
	public PersistentTokenRepository tokenRepository(){
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();	// DB persistent_logins sql 구현체
		jdbcTokenRepository.setDataSource(dataSource);

		return jdbcTokenRepository;
	}

	// 파일 썸네일 출력
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/Users/iilhwan/Desktop/Workspace/Kmarket-Spring-/SpringBoot/file/");
	}

}
