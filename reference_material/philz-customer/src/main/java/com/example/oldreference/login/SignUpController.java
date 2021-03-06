package com.example.oldreference.login;

import java.util.HashMap;

import com.example.oldreference.helper.ApiResponse;
import com.example.oldreference.user.User;
import com.example.oldreference.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/signup")
public class SignUpController {
	@Autowired
	UserService userservice;

	@RequestMapping("user")
	public ResponseEntity<?> userLogin(@RequestBody HashMap<String, String> signupRequest) {
		try {
			// TODO validation has to add for client request
			User user = userservice.signUpUser(signupRequest);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
		}
	}
}