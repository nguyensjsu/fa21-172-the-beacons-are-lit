package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IService<User> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Collection<User> findAll() {
		List<User> allUsers = new ArrayList<User>(); 

		//Conversion to SQL from JPA therefore convert iterable to collection
		this.userRepository.findAll().forEach(allUsers::add);

		return allUsers; 
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public User saveOrUpdate(User user) {
		User test = this.userRepository.findByEmail(user.getEmail()); 
		if(test == null){
			System.out.println("Saving user" + user.getEmail()); 
		}else{
			System.out.println("Updating user" + user.getEmail()); 
		}

		return this.userRepository.save(user); //Crud repository auto saves/updates with .save()
	}

	@Override
	public String deleteById(Integer id) {
		JSONObject jsonObject = new JSONObject();
		try {
			userRepository.deleteById(id);
			jsonObject.put("message", "User deleted successfully");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

}