package pl.kambat.infrastucture.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.entity.PatientVisitEntity;

import java.util.List;

@Repository
public interface PatientVisitJpaRepository extends JpaRepository<PatientVisitEntity, Integer> {
    List<PatientVisitEntity> findByDoctor(DoctorEntity doctor);

    List<PatientVisitEntity> findByPatient(PatientEntity patient);

    @Query("""
            SELECT adv FROM PatientVisitEntity adv
            WHERE adv.doctor.doctorId = :doctorId
            AND adv.visitStatus = true
            """)
    List<PatientVisitEntity> findActiveVisitsByDoctorId(final @Param("doctorId") Integer doctorId);
}
