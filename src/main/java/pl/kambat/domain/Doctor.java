package pl.kambat.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(of = {"name", "surname", "pesel", "pwzNumber"})
public class Doctor implements InternetClinicMember {

    Integer doctorId;
    InternetClinicUser userId;
    String name;
    String surname;
    String pesel;
    String pwzNumber;
    Address address;
    Boolean verified;
    Set<DoctorAvailability> doctorAvailability;
    Set<PatientVisit> patientVisit;
}
