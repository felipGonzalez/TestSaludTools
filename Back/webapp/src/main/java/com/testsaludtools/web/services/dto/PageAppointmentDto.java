package com.testsaludtools.web.services.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageAppointmentDto {
	
	private List<AppointmentDto> appointments;
	private int currentPage;
	private long totalItems;
	private int totalPages;
	
}
