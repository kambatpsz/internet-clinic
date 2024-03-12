package pl.kambat.infrastructure.database.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import pl.kambat.infrastucture.database.entity.DoctorAvailabilityEntity;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.repository.jpa.DoctorAvailabilityJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.util.DataFixtures;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DoctorAvailabilityJpaRepositoryTest {

    @Autowired
    private DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;

    @Autowired
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void findByDoctor() {
        // given
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);
        DoctorAvailabilityEntity doctorAvailabilityEntity = DataFixtures.someDoctorAvailabilityEntity1();
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntity);

        // when
        List<DoctorAvailabilityEntity> foundAvailabilities = doctorAvailabilityJpaRepository
                .findByDoctor(doctorEntity);
        // then
        assertEquals(1, foundAvailabilities.size());
    }

    @Test
    void findDoctorsWithAvailabilityAfterOrEqualDate() {
        //given
        LocalDate localDate = java.time.LocalDate.of(2023, 12, 6);
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);
        DoctorAvailabilityEntity doctorAvailabilityEntity = DataFixtures.someDoctorAvailabilityEntity1();
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntity);

        // when
        List<DoctorEntity> doctorEntityList = doctorAvailabilityJpaRepository
                .findDoctorsWithAvailabilityAfterOrEqualDate(localDate);

        // then
        assertEquals(1, doctorEntityList.size());
        assertEquals(1, doctorEntityList.get(0).getDoctorId());
        assertEquals("12365987", doctorEntityList.get(0).getPwzNumber());
    }

    @Test
    void existsDoctorAvailability() {
        //given
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalTime hourFrom = LocalTime.of(7, 0);
        LocalTime hourTo = LocalTime.of(7, 20);

        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);
        DoctorAvailabilityEntity doctorAvailabilityEntity = DataFixtures.someDoctorAvailabilityEntity1();
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntity);

        //when
        boolean isExist = doctorAvailabilityJpaRepository.existsDoctorAvailability(
                doctorEntity,
                date,
                hourFrom,
                hourTo);

        //then
        assertTrue(isExist);
    }

    @Test
    void findByDoctorAndDateAndHourFrom() {

        //given
        DoctorEntity doctorEntity = DataFixtures.someDoctorEntity1();
        doctorJpaRepository.saveAndFlush(doctorEntity);
        DoctorAvailabilityEntity doctorAvailabilityEntity = DataFixtures.someDoctorAvailabilityEntity1();

        //when
        doctorAvailabilityJpaRepository.saveAndFlush(doctorAvailabilityEntity);
        List<DoctorAvailabilityEntity> doctorAvailabilityJpaRepositoryAll = doctorAvailabilityJpaRepository.findAll();

        //then
        assertEquals(1, doctorAvailabilityJpaRepositoryAll.size());
        assertEquals(1, doctorAvailabilityJpaRepositoryAll.get(0).getDoctorAvailabilityId());
    }

}