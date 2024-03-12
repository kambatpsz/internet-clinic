package pl.kambat.api.controller.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.api.dto.mapper.DoctorNilMapper;
import pl.kambat.business.DoctorNilService;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorNIL;
import pl.kambat.util.DataFixtures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorRestControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorNilService doctorNilService;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private DoctorNilMapper doctorNilMapper;

    @InjectMocks
    private DoctorRestController doctorRestController;

    @Test
    void testCheckDoctorInNilSuccessfulVerification() {
        // given
        Integer doctorId = 1;
        Doctor doctorById = DataFixtures.someDoctor1().withDoctorId(doctorId);
        DoctorNIL doctorFromNil = DataFixtures.someDoctorNIL1();

        when(doctorNilMapper.map(doctorFromNil)).thenReturn(doctorById);
        when(doctorService.getDoctorById(doctorId)).thenReturn(doctorById);
        when(doctorNilService.getDoctorByPwzNumber(doctorById.getPwzNumber())).thenReturn(doctorFromNil);
        when(doctorService.verifyDoctor(doctorById, doctorById)).thenReturn(Boolean.TRUE);

        // when
        String result = doctorRestController.checkDoctorInNil(doctorId);

        // then
        assertThat(result).isEqualTo("Doctor Mirek Mirkowski has been properly verified with NIL");
    }

    @Test
    void testCheckDoctorInNilUnsuccessfulVerification() {
        // given
        Integer doctorId = 1;
        Doctor doctorById = DataFixtures.someDoctor1().withDoctorId(doctorId);
        DoctorNIL doctorFromNil = DataFixtures.someDoctorNIL1().withPesel("98765432132");

        when(doctorService.getDoctorById(doctorId)).thenReturn(doctorById);
        when(doctorNilService.getDoctorByPwzNumber(doctorById.getPwzNumber())).thenReturn(doctorFromNil);

        // when
        String result = doctorRestController.checkDoctorInNil(doctorId);

        // then
        assertThat(result).isEqualTo("Unsuccessful verification of Doctor(Mirek Mirkowski) in NIL");
    }
}