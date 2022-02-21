package com.testsaludtools.web.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.testsaludtools.web.domain.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
