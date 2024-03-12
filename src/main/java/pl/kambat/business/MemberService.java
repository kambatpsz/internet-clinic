package pl.kambat.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.DoctorDAO;
import pl.kambat.business.dao.InternetClinicUserDAO;
import pl.kambat.domain.*;
import pl.kambat.business.dao.PatientDAO;
import pl.kambat.domain.exception.DataAlreadyExistsException;
import pl.kambat.domain.exception.ProcessingDataException;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService {

    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final InternetClinicUserDAO internetClinicUserDAO;
    private final PatientService patientService;
    private final DoctorService doctorService;


    public InternetClinicMember addUser(Member newMember) {
        if (isMemberExists(newMember)) {
            log.info("The provided login, email, or pesel already exists");
            throw new DataAlreadyExistsException("User with the provided login, email or pesel already exists");
        }

        InternetClinicMember internetClinicMember = createMember(newMember);
        log.info("Successfully added user: [{} {}, {},{}]",
                newMember.getUserName(),
                newMember.getUserSurname(),
                newMember.getUserLogin(),
                newMember.getUserEmail());
        return internetClinicMember;
    }

    public boolean isMemberExists(Member newMember) {
        return isExistsDoctorPesel(newMember.getUserPesel()) ||
                isExistsDoctorPwzNumber(newMember.getUserPwzNumber()) ||
               isExistsPatientPesel(newMember.getUserPesel()) ||
               existsInternetClinicUserLogin(newMember.getUserLogin()) ||
               existsInternetClinicUserEmail(newMember.getUserEmail());
    }

    private InternetClinicMember createMember(Member newMember) {

        if ("PATIENT".equals(newMember.getUserType())) {
            return patientService.createPatient(newMember);
        }
        if ("DOCTOR".equals(newMember.getUserType()) && newMember.getUserPwzNumber().length() == 8) {
            return doctorService.createDoctor(newMember);
        }
        log.info("Member not created, requirements have not been met");
        throw new ProcessingDataException("Member not created, requirements have not been met");
    }


    @Transactional
    public Boolean isExistsPatientPesel(String pesel) {
        Optional<Patient> patient = patientDAO.findByPesel(pesel);
        return patient.isPresent();
    }

    @Transactional
    public Boolean isExistsDoctorPesel(String pesel) {
        Optional<Doctor> doctor = doctorDAO.findByPesel(pesel);
        return doctor.isPresent();
    }
    @Transactional
    public Boolean isExistsDoctorPwzNumber(String pwzNumber) {
        if(pwzNumber != null){
            Optional<Doctor> doctor = doctorDAO.findByPwzNumber(pwzNumber);
            return doctor.isPresent();
        }
        return false;
    }

    @Transactional
    public Boolean existsInternetClinicUserLogin(String login) {
        Optional<InternetClinicUser> internetClinicUser = internetClinicUserDAO.findByLogin(login);
        return internetClinicUser.isPresent();
    }

    @Transactional
    public Boolean existsInternetClinicUserEmail(String email) {
        Optional<InternetClinicUser> internetClinicUser = internetClinicUserDAO.findByEmail(email);
        return internetClinicUser.isPresent();
    }
}
