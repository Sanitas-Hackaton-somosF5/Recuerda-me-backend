package com.sanitas.recuerdame.medications.dtos;

import com.sanitas.recuerdame.medications.Medication;

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
                medication.getName(),
                medication.getDescription(),
                medication.getDose(),
                medication.getStartDate(),
                medication.getEndDate(),
                medication.getIntakeSlots()
        );
    }
}
