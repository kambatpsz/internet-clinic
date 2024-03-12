package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kambat.api.dto.DoctorAvailabilityRequestDTO;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.domain.DoctorAvailabilityRequest;

@Mapper(componentModel = "spring")
public interface DoctorAvailabilityRequestMapper {
    DoctorAvailabilityRequestDTO mapToRequest(final DoctorAvailabilityRequest doctorAvailabilityRequest);

    DoctorAvailabilityRequest mapToRequest(final DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO);

    @Mapping(source = "doctorAvailabilityId", target = "doctorAvailabilityId")
    @Mapping(source = "doctor.doctorId", target = "doctorId")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "hourFrom", target = "hourFrom")
    @Mapping(source = "reserved", target = "reserved")
    DoctorAvailabilityRequest mapToRequest(DoctorAvailability doctorAvailability);

}