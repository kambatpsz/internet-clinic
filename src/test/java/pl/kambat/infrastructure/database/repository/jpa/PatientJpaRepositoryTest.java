package pl.kambat.infrastructure.database.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.repository.jpa.PatientJpaRepository;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;
import pl.kambat.util.DataFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PatientJpaRepositoryTest {

    @Autowired
    private PatientJpaRepository patientJpaRepository;

    @Test
    void findByPesel() {
        // given
        String pesel = "75321695478";
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();
        patientJpaRepository.saveAndFlush(patientEntity);

        //when
        Optional<PatientEntity> patientByPesel = patientJpaRepository.findByPesel(pesel);
        //then
        PatientEntity result = patientByPesel.orElseThrow(() -> new NotDataFoundException(
                "Could not find patient by pesel:[%s]"
                        .formatted(pesel)
        ));

        assertEquals(pesel, result.getPesel());
    }

    @Test
    void findByUserId() {
        // given
        InternetClinicUserEntity internetClinicUserEntity = DataFixtures.someInternetClinicUserEntity2();
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();
        patientJpaRepository.saveAndFlush(patientEntity);

        //when
        Optional<PatientEntity> patientByUserId = patientJpaRepository.findByUserId(internetClinicUserEntity);
        //then
        PatientEntity result = patientByUserId.orElseThrow(() -> new NotDataFoundException(
                "Could not find patient by ID:[%s]"
                        .formatted(patientEntity.getUserId())
        ));
        assertEquals(internetClinicUserEntity, result.getUserId());
    }
}