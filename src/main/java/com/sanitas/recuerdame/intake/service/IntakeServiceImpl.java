package com.sanitas.recuerdame.intake.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.IntakeRepository;
import com.sanitas.recuerdame.intake.dtos.IntakeDTORequest;
import com.sanitas.recuerdame.intake.dtos.IntakeDTOResponse;
import com.sanitas.recuerdame.intake.mappers.IntakeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntakeServiceImpl implements InterfaceIntakeService<IntakeDTOResponse, IntakeDTORequest> {

  private final IntakeRepository intakeRepository;

  @Override
  public IntakeDTOResponse createIntake(IntakeDTORequest request) {
    Intake intake = IntakeMapper.toEntity(request, null);
    Intake saved = intakeRepository.save(intake);

    return IntakeMapper.toDTO(saved);
  }

  @Override
  public List<IntakeDTOResponse> getAllIntakes() {
    return intakeRepository.findAll()
        .stream()
        .map(IntakeMapper::toDTO)
        .toList();
  }

  @Override
  public IntakeDTOResponse getIntakeById(Long id) {
    Intake intake = intakeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Intake not found"));

    return IntakeMapper.toDTO(intake);
  }

  @Override
  public void deleteIntake(Long id) {
    intakeRepository.deleteById(id);
  }

  @Override
  public List<IntakeDTOResponse> getTodayIntakes() {
    LocalDate today = LocalDate.now();
    return intakeRepository.findByDate(today)
        .stream()
        .map(IntakeMapper::toDTO)
        .toList();
  }
}
