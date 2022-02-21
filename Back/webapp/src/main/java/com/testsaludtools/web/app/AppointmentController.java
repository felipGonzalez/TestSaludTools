package com.testsaludtools.web.app;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testsaludtools.web.services.dto.AppointmentDto;
import com.testsaludtools.web.services.dto.PageAppointmentDto;
import com.testsaludtools.web.services.dto.ResponseAction;
import com.testsaludtools.web.services.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	private AppointmentService appointmentService;
	
	@Autowired(required = true)
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/{appointmentId}")
	public ResponseEntity<AppointmentDto> getBranch(@PathVariable Long appointmentId) {
		Optional<AppointmentDto> appointment = this.appointmentService.findAppointmentById(appointmentId);
		return appointment.isPresent() ?
				ResponseEntity.ok(appointment.get()) :
					ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/page")
	public ResponseEntity<PageAppointmentDto> getAllAppointment( 
			@RequestParam(defaultValue = "id") String orderBy,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size){
		try {
			PageAppointmentDto PageAppointmentDto = this.appointmentService.findAllAppointments(page, size, orderBy);
			return ResponseEntity.ok(PageAppointmentDto);
		} catch (Exception e) {
			e.printStackTrace();;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
			
	}
	
	@PostMapping
	public ResponseEntity<AppointmentDto> updateBranch(@RequestBody AppointmentDto appointmentDto) {
		Optional<AppointmentDto> newAppointment = Optional.empty();
		try {
			newAppointment = this.appointmentService.saveAppointment(appointmentDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return newAppointment.isPresent() ?
				ResponseEntity.ok(newAppointment.get()) :
					ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity<ResponseAction> deleteBranch(@PathVariable Long appointmentId) {
		ResponseAction action = new ResponseAction();
		action.setRemoved(false);
		try {
			this.appointmentService.deleteAppointment(appointmentId);
			action.setRemoved(true);
			action.setCompleted(true);
			action.setMessage("appointment removed");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok(action);
	}
	
	
	
	
}
