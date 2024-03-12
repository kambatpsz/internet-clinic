package pl.kambat.infrastucture.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@EqualsAndHashCode(of = "patientVisitId")
@ToString(of = {"dateTimeVisit", "doctor", "patient", "visitNote"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_visit")
public class PatientVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_visit_id")
    private Integer patientVisitId;

    @Column(name = "date_time_visit")
    private LocalDateTime dateTimeVisit;
    @Column(name = "visit_status")
    private Boolean visitStatus;
    @Column(name = "visit_note")
    private String visitNote;
    @Column(name = "date_of_cancellation")
    private LocalDateTime dateOfCancellation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
}
