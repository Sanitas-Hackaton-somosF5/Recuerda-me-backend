package com.sanitas.recuerdame.medications.controller;

import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;
import com.sanitas.recuerdame.medications.services.MedicationServiceImpl;
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

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponse> getMedicationById(@PathVariable Long id) {
        MedicationResponse response = medicationService.getMedicationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MedicationResponse>> getMedicationsByUser(@PathVariable Long userId) {
        List<MedicationResponse> responses = medicationService.getMedicationsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MedicationResponse> addMedication(@RequestBody MedicationRequest request) {
        MedicationResponse response = medicationService.addMedication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}
