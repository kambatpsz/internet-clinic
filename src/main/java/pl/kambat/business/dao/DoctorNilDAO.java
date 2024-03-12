package pl.kambat.business.dao;

import pl.kambat.domain.DoctorNIL;

import java.util.Optional;

public interface DoctorNilDAO {
    Optional<DoctorNIL> getDoctorFromNilByPwzNumber(String pwzNumber);
}
