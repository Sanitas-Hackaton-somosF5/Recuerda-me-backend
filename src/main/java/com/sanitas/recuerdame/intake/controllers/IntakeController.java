package com.sanitas.recuerdame.intake.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "${api-endpoint}/intakes")
@RequiredArgsConstructor
public class IntakeController {

  private final IntakeService<IntakeResponse, IntakeRequest> service;

  @Operation(summary = "List all intakes", description = "Returns all intakes registered in the system.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "List of intakes successfully retrieved")
  })
  @GetMapping("")
  public List<IntakeResponse> index() {
    return service.getAllIntakes();
  }

  @Operation(summary = "Get intake by ID", description = "Returns the details of a specific intake by its unique identifier.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Intake found"),
      @ApiResponse(responseCode = "404", description = "Intake not found with the specified ID")
  })
  @GetMapping("/{id}")
  public ResponseEntity<IntakeResponse> singleIntake(@PathVariable("id") Long id) {
    IntakeResponse intake = service.getIntakeById(id);
    return ResponseEntity.ok(intake);
  }

  @Operation(summary = "Get today’s intakes", description = "Returns the intakes scheduled for the current day.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Today’s intakes successfully retrieved"),
      @ApiResponse(responseCode = "404", description = "No intakes found for today")
  })
  @GetMapping("/today")
  public List<IntakeResponse> todayIntake() {
    return service.getTodayIntakes();
  }

  @Operation(summary = "Get intakes by medication", description = "Returns all intakes associated with a specific medication.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "List of intakes successfully retrieved"),
      @ApiResponse(responseCode = "404", description = "No intakes found for the specified medication")
  })
  @GetMapping("/medication/{medicationId}")
  public List<IntakeResponse> getIntakeByMedication(@PathVariable("medicationId") Long medicationId) {
    return service.getIntakesByMedication(medicationId);
  }

  @Operation(summary = "Update intake status", description = "Updates the status of a specific intake (e.g., PENDING → TAKEN).")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Intake status successfully updated"),
      @ApiResponse(responseCode = "404", description = "Intake not found with the specified ID"),
      @ApiResponse(responseCode = "400", description = "Invalid status value provided")
  })
  @PatchMapping("/{id}/{status}")
  public ResponseEntity<IntakeResponse> updateStatus(
      @PathVariable("id") Long id,
      @PathVariable("status") Status status) {

    IntakeResponse updated = service.updateIntakeStatus(id, status);
    return ResponseEntity.ok(updated);
  }
}
