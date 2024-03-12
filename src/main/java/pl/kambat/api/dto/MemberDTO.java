package pl.kambat.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotBlank
    @Pattern(regexp = "^(PATIENT|DOCTOR)$")
    private String userType;
    @NotBlank
    private String userName;
    @NotBlank
    private String userSurname;
    @NotBlank
    @Email
    private String userEmail;
    @Size(min = 3, max = 32)
    private String userLogin;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$")
    private String userPassword;
    @Size(min = 11, max = 11)
    private String userPesel;
    private String userPwzNumber;
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String userPhoneNumber;
    @NotBlank
    private String userAddressCountry;
    @NotBlank
    private String userAddressCity;
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
    private String userAddressPostalCode;
    @NotBlank
    private String userAddressStreet;

    public static MemberDTO buildDefault() {
        return MemberDTO.builder()
                .userType("Patient")
                .userName("Bolek")
                .userSurname("Szukalski")
                .userEmail("bol.szu@gmail.com")
                .userLogin("bolszu123")
                .userPassword("bolO23sr")
                .userPesel("56321456987")
                //.userPwzNumber("12365987")
                .userPhoneNumber("+48 111 111 111")
                .userAddressCountry("Polska")
                .userAddressCity("Kozia Wólka")
                .userAddressPostalCode("00-569")
                .userAddressStreet("Borowa 23")
                .build();
    }
}
