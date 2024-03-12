package pl.kambat.integration.busines;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kambat.api.dto.DoctorAvailabilityDTO;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityMapper;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.business.DoctorService;
import pl.kambat.business.MemberService;
import pl.kambat.domain.*;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.repository.jpa.DoctorAvailabilityJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.integration.configuration.AbstractIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class DoctorAvailabilityServiceIT extends AbstractIT {


    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MemberDTO memberDoctorDTO = createMemberDTO();
        memberService.addUser(memberMapper.map(memberDoctorDTO));
        Doctor doctor = doctorService.getDoctorByPwzNumber(memberDoctorDTO.getUserPwzNumber());
        DoctorAvailabilityDTO doctorAvailabilityDAO =
                createDoctorAvailabilityDtoWithDoctorId(doctor.getDoctorId());
        doctorAvailabilityService.saveNewDoctorAvailability(doctorAvailabilityMapper.map(doctorAvailabilityDAO));
    }

    @AfterEach
    void tearDown() {
        doctorAvailabilityJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
    }

    @Test
    void findAllDoctorAvailabilityByDoctor() {
        //given
        Doctor doctor = doctorService.getDoctorByPwzNumber("77777777");
        //when
        List<DoctorAvailability> doctorAvailabilityList =
                doctorAvailabilityService.findAllDoctorAvailabilityByDoctor(doctor);
        //then
        assertEquals(1, doctorAvailabilityList.size());
    }

    @Test
    void findDoctorAvailabilityByDate() {
        //given
        Doctor doctor = doctorService.getDoctorByPwzNumber("77777777");
        LocalDateTime date1 = LocalDateTime.of(2024, 1, 1, 7, 0);
        LocalDateTime date2 = LocalDateTime.now();

        //when then
        DoctorAvailability doctorAvailabilityExisting =
                doctorAvailabilityService.findDoctorAvailabilityByDate(doctor, date1);

        assertEquals(doctor, doctorAvailabilityExisting.getDoctor());
        assertEquals(date1.toLocalDate(), doctorAvailabilityExisting.getDate());

        assertThatThrownBy(() -> doctorAvailabilityService.findDoctorAvailabilityByDate(doctor, date2))
                .isInstanceOf(NotDataFoundException.class)
                .hasMessageContaining("Could not find doctor availability by doctorID:[%s], and date:[%s]"
                        .formatted(doctor.getDoctorId(), date2));
    }

    private DoctorAvailabilityDTO createDoctorAvailabilityDtoWithDoctorId(Integer doctorId) {
        return DoctorAvailabilityDTO.builder()
                .doctor(Doctor.builder()
                        .doctorId(doctorId)
                        .userId(InternetClinicUser.builder()
                                .userName("mirek")
                                .email("mirek@gmail.com")
                                .password("test")
                                .phone("+48 512 456 789")
                                .roles(Set.of(InternetClinicRole.builder()
                                        .role("DOCTOR")
                                        .build()))
                                .build())
                        .name("Mirek")
                        .surname("Mirkowski")
                        .pesel("65321456935")
                        .pwzNumber("77777777")
                        .address(Address.builder()
                                .country("Polska")
                                .city("Warszawa")
                                .postalCode("00-001")
                                .address("ul. Warszawska 1")
                                .build())
                        .build())
                .date(LocalDate.of(2024, 1, 1))
                .hourFrom(LocalTime.of(7, 0))
                .hourTo(LocalTime.of(7, 20))
                .reserved(Boolean.FALSE)
                .build();
    }

    private MemberDTO createMemberDTO() {
        return MemberDTO.builder()
                .userType("DOCTOR")
                .userName("Miroslaw")
                .userSurname("Mirkowski")
                .userEmail("mirek@gmail.com")
                .userLogin("mirek")
                .userPassword("mirO23sr")
                .userPesel("65321456935")
                .userPwzNumber("77777777")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Warszawa")
                .userAddressPostalCode("00-001")
                .userAddressStreet("ul. Warszawska 1")
                .build();
    }
}