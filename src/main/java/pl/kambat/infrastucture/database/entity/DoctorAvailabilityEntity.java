package pl.kambat.infrastucture.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode(of = "doctorAvailabilityId")
@ToString(of = {"date", "hourFrom", "hourTo"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_availability")
public class DoctorAvailabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_availability_id")
    private Integer doctorAvailabilityId;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "hour_from")
    private LocalTime hourFrom;
    @Column(name = "hour_to")
    private LocalTime hourTo;
    @Column(name = "reserved")
    private Boolean reserved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

}
