/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Role;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.RoleRepository;
import no.hvl.dat152.rest.ws.repository.UserRepository;

import no.hvl.dat152.rest.ws.service.UserService;
/**
 * @author tdoy
 */
@Service
public class AdminUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private UserService userService; 
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public User deleteUserRole(Long id, String role_string) throws UserNotFoundException {
		
		User user = this.findUser(id);
		Role role = new Role(role_string); 
		user.removeRole(role);
		userService.updateUser(user, id);
		return user; 
	}
	
	@Transactional
	public User updateUserRole(Long id, String role_string) throws UserNotFoundException {
		
		User user = this.findUser(id);
		Role role = new Role(role_string); 
		user.addRole(role);
		userService.updateUser(user, id); 
		return user; 
		
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
}
