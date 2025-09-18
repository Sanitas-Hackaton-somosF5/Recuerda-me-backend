package com.sanitas.recuerdame.medications.mapper;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;

public class MedicationMapper {

    public static Medication dtoToEntity(MedicationRequest request) {
        return Medication.builder()
                .name(request.name())
                .dose(request.dose())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .intakeSlots(request.intakeSlots())
                .build();
    }

    public static MedicationResponse entityToDto(Medication medication) {
        return new MedicationResponse(
                medication.getId(),
                medication.getName(),
                medication.getDescription(),
                medication.getDose(),
                medication.getStartDate(),
                medication.getEndDate(),
                medication.getIntakeSlots());
    }
}
