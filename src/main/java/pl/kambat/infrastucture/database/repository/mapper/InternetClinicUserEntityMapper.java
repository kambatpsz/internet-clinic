package pl.kambat.infrastucture.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.kambat.domain.InternetClinicRole;
import pl.kambat.domain.InternetClinicUser;
import pl.kambat.infrastucture.seciurity.InternetClinicRoleEntity;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InternetClinicUserEntityMapper {

    @Mapping(target = "password", ignore = true)
    InternetClinicUser mapFromEntity(InternetClinicUserEntity entity);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapInternetClinicRoles")
    InternetClinicUserEntity mapToEntity(InternetClinicUser internetClinicUser);

    @Named("mapInternetClinicRoles")
    default Set<InternetClinicRoleEntity> mapInternetClinicRoles(Set<InternetClinicRole> internetClinicRoleSet) {
        return internetClinicRoleSet.stream()
                .map(this::mapToEntity).collect(Collectors.toSet());
    }

    InternetClinicRoleEntity mapToEntity(InternetClinicRole internetClinicRole);
}
