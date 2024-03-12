package pl.kambat.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.PatientDAO;
import pl.kambat.infrastucture.database.repository.mapper.InternetClinicUserEntityMapper;
import pl.kambat.domain.*;
import pl.kambat.domain.exception.NotDataFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final PatientDAO patientDAO;
    private final InternetClinicUserService internetClinicUserService;
    private final InternetClinicUserEntityMapper internetClinicUserEntityMapper;

    @Transactional
    public List<Patient> findAllPatients(){
        return patientDAO.findAllPatients();
    }

    @Transactional
    public Patient findPatientsByPesel(String pesel){
        return patientDAO.findByPesel(pesel).orElseThrow(
                () -> new NotDataFoundException(
                        "Could not find patient by pesel:[%s]"
                                .formatted(pesel))
        );
    }

    public InternetClinicMember createPatient(Member newMember) {
        Patient patient =
        Patient.builder()
                .userId(
                        InternetClinicUser.builder()
                                .userName(newMember.getUserLogin())
                                .email(newMember.getUserEmail())
                                .password(encodePassword(newMember.getUserPassword()))
                                .phone(newMember.getUserPhoneNumber())
                                .roles(Set.of(InternetClinicRole.builder()
                                        .role(newMember.getUserType())
                                        .build()))
                                .active(Boolean.TRUE)
                                .build()
                )
                .name(newMember.getUserName())
                .surname(newMember.getUserSurname())
                .pesel(newMember.getUserPesel())
                .address(
                        Address.builder()
                                .country(newMember.getUserAddressCountry())
                                .city(newMember.getUserAddressCity())
                                .postalCode(newMember.getUserAddressPostalCode())
                                .address(newMember.getUserAddressStreet())
                                .build()
                )
                .patientVisit(new HashSet<>())
                .build();
        savePatient(patient);
        return patient;
    }

    private String encodePassword(String password)
    {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public void savePatient(Patient patient)
    {
        patientDAO.savePatient(patient);
    }

    @Transactional
    public void deleteAllPatient()
    {
        patientDAO.deleteAllPatient();
    }

    @Transactional
    public Patient getLoggedPatient() {
        InternetClinicUser patientID = internetClinicUserService.getLoggedUser();
        return patientDAO.findByUserId(internetClinicUserEntityMapper.mapToEntity(patientID))
                .orElseThrow(() -> new NotDataFoundException(
                "Could not find patient by ID:[%s]"
                        .formatted(patientID.getUserId())
        ));
    }

}
