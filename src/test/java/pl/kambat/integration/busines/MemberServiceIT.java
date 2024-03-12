package pl.kambat.integration.busines;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kambat.api.dto.MemberDTO;
import pl.kambat.api.dto.mapper.MemberMapper;
import pl.kambat.business.MemberService;
import pl.kambat.domain.Member;
import pl.kambat.domain.exception.DataAlreadyExistsException;
import pl.kambat.infrastucture.database.repository.jpa.DoctorAvailabilityJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.infrastucture.database.repository.jpa.PatientJpaRepository;
import pl.kambat.integration.configuration.AbstractIT;
import pl.kambat.util.DataFixtures;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MemberServiceIT extends AbstractIT {


    private final MemberService memberService;
    private final PatientJpaRepository patientJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;


    @BeforeEach
    void beforeEach() {

        doctorAvailabilityJpaRepository.deleteAll();
        patientJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
        Member memberPatientDTO = someMemberDTO1();
        Member memberDoctorDTO = someMemberDTO2();

        if (!memberService.isMemberExists(memberDoctorDTO)) {
            memberService.addUser(memberDoctorDTO);
        }
        if (!memberService.isMemberExists(memberPatientDTO)) {
            memberService.addUser(memberPatientDTO);
        }

    }

    @AfterEach
    void afterAll() {
        patientJpaRepository.deleteAll();
        doctorJpaRepository.deleteAll();
    }

    @Test
    void checkAddingAnExistingUser() {
        //given
        Member newMember = someMemberDTO1();

        // when and then
        assertThrows(DataAlreadyExistsException.class, () -> {
            memberService.addUser(newMember);
        });
    }

    @Test
    void existsPatient() {
        //given
        String existingPesel = "44444444444";
        String noExistingPesel = "11111111111";

        // when and then
        assertTrue(memberService.isExistsPatientPesel(existingPesel));
        assertFalse(memberService.isExistsPatientPesel(noExistingPesel));
    }

    @Test
    void existsDoctor() {
        //given
        String existingPesel = "55555555555";
        String noExistingPesel = "11111111111";

        // when and then
        assertTrue(memberService.isExistsDoctorPesel(existingPesel));
        assertFalse(memberService.isExistsDoctorPesel(noExistingPesel));
    }

    @Test
    void existsInternetClinicUserLogin() {
        //given
        String existingLogin = "miroslaw123";
        String noExistingLogin = "noExistingLogin";

        // when and then
        assertTrue(memberService.existsInternetClinicUserLogin(existingLogin));
        assertFalse(memberService.existsInternetClinicUserLogin(noExistingLogin));
    }

    @Test
    void existsInternetClinicUserEmail() {
        //given
        String existingEmail = "miroslaw123@gmail.com";
        String noExistingEmail = "noExistingEmail@gmail.com";

        // when and then
        assertTrue(memberService.existsInternetClinicUserEmail(existingEmail));
        assertFalse(memberService.existsInternetClinicUserEmail(noExistingEmail));
    }



    public static Member someMemberDTO1() {
        return Member.builder()
                .userType("PATIENT")
                .userName("Tomasz")
                .userSurname("Miesek")
                .userEmail("miroslaw123@gmail.com")
                .userLogin("miroslaw123")
                .userPassword("mirO23sr")
                .userPesel("44444444444")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Kozia Wólka")
                .userAddressPostalCode("00-569")
                .userAddressStreet("Borowa 23")
                .build();
    }

    public static Member someMemberDTO2() {
        return Member.builder()
                .userType("DOCTOR")
                .userName("Miroslaw")
                .userSurname("Mirkowski")
                .userEmail("mirek@gmail.com")
                .userLogin("mirek")
                .userPassword("mirO23sr")
                .userPesel("55555555555")
                .userPwzNumber("56565656")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Warszawa")
                .userAddressPostalCode("00-001")
                .userAddressStreet("ul. Warszawska 1")
                .build();
    }
}