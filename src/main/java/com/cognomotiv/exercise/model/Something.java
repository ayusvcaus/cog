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
@Table(name="SOMETHING", uniqueConstraints={
		@UniqueConstraint(columnNames="ID") })
public class Something implements java.io.Serializable {
	
	private static final long serialVersionUID=1L;
	
	private static final Logger s_logger=LogManager.getLogger(Something.class);
	
	private Integer id;
	private String name;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="ID", unique=true, nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id=id;
	}

	@Column(name="NAME", nullable=false, length=16)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
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