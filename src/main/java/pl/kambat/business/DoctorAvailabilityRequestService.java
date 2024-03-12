package pl.kambat.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.api.dto.mapper.DoctorAvailabilityRequestMapper;
import pl.kambat.business.dao.DoctorAvailabilityDAO;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.domain.DoctorAvailabilityRequest;
import pl.kambat.domain.exception.DataAlreadyExistsException;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.domain.exception.ProcessingDataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorAvailabilityRequestService {

    private final DoctorAvailabilityDAO doctorAvailabilityDAO;
    private final DoctorAvailabilityRequestMapper doctorAvailabilityRequestMapper;
    private final DoctorService doctorService;


    @Transactional
    public List<DoctorAvailabilityRequest> findAllDoctorAvailabilityRequestByDoctor(Doctor doctor) {
        LocalDate actualDate = LocalDate.now();
        return doctorAvailabilityDAO.findAllAvailabilityByDoctor(doctor).stream()
                .filter(dA -> dA.getDate().isAfter(actualDate) || dA.getDate().isEqual(actualDate))
                .map(doctorAvailabilityRequestMapper::mapToRequest)
                .toList();
    }

    @Transactional
    public DoctorAvailabilityRequest saveNewDoctorAvailabilityRequest(
            DoctorAvailabilityRequest newDoctorAvailabilityRequest) {

        DoctorAvailability newDoctorAvailability = buildDoctorAvailabilityFromRequest(newDoctorAvailabilityRequest);
        checkTheTime(newDoctorAvailabilityRequest.getHourFrom());

        if (doctorAvailabilityDAO.checkIfDoctorAvailabilityAlreadyExists(newDoctorAvailability)) {
            throw new DataAlreadyExistsException("Doctor availability already exists");
        }

        doctorAvailabilityDAO.saveDoctorAvailability(newDoctorAvailability);

        return newDoctorAvailabilityRequest;
    }

    public void updateDoctorAvailabilityRequest(
            DoctorAvailabilityRequest newDoctorAvailabilityRequest) {

        DoctorAvailability newDoctorAvailability = buildDoctorAvailabilityFromRequest(newDoctorAvailabilityRequest);
        checkTheTime(newDoctorAvailabilityRequest.getHourFrom());

        if (doctorAvailabilityDAO.checkIfDoctorAvailabilityAlreadyExists(newDoctorAvailability)) {
            throw new DataAlreadyExistsException("Doctor availability already exists");
        }

        DoctorAvailability existingDoctorAvailability =
                doctorAvailabilityDAO.findDoctorAvailabilityById(newDoctorAvailability.getDoctorAvailabilityId())
                        .orElseThrow(() ->
                                new NotDataFoundException(
                                        "Could not find doctor availability by ID:[%s],"
                                                .formatted(newDoctorAvailability.getDoctorAvailabilityId())
                                ));

        if (existingDoctorAvailability.getReserved()) {
            doctorAvailabilityDAO.saveDoctorAvailability(newDoctorAvailability.withReserved(Boolean.TRUE));
        } else {
            doctorAvailabilityDAO.saveDoctorAvailability(newDoctorAvailability);
        }
    }

    private boolean checkTheTime(LocalTime hourFrom) {
        Integer minutes = hourFrom.getMinute();
        if (minutes.equals(0) || minutes.equals(20) || minutes.equals(40)) {
            return true;
        }
        throw new ProcessingDataException("Visits can only start at 0, 20, 40 minutes");
    }

    private DoctorAvailability buildDoctorAvailabilityFromRequest(DoctorAvailabilityRequest doctorAvailabilityRequest) {
        Doctor doctor = doctorService.getDoctorById(doctorAvailabilityRequest.getDoctorId());
        return DoctorAvailability.builder()
                .doctorAvailabilityId(doctorAvailabilityRequest.getDoctorAvailabilityId())
                .date(doctorAvailabilityRequest.getDate())
                .hourFrom(doctorAvailabilityRequest.getHourFrom())
                .hourTo(
                        doctorAvailabilityRequest.getHourFrom().plusMinutes(DoctorAvailabilityService.TIME_OF_VISIT)
                )
                .doctor(doctor)
                .reserved(doctorAvailabilityRequest.getReserved())
                .build();
    }

    public void deleteDoctorAvailabilityRequest(Integer doctorAvailabilityId) {
        DoctorAvailability existingDoctorAvailability =
                doctorAvailabilityDAO.findDoctorAvailabilityById(doctorAvailabilityId)
                        .orElseThrow(() ->
                                new NotDataFoundException(
                                        "Could not find doctor availability by ID:[%s],"
                                                .formatted(doctorAvailabilityId)
                                ));

        if (existingDoctorAvailability.getReserved()) {
            throw new ProcessingDataException("Unable to remove reserved visit");
        } else {
            doctorAvailabilityDAO.deleteDoctorAvailabilityById(doctorAvailabilityId);
        }
    }
}
