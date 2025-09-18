package com.sanitas.recuerdame.medications;

import com.sanitas.recuerdame.intake.Intake;
import com.sanitas.recuerdame.shared.IntakeSlot;
import com.sanitas.recuerdame.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String dose;

    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection(targetClass = IntakeSlot.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "medication_slots",
            joinColumns = @JoinColumn(name = "medication_id")
    )
    @Column(name = "slot")
    private List<IntakeSlot> intakeSlots;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Intake> intakes;
}
