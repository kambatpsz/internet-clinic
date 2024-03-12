package pl.kambat.integration.rest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kambat.api.dto.DoctorDTO;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.business.DoctorNilService;
import pl.kambat.business.DoctorService;
import pl.kambat.business.MemberService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorNIL;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.integration.configuration.RestAssuredIntegrationTestBase;
import pl.kambat.integration.support.DoctorControllerRestTestSupport;
import pl.kambat.integration.support.WireMockTestSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorControllerRestITestRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements DoctorControllerRestTestSupport, WireMockTestSupport {

    private final DoctorService doctorService;
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorNilService doctorNilService;

    @AfterEach
    void afterAll() {
        doctorJpaRepository.deleteAll();
    }

    @Test
    void doctorList() {
        // given
        MemberDTO memberDoctorDTO1 = createAndAddMemberDTO1();
        MemberDTO memberDoctorDTO2 = createAndAddMemberDTO2();

        DoctorDTO doctorDTO1 = createDoctorDtoFromMemberDto(memberDoctorDTO1);
        DoctorDTO doctorDTO2 = createDoctorDtoFromMemberDto(memberDoctorDTO2);

        // when
        List<DoctorDTO> retrievedDoctors = listDoctors();

        // then
        assertThat(retrievedDoctors)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("doctorId")
                .containsExactlyInAnyOrder(doctorDTO1, doctorDTO2);
    }

    @Test
    void showDoctorDetails() {
        // given
        MemberDTO memberDoctorDTO = createAndAddMemberDTO1();
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());

        DoctorDTO doctorDTO = createDoctorDtoFromMemberDto(memberDoctorDTO);

        // when
        DoctorDTO retrievedDoctor = getDoctorById(doctor.getDoctorId());
        // then
        assertThat(retrievedDoctor)
                .usingRecursiveComparison()
                .ignoringFields("doctorId")
                .isEqualTo(doctorDTO);
    }

    @Test
    void checkDoctorInNil(){
        //given
        String pwzNumberExists = "1234567";
        String pwzNumberNoExists = "0000000";

        stubForNil(wireMockServer, pwzNumberExists);

        //when
        DoctorNIL doctorNilExist = doctorNilService.getDoctorByPwzNumber(pwzNumberExists);
        DoctorNIL doctorNilNoExist = doctorNilService.getDoctorByPwzNumber(pwzNumberNoExists);

        assertThat(doctorNilExist).isNotNull();
        assertThat(doctorNilNoExist).isNull();
        assertThat(doctorNilExist.getPwzNumber()).isEqualTo(pwzNumberExists);
    }


    private DoctorDTO createDoctorDtoFromMemberDto(MemberDTO memberDoctorDTO) {
        return DoctorDTO.builder()
                .name(memberDoctorDTO.getUserName())
                .surname(memberDoctorDTO.getUserSurname())
                .pesel(memberDoctorDTO.getUserPesel())
                .pwzNumber(memberDoctorDTO.getUserPwzNumber())
                .build();
    }

    private MemberDTO createAndAddMemberDTO1() {
        MemberDTO memberDoctorDTO =
                MemberDTO.builder()
                        .userType("DOCTOR")
                        .userName("Miroslaw")
                        .userSurname("Mirkowski")
                        .userEmail("mirek@gmail.com")
                        .userLogin("mirek")
                        .userPassword("mirO23sr")
                        .userPesel("65321456935")
                        .userPwzNumber("12365987")
                        .userPhoneNumber("+48 512 456 789")
                        .userAddressCountry("Polska")
                        .userAddressCity("Warszawa")
                        .userAddressPostalCode("00-001")
                        .userAddressStreet("ul. Warszawska 1")
                        .build();

        memberService.addUser(memberMapper.map(memberDoctorDTO));
        return memberDoctorDTO;
    }

    private MemberDTO createAndAddMemberDTO2() {
        MemberDTO memberDoctorDTO =
                MemberDTO.builder()
                        .userType("DOCTOR")
                        .userName("Katarzyna")
                        .userSurname("Slowacka")
                        .userEmail("kat.slo@gmail.com")
                        .userLogin("katslo")
                        .userPassword("katslO23sr")
                        .userPesel("77721456935")
                        .userPwzNumber("33365987")
                        .userPhoneNumber("+48 512 456 789")
                        .userAddressCountry("Polska")
                        .userAddressCity("Warszawa")
                        .userAddressPostalCode("00-001")
                        .userAddressStreet("ul. Warszawska 1")
                        .build();

        memberService.addUser(memberMapper.map(memberDoctorDTO));
        return memberDoctorDTO;
    }
}
