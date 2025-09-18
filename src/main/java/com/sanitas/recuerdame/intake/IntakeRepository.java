package com.sanitas.recuerdame.intake;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sanitas.recuerdame.medications.Medication;

public interface IntakeRepository extends JpaRepository<Intake, Long> {
  List<Intake> findByDate(LocalDate date);

  List<Intake> findByMedication(Medication medication);
}
