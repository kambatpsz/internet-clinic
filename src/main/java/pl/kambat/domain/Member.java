package pl.kambat.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Member {

    String userType;
    String userName;
    String userSurname;
    String userEmail;
    String userLogin;
    String userPassword;
    String userPesel;
    String userPwzNumber;
    String userPhoneNumber;
    String userAddressCountry;
    String userAddressCity;
    String userAddressPostalCode;
    String userAddressStreet;
}
