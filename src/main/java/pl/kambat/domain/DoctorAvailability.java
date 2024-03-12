package pl.kambat.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "doctorAvailabilityId")
@ToString(of = {"doctorAvailabilityId", "doctor", "date", "hourFrom", "hourTo"})
public class DoctorAvailability {

    Integer doctorAvailabilityId;
    LocalDate date;
    LocalTime hourFrom;
    LocalTime hourTo;
    Boolean reserved;
    Doctor doctor;
}