package com.testsaludtools.web.services.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.Data;

@Data
public class AppointmentDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private String color;

	private int duration;

	private boolean active;

	private Date creationDate;

	private Date lastEdition;
}
