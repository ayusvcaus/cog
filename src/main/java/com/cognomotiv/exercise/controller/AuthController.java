package com.cognomotiv.exercise.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognomotiv.exercise.dto.ResponseObject;
import com.cognomotiv.exercise.service.UserService;

import com.cognomotiv.exercise.auth.JwtToken;

@Controller
@RequestMapping("/v1")
public class AuthController {
	
	private static final Logger s_logger=LogManager.getLogger(AuthController.class);
	
	private final String INVALID_USERNAME_PASSWORD="username and/or password invalid.";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtToken jTk;
   
    @RequestMapping(value="/auth", method=RequestMethod.POST)
	public ResponseEntity<ResponseObject> login(
			@RequestHeader(value="username", required=true) final String username,
			@RequestHeader(value="password", required=true) final String password) throws Exception {
		ResponseObject obj=new ResponseObject();
		if (userService.exists(username, password)) {
			try {
			    String token=jTk.generate(username);
			    String msg="Token generated";
			    s_logger.info(msg+"="+token); 
			    obj.setMessage(msg);
			    obj.setToken(token);
			    return new ResponseEntity<ResponseObject>(obj, HttpStatus.CREATED);
			} catch (Exception e) {
				String err="Token generation failed, err="+e.getMessage();
				s_logger.info(err);
				obj.setMessage(err);
				return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		s_logger.info(INVALID_USERNAME_PASSWORD);
		obj.setMessage(INVALID_USERNAME_PASSWORD);
		return new ResponseEntity<ResponseObject>(obj, HttpStatus.FORBIDDEN);			
    }

}
