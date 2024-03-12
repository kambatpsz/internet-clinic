package pl.kambat.infrastructure.database.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.entity.PatientVisitEntity;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.PatientJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.PatientVisitJpaRepository;
import pl.kambat.util.DataFixtures;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PatientVisitJpaRepositoryTest {

    @Autowired
    private PatientVisitJpaRepository patientVisitJpaRepository;

    @Autowired
    private PatientJpaRepository patientJpaRepository;

    @Autowired
    private DoctorJpaRepository doctorJpaRepository;

    @BeforeEach
    public void setup() {
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();
        patientJpaRepository.saveAndFlush(patientEntity);
    }

    @Test
    void findByDoctor() {
        //given
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        PatientVisitEntity patientVisitEntity = DataFixtures.somePatientVisitEntity1();
        patientVisitJpaRepository.saveAndFlush(patientVisitEntity);

        //when
        List<PatientVisitEntity> patientVisitList = patientVisitJpaRepository.findByDoctor(doctorEntity);

        //then
        assertEquals(doctorEntity.getDoctorId(), patientVisitList.get(0).getDoctor().getDoctorId());
        assertEquals(1, patientVisitList.size());
    }

    @Test
    void findByPatient() {
        //given
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();
        PatientVisitEntity patientVisitEntity = DataFixtures.somePatientVisitEntity1();
        patientVisitJpaRepository.saveAndFlush(patientVisitEntity);

        //when
        List<PatientVisitEntity> patientVisitList = patientVisitJpaRepository.findByPatient(patientEntity);

        //then
        assertEquals(patientEntity.getPatientId(), patientVisitList.get(0).getPatient().getPatientId());
        assertEquals(1, patientVisitList.size());
    }

    @Test
    void findActiveVisitsByDoctorId() {
        //given
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        PatientVisitEntity patientVisitEntity = DataFixtures.somePatientVisitEntity1();
        patientVisitJpaRepository.saveAndFlush(patientVisitEntity);

        //when
        List<PatientVisitEntity> patientVisitList =
                patientVisitJpaRepository.findActiveVisitsByDoctorId(doctorEntity.getDoctorId());

        //then
        assertEquals(doctorEntity.getDoctorId(), patientVisitList.get(0).getDoctor().getDoctorId());
        assertTrue(patientVisitList.get(0).getVisitStatus());
        assertEquals(1, patientVisitList.size());
    }

    @Test
    void findPatientVisitById() {
        //given
        PatientVisitEntity patientVisitEntity = DataFixtures.somePatientVisitEntity1();
        patientVisitJpaRepository.saveAndFlush(patientVisitEntity);

        //when
        Optional<PatientVisitEntity> patientVisit =
                patientVisitJpaRepository.findById(patientVisitEntity.getPatientVisitId());
        PatientVisitEntity pV = patientVisit.orElseThrow(
                () -> new NotDataFoundException(
                        "Could not find patient visit by ID:[%s]"
                                .formatted(patientVisitEntity.getPatientVisitId()))
        );

        //then
        assertEquals(patientVisitEntity.getPatientVisitId(), pV.getPatientVisitId());
    }
}