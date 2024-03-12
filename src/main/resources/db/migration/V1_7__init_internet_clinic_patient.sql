CREATE TABLE patient
(
    patient_id  SERIAL      NOT NULL,
	user_id		INT		    NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    pesel       VARCHAR(32) NOT NULL,
	address_id  INT         NOT NULL,
    PRIMARY KEY (patient_id),
	UNIQUE (pesel),
	    CONSTRAINT fk_patient_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id),
		CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES internet_clinic_user (user_id)
);
