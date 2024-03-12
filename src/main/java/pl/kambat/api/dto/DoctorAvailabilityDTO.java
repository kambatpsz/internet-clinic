package pl.kambat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kambat.domain.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityDTO {
    Integer doctorAvailabilityId;
    LocalDate date;
    LocalTime hourFrom;
    LocalTime hourTo;
    Boolean reserved;
    Doctor doctor;
}
