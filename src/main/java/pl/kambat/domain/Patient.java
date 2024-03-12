package pl.kambat.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(of = {"name", "surname", "pesel"})
public class Patient implements InternetClinicMember {
    Integer patientId;
    InternetClinicUser userId;
    String name;
    String surname;
    String pesel;
    Address address;
    Set<PatientVisit> patientVisit;
}
