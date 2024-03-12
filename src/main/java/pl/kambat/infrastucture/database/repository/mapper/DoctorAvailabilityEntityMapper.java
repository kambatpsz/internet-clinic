package pl.kambat.infrastucture.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.infrastucture.database.entity.DoctorAvailabilityEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorAvailabilityEntityMapper {

    @Mapping(target = "doctor.address", ignore = true)
    @Mapping(target = "doctor.patientVisit", ignore = true)
    @Mapping(target = "doctor.doctorAvailability", ignore = true)
    DoctorAvailability mapFromEntity(DoctorAvailabilityEntity entity);

    DoctorAvailabilityEntity mapToEntity(DoctorAvailability doctorAvailability);
}
