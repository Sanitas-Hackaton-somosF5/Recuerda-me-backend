package com.sanitas.recuerdame.intake.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.sanitas.recuerdame.intake.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.sanitas.recuerdame.intake.dtos.IntakeRequest;
import com.sanitas.recuerdame.intake.dtos.IntakeResponse;
import com.sanitas.recuerdame.intake.service.IntakeService;
import com.sanitas.recuerdame.shared.IntakeSlot;

@ExtendWith(MockitoExtension.class)
class IntakeControllerTest {

  @InjectMocks
  private IntakeController controller;

  @Mock
  private IntakeService<IntakeResponse, IntakeRequest> service;

  private IntakeResponse mockResponse;

  @BeforeEach
  void setUp() {
    mockResponse = new IntakeResponse(
        1L,
        "Paracetamol",
        LocalDate.of(2025, 9, 17),
        IntakeSlot.BREAKFAST,
        Status.PENDING,
        "Take with water",
        "500mg");
  }

  @Test
  void testIndex_ShouldReturnAllIntakes() {
    when(service.getAllIntakes()).thenReturn(List.of(mockResponse));

    List<IntakeResponse> result = controller.index();

    assertThat(result.size(), is(equalTo(1)));
    assertThat(result.get(0).medication_name(), is(equalTo("Paracetamol")));
  }

  @Test
  void testIndex_ShouldReturnEmptyList() {
    when(service.getAllIntakes()).thenReturn(Collections.emptyList());

    List<IntakeResponse> result = controller.index();

    assertThat(result.size(), is(equalTo(0)));
  }

  @Test
  void testSingleIntake_ShouldReturnIntakeById() {
    when(service.getIntakeById(1L)).thenReturn(mockResponse);

    ResponseEntity<IntakeResponse> response = controller.singleIntake(1L);

    assertThat(response.getStatusCode().value(), is(equalTo(200)));
    assertThat(response.getBody().medication_name(), is(equalTo("Paracetamol")));
    assertThat(response.getBody().dose(), is(equalTo("500mg")));
  }

  @Test
  void testTodayIntake_ShouldReturnList() {
    when(service.getTodayIntakes()).thenReturn(List.of(mockResponse));

    List<IntakeResponse> result = controller.todayIntake();

    assertThat(result.size(), is(equalTo(1)));
    assertThat(result.get(0).status(), is(equalTo(Status.PENDING)));
  }

  @Test
  void testGetIntakeByMedication_ShouldReturnList() {
    when(service.getIntakesByMedication(1L)).thenReturn(List.of(mockResponse));

    List<IntakeResponse> result = controller.getIntakeByMedication(1L);

    assertThat(result.size(), is(equalTo(1)));
    assertThat(result.get(0).medication_name(), is(equalTo("Paracetamol")));
  }

  @Test
  void testUpdateStatus_ShouldReturnUpdatedIntake() {
    IntakeResponse updatedResponse = new IntakeResponse(
        1L,
        "Paracetamol",
        LocalDate.of(2025, 9, 17),
        IntakeSlot.BREAKFAST,
        Status.TAKEN,
        "Take with water",
        "500mg");

    when(service.updateIntakeStatus(1L, Status.TAKEN)).thenReturn(updatedResponse);

    ResponseEntity<IntakeResponse> response = controller.updateStatus(1L, Status.TAKEN);

    assertThat(response.getStatusCode().value(), is(equalTo(200)));
    assertThat(response.getBody().status(), is(equalTo(Status.TAKEN)));
  }
}
