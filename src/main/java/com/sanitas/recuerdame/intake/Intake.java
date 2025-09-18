package com.sanitas.recuerdame.intake;

import java.time.LocalDate;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "intakes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intake {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "intake_date", updatable = false)
  private LocalDate date;

  @Column(name = "slot", updatable = false)
  @Enumerated(EnumType.STRING)
  private IntakeSlot slot;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Status status = Status.PENDING;

  @ManyToOne
  @JoinColumn(name = "medication_id")
  private Medication medication;
}