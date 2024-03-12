package pl.kambat.infrastucture.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {
    Optional<DoctorEntity> findByPesel(String pesel);

    Optional<DoctorEntity> findByUserId(InternetClinicUserEntity userId);

    Optional<DoctorEntity> findByPwzNumber(String pwzNumber);
}
