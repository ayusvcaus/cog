package com.cognomotiv.exercise.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="USER", uniqueConstraints={
		@UniqueConstraint(columnNames="USERNAME") })
public class User implements java.io.Serializable {
	
	private static final long serialVersionUID=1L;
	
	private static final Logger s_logger=LogManager.getLogger(User.class);
	
	private String username;
	private String password;

	@Id
	@Column(name="USERNAME", unique=true, nullable=false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	@Column(name="PASSWORD", nullable=false, length=16)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	@Override
	public String toString() {
		String s="";
		try {
			ObjectMapper mapper=new ObjectMapper();
		    s=mapper.writeValueAsString(this);
		} catch (Exception e) {
			s_logger.info("Convert to JSON string failed, err="+e.getMessage());
		}
		return s;
	}
}