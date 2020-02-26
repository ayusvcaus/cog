package com.cognomotiv.exercise.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognomotiv.exercise.dto.ResponseObject;
import com.cognomotiv.exercise.service.SomethingService;
import com.cognomotiv.exercise.service.UserService;
import com.cognomotiv.exercise.model.Something;
import com.cognomotiv.exercise.auth.JwtToken;

@Controller
@RequestMapping("/v1")
public class SomethingController {
	
	private static final Logger s_logger=LogManager.getLogger(SomethingController.class);
	
	private final String INVALID_USERNAME_PASSWORD_TOKEN="username or password, token are invalid.";
	private final String INVALID_ID="id is invalid.";
	private final String INVALID_ID_NAME="id and/or name are invalid.";

	@Autowired
	private SomethingService somethingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtToken jTk;

	@RequestMapping(value="/something", method=RequestMethod.POST)
	public ResponseEntity<ResponseObject> readNCreateRecord(@RequestBody(required=false) final Map<String, Object> payload,			
			@RequestHeader(value="Authorization", required=false) final String originToken,
			@RequestHeader(value="username", required=true) final String username,
			@RequestHeader(value="password", required=false) final String password) throws Exception {
		ResponseObject obj=new ResponseObject();
		String[] token= {originToken};
		if (jTk.verifyToken(username, token) || userService.exists(username, password)) {
			obj.setToken(token[0]);
			String name=null;
			String id=null;
			if (payload!=null) {
			    if (payload.containsKey("name")) {
				    name=(String)payload.get("name");
			    }
			    if (payload.containsKey("id")) {
				    id=""+payload.get("id");
			    }
			}    
			s_logger.info("name="+name+ "  id="+id);
			if (name==null && id==null) { // case: read all, use method POST for security
				try {
					List<Something> names=somethingService.read();
					String succ="Reading all records are successful.";
					s_logger.info(succ);
					obj.setMessage(succ);
					obj.setData(names);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.OK);
				} catch (Exception e) {
					String err="Reading records failed. Err="+e.getMessage();
					s_logger.info(err);
					obj.setMessage(err);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else if (name==null && id!=null) { // case: read name with id provided
				try {
					Something st=somethingService.read(id);
					String succ="The record with id="+id+" was read.";
					s_logger.info(succ);
					obj.setMessage(succ);
					obj.setData(st);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.OK);
				} catch (Exception e) {
					String err="Reading record with id="+id+" not found. Err="+e.getMessage();
					s_logger.info(err);
					obj.setMessage(err);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else if (name!=null && id==null) {
				// case: create
				try {
					somethingService.create(name);
					String succ="The record with name="+name+" has been created";
					s_logger.info(succ);
					obj.setMessage(succ);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.CREATED);
				} catch (Exception e) {
					String err="Creating record with name="+name+" failed. Err="+e.getMessage();
					s_logger.info(err);
					obj.setMessage(err);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			obj.setMessage("Confusing inputs, both id and name are provided.");
			return new ResponseEntity<ResponseObject>(obj, HttpStatus.BAD_REQUEST);
		}
		s_logger.info(INVALID_USERNAME_PASSWORD_TOKEN);
		obj.setMessage(INVALID_USERNAME_PASSWORD_TOKEN);
		return new ResponseEntity<ResponseObject>(obj, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/something", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseObject> RemoveRecord(@RequestBody(required=false) final Map<String, Object> payload,			
			@RequestHeader(value="Authorization", required=false) final String originToken,
			@RequestHeader(value="username", required=true) final String username,
			@RequestHeader(value="password", required=false) final String password) throws Exception {
		ResponseObject obj=new ResponseObject();
		String[] token= {originToken};
		if (jTk.verifyToken(username, token) || userService.exists(username, password)) {
			obj.setToken(token[0]);
			String id=null;
			if (payload!=null) {
			    if (payload.containsKey("id")) {
				    id=""+payload.get("id");
			    }
			}    
			s_logger.info("id="+id);
			if (id!=null) { 
				try {
					somethingService.remove(id);
					String succ="The record with id="+id+" has been removed";
					s_logger.info(succ);
					obj.setMessage(succ);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.OK);
				} catch (Exception e) {
					String err="Removing record with id="+id+" failed. Err="+e.getMessage();
					s_logger.info(err);
					obj.setMessage(err);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			s_logger.info(INVALID_ID);
			obj.setMessage(INVALID_ID);
			return new ResponseEntity<ResponseObject>(obj, HttpStatus.BAD_REQUEST);
		}
		s_logger.info(INVALID_USERNAME_PASSWORD_TOKEN);
		obj.setMessage(INVALID_USERNAME_PASSWORD_TOKEN);
		return new ResponseEntity<ResponseObject>(obj, HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value="/something", method=RequestMethod.PUT)
	public ResponseEntity<ResponseObject> updateReocrd(@RequestBody(required=false) final Map<String, Object> payload,			
			@RequestHeader(value="Authorization", required=false) final String originToken,
			@RequestHeader(value="username", required=true) final String username,
			@RequestHeader(value="password", required=false) final String password) throws Exception {
		ResponseObject obj=new ResponseObject();
		String[] token= {originToken};
		if (jTk.verifyToken(username, token) || userService.exists(username, password)) {
			obj.setToken(token[0]);
			String name=null;
			String id=null;
			if (payload!=null) {
			    if (payload.containsKey("name")) {
				    name=(String)payload.get("name");
			    }
			    if (payload.containsKey("id")) {
				    id=""+payload.get("id");
			    }
			}    
			s_logger.info("name="+name+ "  id="+id);
			if (name!=null && id!=null) { 
				try {
					somethingService.update(name, id);
					String succ="The record with id="+id+" and name="+name+" has been updated";
					s_logger.info(succ);
					obj.setMessage(succ);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.OK);
				} catch (Exception e) {
					String err="Updating record with id="+id+" and name="+name+" failed. Err="+e.getMessage();
					s_logger.info(err);
					obj.setMessage(err);
					return new ResponseEntity<ResponseObject>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			s_logger.info(INVALID_ID_NAME);
			obj.setMessage(INVALID_ID_NAME);
			return new ResponseEntity<ResponseObject>(obj, HttpStatus.BAD_REQUEST);
		}
		s_logger.info(INVALID_USERNAME_PASSWORD_TOKEN);
		obj.setMessage(INVALID_USERNAME_PASSWORD_TOKEN);
		return new ResponseEntity<ResponseObject>(obj, HttpStatus.FORBIDDEN);
	}
}
