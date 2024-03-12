package pl.kambat.business.dao;

import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;

import java.util.List;
import java.util.Optional;

public interface PatientVisitDAO {
    List<PatientVisit> findAllPatientsVisitsByDoctor(Doctor doctor);

    List<PatientVisit> findAllPatientsVisitsByPatient(Patient patient);

    List<PatientVisit> findActiveVisitsByDoctorId(Integer doctorId);

    Optional<PatientVisit> findPatientVisitById(Integer patientVisitID);

    void save(PatientVisit patientVisit);
}
