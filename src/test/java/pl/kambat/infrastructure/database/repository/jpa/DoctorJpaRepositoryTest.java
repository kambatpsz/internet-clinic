package pl.kambat.infrastructure.database.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;
import pl.kambat.util.DataFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DoctorJpaRepositoryTest {

    @Autowired
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void findByPesel() {
        // given
        String pesel = "65321456935";
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);

        //when
        Optional<DoctorEntity> doctorByPesel = doctorJpaRepository.findByPesel(pesel);
        //then
        DoctorEntity result = doctorByPesel.orElseThrow();
        assertEquals(pesel, result.getPesel());
    }

    @Test
    void findByUserId() {
        // given
        InternetClinicUserEntity internetClinicUserEntity = DataFixtures.someInternetClinicUserEntity1();
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);

        //when
        Optional<DoctorEntity> doctorByUserId = doctorJpaRepository.findByUserId(internetClinicUserEntity);
        //then
        DoctorEntity result = doctorByUserId.orElseThrow(() -> new NotDataFoundException(
                "Could not find doctor by ID:[%s]"
                        .formatted(doctorEntity.getUserId())
        ));
        assertEquals(internetClinicUserEntity, result.getUserId());
    }

    @Test
    void findByPwzNumber() {
        // given
        String pwzNumber = "12365987";
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);

        //when
        Optional<DoctorEntity> doctorByPwzNumber = doctorJpaRepository.findByPwzNumber(pwzNumber);
        //then
        DoctorEntity result = doctorByPwzNumber.orElseThrow(
                () -> new NotDataFoundException(
                        "Could not find doctor by PWZ number:[%s]"
                                .formatted(pwzNumber))
        );
        assertEquals(pwzNumber, result.getPwzNumber());
    }
}