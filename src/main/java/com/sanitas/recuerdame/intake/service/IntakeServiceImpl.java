package com.sanitas.recuerdame.intake.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.IntakeRepository;
import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.intake.mappers.IntakeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntakeServiceImpl implements IntakeService<IntakeResponse, IntakeRequest> {

  private final IntakeRepository intakeRepository;

  @Override
  public IntakeResponse createIntake(IntakeRequest request) {
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
        .orElseThrow(() -> new RuntimeException("Intake not found"));

    return IntakeMapper.entityToDTO(intake);
  }

  @Override
  public void deleteIntake(Long id) {
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
        .orElseThrow(() -> new RuntimeException("Intake not found"));

    intake.setStatus(status);
    Intake updated = intakeRepository.save(intake);

    return IntakeMapper.entityToDTO(updated);
  }
}
