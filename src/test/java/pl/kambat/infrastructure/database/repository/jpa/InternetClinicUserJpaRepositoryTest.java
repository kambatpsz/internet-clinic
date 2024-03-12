package pl.kambat.infrastructure.database.repository.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.repository.jpa.InternetClinicUserJpaRepository;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;
import pl.kambat.util.DataFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
class InternetClinicUserJpaRepositoryTest {
    @Autowired
    private InternetClinicUserJpaRepository internetClinicUserJpaRepository;

    @BeforeEach
    void setUp() {
        InternetClinicUserEntity internetClinicUserEntity = DataFixtures.someInternetClinicUserEntity1();
        internetClinicUserJpaRepository.saveAndFlush(internetClinicUserEntity);
    }

    @Test
    void findByEmail() {
        //given
        String email = "mirek@gmail.com";
        //when
        Optional<InternetClinicUserEntity> internetClinicUserEmail =
                internetClinicUserJpaRepository.findByEmail(email);

        InternetClinicUserEntity iCUE = internetClinicUserEmail.orElseThrow(() -> new NotDataFoundException(
                "Could not find InternetClinicUserEntity by email:[%s]"
                        .formatted(email)
        ));
        //then
        assertEquals(email, iCUE.getEmail());
    }

    @Test
    void findByUserName() {
        //given
        String userName = "mirek";
        //when
        Optional<InternetClinicUserEntity> internetClinicUserName =
                internetClinicUserJpaRepository.findByUserName(userName);

        InternetClinicUserEntity iCUN = internetClinicUserName.orElseThrow(() -> new NotDataFoundException(
                "Could not find InternetClinicUserEntity by userName:[%s]"
                        .formatted(userName)
        ));
        //then
        assertEquals(userName, iCUN.getUserName());
    }
}