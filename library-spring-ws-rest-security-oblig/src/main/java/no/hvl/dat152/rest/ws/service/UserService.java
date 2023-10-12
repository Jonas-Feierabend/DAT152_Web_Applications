/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.model.Role;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		
		userRepository.deleteUser(id);
	}
	
	public User updateUser(User user, Long id) throws UserNotFoundException {
		
		// TODO
		//userRepository.updateUser(id,user.getFirstname(), user.getLastname(), user.getEmail(), user.getOrders(), user.getUserid()); 
		User user_obj = this.findUser(id); 
		user_obj.setFirstname(user.getFirstname());
		user_obj.setLastname(user.getLastname());
		user_obj.setEmail(user.getEmail());
		user_obj.setOrders(user.getOrders());
		user_obj.setPassword(user.getPassword());
		user_obj.setRoles(user.getRoles());
		user_obj.setUserid(user.getUserid());
		
		
		return this.saveUser(user_obj);
		
	}
	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
	
	public Set<Order> findOrdersForUser(Long id) throws UserNotFoundException{
		
		User user = findUser(id);
		
		return user.getOrders();
	}
	
	public User createOrdersForUser(Long id, Order order) throws UserNotFoundException{
		
		// get user
		User user = this.findUser(id); 
		//updte user
		user.addOrder(order);
		this.saveUser(user); 
		
		return user;
	}
	
	
}
