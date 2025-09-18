package com.sanitas.recuerdame.medications.services;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.medications.mapper.MedicationMapper;
import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;
import com.sanitas.recuerdame.medications.repository.MedicationRepository;
import com.sanitas.recuerdame.shared.IntakeSlot;
import com.sanitas.recuerdame.shared.exceptions.MedicationAlreadyExistsException;
import com.sanitas.recuerdame.shared.exceptions.MedicationNotFoundException;
import com.sanitas.recuerdame.shared.exceptions.UserNotFoundException;
import com.sanitas.recuerdame.user.User;
import com.sanitas.recuerdame.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService{

    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;

    @Override
    public List<MedicationResponse> getMedicationsByUserId(Long userId) {
        List<Medication> medications = medicationRepository.findByUserId(userId);

        return medications.stream()
                .map(MedicationMapper::entityToDto)
                .toList();
    }

    @Override
    public MedicationResponse getMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException("Medication not found with id: " + id));

        return MedicationMapper.entityToDto(medication);
    }

    @Override
    public MedicationResponse addMedication(MedicationRequest request) {
        User demoUser = userRepository.findByUsername("Ana")
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean exists = medicationRepository.existsByNameAndDoseAndUserAndStartDateAndEndDate(
                request.name(),
                request.dose(),
                demoUser,
                request.startDate(),
                request.endDate()
        );

        if (exists) {
            throw new MedicationAlreadyExistsException("Ya existe un tratamiento idéntico registrado");
        }

        Medication medication = MedicationMapper.dtoToEntity(request);
        medication.setUser(demoUser);

        List<Intake> intakes = new ArrayList<>();
        LocalDate date = request.startDate();
        while (!date.isAfter(request.endDate())) {
            for (IntakeSlot slot : request.intakeSlots()) {
                Intake intake = Intake.builder()
                        .medication(medication)
                        .date(date)
                        .slot(slot)
                        .status(Status.PENDING)
                        .build();
                intakes.add(intake);
            }
            date = date.plusDays(1);
        }
        medication.setIntakes(intakes);

        Medication savedMedication = medicationRepository.save(medication);

        return MedicationMapper.entityToDto(savedMedication);
    }

    @Override
    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}
