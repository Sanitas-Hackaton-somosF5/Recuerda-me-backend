package com.sanitas.recuerdame.intake;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntakeRepository extends JpaRepository<Intake, Long> {
  List<Intake> findByDate(LocalDate date);

  @Query("SELECT i FROM Intake i WHERE i.medication.id = :medicationId")
  List<Intake> findByMedication(@Param("medicationId") Long medicationId);
}
