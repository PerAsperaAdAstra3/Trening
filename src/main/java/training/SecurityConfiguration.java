package training;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("Salter").password("salter").roles("USER")
		.and()
		.withUser("Admin").password("admin").roles("USER", "ADMIN", "TRENER")
		.and()
		.withUser("Trener").password("trener").roles("TRENER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .authorizeRequests()
            .anyRequest().permitAll();
	/*	http.authorizeRequests()
		.antMatchers("/exerciseList/**").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/clientList").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/exerciseGroupList").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/trainingList/**").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/clientTrainingSubmit/**").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/getTraining/**").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/trainingCreationHandler/**").hasAnyRole("TRENER", "ADMIN")
		.antMatchers("/circularTrainingCreationHandler/**").hasAnyRole("TRENER", "ADMIN")
		.and().httpBasic();*/
	//	http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//		anyRequest().authenticated().and().httpBasic();
	}
}
