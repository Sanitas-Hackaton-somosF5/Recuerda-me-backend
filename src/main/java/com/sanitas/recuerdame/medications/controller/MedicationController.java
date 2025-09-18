package com.sanitas.recuerdame.medications.controller;

import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;
import com.sanitas.recuerdame.medications.services.MedicationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${api-endpoint}/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationServiceImpl medicationService;

    @Operation(summary = "Get medication by ID", description = "Returns the details of a medication given its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Medication found"),
            @ApiResponse(responseCode = "404", description = "Medication not found with the specified ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponse> getMedicationById(@PathVariable Long id) {
        MedicationResponse response = medicationService.getMedicationById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List medications for a user", description = "Returns all medications registered for a specific user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of medications successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No medications found for the specified user")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicationResponse>> getMedicationsByUser(@PathVariable Long userId) {
        List<MedicationResponse> responses = medicationService.getMedicationsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Register a new medication", description = "Registers a new medication with its treatment period and associated intakes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Medication successfully created"),
            @ApiResponse(responseCode = "400", description = "Medication already exists or provided data is invalid")
    })
    @PostMapping
    public ResponseEntity<MedicationResponse> addMedication(@RequestBody MedicationRequest request) {
        MedicationResponse response = medicationService.addMedication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete a medication", description = "Deletes a medication by its ID. All associated intakes will also be removed.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Medication successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Medication not found with the specified ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}
