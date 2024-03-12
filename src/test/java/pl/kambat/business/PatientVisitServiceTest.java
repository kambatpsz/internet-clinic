package pl.kambat.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kambat.business.dao.PatientVisitDAO;
import pl.kambat.domain.Doctor;
import pl.kambat.domain.Patient;
import pl.kambat.domain.PatientVisit;
import pl.kambat.domain.exception.NotDataFoundException;
import pl.kambat.util.DataFixtures;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientVisitServiceTest {

    @Mock
    private PatientVisitDAO patientVisitDAO;
    @InjectMocks
    private PatientVisitService patientVisitService;

    @Test
    void findVisitsByDoctorId() {
        // given
        Doctor doctor = DataFixtures.someDoctor1();
        List<PatientVisit> expectedVisits = Arrays.asList(
                DataFixtures.somePatientVisit1(),
                DataFixtures.somePatientVisit2()
        );

        when(patientVisitDAO.findAllPatientsVisitsByDoctor(doctor)).thenReturn(expectedVisits);

        // when
        List<PatientVisit> actualVisits = patientVisitService.findVisitsByDoctorId(doctor);

        // then
        assertEquals(expectedVisits, actualVisits);
    }

    @Test
    void findVisitsByPatientId() {
        // given
        Patient patient = DataFixtures.somePatient1();
        List<PatientVisit> expectedVisits = Arrays.asList(
                DataFixtures.somePatientVisit1(),
                DataFixtures.somePatientVisit2()
        );

        when(patientVisitDAO.findAllPatientsVisitsByPatient(patient)).thenReturn(expectedVisits);

        // when
        List<PatientVisit> actualVisits = patientVisitService.findVisitsByPatientId(patient);

        // then
        assertEquals(expectedVisits, actualVisits);
        assertEquals(2, actualVisits.size());
    }

    @Test
    void findActiveDoctorVisits() {
        // given
        Doctor doctor = DataFixtures.someDoctor1();
        List<PatientVisit> expectedVisits = Arrays.asList(
                DataFixtures.somePatientVisit1(),
                DataFixtures.somePatientVisit2()
        );

        when(patientVisitDAO.findActiveVisitsByDoctorId(doctor.getDoctorId())).thenReturn(expectedVisits);

        // when
        List<PatientVisit> actualVisits = patientVisitService.findActiveDoctorVisits(doctor.getDoctorId());

        // then
        assertEquals(expectedVisits, actualVisits);
    }

    @Test
    void findPatientVisitById() {
        // given
        Integer patientVisitId = 0;

        // when
        when(patientVisitDAO.findPatientVisitById(patientVisitId)).thenReturn(Optional.empty());

        // then
        assertThrows(NotDataFoundException.class, () -> {
            patientVisitService.findPatientVisitById(patientVisitId);
        });
    }

}