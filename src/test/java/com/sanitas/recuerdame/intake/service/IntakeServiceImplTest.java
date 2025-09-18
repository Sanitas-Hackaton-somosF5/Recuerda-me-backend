package com.sanitas.recuerdame.intake.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.intake.Status;
import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.intake.repository.IntakeRepository;
import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;

@ExtendWith(MockitoExtension.class)
public class IntakeServiceImplTest {

  @InjectMocks
  private IntakeServiceImpl intakeService;

  @Mock
  private IntakeRepository intakeRepository;

  private Medication mockMedication;
  private Intake mockIntake;

  @BeforeEach
  void setUp() {
    mockMedication = Medication.builder()
        .id(1L)
        .name("Paracetamol")
        .description("Para el dolor y la fiebre")
        .dose("500mg")
        .build();

    mockIntake = Intake.builder()
        .id(1L)
        .date(LocalDate.of(2025, 9, 17))
        .slot(IntakeSlot.BREAKFAST)
        .status(Status.PENDING)
        .medication(mockMedication)
        .build();
  }

  @Test
  void createIntake_shouldSaveAndReturnDTO() {
    IntakeRequest request = new IntakeRequest(
        1L,
        LocalDate.of(2025, 9, 17),
        IntakeSlot.BREAKFAST);
    when(intakeRepository.save(any(Intake.class))).thenReturn(mockIntake);

    IntakeResponse response = intakeService.createIntake(request);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.medicineName()).isEqualTo("Paracetamol");
    assertThat(response.date()).isEqualTo(request.date());
    assertThat(response.slot()).isEqualTo(request.slot());
    verify(intakeRepository, times(1)).save(any(Intake.class));
  }

  @Test
  void getAllIntakes_shouldReturnListOfDTOs() {
    when(intakeRepository.findAll()).thenReturn(List.of(mockIntake));

    List<IntakeResponse> responses = intakeService.getAllIntakes();

    assertThat(responses).hasSize(1);
    assertThat(responses.get(0).medicineName()).isEqualTo("Paracetamol");
    verify(intakeRepository, times(1)).findAll();
  }

  @Test
  void getIntakeById_whenExists_shouldReturnDTO() {
    when(intakeRepository.findById(1L)).thenReturn(Optional.of(mockIntake));

    IntakeResponse response = intakeService.getIntakeById(1L);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.status()).isEqualTo(Status.PENDING);
    verify(intakeRepository, times(1)).findById(1L);
  }

  @Test
  void getIntakeById_whenNotExists_shouldThrow() {
    when(intakeRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> intakeService.getIntakeById(99L))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Intake not found");
  }

  @Test
  void deleteIntake_shouldCallRepository() {
    intakeService.deleteIntake(1L);

    verify(intakeRepository, times(1)).deleteById(1L);
  }

  @Test
  void getTodayIntakes_shouldReturnListOfDTOs() {
    when(intakeRepository.findByDate(LocalDate.now())).thenReturn(List.of(mockIntake));

    List<IntakeResponse> responses = intakeService.getTodayIntakes();

    assertThat(responses).hasSize(1);
    assertThat(responses.get(0).medicineName()).isEqualTo("Paracetamol");
    verify(intakeRepository).findByDate(LocalDate.now());
  }

  @Test
  void getIntakesByMedication_shouldReturnListOfDTOs() {
    when(intakeRepository.findByMedication(1L)).thenReturn(List.of(mockIntake));

    List<IntakeResponse> responses = intakeService.getIntakesByMedication(1L);

    assertThat(responses).hasSize(1);
    assertThat(responses.get(0).medicineName()).isEqualTo("Paracetamol");
    verify(intakeRepository).findByMedication(1L);
  }

  @Test
  void updateIntakeStatus_whenExists_shouldUpdateAndReturnDTO() {
    when(intakeRepository.findById(10L)).thenReturn(Optional.of(mockIntake));
    when(intakeRepository.saveAndFlush(any(Intake.class))).thenAnswer(inv -> inv.getArgument(0));

    IntakeResponse response = intakeService.updateIntakeStatus(10L, Status.TAKEN);

    assertThat(response.id()).isEqualTo(1L);
    assertThat(response.status()).isEqualTo(Status.TAKEN);
    verify(intakeRepository).saveAndFlush(mockIntake);
  }

  @Test
  void updateIntakeStatus_whenNotExists_shouldThrow() {
    when(intakeRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> intakeService.updateIntakeStatus(99L, Status.SKIPPED))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Intake not found");
  }
}
