package com.sanitas.recuerdame.intake.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.IntakeRepository;
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
}
