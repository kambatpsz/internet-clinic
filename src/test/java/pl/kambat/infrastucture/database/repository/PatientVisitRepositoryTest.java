package pl.kambat.infrastucture.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.entity.PatientVisitEntity;
import pl.kambat.infrastucture.database.repository.jpa.PatientVisitJpaRepository;
import pl.kambat.infrastucture.database.repository.mapper.DoctorEntityMapper;
import pl.kambat.infrastucture.database.repository.mapper.PatientEntityMapper;
import pl.kambat.infrastucture.database.repository.mapper.PatientVisitEntityMapper;
import pl.kambat.util.DataFixtures;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientVisitRepositoryTest {

    @Mock
    private PatientVisitJpaRepository patientVisitJpaRepository;

    @Mock
    private PatientVisitEntityMapper patientVisitEntityMapper;

    @Mock
    private DoctorEntityMapper doctorEntityMapper;

    @Mock
    private PatientEntityMapper patientEntityMapper;

    @InjectMocks
    private PatientVisitRepository patientVisitRepository;

    @Test
    void findAllPatientsVisitsByDoctor() {
        // given
        Doctor doctor = DataFixtures.someDoctor1();
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        when(doctorEntityMapper.mapToEntity(any(Doctor.class))).thenReturn(doctorEntity);

        PatientVisitEntity visitEntity1 = DataFixtures.somePatientVisitEntity1();
        when(patientVisitJpaRepository.findByDoctor(any(DoctorEntity.class)))
                .thenReturn(List.of(visitEntity1));

        // when
        List<PatientVisit> result = patientVisitRepository.findAllPatientsVisitsByDoctor(doctor);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findAllPatientsVisitsByPatient() {
        // given
        Patient patient = DataFixtures.somePatient1();
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();
        when(patientEntityMapper.mapToEntity(patient)).thenReturn(patientEntity);

        PatientVisitEntity visitEntity1 = DataFixtures.somePatientVisitEntity1();
        when(patientVisitJpaRepository.findByPatient(patientEntity))
                .thenReturn(List.of(visitEntity1));
        when(patientVisitEntityMapper.mapFromEntity(visitEntity1))
                .thenReturn(DataFixtures.somePatientVisit1());

        // when
        List<PatientVisit> result = patientVisitRepository.findAllPatientsVisitsByPatient(patient);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findActiveVisitsByDoctorId() {
        // given
        Doctor doctor = DataFixtures.someDoctor1().withDoctorId(1);
        List<PatientVisitEntity> patientVisitsEntityList = List.of(DataFixtures.somePatientVisitEntity1());

        when(patientVisitJpaRepository.findActiveVisitsByDoctorId(doctor.getDoctorId()))
                .thenReturn(patientVisitsEntityList);
        when(patientVisitEntityMapper.mapFromEntity(any()))
                .thenReturn(DataFixtures.somePatientVisit1());

        // when
        List<PatientVisit> result = patientVisitRepository.findActiveVisitsByDoctorId(doctor.getDoctorId());

        // then
        assertThat(result).containsExactlyElementsOf(List.of(DataFixtures.somePatientVisit1()));

    }

    @Test
    void findPatientVisitById() {
        // given
        Integer patientVisitId = 1;
        PatientVisitEntity patientVisitEntity = DataFixtures.somePatientVisitEntity1();

        when(patientVisitJpaRepository.findById(patientVisitId))
                .thenReturn(Optional.of(patientVisitEntity));

        when(patientVisitEntityMapper.mapFromEntity(patientVisitEntity))
                .thenReturn(DataFixtures.somePatientVisit1());

        // when
        Optional<PatientVisit> result = patientVisitRepository.findPatientVisitById(patientVisitId);

        // then
        assertThat(result).isPresent().contains(DataFixtures.somePatientVisit1());
    }
}