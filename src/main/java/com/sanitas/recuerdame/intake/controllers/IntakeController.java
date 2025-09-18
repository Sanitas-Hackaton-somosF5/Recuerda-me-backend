package com.sanitas.recuerdame.intake.controllers;

import java.util.List;

import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.service.IntakeService;

@RestController
@RequestMapping(path = "${api-endpoint}/intakes")
public class IntakeController {

  private final IntakeService<IntakeResponse, IntakeRequest> service;

  public IntakeController(IntakeService<IntakeResponse, IntakeRequest> service) {
    this.service = service;
  }

  @GetMapping("")
  public List<IntakeResponse> index() {
    return service.getAllIntakes();
  }

  @GetMapping("/{id}")
  public ResponseEntity<IntakeResponse> singleIntake(@PathVariable("id") Long id) {
    IntakeResponse intake = service.getIntakeById(id);
    return ResponseEntity.ok(intake);
  }

  @PostMapping("")
  public ResponseEntity<IntakeResponse> storeIntake(@RequestBody IntakeRequest dtoRequest) {
    if (dtoRequest == null) {
      return ResponseEntity.badRequest().build();
    }

    IntakeResponse entityStored = service.createIntake(dtoRequest);

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
}
