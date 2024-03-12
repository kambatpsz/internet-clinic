package pl.kambat.infrastucture.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kambat.infrastucture.database.entity.DoctorAvailabilityEntity;
import pl.kambat.infrastucture.database.entity.DoctorEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityJpaRepository extends JpaRepository<DoctorAvailabilityEntity, Integer> {

    List<DoctorAvailabilityEntity> findByDoctor(DoctorEntity doctor);

    @Query("SELECT DISTINCT da.doctor FROM DoctorAvailabilityEntity da " +
            "WHERE da.date >= :currentDate " +
            "AND da.reserved = false")
    List<DoctorEntity> findDoctorsWithAvailabilityAfterOrEqualDate(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT CASE WHEN COUNT(da) > 0 THEN true ELSE false END FROM DoctorAvailabilityEntity da " +
            "WHERE da.doctor = :doctor " +
            "AND da.date = :date " +
            "AND da.hourFrom = :hourFrom " +
            "AND da.hourTo = :hourTo")
    boolean existsDoctorAvailability(
            @Param("doctor") DoctorEntity doctor,
            @Param("date") LocalDate date,
            @Param("hourFrom") LocalTime hourFrom,
            @Param("hourTo") LocalTime hourTo
    );

    Optional<DoctorAvailabilityEntity> findByDoctorAndDateAndHourFrom(
            DoctorEntity doctorEntity,
            LocalDate availabilityDoctorDate,
            LocalTime availabilityDoctorTime
    );
}
