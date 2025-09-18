package com.sanitas.recuerdame.intake.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.intake.mappers.IntakeMapper;
import com.sanitas.recuerdame.intake.repository.IntakeRepository;
import com.sanitas.recuerdame.shared.exceptions.IntakeNotFoundException;
import com.sanitas.recuerdame.shared.exceptions.MedicationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntakeServiceImpl implements IntakeService<IntakeResponse, IntakeRequest> {

  private final IntakeRepository intakeRepository;

  @Override
  public IntakeResponse createIntake(IntakeRequest request) {
    if (request.medicationId() == null) {
      throw new MedicationNotFoundException("MedicationId is required to create an intake");
    }
    Intake intake = IntakeMapper.dtoToEntity(request, null);
    Intake saved = intakeRepository.save(intake);

    return IntakeMapper.entityToDTO(saved);
  }

  @Override
  public List<IntakeResponse> getAllIntakes() {
    return intakeRepository.findAll()
        .stream()
        .map(IntakeMapper::entityToDTO)
        .toList();
  }

  @Override
  public IntakeResponse getIntakeById(Long id) {
    Intake intake = intakeRepository.findById(id)
        .orElseThrow(() -> new IntakeNotFoundException("Intake with id " + id + " not found"));

    return IntakeMapper.entityToDTO(intake);
  }

  @Override
  public void deleteIntake(Long id) {
    if (!intakeRepository.existsById(id)) {
      throw new IntakeNotFoundException("Intake with id " + id + " not found");
    }
    intakeRepository.deleteById(id);
  }

  @Override
  public List<IntakeResponse> getTodayIntakes() {
    LocalDate today = LocalDate.now();
    return intakeRepository.findByDate(today)
        .stream()
        .map(IntakeMapper::entityToDTO)
        .toList();
  }

  @Override
  public List<IntakeResponse> getIntakesByMedication(Long medicationId) {
    return intakeRepository.findByMedication(medicationId)
        .stream()
        .map(IntakeMapper::entityToDTO)
        .toList();
  }

  @Override
  public IntakeResponse updateIntakeStatus(Long id, Status status) {
    Intake intake = intakeRepository.findById(id)
        .orElseThrow(() -> new IntakeNotFoundException("Intake with id " + id + " not found"));

    intake.setStatus(status);
    Intake updated = intakeRepository.saveAndFlush(intake);

    return IntakeMapper.entityToDTO(updated);
  }
}
