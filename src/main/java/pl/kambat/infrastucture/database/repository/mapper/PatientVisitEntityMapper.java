package pl.kambat.infrastucture.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.kambat.domain.PatientVisit;
import pl.kambat.infrastucture.database.entity.PatientVisitEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientVisitEntityMapper {

    @Mapping(target = "doctor.address", ignore = true)
    @Mapping(target = "doctor.patientVisit", ignore = true)
    @Mapping(target = "doctor.doctorAvailability", ignore = true)
    @Mapping(target = "patient.patientVisit", ignore = true)
    @Mapping(target = "patient.address", ignore = true)
    PatientVisit mapFromEntity(PatientVisitEntity entity);

    PatientVisitEntity mapToEntity(PatientVisit patientVisit);

}
