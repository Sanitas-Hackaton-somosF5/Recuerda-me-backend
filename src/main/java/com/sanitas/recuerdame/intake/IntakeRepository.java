package com.sanitas.recuerdame.intake;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntakeRepository extends JpaRepository<Intake, Long> {
  List<Intake> findByDate(LocalDate date);

  List<Intake> findByMedication(Long medicationId);
}
