package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.kambat.api.dto.PatientDTO;
import pl.kambat.domain.Patient;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    PatientDTO map(final Patient patient);

}
