package pl.kambat.business.dao;

import pl.kambat.domain.Doctor;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    Optional<Doctor> findByPesel(String pesel);

    Doctor saveDoctor(Doctor doctor);

    Optional<Doctor> findByUserId(InternetClinicUserEntity doctorID);

    Optional<Doctor> findByPwzNumber(String pwzNumber);

    List<Doctor> findAll();

    Optional<Doctor> findDoctorById(Integer doctorId);

    void changeDoctorVerifiedStatusById(Integer doctorID, Boolean status);

}
