package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.kambat.api.dto.PatientVisitDTO;
import pl.kambat.domain.PatientVisit;

@Mapper(componentModel = "spring")
public interface PatientVisitMapper {

    PatientVisitDTO map(final PatientVisit patientVisit);

    PatientVisit map(final PatientVisitDTO patientVisitDTO);

}
