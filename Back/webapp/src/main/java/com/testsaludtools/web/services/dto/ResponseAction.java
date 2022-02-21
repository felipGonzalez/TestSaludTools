package com.testsaludtools.web.services.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseAction implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean removed;
	private String message;
	private boolean completed;

}
