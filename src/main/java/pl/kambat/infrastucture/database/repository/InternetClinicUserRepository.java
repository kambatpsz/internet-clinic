package pl.kambat.infrastucture.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kambat.business.dao.InternetClinicUserDAO;
import pl.kambat.infrastucture.database.repository.jpa.InternetClinicUserJpaRepository;
import pl.kambat.domain.InternetClinicUser;
import pl.kambat.infrastucture.database.repository.mapper.InternetClinicUserEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class InternetClinicUserRepository implements InternetClinicUserDAO {

    private final InternetClinicUserJpaRepository internetClinicUserJpaRepository;
    private final InternetClinicUserEntityMapper internetClinicUserEntityMapper;

    @Override
    public Optional<InternetClinicUser> findByEmail(String email) {
        return internetClinicUserJpaRepository.findByEmail(email).map(internetClinicUserEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<InternetClinicUser> findByLogin(String login) {
        return internetClinicUserJpaRepository.findByUserName(login).map(internetClinicUserEntityMapper::mapFromEntity);
    }

}
