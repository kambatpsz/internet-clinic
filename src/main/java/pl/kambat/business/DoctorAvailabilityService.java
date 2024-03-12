package pl.kambat.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kambat.business.dao.DoctorAvailabilityDAO;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.domain.exception.NotDataFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorAvailabilityService {

    public static final Integer TIME_OF_VISIT = 20;

    private final DoctorAvailabilityDAO doctorAvailabilityDAO;

    @Transactional
    public List<DoctorAvailability> findAllDoctorAvailabilityByDoctor(Doctor doctor) {
        return doctorAvailabilityDAO.findAllAvailabilityByDoctor(doctor);
    }

    public List<DoctorAvailability> createNewAvailability(Doctor doctor,
                                                          String availableDate,
                                                          String availableHour,
                                                          String availableMinute,
                                                          String quantityHour) {


        LocalDate newAvailableDate = LocalDate.parse(availableDate);
        LocalTime newAvailableHourFrom = LocalTime.of(
                Integer.parseInt(availableHour),
                Integer.parseInt(availableMinute)
        );

        LocalTime newAvailableHourTo = newAvailableHourFrom.plusHours(Integer.parseInt(quantityHour));
        LocalTime startTime = newAvailableHourFrom;
        LocalTime endTime = newAvailableHourFrom.plusMinutes(TIME_OF_VISIT);


        List<DoctorAvailability> doctorAvailabilityList = new ArrayList<>();
        do {
            doctorAvailabilityList.add(DoctorAvailability.builder()
                    .date(newAvailableDate)
                    .hourFrom(startTime)
                    .hourTo(endTime)
                    .reserved(false)
                    .doctor(doctor)
                    .build());

            startTime = endTime;
//            endTime = endTime.plusMinutes(20);
            endTime = endTime.plusMinutes(TIME_OF_VISIT);

        } while (endTime.isBefore(newAvailableHourTo.plusMinutes(1)));

        return doctorAvailabilityList;
    }

    @Transactional
    public void saveNewDoctorAvailability(DoctorAvailability doctorAvailability) {
        doctorAvailabilityDAO.saveDoctorAvailability(doctorAvailability);
    }

    @Transactional
    public List<Doctor> findDoctorsWithActiveAppointments() {
        return doctorAvailabilityDAO.findDoctorsWithActiveAppointments();
    }

    @Transactional
    public boolean checkIfDoctorAvailabilityAlreadyExists(DoctorAvailability doctorAvailability) {
        return doctorAvailabilityDAO.checkIfDoctorAvailabilityAlreadyExists(doctorAvailability);
    }

    @Transactional
    public void changeStatusDoctorAvailability(DoctorAvailability doctorAvailability) {
        doctorAvailabilityDAO.changeStatusDoctorAvailability(doctorAvailability);
    }

    @Transactional
    public DoctorAvailability findDoctorAvailabilityByDate(Doctor doctor, LocalDateTime dateTimeVisit) {

        LocalDate availabilityDoctorDate = dateTimeVisit.toLocalDate();
        LocalTime availabilityDoctorTime = dateTimeVisit.toLocalTime();
        return doctorAvailabilityDAO.findDoctorAvailabilityByDate(doctor, availabilityDoctorDate, availabilityDoctorTime)
                .orElseThrow(() ->
                        new NotDataFoundException(
                                "Could not find doctor availability by doctorID:[%s], and date:[%s]"
                                        .formatted(doctor.getDoctorId(), dateTimeVisit)
                        )
                );
    }
}
