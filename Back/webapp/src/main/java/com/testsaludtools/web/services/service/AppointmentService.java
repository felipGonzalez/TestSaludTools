package com.testsaludtools.web.services.service;

import java.util.List;
import java.util.Optional;

import com.testsaludtools.web.services.dto.AppointmentDto;
import com.testsaludtools.web.services.dto.PageAppointmentDto;

public interface AppointmentService {

	/**
	 * Returns a Appointment for the approved id.
	 * @param appointmentId id of the appointment
	 * @return Optional with appointment data or empty if no appointment is found for passed id
	 */
	public Optional<AppointmentDto> findAppointmentById(Long appointmentId);

	/**
	 * returns multiple sorted and paginated citation data
	 * @param pageNo page number to return
	 * @param pageSize size of page elements to return
	 * @param sortBy sort filter
	 * @return Optional with appointments data or empty if no appointment is found for passed id
	 */
	public PageAppointmentDto findAllAppointments(Integer pageNo, Integer pageSize, String sortBy);

	/**
	 * create new appointmet
	 * @param appointmentDto data of new appointment
	 * @return id of created appointment
	 * @throws DataNotNull
	 */
	public Optional<AppointmentDto> saveAppointment(AppointmentDto appointmentDto) throws IllegalArgumentException;
	
	
	/**
	 * update appointmet
	 * @param appointmentDto data of new appointment
	 * @return id of created appointment
	 * @throws DataNotNull
	 */
	public Optional<AppointmentDto> updateAppointment(AppointmentDto appointmentDto,Long appointmentId) throws IllegalArgumentException;
	
	/**
	 * Delete a Appointment for the approved id.
	 * @param appointmentId appointmentId id of the appointment
	 */
	public void deleteAppointment(Long appointmentId);
	
}
