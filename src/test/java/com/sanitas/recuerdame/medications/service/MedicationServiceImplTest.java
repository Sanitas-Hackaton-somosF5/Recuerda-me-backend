package com.sanitas.recuerdame.medications.service;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.medications.dtos.MedicationRequest;
import com.sanitas.recuerdame.medications.dtos.MedicationResponse;
import com.sanitas.recuerdame.medications.mapper.MedicationMapper;
import com.sanitas.recuerdame.medications.repository.MedicationRepository;
import com.sanitas.recuerdame.medications.services.MedicationServiceImpl;
import com.sanitas.recuerdame.shared.IntakeSlot;
import com.sanitas.recuerdame.shared.exceptions.MedicationAlreadyExistsException;
import com.sanitas.recuerdame.shared.exceptions.MedicationNotFoundException;
import com.sanitas.recuerdame.shared.exceptions.UserNotFoundException;
import com.sanitas.recuerdame.user.entity.User;
import com.sanitas.recuerdame.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationServiceImplTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MedicationServiceImpl medicationService;

    private Medication med1;
    private Medication med2;
    private User demoUser;

    @BeforeEach
    void setUp() {
        demoUser = User.builder()
                .id(99L)
                .username("Ana")
                .build();

        med1 = Medication.builder()
                .id(1L)
                .name("Ibuprofeno")
                .dose("400mg")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .user(demoUser)
                .build();

        med2 = Medication.builder()
                .id(2L)
                .name("Paracetamol")
                .dose("500mg")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .user(demoUser)
                .build();
    }

    @Test
    void testGetMedicationsByUserId() {
        when(medicationRepository.findByUserId(99L)).thenReturn(List.of(med1, med2));

        try (MockedStatic<MedicationMapper> mapperMock = mockStatic(MedicationMapper.class)) {
            mapperMock.when(() -> MedicationMapper.entityToDto(any(Medication.class)))
                    .thenAnswer(invocation -> {
                        Medication m = invocation.getArgument(0);
                        return new MedicationResponse(
                                m.getName(),
                                m.getDescription(),
                                m.getDose(),
                                m.getStartDate(),
                                m.getEndDate(),
                                m.getIntakeSlots()
                        );
                    });

            List<MedicationResponse> responses = medicationService.getMedicationsByUserId(99L);

            assertEquals(2, responses.size());
            assertEquals("Ibuprofeno", responses.get(0).name());
            assertEquals("Paracetamol", responses.get(1).name());

            verify(medicationRepository).findByUserId(99L);
        }
    }

    @Test
    void testGetMedicationsByUserId_emptyList() {
        when(medicationRepository.findByUserId(99L)).thenReturn(List.of());

        List<MedicationResponse> responses = medicationService.getMedicationsByUserId(99L);

        assertTrue(responses.isEmpty());
        verify(medicationRepository).findByUserId(99L);
    }


    @Test
    void testGetMedicationById_found() {
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(med1));

        try (MockedStatic<MedicationMapper> mapperMock = mockStatic(MedicationMapper.class)) {
            mapperMock.when(() -> MedicationMapper.entityToDto(med1))
                    .thenReturn(new MedicationResponse("Ibuprofeno", null, "400mg",
                            med1.getStartDate(), med1.getEndDate(),
                            med1.getIntakeSlots()));

            MedicationResponse response = medicationService.getMedicationById(1L);

            assertEquals("Ibuprofeno", response.name());
            verify(medicationRepository).findById(1L);
        }
    }

    @Test
    void testGetMedicationById_notFound() {
        when(medicationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MedicationNotFoundException.class,
                () -> medicationService.getMedicationById(1L));

        verify(medicationRepository).findById(1L);
    }

    @Test
    void testAddMedication_success() {
        // Arrange
        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Dolor de cabeza",
                "600mg",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                List.of(IntakeSlot.BREAKFAST, IntakeSlot.DINNER)
        );

        User demoUser = new User();
        demoUser.setId(1L);
        demoUser.setUsername("Ana");

        when(userRepository.findByUsername("Ana")).thenReturn(Optional.of(demoUser));
        when(medicationRepository.existsByNameAndDoseAndUserAndStartDateAndEndDate(
                request.name(), request.dose(), demoUser, request.startDate(), request.endDate()
        )).thenReturn(false);

        Medication medicationEntity = MedicationMapper.dtoToEntity(request);
        medicationEntity.setUser(demoUser);

        when(medicationRepository.save(any(Medication.class))).thenAnswer(invocation -> {
            Medication med = invocation.getArgument(0);
            med.setId(1L);
            return med;
        });

        // Act
        MedicationResponse response = medicationService.addMedication(request);

        // Assert
        assertNotNull(response);
        assertEquals("Ibuprofeno", response.name());
        assertEquals("600mg", response.dose());
        verify(userRepository, times(1)).findByUsername("Ana");
        verify(medicationRepository, times(1)).save(any(Medication.class));
    }

    @Test
    void testAddMedication_userNotFound() {
        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Dolor",
                "400mg",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                List.of(IntakeSlot.BREAKFAST)
        );

        when(userRepository.findByUsername("Ana")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> medicationService.addMedication(request));

        verify(userRepository, times(1)).findByUsername("Ana");
        verify(medicationRepository, never()).save(any(Medication.class));
    }


    @Test
    void testAddMedication_alreadyExists() {
        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Para el dolor de cabeza",
                "400mg",
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                List.of(IntakeSlot.BREAKFAST)
        );


        when(userRepository.findByUsername("Ana")).thenReturn(Optional.of(demoUser));
        when(medicationRepository.existsByNameAndDoseAndUserAndStartDateAndEndDate(
                request.name(), request.dose(), demoUser, request.startDate(), request.endDate()))
                .thenReturn(true);

        assertThrows(MedicationAlreadyExistsException.class,
                () -> medicationService.addMedication(request));
    }

    @Test
    void testAddMedication_saveThrowsException() {
        MedicationRequest request = new MedicationRequest(
                "Ibuprofeno",
                "Dolor",
                "400mg",
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                List.of(IntakeSlot.BREAKFAST)
        );

        when(userRepository.findByUsername("Ana")).thenReturn(Optional.of(demoUser));
        when(medicationRepository.existsByNameAndDoseAndUserAndStartDateAndEndDate(
                any(), any(), any(), any(), any())).thenReturn(false);
        when(medicationRepository.save(any(Medication.class))).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> medicationService.addMedication(request));

        verify(medicationRepository).save(any(Medication.class));
    }

    @Test
    void testDeleteMedication() {
        medicationService.deleteMedication(1L);
        verify(medicationRepository).deleteById(1L);
    }

    @Test
    void testDeleteMedication_notFound() {
        doThrow(new RuntimeException("Not found")).when(medicationRepository).deleteById(99L);

        assertThrows(RuntimeException.class, () -> medicationService.deleteMedication(99L));
        verify(medicationRepository).deleteById(99L);
    }
}
