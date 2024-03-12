package pl.kambat.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.PatientVisitDAO;
import pl.kambat.domain.PatientVisit;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.exception.NotDataFoundException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatientVisitService {

    private final PatientVisitDAO patientVisitDAO;

    @Transactional
    public List<PatientVisit> findVisitsByDoctorId(Doctor doctor) {
            return patientVisitDAO.findAllPatientsVisitsByDoctor(doctor);
        }
    @Transactional
    public List<PatientVisit> findVisitsByPatientId(Patient patient) {
        return patientVisitDAO.findAllPatientsVisitsByPatient(patient);
    }
    @Transactional
    public List<PatientVisit> findActiveDoctorVisits(Integer doctorId) {
        return patientVisitDAO.findActiveVisitsByDoctorId(doctorId);
    }

    @Transactional
    public PatientVisit findPatientVisitById(Integer patientVisitID) {
        return patientVisitDAO.findPatientVisitById(patientVisitID).orElseThrow(
                () -> new NotDataFoundException(
                        "Could not find patient visit by ID:[%s]"
                                .formatted(patientVisitID))
        );
    }

    @Transactional
    public void save(PatientVisit patientVisit) {
        patientVisitDAO.save(patientVisit);
    }
}
