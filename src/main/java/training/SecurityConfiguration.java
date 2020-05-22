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

import training.enumerations.Roles;
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
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/exerciseList/**").hasAnyAuthority(Roles.ADMIN.getNameText())
		.antMatchers("/clientList").hasAnyAuthority(Roles.ADMIN.getNameText(), Roles.TRAINER.getNameText(), Roles.FRONTDESK.getNameText())
		.antMatchers("/exerciseGroupList").hasAnyAuthority(Roles.ADMIN.getNameText())
		.antMatchers("/trainingList/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/operatorList").hasAnyAuthority(Roles.ADMIN.getNameText())
		.antMatchers("/packageList").hasAnyAuthority(Roles.ADMIN.getNameText())
		.antMatchers("/").hasAnyAuthority(Roles.ADMIN.getNameText(), Roles.FRONTDESK.getNameText(), Roles.TRAINER.getNameText())
		.antMatchers("/clientManagement").hasAnyAuthority(Roles.FRONTDESK.getNameText(), Roles.ADMIN.getNameText())
		
		//Rest of the pages
		.antMatchers("/clientTrainingSubmit/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/personalInfoManagementCtrl/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText(), Roles.FRONTDESK.getNameText())
		.antMatchers("/getTraining/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/trainingCreationHandler/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/circularTrainingCreationHandler/**").hasAnyAuthority(Roles.TRAINER.getNameText(), Roles.ADMIN.getNameText())
		//new
		.antMatchers("/deleteClientPackage/**").hasAnyAuthority(Roles.FRONTDESK.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/useUpAPackageElement/**").hasAnyAuthority(Roles.FRONTDESK.getNameText(), Roles.ADMIN.getNameText())
		.antMatchers("/changeClientPackageStatus/**").hasAnyAuthority(Roles.FRONTDESK.getNameText(), Roles.ADMIN.getNameText())
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
