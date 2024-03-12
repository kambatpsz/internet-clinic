package pl.kambat.infrastucture.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kambat.domain.Patient;
import pl.kambat.infrastucture.database.entity.PatientEntity;
import pl.kambat.infrastucture.database.repository.jpa.PatientJpaRepository;
import pl.kambat.infrastucture.database.repository.mapper.PatientEntityMapper;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;
import pl.kambat.util.DataFixtures;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

    @Mock
    private PatientJpaRepository patientJpaRepository;

    @Mock
    private PatientEntityMapper patientEntityMapper;

    @InjectMocks
    private PatientRepository patientRepository;

    @Test
    void findAllPatients() {
        // given
        List<PatientEntity> patientEntities = List.of(DataFixtures.somePatientEntity1());

        when(patientJpaRepository.findAll()).thenReturn(patientEntities);
        when(patientEntityMapper.mapFromEntity(any())).thenReturn(DataFixtures.somePatient1());

        // when
        List<Patient> result = patientRepository.findAllPatients();

        // then
        assertThat(result).containsExactly(DataFixtures.somePatient1());
    }

    @Test
    void findByUserId() {
        // given
        InternetClinicUserEntity internetClinicUserEntity = DataFixtures.someInternetClinicUserEntity1();
        PatientEntity patientEntity = DataFixtures.somePatientEntity1();

        when(patientJpaRepository.findByUserId(internetClinicUserEntity)).thenReturn(Optional.of(patientEntity));
        when(patientEntityMapper.mapFromEntity(patientEntity)).thenReturn(DataFixtures.somePatient1());
        // when
        Optional<Patient> result = patientRepository.findByUserId(internetClinicUserEntity);

        // then
        assertThat(result).isPresent().contains(DataFixtures.somePatient1());
    }
}