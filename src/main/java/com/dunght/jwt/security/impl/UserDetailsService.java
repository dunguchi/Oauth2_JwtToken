package com.dunght.jwt.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dunght.jwt.entity.Role;
import com.dunght.jwt.entity.User;
import com.dunght.jwt.repository.UserRepository;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		User userFromDatabase;
		userFromDatabase = userRepository.findByUsername(login);

		if (userFromDatabase == null) {
			throw new UsernameNotFoundException("User " + login + " was not found in the database");
		}
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Role role : userFromDatabase.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			grantedAuthorities.add(grantedAuthority);
		}
		return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(),
				userFromDatabase.getPassword(),
				userFromDatabase.isEnabled(),
				!userFromDatabase.isExpired(),
				!userFromDatabase.isCredentialsexpired(),
				!userFromDatabase.isLocked() ,grantedAuthorities);
	}
	
	private final static class UserRepositoryUserDetails extends User implements UserDetails {
		

        private static final long serialVersionUID = 1L;



        private UserRepositoryUserDetails(User user) {
            super();
        }
        
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            for (Role role : getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return grantedAuthorities;
        }

        @Override
        public String getUsername() {
            return getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return !isExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return !isLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return !isCredentialsexpired();
        }

        @Override
        public boolean isEnabled() {
            return isEnabled();
        }

        @Override
        public Set<Role> getRoles() {
            return getRoles();
        }
		
	}
}
