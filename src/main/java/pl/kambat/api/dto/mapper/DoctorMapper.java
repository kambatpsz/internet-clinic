package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.domain.Doctor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {
    DoctorDTO map(final Doctor doctor);

    Doctor map(final DoctorDTO doctorDTO);
}
