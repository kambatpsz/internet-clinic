package pl.kambat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    Integer doctorId;
    String name;
    String surname;
    String pesel;
    String pwzNumber;
}
