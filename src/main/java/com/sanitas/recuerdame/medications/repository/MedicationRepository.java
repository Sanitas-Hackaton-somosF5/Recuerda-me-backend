package com.sanitas.recuerdame.medications.repository;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByUserId(Long userId);
    boolean existsByNameAndDoseAndUserAndStartDateAndEndDate(
            String name,
            String dose,
            User user,
            LocalDate startDate,
            LocalDate endDate
    );
}
