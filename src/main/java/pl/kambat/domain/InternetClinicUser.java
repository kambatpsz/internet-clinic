package pl.kambat.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"userName", "email", "phone"})
public class InternetClinicUser {

    Integer userId;
    String userName;
    String email;
    String password;
    String phone;
    Boolean active;
    Set<InternetClinicRole> roles;
}
