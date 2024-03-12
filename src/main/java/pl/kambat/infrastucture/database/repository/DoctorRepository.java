package pl.kambat.infrastucture.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.kambat.business.dao.DoctorDAO;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.infrastucture.database.entity.DoctorEntity;
import pl.kambat.infrastucture.database.repository.jpa.DoctorJpaRepository;
import pl.kambat.infrastucture.database.repository.mapper.DoctorEntityMapper;
import pl.kambat.domain.Doctor;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {

    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;

    @Override
    public Optional<Doctor> findByPesel(String pesel) {
        return doctorJpaRepository.findByPesel(pesel).map(doctorEntityMapper::mapFromEntity);
    }


    @Override
    public Doctor saveDoctor(Doctor doctor) {
        DoctorEntity toSave = doctorEntityMapper.mapToEntity(doctor);
        DoctorEntity saved = doctorJpaRepository.saveAndFlush(toSave);
        return doctorEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Doctor> findByUserId(InternetClinicUserEntity doctorID) {
        return doctorJpaRepository.findByUserId(doctorID).map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Doctor> findByPwzNumber(String pwzNumber) {
        return doctorJpaRepository.findByPwzNumber(pwzNumber).map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Doctor> findDoctorById(Integer doctorId) {
        return doctorJpaRepository.findById(doctorId).map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public void changeDoctorVerifiedStatusById(Integer doctorID, Boolean status) {
        DoctorEntity doctorEntityById = doctorJpaRepository.findById(doctorID)
                .orElseThrow(() -> new NotDataFoundException(
                "Could not find doctor by ID:[%s]"
                        .formatted(doctorID))
                );
        doctorEntityById.setVerified(status);
        doctorJpaRepository.saveAndFlush(doctorEntityById);
    }

}
