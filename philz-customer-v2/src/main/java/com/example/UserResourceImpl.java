package com.example;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResourceImpl {

	private static Logger log = LoggerFactory.getLogger(UserResourceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	// @Autowired
	// private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestBody User user) {
		JSONObject jsonObject = new JSONObject();
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			//user.setRole(roleRepository.findByName(ConstantUtils.USER.toString()));
			User savedUser = userRepository.save(user);
			jsonObject.put("message", savedUser.getName() + " saved succesfully");
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		} catch (JSONException e) {
			try {
				jsonObject.put("exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
		}
	}

	//login
	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authenticate(@RequestBody User user) {
		log.info("UserResourceImpl : authenticate");
		JSONObject jsonObject = new JSONObject();
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			if (authentication.isAuthenticated()) {
				String email = user.getEmail();
				jsonObject.put("name", authentication.getName());
				jsonObject.put("authorities", authentication.getAuthorities());
				jsonObject.put("token", tokenProvider.createToken(email, userRepository.findByEmail(email).getMobile()));
				return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
			}
		} catch (JSONException e) {
			try {
				jsonObject.put("exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
		}
		return null;
	}

	/**
	 * Resets password for the user provided that they enter their securityquestionanswercorrectly
	 * @param user the user to reset
	 * @param newPassword the new password to use
	 * @param securityQuestionAnswer the security question to answer. Technically this is a second password so maybe insecure. TOO BAD
	 * @return JSON response object. 
	 */
	@PostMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reset(@RequestParam String email ,@RequestParam String newPassword, @RequestParam String securityQuestionAnswer) {
		JSONObject jsonObject = new JSONObject(); 
		try{
			
			User user = this.userRepository.findByEmail(email); 

			if(user == null){
				throw new JSONException("User does not exist!"); 
			} 

			if(securityQuestionAnswer.toUpperCase().equals(user.getSecurityQuestionAnswer().toUpperCase())){
				user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
				this.userRepository.save(user); 
				jsonObject.put("message", "set new password for: " + user.getEmail() + " Successfully");
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
			}else{
				throw new JSONException("Invalid answer"); 
			}
		}catch(JSONException e){
			try {
				jsonObject.put("exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.UNAUTHORIZED);
		}
	}
}
