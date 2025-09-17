package com.sanitas.recuerdame.intake;

import java.time.LocalDateTime;

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
public class Intake {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "scheduled_time", nullable = false)
  private LocalDateTime scheduledTime;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private StatusEnum status = StatusEnum.PENDING;

  @ManyToOne
  @JoinColumn(name = "medicine_id")
  private Medication medication;

}