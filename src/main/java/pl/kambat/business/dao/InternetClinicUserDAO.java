package pl.kambat.business.dao;

import pl.kambat.domain.InternetClinicUser;

import java.util.Optional;

public interface InternetClinicUserDAO {

    Optional<InternetClinicUser> findByEmail(String email);
    Optional<InternetClinicUser> findByLogin(String name);

}
