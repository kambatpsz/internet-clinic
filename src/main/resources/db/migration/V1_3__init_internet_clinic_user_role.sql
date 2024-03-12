CREATE TABLE internet_clinic_user_role
(
    user_id   INT      NOT NULL,
    role_id   INT      NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_internet_clinic_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES internet_clinic_user (user_id),
    CONSTRAINT fk_internet_clinic_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES internet_clinic_role (role_id)
);