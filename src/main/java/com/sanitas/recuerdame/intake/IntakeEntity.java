package com.sanitas.recuerdame.intake;

import java.time.LocalDate;

import com.sanitas.recuerdame.medications.Medication;
import com.sanitas.recuerdame.shared.IntakeSlot;

import com.sanitas.recuerdame.medications.Medication;
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
public class IntakeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;

  @Enumerated(EnumType.STRING)
  private IntakeSlot slot;

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