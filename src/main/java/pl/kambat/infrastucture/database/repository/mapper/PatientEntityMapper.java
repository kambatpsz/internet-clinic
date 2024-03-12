package pl.kambat.infrastucture.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.domain.InternetClinicRole;
import pl.kambat.domain.Patient;
import pl.kambat.infrastucture.seciurity.InternetClinicRoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {

    @Mapping(target = "address.patient", ignore = true)
    @Mapping(target = "address.doctor", ignore = true)
    @Mapping(target = "patientVisit", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Patient mapFromEntity(PatientEntity entity);

    @Mapping(source = "userId.roles", target = "userId.roles", qualifiedByName = "mapInternetClinicRoles")
    PatientEntity mapToEntity(Patient patient);

    @Named("mapInternetClinicRoles")
    default Set<InternetClinicRoleEntity> mapInternetClinicRoles(Set<InternetClinicRole> internetClinicRoleSet) {
        return internetClinicRoleSet.stream()
                .map(this::mapToEntity).collect(Collectors.toSet());
    }

    InternetClinicRoleEntity mapToEntity(InternetClinicRole internetClinicRole);
}
