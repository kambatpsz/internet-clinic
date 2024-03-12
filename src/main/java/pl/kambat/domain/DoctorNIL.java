package pl.kambat.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(of = {"name", "surname", "pesel", "pwzNumber"})
public class DoctorNIL{

    String id;
    String name;
    String surname;
    String pesel;
    String pwzNumber;
}
