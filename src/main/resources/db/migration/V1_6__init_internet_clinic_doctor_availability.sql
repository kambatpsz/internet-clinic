CREATE TABLE doctor_availability
(
    doctor_availability_id 	SERIAL      		NOT NULL,
	doctor_id				INT					NOT NULL,
    date 					DATE           NOT NULL,
	hour_from				TIME 			    NOT NULL,
	hour_to					TIME 				NOT NULL,
	reserved                BOOLEAN             NOT NULL,
    PRIMARY KEY (doctor_availability_id),
	    CONSTRAINT fk_doctor_availability
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id)
);