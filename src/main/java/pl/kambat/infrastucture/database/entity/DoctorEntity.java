package pl.kambat.infrastucture.database.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.kambat.infrastucture.seciurity.InternetClinicUserEntity;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "doctorId")
@ToString(of = {"doctorId", "name", "surname"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private InternetClinicUserEntity userId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "pesel")
    private String pesel;
    @Column(name = "pwz_number")
    private String pwzNumber;
    @Column(name = "verified")
    private Boolean verified;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<DoctorAvailabilityEntity> doctorAvailability;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<PatientVisitEntity> patientVisit;

}
