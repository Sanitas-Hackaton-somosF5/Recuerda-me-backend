package com.sanitas.recuerdame.intake.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.dtos.IntakeDTORequest;
import com.sanitas.recuerdame.intake.dtos.IntakeDTOResponse;
import com.sanitas.recuerdame.intake.service.InterfaceIntakeService;

@RestController
@RequestMapping(path = "${api-endpoint}/intakes")
public class IntakeController {

  private final InterfaceIntakeService<IntakeDTOResponse, IntakeDTORequest> service;

  public IntakeController(InterfaceIntakeService<IntakeDTOResponse, IntakeDTORequest> service) {
    this.service = service;
  }

  @GetMapping("")
  public List<IntakeDTOResponse> index() {
    return service.getAllIntakes();
  }

  @GetMapping("/{id}")
  public ResponseEntity<IntakeDTOResponse> singleIntake(@PathVariable("id") Long id) {
    IntakeDTOResponse intake = service.getIntakeById(id);
    return ResponseEntity.ok(intake);
  }

  @PostMapping("")
  public ResponseEntity<IntakeDTOResponse> storeIntake(@RequestBody IntakeDTORequest dtoRequest) {
    if (dtoRequest == null) {
      return ResponseEntity.badRequest().build();
    }

    IntakeDTOResponse entityStored = service.createIntake(dtoRequest);

    if (entityStored == null) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.status(201).body(entityStored);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIntake(@PathVariable("id") Long id) {
    try {
      service.deleteIntake(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // TODO: Update and date path

  @GetMapping("/today")
  public List<IntakeDTOResponse> todayIntake() {
    return service.getTodayIntakes();
  }

  @GetMapping("/medication/{medicationid}")
  public List<IntakeDTOResponse> getIntakeByMedication(@PathVariable("medicationId") Long medicationId) {
    return service.getIntakesByMedication(medicationId);
  }

  @PutMapping("/{id}/{status}")
  public ResponseEntity<IntakeDTOResponse> updateStatus(
      @PathVariable("id") Long id,
      @PathVariable("status") Intake.StatusEnum status) {

    IntakeDTOResponse updated = service.updateIntakeStatus(id, status);
    return ResponseEntity.ok(updated);
  };

}
