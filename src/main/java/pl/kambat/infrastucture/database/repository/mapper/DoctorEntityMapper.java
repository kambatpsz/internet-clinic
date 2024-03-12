package pl.kambat.infrastucture.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kambat.domain.InternetClinicRole;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.domain.Doctor;
import pl.kambat.infrastucture.seciurity.InternetClinicRoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorEntityMapper {


    @Mapping(target = "address", ignore = true)
    @Mapping(target = "patientVisit", ignore = true)
    @Mapping(target = "doctorAvailability", ignore = true)
    @Mapping(source = "userId.roles", target = "userId.roles", qualifiedByName = "mapInternetClinicRolesEntity")
    Doctor mapFromEntity(DoctorEntity entity);

    @Mapping(source = "userId.roles", target = "userId.roles", qualifiedByName = "mapInternetClinicRoles")
    DoctorEntity mapToEntity(Doctor doctor);

    @Named("mapInternetClinicRoles")
    default Set<InternetClinicRoleEntity> mapInternetClinicRoles(Set<InternetClinicRole> internetClinicRoleSet) {
        return internetClinicRoleSet.stream()
                .map(this::mapToEntity).collect(Collectors.toSet());
    }

    InternetClinicRoleEntity mapToEntity(InternetClinicRole internetClinicRole);

    @Named("mapInternetClinicRolesEntity")
    default Set<InternetClinicRole> mapInternetClinicRolesEntity(Set<InternetClinicRoleEntity> internetClinicRoleEntitySet) {
        return internetClinicRoleEntitySet.stream()
                .map(this::mapFromEntity).collect(Collectors.toSet());
    }

    InternetClinicRole mapFromEntity(InternetClinicRoleEntity internetClinicRoleEntity);

}
