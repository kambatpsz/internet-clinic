package pl.kambat.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class InternetClinicRole {

    Integer role_id;
    String role;
}
