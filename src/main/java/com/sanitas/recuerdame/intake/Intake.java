package com.sanitas.recuerdame.intake;

import java.time.LocalDate;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @Column(name = "status", updatable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private StatusEnum status = StatusEnum.PENDING;

  @ManyToOne
  @JoinColumn(name = "medication_id")
  private Medication medication;

  public enum StatusEnum {
    PENDING,
    TAKEN,
    SKIPPED
  }
}