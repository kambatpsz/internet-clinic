package pl.kambat.api.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kambat.domain.DoctorAvailability;
import pl.kambat.api.dto.DoctorAvailabilityDTO;
import pl.kambat.api.dto.mapper.DoctorAvailabilityMapper;
import pl.kambat.api.dto.mapper.DoctorMapper;
import pl.kambat.business.DoctorAvailabilityService;
import pl.kambat.business.DoctorService;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.exception.ProcessingDataException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DoctorScheduleController {
    private static final String DOCTOR_SCHEDULE = "/doctor/schedule";
    private static final String DOCTOR_SCHEDULE_APPOINTMENT = "/patient/doctor/schedule";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    @GetMapping(value = DOCTOR_SCHEDULE)
    public ModelAndView doctorSchedule(
    ) {
        Map<String, ?> model = prepareTimeData();
        return new ModelAndView("doctor/doctor_schedule", model);
    }

    private Map<String, ?> prepareTimeData() {
        var availableDate = generateDate();
        var availableHours = generateDataTime(7, 16, 1);
        var availableMinutes = generateDataTime(0, 40, 20);
        var quantityHours = generateQuantityHours();

        return Map.of(
                "availableHourS", availableHours,
                "availableMinuteS", availableMinutes,
                "quantityHourS", quantityHours,
                "availableDate", availableDate,
                "doctorAvailabilityDTOs", getDoctorAvailabilityDTO()
        );
    }

    private String generateDate() {
        LocalDate localDate = LocalDate.now();
        return String.valueOf(localDate);
    }

    private Set<Byte> generateDataTime(int startTime, int endTime, int stepTime) {
        Set<Byte> dataTime = new HashSet<>();
        for (int i = startTime; i <= endTime; i += stepTime) {
            dataTime.add((byte) i);
        }
        return dataTime.stream().sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Byte> generateQuantityHours() {
        Set<Byte> quantityHours = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            quantityHours.add((byte) i);
        }
        return quantityHours;
    }

    private List<DoctorAvailabilityDTO> getDoctorAvailabilityDTO() {
        Doctor doctor = doctorService.getLoggedDoctor();

        return doctorAvailabilityService
                .findAllDoctorAvailabilityByDoctor(doctor).stream()
                .map(doctorAvailabilityMapper::map)
                .filter(doctorAvailabilityDTO ->
                        doctorAvailabilityDTO.getDate().isAfter(LocalDate.now()) ||
                                doctorAvailabilityDTO.getDate().isEqual(LocalDate.now()))
                .sorted(Comparator.comparing(DoctorAvailabilityDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    @PostMapping(value = DOCTOR_SCHEDULE)
    public ModelAndView doctorSchedule(
            @RequestParam("availableDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            String availableDate,
            @RequestParam("availableHour") String availableHour,
            @RequestParam("availableMinute") String availableMinute,
            @RequestParam("quantityHour") String quantityHour
    ) {

        if (LocalDate.parse(availableDate).isBefore(LocalDate.now())) {
            doctorSchedule();
        }

        Doctor doctor = doctorService.getLoggedDoctor();
        List<DoctorAvailability> doctorAvailabilityList = doctorAvailabilityService
                .createNewAvailability(doctor, availableDate, availableHour, availableMinute, quantityHour);

        for (DoctorAvailability doctorAvailability : doctorAvailabilityList) {

            if (!doctorAvailabilityService.checkIfDoctorAvailabilityAlreadyExists(doctorAvailability)) {
                doctorAvailabilityService.saveNewDoctorAvailability(doctorAvailability);
            }
        }
        return doctorSchedule();
    }

    @PostMapping(value = DOCTOR_SCHEDULE_APPOINTMENT)
    public String doctorScheduleAppointment(
            @RequestParam("availableDoctor") @NotEmpty String availableDoctor,
            ModelMap model
    ) {

        if (availableDoctor == null) {
            throw new ProcessingDataException("Doctor not found");
        }
        String pwzNumber = getPwzNumber(availableDoctor);
        Doctor doctor = doctorService.getDoctorByPwzNumber(pwzNumber);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOs =
                doctorAvailabilityService.findAllDoctorAvailabilityByDoctor(doctor).stream()
                        .map(doctorAvailabilityMapper::map)
                        .filter(doctorAvailabilityDTO -> doctorAvailabilityDTO.getDate().isEqual(LocalDate.now()) ||
                                doctorAvailabilityDTO.getDate().isAfter(LocalDate.now()))
                        .filter(doctorAvailabilityDTO -> !doctorAvailabilityDTO.getReserved())
                        .sorted(Comparator.comparing(DoctorAvailabilityDTO::getDate))
                        .toList();

        model.addAttribute("doctorDTO", doctorMapper.map(doctor));
        model.addAttribute("doctorAvailabilityDTOs", doctorAvailabilityDTOs);
        return "patient/doctor_schedule";
    }

    private String getPwzNumber(String appointmentDoctor) {
        String[] strings = appointmentDoctor.split(" ");
        if (strings.length >= 3 && strings[2].length() == 8) {
            return strings[2];
        } else {
            throw new ProcessingDataException("Missing or invalid PWZ number, pwz number must have 8 characters");
        }
    }
}
