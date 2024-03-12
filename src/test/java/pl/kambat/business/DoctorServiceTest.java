package pl.kambat.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kambat.business.dao.DoctorDAO;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.InternetClinicUser;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.repository.mapper.InternetClinicUserEntityMapper;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;
import pl.kambat.util.DataFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorDAO doctorDAO;

    @Mock
    private InternetClinicUserService internetClinicUserService;

    @Mock
    private InternetClinicUserEntityMapper internetClinicUserEntityMapper;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void getLoggedDoctor() {
        // given
        InternetClinicUser internetClinicUser = DataFixtures.someInternetClinicUser1();
        when(internetClinicUserService.getLoggedUser()).thenReturn(internetClinicUser);

        Doctor doctor = DataFixtures.someDoctor1();

        when(internetClinicUserEntityMapper.mapToEntity(internetClinicUser)).thenReturn(new InternetClinicUserEntity());
        when(doctorDAO.findByUserId(any(InternetClinicUserEntity.class))).thenReturn(Optional.of(doctor));

        // when
        Doctor result = doctorService.getLoggedDoctor();

        // then
        assertEquals(doctor, result);
    }

    @Test
    void getLoggedDoctor_ThrowsException() {
        // given
        when(internetClinicUserService.getLoggedUser()).thenReturn(DataFixtures.someInternetClinicUser1());

        // when, then
        assertThrows(NotDataFoundException.class, () -> doctorService.getLoggedDoctor());
    }

    @Test
    void verifyDoctor() {
        // given
        Doctor doctor1 = DataFixtures.someDoctor1();
        Doctor doctor2 = DataFixtures.someDoctor1();
        Doctor doctor3 = DataFixtures.someDoctor1().withPwzNumber("7777777");
        Doctor doctor4 = DataFixtures.someDoctor1().withPesel("54789654652");

        // when then
        assertTrue(doctorService.verifyDoctor(doctor1, doctor2));
        assertFalse(doctorService.verifyDoctor(doctor1, doctor3));
        assertFalse(doctorService.verifyDoctor(doctor1, doctor4));
        assertFalse(doctorService.verifyDoctor(doctor3, doctor4));
    }
}