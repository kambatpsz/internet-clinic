package pl.kambat.business.dao;

import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DoctorAvailabilityDAO {
    List<DoctorAvailability> findAllAvailabilityByDoctor(Doctor doctor);

    List<Doctor> findDoctorsWithActiveAppointments();

    void saveDoctorAvailability(DoctorAvailability doctorAvailability);

    boolean checkIfDoctorAvailabilityAlreadyExists(DoctorAvailability doctorAvailability);

    void changeStatusDoctorAvailability(DoctorAvailability doctorAvailability);

    Optional<DoctorAvailability> findDoctorAvailabilityByDate(
            Doctor doctor,
            LocalDate availabilityDoctorDate,
            LocalTime availabilityDoctorTime
    );

    Optional<DoctorAvailability> findDoctorAvailabilityById(Integer doctorAvailabilityId);

    void deleteDoctorAvailabilityById(Integer doctorAvailabilityId);
}
