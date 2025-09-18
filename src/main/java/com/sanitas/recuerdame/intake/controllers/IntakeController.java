package com.sanitas.recuerdame.intake.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PatchMapping("/{id}/{status}")
  public ResponseEntity<IntakeResponse> updateStatus(
      @PathVariable("id") Long id,
      @PathVariable("status") Status status) {

    IntakeResponse updated = service.updateIntakeStatus(id, status);
    return ResponseEntity.ok(updated);
  };

}
