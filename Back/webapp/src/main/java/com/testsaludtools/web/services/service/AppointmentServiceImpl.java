package com.testsaludtools.web.services.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testsaludtools.web.domain.model.Appointment;
import com.testsaludtools.web.domain.repository.AppointmentRepository;
import com.testsaludtools.web.services.dto.AppointmentDto;
import com.testsaludtools.web.services.dto.PageAppointmentDto;

import org.apache.commons.lang3.StringUtils;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;
	private ModelMapper modelMapper;
	
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository,ModelMapper modelMapper) {
		super();
		this.appointmentRepository = appointmentRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Optional<AppointmentDto> findAppointmentById(Long appointmentId) {
		Optional<Appointment> optional = appointmentRepository.findById(appointmentId);
		return optional.isPresent() ?
				Optional.ofNullable(modelMapper.map(optional.get(), AppointmentDto.class)) :
					Optional.empty();	
	}
	
	@Override
	public PageAppointmentDto findAllAppointments(Integer pageNo, Integer pageSize, String sortBy) {
		 Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
         Page<Appointment> pagedResult = appointmentRepository.findAll(paging);
         if(pagedResult.hasContent()) {
        	 List<AppointmentDto> elements = mapList(pagedResult.getContent(),AppointmentDto.class);
             return new PageAppointmentDto(elements, pagedResult.getNumber(),
            		 pagedResult.getTotalElements(), pagedResult.getTotalPages());
         } else {
             return new PageAppointmentDto(new ArrayList<AppointmentDto>(), 0,0, 0);
         }
	}

	@Override
	public Optional<AppointmentDto> saveAppointment(AppointmentDto appointmentDto) throws IllegalArgumentException{
		checkNulls(appointmentDto);
		Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
		Optional<Appointment> newAppointment  = Optional.ofNullable(this.appointmentRepository.save(appointment));
		return newAppointment.isPresent() ?
				Optional.ofNullable(modelMapper.map(newAppointment.get(), AppointmentDto.class)) :
					Optional.empty();
	}

	@Override
	public void deleteAppointment(Long appointmentId) {
		this.appointmentRepository.deleteById(appointmentId);		
	}
	
	private void checkNulls(AppointmentDto appointmentDto) {
		
		if(Objects.isNull(appointmentDto.getName()) || !verifyString(appointmentDto.getName()) ) {
			throw new IllegalArgumentException("Name cannot be null or empty"); 
		}
		
		if(	Objects.isNull(appointmentDto.getColor()) ||  !verifyString(appointmentDto.getColor()) ) {
			throw new IllegalArgumentException("Color cannot be null or empty"); 
		}
		
		if( Objects.isNull(appointmentDto.getDuration())) {
			throw new IllegalArgumentException("Duration cannot be null or empty"); 
		}
		
		if( Objects.isNull(appointmentDto.isActive())) {
			throw new IllegalArgumentException("active cannot be null or empty"); 
		}
		
		if( Objects.isNull(appointmentDto.getCreationDate())) {
			throw new IllegalArgumentException("creation date cannot be null or empty"); 
		}
	}
	
	public boolean verifyString(String data) {
		if(StringUtils.isEmpty(data)) return false;
		if(data.isBlank() || (data.length() < 2)) return false;
		return true;
	}
	
	public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		ModelMapper modelMapper = new ModelMapper();
		try {
			return source.stream().map(element -> modelMapper.map(element, targetClass))
					.collect(Collectors.toList());	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
