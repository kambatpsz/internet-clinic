package pl.kambat.infrastucture.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kambat.business.dao.PatientVisitDAO;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.repository.mapper.DoctorEntityMapper;
import pl.kambat.infrastucture.database.repository.mapper.PatientEntityMapper;
import pl.kambat.infrastucture.database.repository.mapper.PatientVisitEntityMapper;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;
import pl.kambat.infrastucture.database.repository.jpa.PatientVisitJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientVisitRepository implements PatientVisitDAO {

    private final PatientVisitJpaRepository patientVisitJpaRepository;
    private final PatientVisitEntityMapper patientVisitEntityMapper;
    private final DoctorEntityMapper doctorEntityMapper;
    private final PatientEntityMapper patientEntityMapper;

    @Override
    public List<PatientVisit> findAllPatientsVisitsByDoctor(Doctor doctor) {
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);
        return patientVisitJpaRepository.findByDoctor(doctorEntity).stream()
                .map(patientVisitEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<PatientVisit> findAllPatientsVisitsByPatient(Patient patient) {
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);
        return patientVisitJpaRepository.findByPatient(patientEntity).stream()
                .map(patientVisitEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<PatientVisit> findActiveVisitsByDoctorId(Integer doctorId) {
        return patientVisitJpaRepository.findActiveVisitsByDoctorId(doctorId).stream()
                .map(patientVisitEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<PatientVisit> findPatientVisitById(Integer patientVisitID) {
        return patientVisitJpaRepository.findById(patientVisitID).map(patientVisitEntityMapper::mapFromEntity);
    }

    @Override
    public void save(PatientVisit patientVisit) {
        patientVisitJpaRepository.saveAndFlush(patientVisitEntityMapper.mapToEntity(patientVisit));
    }

}
