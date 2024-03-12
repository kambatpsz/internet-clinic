package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.api.dto.DoctorAvailabilityDTO;

@Mapper(componentModel = "spring")
public interface DoctorAvailabilityMapper {
    DoctorAvailabilityDTO map(final DoctorAvailability doctorAvailability);
    DoctorAvailability map(final DoctorAvailabilityDTO doctorAvailabilityDTO);
}
