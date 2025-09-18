package com.sanitas.recuerdame.intake.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.intake.service.IntakeService;

@RestController
@RequestMapping(path = "${api-endpoint}/intakes")
public class IntakeController {

  private final IntakeService<IntakeResponse, IntakeRequest> service;

  public IntakeController(IntakeService<IntakeResponse, IntakeRequest> service) {
    this.service = service;
  }

  @GetMapping()
  public List<IntakeResponse> index() {
    return service.getAllIntakes();
  }

  @GetMapping("/{id}")
  public ResponseEntity<IntakeResponse> singleIntake(@PathVariable("id") Long id) {
    IntakeResponse intake = service.getIntakeById(id);
    return ResponseEntity.ok(intake);
  }

  @GetMapping("/today")
  public List<IntakeResponse> todayIntake() {
    return service.getTodayIntakes();
  }

  @GetMapping("/medication/{medicationId}")
  public List<IntakeResponse> getIntakeByMedication(@PathVariable("medicationId") Long medicationId) {
    return service.getIntakesByMedication(medicationId);
  }

  @PatchMapping("/{id}/{status}")
  public ResponseEntity<IntakeResponse> updateStatus(
      @PathVariable("id") Long id,
      @PathVariable("status") Status status) {

    IntakeResponse updated = service.updateIntakeStatus(id, status);
    return ResponseEntity.ok(updated);
  };

}
