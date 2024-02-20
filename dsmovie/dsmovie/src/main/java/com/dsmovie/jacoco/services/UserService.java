package com.dsmovie.jacoco.services;

import com.dsmovie.jacoco.entities.RoleEntity;
import com.dsmovie.jacoco.entities.UserEntity;
import com.dsmovie.jacoco.projections.UserDetailsProjection;
import com.dsmovie.jacoco.repositories.UserRepository;
import com.dsmovie.jacoco.utils.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dsmovie.jacoco.constants.Constants.EMAIL_NAO_ENCONTRADO;
import static com.dsmovie.jacoco.constants.Constants.USUARIO_INVALIDO;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CustomUserUtil userUtil;

	public UserEntity authenticated() {
		try {
			String username = userUtil.getLoggedUsername();
			return repository.findByUsername(username).get();
		}
		catch (Exception e) {
			throw new UsernameNotFoundException(USUARIO_INVALIDO);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> result = repository.searchUserAndRolesByUsername(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException(EMAIL_NAO_ENCONTRADO);
		}
		
		UserEntity user = new UserEntity();
		user.setUsername(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new RoleEntity(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}
}
