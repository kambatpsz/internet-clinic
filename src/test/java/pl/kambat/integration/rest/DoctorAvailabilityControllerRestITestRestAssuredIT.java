package pl.kambat.integration.rest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kambat.api.dto.DoctorAvailabilityRequestDTO;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.business.DoctorService;
import pl.kambat.business.MemberService;
import pl.kambat.domain.Doctor;
import pl.kambat.infrastucture.database.repository.jpa.DoctorAvailabilityJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.integration.configuration.RestAssuredIntegrationTestBase;
import pl.kambat.integration.support.DoctorAvailabilityRestTestSupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class DoctorAvailabilityControllerRestITestRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements DoctorAvailabilityRestTestSupport {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorService doctorService;
    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;

    @AfterEach
    void afterEach() {
        doctorAvailabilityJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
    }

    @Test
    void showDoctorAvailabilityRequestList() {
        // given
        MemberDTO memberDoctorDTO = createAndAddMemberDTO();
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());
        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO1 =
                createDoctorAvailabilityRequestDto1WithDoctorId(doctor.getDoctorId());
        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO2 =
                createDoctorAvailabilityRequestDto2WithDoctorId(doctor.getDoctorId());

        //when
        addDoctorAvailabilityRequest(doctorAvailabilityRequestDTO1);
        addDoctorAvailabilityRequest(doctorAvailabilityRequestDTO2);
        List<DoctorAvailabilityRequestDTO> doctorAvailabilityRequestDTOList =
                getDoctorAvailabilityList(doctor.getDoctorId());

        // then
        assertThat(doctorAvailabilityRequestDTOList)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("doctorAvailabilityId")
                .containsExactlyInAnyOrder(doctorAvailabilityRequestDTO1, doctorAvailabilityRequestDTO2);
    }

    @Test
    void addDoctorAvailabilityRequest() {
        // given

        MemberDTO memberDoctorDTO = createAndAddMemberDTO();
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());
        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO1 =
                createDoctorAvailabilityRequestDto1WithDoctorId(doctor.getDoctorId());

        //when
        addDoctorAvailabilityRequest(doctorAvailabilityRequestDTO1);
        List<DoctorAvailabilityRequestDTO> doctorAvailabilityRequestDTOList =
                getDoctorAvailabilityList(doctor.getDoctorId());
        //then
        assertThat(doctorAvailabilityRequestDTOList).hasSize(1);

        DoctorAvailabilityRequestDTO addedRequest = doctorAvailabilityRequestDTOList.get(0);
        assertThat(addedRequest.getDoctorId()).isEqualTo(doctor.getDoctorId());
        assertThat(addedRequest.getDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(addedRequest.getHourFrom()).isEqualTo(LocalTime.of(7, 0));
        assertThat(addedRequest.getReserved()).isEqualTo(Boolean.FALSE);
    }

    @Test
    void updateDoctorAvailabilityRequest() {
        // given
        MemberDTO memberDoctorDTO = createAndAddMemberDTO();
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());
        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO1 =
                createDoctorAvailabilityRequestDto1WithDoctorId(doctor.getDoctorId());

        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO2 =
                createDoctorAvailabilityRequestDto2WithDoctorId(doctor.getDoctorId());

        //when
        addDoctorAvailabilityRequest(doctorAvailabilityRequestDTO1);
        List<DoctorAvailabilityRequestDTO> doctorAvailabilityRequestDTOListBeforeUpdate =
                getDoctorAvailabilityList(doctor.getDoctorId());
        Integer doctorAvailabilityId = doctorAvailabilityRequestDTOListBeforeUpdate.get(0).getDoctorAvailabilityId();
        updateDoctorAvailabilityRequest(doctorAvailabilityId, doctorAvailabilityRequestDTO2);
        List<DoctorAvailabilityRequestDTO> doctorAvailabilityRequestDTOListAfterUpdate =
                getDoctorAvailabilityList(doctor.getDoctorId());
        //then
        assertThat(doctorAvailabilityRequestDTOListAfterUpdate).hasSize(1);

        DoctorAvailabilityRequestDTO addedRequest = doctorAvailabilityRequestDTOListAfterUpdate.get(0);
        assertThat(addedRequest.getDoctorId()).isEqualTo(doctor.getDoctorId());
        assertThat(addedRequest.getDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(addedRequest.getHourFrom()).isEqualTo(LocalTime.of(8, 0));
        assertThat(addedRequest.getReserved()).isEqualTo(Boolean.FALSE);
    }

    @Test
    void deleteAddDoctorAvailabilityRequest() {
        // given
        MemberDTO memberDoctorDTO = createAndAddMemberDTO();
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());
        DoctorAvailabilityRequestDTO doctorAvailabilityRequestDTO1 =
                createDoctorAvailabilityRequestDto1WithDoctorId(doctor.getDoctorId());

        //when
        addDoctorAvailabilityRequest(doctorAvailabilityRequestDTO1);
        List<DoctorAvailabilityRequestDTO> beforeDeletedoctorAvailabilityRequestDTOList =
                getDoctorAvailabilityList(doctor.getDoctorId());
        deleteDoctorAvailabilityRequest(beforeDeletedoctorAvailabilityRequestDTOList.get(0).getDoctorAvailabilityId());
        List<DoctorAvailabilityRequestDTO> afterDeletedoctorAvailabilityRequestDTOList =
                getDoctorAvailabilityList(doctor.getDoctorId());
        //then
        assertThat(afterDeletedoctorAvailabilityRequestDTOList).hasSize(0);

    }

    private MemberDTO createAndAddMemberDTO() {
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

    private DoctorAvailabilityRequestDTO createDoctorAvailabilityRequestDto1WithDoctorId(Integer doctorId) {
        return DoctorAvailabilityRequestDTO.builder()
                .doctorId(doctorId)
                .date(LocalDate.of(2025, 1, 1))
                .hourFrom(LocalTime.of(7, 0))
                .reserved(Boolean.FALSE)
                .build();
    }

    private DoctorAvailabilityRequestDTO createDoctorAvailabilityRequestDto2WithDoctorId(Integer doctorId) {
        return DoctorAvailabilityRequestDTO.builder()
                .doctorId(doctorId)
                .date(LocalDate.of(2025, 1, 1))
                .hourFrom(LocalTime.of(8, 0))
                .reserved(Boolean.FALSE)
                .build();
    }
}