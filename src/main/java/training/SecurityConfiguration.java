package training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import training.service.OperatorDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

@Autowired
OperatorDetailsService operatorDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http//.csrf().disable()
		.authorizeRequests()
		.antMatchers("/exerciseList/**").hasAnyAuthority("ADMIN")
		.antMatchers("/clientList").hasAnyAuthority("TRENER", "ADMIN", "RECEPCIJA")
		.antMatchers("/exerciseGroupList").hasAnyAuthority("ADMIN")
		.antMatchers("/trainingList/**").hasAnyAuthority("TRENER", "ADMIN")
		.antMatchers("/operatorList").hasAnyAuthority("ADMIN")
		.antMatchers("/packageList").hasAnyAuthority("ADMIN")
		.antMatchers("/").hasAnyAuthority("ADMIN", "RECEPCIJA", "TRENER")
		.antMatchers("/clientManagement").hasAnyAuthority("RECEPCIJA", "ADMIN")
		
		//Rest of the pages
		.antMatchers("/clientTrainingSubmit/**").hasAnyAuthority("TRENER", "ADMIN")
		.antMatchers("/personalInfoManagementCtrl/**").hasAnyAuthority("TRENER", "ADMIN", "RECEPCIJA")
		.antMatchers("/getTraining/**").hasAnyAuthority("TRENER", "ADMIN")
		.antMatchers("/trainingCreationHandler/**").hasAnyAuthority("TRENER", "ADMIN")
		.antMatchers("/circularTrainingCreationHandler/**").hasAnyAuthority("TRENER", "ADMIN")
		.antMatchers("/sendPasswordToEmail").permitAll()
		.and()
		.formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout")
        .permitAll();
		/*and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and().httpBasic();
		*/

	/*	.and()
		.formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login");*/
//		.and().httpBasic();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(operatorDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	
	@Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
