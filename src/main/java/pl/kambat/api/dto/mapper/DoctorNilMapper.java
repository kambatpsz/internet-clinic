package pl.kambat.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorNIL;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorNilMapper {


    Doctor map(final DoctorNIL doctorNIL);
}
