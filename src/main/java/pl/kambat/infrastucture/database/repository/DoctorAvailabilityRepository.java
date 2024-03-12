package pl.kambat.infrastucture.database.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.kambat.business.dao.DoctorAvailabilityDAO;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.repository.jpa.DoctorAvailabilityJpaRepository;
import pl.kambat.infrastucture.database.repository.mapper.DoctorAvailabilityEntityMapper;
import pl.kambat.infrastucture.database.repository.mapper.DoctorEntityMapper;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class DoctorAvailabilityRepository implements DoctorAvailabilityDAO {

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;
    private final DoctorEntityMapper doctorEntityMapper;
    @Override
    public List<DoctorAvailability> findAllAvailabilityByDoctor(Doctor doctor) {

        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        return doctorAvailabilityJpaRepository.findByDoctor(doctorEntity).stream()
                .map(doctorAvailabilityEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Doctor> findDoctorsWithActiveAppointments() {
        return doctorAvailabilityJpaRepository.findDoctorsWithAvailabilityAfterOrEqualDate(LocalDate.now()).stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void saveDoctorAvailability(DoctorAvailability doctorAvailability) {
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntityMapper.mapToEntity(doctorAvailability));
    }

    @Override
    public boolean checkIfDoctorAvailabilityAlreadyExists(DoctorAvailability doctorAvailability) {
        return doctorAvailabilityJpaRepository.existsDoctorAvailability(
                doctorEntityMapper.mapToEntity(doctorAvailability.getDoctor()),
                doctorAvailability.getDate(),
                doctorAvailability.getHourFrom(),
                doctorAvailability.getHourTo());
    }

    @Override
    public void changeStatusDoctorAvailability(DoctorAvailability doctorAvailability) {
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntityMapper.mapToEntity(doctorAvailability));
    }

    @Override
    public Optional<DoctorAvailability> findDoctorAvailabilityByDate(
            Doctor doctor,
            LocalDate availabilityDoctorDate,
            LocalTime availabilityDoctorTime
    ) {

        return doctorAvailabilityJpaRepository.findByDoctorAndDateAndHourFrom(
                doctorEntityMapper.mapToEntity(doctor),
                availabilityDoctorDate,
                availabilityDoctorTime
        ).map(doctorAvailabilityEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<DoctorAvailability> findDoctorAvailabilityById(Integer doctorAvailabilityId) {
        return doctorAvailabilityJpaRepository.findById(doctorAvailabilityId)
                .map(doctorAvailabilityEntityMapper::mapFromEntity);
    }

    @Override
    public void deleteDoctorAvailabilityById(Integer doctorAvailabilityId) {
        doctorAvailabilityJpaRepository.deleteById(doctorAvailabilityId);
    }
}
