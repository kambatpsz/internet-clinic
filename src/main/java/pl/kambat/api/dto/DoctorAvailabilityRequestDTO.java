package pl.kambat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailabilityRequestDTO {
    Integer doctorAvailabilityId;
    Integer doctorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime hourFrom;
    Boolean reserved;
}
