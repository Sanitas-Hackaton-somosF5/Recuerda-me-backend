package com.sanitas.recuerdame.medications.services;

import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;

import java.util.List;

public interface MedicationService {

    List<MedicationResponse> getMedicationsByUserId(Long userId);
    MedicationResponse getMedicationById(Long id);
    MedicationResponse addMedication(MedicationRequest request);
    void deleteMedication(Long id);
}
