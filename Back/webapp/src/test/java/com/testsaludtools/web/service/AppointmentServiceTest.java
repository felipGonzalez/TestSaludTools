package com.testsaludtools.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.testsaludtools.web.domain.model.Appointment;
import com.testsaludtools.web.domain.repository.AppointmentRepository;
import com.testsaludtools.web.services.dto.AppointmentDto;
import com.testsaludtools.web.services.dto.PageAppointmentDto;
import com.testsaludtools.web.services.service.AppointmentService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource("/application.properties")
@ActiveProfiles(value = "test")
public class AppointmentServiceTest {

	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Test
	public void shouldReturnCreatedAppointment() throws ParseException {
		Appointment appointment = new Appointment();
		appointment.setName("tipo1");
		appointment.setDescription("Descripcion tipo 1");
		appointment.setColor("FFFFFF");
		appointment.setActive(true);
		appointment.setDuration(20);
		appointmentRepository.save(appointment);
		
		Optional<AppointmentDto> appOptional = appointmentService.findAppointmentById(appointment.getId());
		
		assertEquals(true, appOptional.isPresent());
		AppointmentDto appointmentDto = appOptional.get(); 
		assertNotNull(appointmentDto);
        assertEquals(appointmentDto.getName(), "tipo1");
        assertEquals(appointmentDto.getDescription(), "Descripcion tipo 1");
        assertEquals(appointmentDto.getColor(), "FFFFFF");
        assertEquals(appointmentDto.isActive(), true);
        assertEquals(appointmentDto.getDuration(), 20);
   	}
	
	
	@Test
    public void shouldReturnNullForNotExistingAppointment() {
		Optional<AppointmentDto> appOptional = appointmentService.findAppointmentById(123L);
		assertEquals(false, appOptional.isPresent());
    }
	
	@Test 
	void returnAppointmentFindById() {
		// Elements created file import.sql
		Optional<AppointmentDto> appOptional = appointmentService.findAppointmentById(1L);
		assertEquals(true, appOptional.isPresent());
		AppointmentDto appointmentDto = appOptional.get(); 
		assertNotNull(appointmentDto);
		assertEquals(appointmentDto.getId(), 1L);
	}
	
	@Test 
	void returnAllAppointment() {
		// Elements created file import.sql
		Integer pageNo = 0;
		Integer pageSize = 2; 
		String sortBy = "id";
		
		PageAppointmentDto pageAppointmentDto = appointmentService.findAllAppointments(pageNo, pageSize, sortBy);
		assertEquals(pageAppointmentDto.getTotalPages(),2 );
		assertEquals(pageAppointmentDto.getTotalItems(), 4);
		assertEquals(pageAppointmentDto.getCurrentPage(), pageNo);
		assertEquals(pageAppointmentDto.getAppointments().get(0).getId(), 1L);
		
	}
	
	@Test 
	void deleteAppointment() {
		Appointment appointment = new Appointment();
		appointment.setName("tipo1");
		appointment.setDescription("Descripcion tipo 1");
		appointment.setColor("FFFFFF");
		appointment.setActive(true);
		appointment.setDuration(20);
		Date creationDate = new Date();
		appointment.setCreationDate(creationDate);
		appointmentRepository.save(appointment);
		Optional<AppointmentDto> appOptional = appointmentService.findAppointmentById(appointment.getId());
		assertEquals(true, appOptional.isPresent());
		
		Long id = appOptional.get().getId();
		appointmentService.deleteAppointment(id);
		
		Optional<AppointmentDto> appOptionalDeleted = appointmentService.findAppointmentById(id);
		assertEquals(false, appOptionalDeleted.isPresent());
		
	}
}
