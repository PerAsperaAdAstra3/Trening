package training.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Operator;
import training.repository.OperatorRepository;

@Service("operatorDetailsService")
@Transactional
public class OperatorDetailsService implements UserDetailsService {

	@Autowired
	OperatorService operatorService;
	
	@Autowired
	private OperatorRepository operatorRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/*		 final String ip = getClientIP();
	        if (loginAttemptService.isBlocked(ip)) {
	            throw new RuntimeException("blocked");
	        }
*/
	        try {
	            final List<Operator> operator = operatorRepository.findByUserName(username);
	            if (operator == null) {
	                throw new UsernameNotFoundException("No user found with username: " + username);
	            }
	            List<String> roles = new ArrayList<String>();
	            roles.add(operator.get(0).getAuthorities());
	            
	            return new org.springframework.security.core.userdetails.User(operator.get(0).getUserName(), operator.get(0).getPassword(), true, true, true, true, getAuthorities(roles));
	        } catch (final Exception e) {
	            throw new RuntimeException(e);
	        }
	}

	 private final Collection<? extends GrantedAuthority> getAuthorities(List<String> roles){//final Collection<Role> roles) {
	        return getGrantedAuthorities(roles);
	    }
	
	 private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
	        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        for (final String privilege : privileges) {
	            authorities.add(new SimpleGrantedAuthority(privilege));
	        }
	        return authorities;
	    }
}
