package pl.kambat.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.business.DoctorService;
import pl.kambat.business.MemberService;
import pl.kambat.business.PatientService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.domain.Member;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MemberService memberService;
    private final DoctorAvailabilityService doctorAvailabilityService;

    @Override
    @Transactional
    public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {

//      Create of test users
        createRestApiTesterUser();
        createTesterUser(doctorMember());
        createTesterUser(patientMember());
    }

    private void createRestApiTesterUser() {
        Member restApiTestUser = Member.builder()
                .userType("REST_API")
                .userName("REST_API")
                .userSurname("REST_API")
                .userEmail("REST_API@gmail.com")
                .userLogin("REST_API_TEST")
                .userPassword("REST_API_TEST")
                .userPesel("99999999999")
                .userPhoneNumber("+00 000 000 000")
                .userAddressCountry("")
                .userAddressCity("")
                .userAddressPostalCode("")
                .userAddressStreet("")
                .build();
        if(!memberService.isMemberExists(restApiTestUser))
        {
            patientService.createPatient(restApiTestUser);
        }
    }

    private void createTesterUser(Member member) {
        if(!memberService.isMemberExists(member))
        {
            memberService.addUser(member);
            if("DOCTOR".equals(member.getUserType())){
                Doctor doctor = doctorService.getDoctorByPwzNumber(member.getUserPwzNumber());
                String date = LocalDate.now().plusDays(3).toString();
                List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityService.createNewAvailability(
                        doctor,
                        date,
                        "12",
                        "00",
                        "2"
                );
                for (DoctorAvailability doctorAvailability : doctorAvailabilityList) {
                    doctorAvailabilityService.saveNewDoctorAvailability(doctorAvailability);
                }

            }
        }
    }


    public static Member restApiMember() {
        return  Member.builder()
                .userType("REST_API")
                .userName("REST_API")
                .userSurname("REST_API")
                .userEmail("REST_API@gmail.com")
                .userLogin("REST_API_TEST")
                .userPassword("REST_API_TEST")
                .userPesel("99999999999")
                .userPhoneNumber("+00 000 000 000")
                .userAddressCountry("")
                .userAddressCity("")
                .userAddressPostalCode("")
                .userAddressStreet("")
                .build();
    }

    public static Member patientMember() {
        return  Member.builder()
                .userType("PATIENT")
                .userName("Magdalena")
                .userSurname("Las")
                .userEmail("mag.las@gmail.com")
                .userLogin("patient")
                .userPassword("test")
                .userPesel("89652332145")
                .userPhoneNumber("+48 345 456 543")
                .userAddressCountry("Polska")
                .userAddressCity("Lublin")
                .userAddressPostalCode("20-003")
                .userAddressStreet("Brylantowa 4")
                .build();
    }

    public static Member doctorMember() {
        return  Member.builder()
                .userType("DOCTOR")
                .userName("Miroslaw")
                .userSurname("Dezert")
                .userEmail("mir.dez@gmail.com")
                .userLogin("doctor")
                .userPassword("test")
                .userPesel("75468963251")
                .userPwzNumber("12365987")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Kozia Wólka")
                .userAddressPostalCode("00-569")
                .userAddressStreet("Borowa 23")
                .build();
    }
}