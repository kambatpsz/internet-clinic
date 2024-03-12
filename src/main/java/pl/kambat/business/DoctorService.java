package pl.kambat.business;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.DoctorDAO;
import pl.kambat.domain.*;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.repository.mapper.InternetClinicUserEntityMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO doctorDAO;
    private final InternetClinicUserService internetClinicUserService;
    private final InternetClinicUserEntityMapper internetClinicUserEntityMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public InternetClinicMember createDoctor(Member newMember) {
        Doctor doctor =
                Doctor.builder()
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
                        .pwzNumber(newMember.getUserPwzNumber())
                        .address(
                                Address.builder()
                                        .country(newMember.getUserAddressCountry())
                                        .city(newMember.getUserAddressCity())
                                        .postalCode(newMember.getUserAddressPostalCode())
                                        .address(newMember.getUserAddressStreet())
                                        .build()
                        )
                        .verified(Boolean.FALSE)
                        .doctorAvailability(new HashSet<>())
                        .patientVisit(new HashSet<>())
                        .build();
        saveDoctor(doctor);
        return doctor;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    private void saveDoctor(Doctor doctor) {
        doctorDAO.saveDoctor(doctor);
    }

    @Transactional
    public Doctor getLoggedDoctor() {
        InternetClinicUser doctorID = internetClinicUserService.getLoggedUser();
        return doctorDAO.findByUserId(internetClinicUserEntityMapper.mapToEntity(doctorID))
                .orElseThrow(() -> new NotDataFoundException(
                        "Could not find doctor by ID:[%s]"
                                .formatted(doctorID.getUserId())
                ));
    }

    @Transactional
    public Doctor getDoctorByPwzNumber(String pwzNumber) {
        return doctorDAO.findByPwzNumber(pwzNumber).orElseThrow(
                () -> new NotDataFoundException(
                        "Could not find doctor by PWZ number:[%s]"
                                .formatted(pwzNumber))
        );
    }

    @Transactional
    public List<Doctor> findAllDoctors() {
        return doctorDAO.findAll();
    }

    public Doctor getDoctorById(Integer doctorId) {
        return doctorDAO.findDoctorById(doctorId)
                .orElseThrow(() -> new NotDataFoundException(
                        "Could not find Doctor by doctorID:[%s]"
                                .formatted(doctorId)
                ));
    }

    @Transactional
    public void changeDoctorVerifiedStatusById(Integer doctorId, Boolean status) {
        doctorDAO.changeDoctorVerifiedStatusById(doctorId, status);
    }

    public boolean verifyDoctor(Doctor doctor, Doctor doctorFromNil) {
        return doctorFromNil.getName().equals(doctor.getName()) &&
                doctorFromNil.getSurname().equals(doctor.getSurname()) &&
                doctorFromNil.getPesel().equals(doctor.getPesel()) &&
                doctorFromNil.getPwzNumber().equals(doctor.getPwzNumber());
    }
}