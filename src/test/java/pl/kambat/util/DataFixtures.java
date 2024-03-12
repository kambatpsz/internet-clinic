package pl.kambat.util;

import lombok.experimental.UtilityClass;
import pl.kambat.api.dto.*;
import pl.kambat.domain.*;
import pl.kambat.infrastucture.database.entity.*;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@UtilityClass
public class DataFixtures {

    public static InternetClinicUser someInternetClinicUser1() {
        return InternetClinicUser.builder()
                .userName("mirek")
                .email("mirek@gmail.com")
                .password("test")
                .phone("+48 512 456 789")
                .build();
    }

    public static InternetClinicUserEntity someInternetClinicUserEntity1() {
        return InternetClinicUserEntity.builder()
                .userId(1)
                .userName("mirek")
                .email("mirek@gmail.com")
                .password("test")
                .phone("+48 512 456 789")
                .active(Boolean.TRUE)
                .build();
    }

    public static Address someAddress1() {
        return Address.builder()
                .country("Polska")
                .city("Warszawa")
                .postalCode("00-001")
                .address("ul. Warszawska 1")
                .build();
    }

    public static AddressEntity someAddressEntity1() {
        return AddressEntity.builder()
                .country("Polska")
                .city("Warszawa")
                .postalCode("00-001")
                .address("ul. Warszawska 1")
                .build();
    }

    public static Doctor someDoctor1() {
        return Doctor.builder()
                .doctorId(1)
                .userId(someInternetClinicUser1())
                .name("Mirek")
                .surname("Mirkowski")
                .pesel("65321456935")
                .pwzNumber("12365987")
                .address(someAddress1())
                .build();
    }
    public static DoctorNIL someDoctorNIL1() {
        return DoctorNIL.builder()
                .name("Mirek")
                .surname("Mirkowski")
                .pesel("65321456935")
                .pwzNumber("12365987")
                .build();
    }

    public static DoctorDTO someDoctorDTO1() {
        return DoctorDTO.builder()
                .name("Mirek")
                .surname("Mirkowski")
                .pesel("65321456935")
                .pwzNumber("12365987")
                .build();
    }

    public static DoctorEntity someDoctorEntity1() {
        return DoctorEntity.builder()
                .doctorId(1)
                .userId(DataFixtures.someInternetClinicUserEntity1())
                .name("Mirek")
                .surname("Mirkowski")
                .pesel("65321456935")
                .pwzNumber("12365987")
                .address(DataFixtures.someAddressEntity1())
                .verified(Boolean.TRUE)
                .build();
    }

    public static InternetClinicUser someInternetClinicUser3() {
        return InternetClinicUser.builder()
                .userName("bolek")
                .email("bolekk@gmail.com")
                .password("test")
                .phone("+48 512 111 789")
                .build();
    }

    public static Address someAddress3() {
        return Address.builder()
                .country("Polska")
                .city("Poznań")
                .postalCode("64-001")
                .address("ul. Poznańska 1")
                .build();
    }

    public static Patient somePatient1() {
        return Patient.builder()
                .userId(someInternetClinicUser3())
                .name("Bolek")
                .surname("Bolkowski")
                .pesel("75321695478")
                .address(someAddress3())
                .build();
    }


    public static PatientDTO somePatientDTO1() {
        return PatientDTO.builder()
                .name("Bolek")
                .surname("Bolkowski")
                .pesel("75321695478")
                .build();
    }

    public static PatientEntity somePatientEntity1() {
        return PatientEntity.builder()
                .patientId(1)
                .userId(DataFixtures.someInternetClinicUserEntity2())
                .name("Bolek")
                .surname("Bolkowski")
                .pesel("75321695478")
                .address(DataFixtures.someAddressEntity2())
                .build();
    }

    public static InternetClinicUserEntity someInternetClinicUserEntity2() {
        return InternetClinicUserEntity.builder()
                .userId(1)
                .userName("bolek")
                .email("bolekk@gmail.com")
                .password("test")
                .phone("+48 512 111 789")
                .active(Boolean.TRUE)
                .build();
    }

    public static AddressEntity someAddressEntity2() {
        return AddressEntity.builder()
                .country("Polska")
                .city("Poznań")
                .postalCode("64-001")
                .address("ul. Poznańska 1")
                .build();
    }


    public static InternetClinicUser someInternetClinicUser4() {
        return InternetClinicUser.builder()
                .userName("tola")
                .email("tola@gmail.com")
                .password("test")
                .phone("+48 777 111 789")
                .build();
    }

    public static Address someAddress4() {
        return Address.builder()
                .country("Polska")
                .city("Kraków")
                .postalCode("32-001")
                .address("ul.Krakowskia 1")
                .build();
    }

    public static Patient somePatient2() {
        return Patient.builder()
                .userId(someInternetClinicUser4())
                .name("Tola")
                .surname("Tolkowska")
                .pesel("96357426354")
                .address(someAddress4())
                .build();
    }

    public static PatientVisitEntity somePatientVisitEntity1() {
        return PatientVisitEntity.builder()
                .dateTimeVisit(LocalDateTime.now())
                .visitStatus(Boolean.TRUE)
                .visitNote("")
                .doctor(someDoctorEntity1())
                .patient(somePatientEntity1())
                .build();
    }

    public static PatientVisit somePatientVisit1() {
        return PatientVisit.builder()
                .dateTimeVisit(LocalDateTime.now())
                .visitStatus(Boolean.TRUE)
                .visitNote("")
                .doctor(someDoctor1())
                .patient(somePatient1())
                .build();
    }

    public static PatientVisitDTO somePatientVisitDTO1() {
        return PatientVisitDTO.builder()
                .dateTimeVisit(LocalDateTime.now())
                .visitStatus(Boolean.TRUE)
                .visitNote("note")
                .doctor(someDoctor1())
                .patient(somePatient1())
                .build();
    }

    public static PatientVisit somePatientVisit2() {
        return PatientVisit.builder()
                .dateTimeVisit(LocalDateTime.of(2024, 2, 19, 10, 0))
                .visitStatus(Boolean.FALSE)
                .visitNote("Zalecany odpoczynek")
                .doctor(someDoctor1())
                .patient(somePatient2())
                .build();
    }

    public static DoctorAvailabilityDTO someDoctorAvailabilityDTO1() {
        return DoctorAvailabilityDTO.builder()
                .doctor(DataFixtures.someDoctorInDoctorAvailability())
                .date(LocalDate.of(2024, 1, 1))
                .hourFrom(LocalTime.of(7, 0))
                .hourTo(LocalTime.of(7, 20))
                .reserved(Boolean.FALSE)
                .build();
    }

    public static DoctorAvailability someDoctorAvailability1() {
        return DoctorAvailability.builder()
                .doctor(DataFixtures.someDoctorInDoctorAvailability())
                .date(LocalDate.of(2024, 1, 1))
                .hourFrom(LocalTime.of(7, 0))
                .hourTo(LocalTime.of(7, 20))
                .reserved(Boolean.FALSE)
                .build();
    }

    public static Doctor someDoctorInDoctorAvailability() {
        return
                Doctor.builder()
                        .doctorId(1)
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
                        .pwzNumber("12365987")
                        .address(someAddress1())
                        .build();
    }

    public static DoctorAvailabilityEntity someDoctorAvailabilityEntity1() {
        return DoctorAvailabilityEntity.builder()
                .date(LocalDate.of(2024, 1, 1))
                .hourFrom(LocalTime.of(7, 0))
                .hourTo(LocalTime.of(7, 20))
                .doctor(DataFixtures.someDoctorEntity1())
                .reserved(Boolean.FALSE)
                .build();
    }

    public static MemberDTO someMemberDTO1() {
        return MemberDTO.builder()
                .userType("PATIENT")
                .userName("Miroslaw")
                .userSurname("Dezert")
                .userEmail("mir.dez@gmail.com")
                .userLogin("miro123")
                .userPassword("mirO23sr")
                .userPesel("75468963251")
                .userPwzNumber("12365987")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Kozia Wólka")
                .userAddressPostalCode("00-569")
                .userAddressStreet("Borowa 23")
                .build();
    }
    public static Member someMember1() {
        return Member.builder()
                .userType("PATIENT")
                .userName("Miroslaw")
                .userSurname("Dezert")
                .userEmail("mir.dez@gmail.com")
                .userLogin("miro123")
                .userPassword("mirO23sr")
                .userPesel("75468963251")
                .userPwzNumber("12365987")
                .userPhoneNumber("+48 512 456 789")
                .userAddressCountry("Polska")
                .userAddressCity("Kozia Wólka")
                .userAddressPostalCode("00-569")
                .userAddressStreet("Borowa 23")
                .build();
    }

    public static MemberDTO someMemberDTO2() {
        return MemberDTO.builder()
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
    }
}