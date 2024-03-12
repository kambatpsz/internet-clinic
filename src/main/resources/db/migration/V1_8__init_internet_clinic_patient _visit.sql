CREATE TABLE patient_visit
(
    patient_visit_id		SERIAL		NOT NULL,
	patient_id				INT			NOT NULL,
    doctor_id				INT 		NOT NULL,
    date_time_visit			TIMESTAMP	NOT NULL,
	visit_status			BOOLEAN		NOT NULL,
	visit_note				TEXT,
	date_of_cancellation	TIMESTAMP,
    PRIMARY KEY (patient_visit_id),
	    CONSTRAINT fk_patient_id
        FOREIGN KEY (patient_id)
            REFERENCES patient (patient_id),
		CONSTRAINT fk_doctor_id
        FOREIGN KEY (doctor_id)
            REFERENCES doctor (doctor_id)
);