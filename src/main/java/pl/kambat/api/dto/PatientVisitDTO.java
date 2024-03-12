package pl.kambat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientVisitDTO {

    Integer patientVisitId;
    LocalDateTime dateTimeVisit;
    Boolean visitStatus;
    String visitNote;
    LocalDateTime dateOfCancellation;
    Doctor doctor;
    Patient patient;
}
