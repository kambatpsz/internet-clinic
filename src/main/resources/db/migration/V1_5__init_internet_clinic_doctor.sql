CREATE TABLE doctor
(
    doctor_id 	            SERIAL      NOT NULL,
	user_id		            INT			NOT NULL,
    name                    VARCHAR(32) NOT NULL,
    surname                 VARCHAR(32) NOT NULL,
    pesel                   VARCHAR(32) NOT NULL,
    pwz_number              VARCHAR(8)  NOT NULL,
	address_id              INT         NOT NULL,
	verified                 BOOLEAN     NOT NULL,
    PRIMARY KEY (doctor_id),
	UNIQUE (pesel),
	    CONSTRAINT fk_doctor_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id),
		CONSTRAINT fk_doctor_user_id
        FOREIGN KEY (user_id)
            REFERENCES internet_clinic_user (user_id)
);