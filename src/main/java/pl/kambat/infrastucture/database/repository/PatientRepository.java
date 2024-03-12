package pl.kambat.infrastucture.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kambat.business.dao.PatientDAO;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.repository.mapper.PatientEntityMapper;
import pl.kambat.infrastucture.database.repository.jpa.PatientJpaRepository;
import pl.kambat.domain.Patient;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientEntityMapper patientEntityMapper;

    @Override
    public List<Patient> findAllPatients() {
        return patientJpaRepository.findAll().stream()
                .map(patientEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientJpaRepository.findByPesel(pesel)
                .map(patientEntityMapper::mapFromEntity);
    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientEntityMapper.mapToEntity(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Patient> findByUserId(InternetClinicUserEntity patient) {
        return patientJpaRepository.findByUserId(patient).map(patientEntityMapper::mapFromEntity);
    }

    @Override
    public void deleteAllPatient() {
        patientJpaRepository.deleteAll();
    }
}
