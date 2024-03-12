package pl.kambat.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kambat.business.dao.DoctorNilDAO;
import pl.kambat.domain.DoctorNIL;

@Service
@AllArgsConstructor
public class DoctorNilService {

    private final DoctorNilDAO doctorNilDAO;

    public DoctorNIL getDoctorByPwzNumber(String pwzNumber) {
        return doctorNilDAO.getDoctorFromNilByPwzNumber(pwzNumber).orElse(null);
    }
}