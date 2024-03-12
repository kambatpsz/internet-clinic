package pl.kambat.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "doctorId")
@ToString(of = {"doctorId", "date", "hourFrom"})
public class DoctorAvailabilityRequest {

    Integer doctorAvailabilityId;
    Integer doctorId;
    LocalDate date;
    LocalTime hourFrom;
    Boolean reserved;
}