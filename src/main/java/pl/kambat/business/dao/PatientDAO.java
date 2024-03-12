package pl.kambat.business.dao;

import pl.kambat.domain.Patient;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.List;
import java.util.Optional;

public interface PatientDAO {

    List<Patient> findAllPatients();

    Optional<Patient> findByPesel(String pesel);

    Patient savePatient(Patient patient);

    Optional<Patient> findByUserId(InternetClinicUserEntity patient);

    void deleteAllPatient();
}
