package com.testsaludtools.web.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false)
	private String color;
	
	@Column(nullable = false, length = 2)
	private int duration;
	
	@Column(nullable = false)
	private boolean active;
	
	private Date creationDate;
	
	private Date lastEdition;

	public void update(String name, String description, String color, int duration, boolean active) {
		this.name = name;
		this.description = description;
		this.color = color;
		this.duration = duration;
		this.active = active;
		this.lastEdition = new Date();
	}
	

	
}
