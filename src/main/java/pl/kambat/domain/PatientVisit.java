package pl.kambat.domain;

import lombok.*;

import java.time.LocalDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "patientVisitId")
@ToString(of = {"patientVisitId", "dateTimeVisit", "patient", "doctor", "visitNote", "dateOfCancellation"})
public class PatientVisit {

    Integer patientVisitId;
    LocalDateTime dateTimeVisit;
    Boolean visitStatus;
    String visitNote;
    LocalDateTime dateOfCancellation;
    Doctor doctor;
    Patient patient;
}
