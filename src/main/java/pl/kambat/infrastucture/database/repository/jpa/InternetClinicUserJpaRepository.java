package pl.kambat.infrastucture.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.Optional;

@Repository
public interface InternetClinicUserJpaRepository extends JpaRepository<InternetClinicUserEntity, Integer> {
    Optional<InternetClinicUserEntity> findByEmail(String email);

    Optional<InternetClinicUserEntity> findByUserName(String userName);

}
