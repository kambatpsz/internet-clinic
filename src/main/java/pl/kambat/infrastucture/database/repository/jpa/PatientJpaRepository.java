package pl.kambat.infrastucture.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {
    Optional<PatientEntity> findByPesel(String pesel);
    Optional<PatientEntity> findByUserId(InternetClinicUserEntity patient);
}

