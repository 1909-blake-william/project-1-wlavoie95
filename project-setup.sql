DROP USER ers CASCADE;

CREATE USER ers
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT CONNECT TO ers;
GRANT RESOURCE TO ers;
GRANT CREATE SESSION TO ers;
GRANT CREATE TABLE TO ers;
GRANT CREATE VIEW TO ers;

conn ers/p4ssw0rd;

/************************************
Tables and sequences
************************************/

CREATE SEQUENCE ers_reimb_status_id_seq;
CREATE TABLE ers_reimbursement_status(
    reimb_status_id NUMBER PRIMARY KEY NOT NULL,
    reimb_status VARCHAR2(10) DEFAULT 'Pending' NOT NULL
);

CREATE SEQUENCE ers_reimb_type_id_seq;
CREATE TABLE ers_reimbursement_type(
    reimb_type_id NUMBER PRIMARY KEY NOT NULL,
    reimb_type VARCHAR2(10) NOT NULL
);

CREATE SEQUENCE ers_user_roles_id_seq;
CREATE TABLE ers_user_roles(
    ers_user_role_id NUMBER PRIMARY KEY NOT NULL,
    user_role VARCHAR2(10) NOT NULL
);

CREATE SEQUENCE ers_user_id_seq;
CREATE TABLE ers_users(
    ers_users_id NUMBER PRIMARY KEY NOT NULL,
    ers_username VARCHAR2(50) UNIQUE NOT NULL,
    ers_password VARCHAR2(50) NOT NULL,
    user_first_name VARCHAR2(100) NOT NULL,
    user_last_name VARCHAR2(100) NOT NULL,
    user_email VARCHAR2(150) UNIQUE NOT NULL,
    user_role_id NUMBER REFERENCES ers_user_roles(ers_user_role_id) NOT NULL
);

CREATE SEQUENCE ers_reimbursement_id_seq;
CREATE TABLE ers_reimbursement (
    reimb_id NUMBER PRIMARY KEY NOT NULL,
    reimb_amount NUMBER NOT NULL,
    reimb_submitted TIMESTAMP NOT NULL,
    reimb_resolved TIMESTAMP,
    reimb_description VARCHAR(250),
    reimb_receipt BLOB,
    reimb_author NUMBER REFERENCES ers_users(ers_users_id) NOT NULL,
    reimb_resolver NUMBER REFERENCES ers_users(ers_users_id),
    -- status id defaults to id 1, which is the "Pending" status
    reimb_status_id NUMBER DEFAULT 1 REFERENCES ers_reimbursement_status(reimb_status_id) NOT NULL,
    reimb_type_id NUMBER REFERENCES ers_reimbursement_type(reimb_type_id) NOT NULL
);

/*******************************************************
Data
*******************************************************/
-- User Roles
INSERT INTO ers_user_roles (ers_user_role_id, user_role) 
    VALUES (ers_user_roles_id_seq.nextval, 'Apprentice');

INSERT INTO ers_user_roles (ers_user_role_id, user_role) 
    VALUES (ers_user_roles_id_seq.nextval, 'Wizard');
    
 -- Default Users   
INSERT INTO ers_users(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES(ers_user_id_seq.nextval, 'Gandalf', 'YouShallNotPass', 'Gandalf', 'The White', 'gandalfthewhite@isengard.net', 2);
    
INSERT INTO ers_users(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES(ers_user_id_seq.nextval, 'YenSid', 'Disney', 'Yen', 'Sid', 'yensid@disney.com', 2);
    
INSERT INTO ers_users(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES(ers_user_id_seq.nextval, 'Dumbledore', 'licorice', 'Albus', 'Dumbledore', 'profdumbledore@hogwarts.edu', 2);

INSERT INTO ers_users(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES(ers_user_id_seq.nextval, 'Mickey', 'Mouse', 'Mickey', 'Mouse', 'mickeymouse@disney.com', 1);
    
INSERT INTO ers_users(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
    VALUES(ers_user_id_seq.nextval, 'HarryPotter', 'Hogwarts', 'Harry', 'Potter', 'harrypotter@hogwarts.edu', 1);
    

-- Reimbursement Statuses
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) 
    VALUES (ers_reimb_status_id_seq.nextval, 'Pending');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) 
    VALUES (ers_reimb_status_id_seq.nextval, 'Approved');
    
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) 
    VALUES (ers_reimb_status_id_seq.nextval, 'Denied');


-- Default Reimbursement Types
INSERT INTO ers_reimbursement_type(reimb_type_id, reimb_type)
    VALUES(ers_reimb_type_id_seq.nextval, 'Lodging');

INSERT INTO ers_reimbursement_type(reimb_type_id, reimb_type)
    VALUES(ers_reimb_type_id_seq.nextval, 'Food');

INSERT INTO ers_reimbursement_type(reimb_type_id, reimb_type)
    VALUES(ers_reimb_type_id_seq.nextval, 'Travel');

INSERT INTO ers_reimbursement_type(reimb_type_id, reimb_type)
    VALUES(ers_reimb_type_id_seq.nextval, 'Equipment');

-- Default Example Reimbursements
INSERT INTO ers_reimbursement(reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_type_id)
    VALUES(ERS_REIMBURSEMENT_ID_SEQ.nextval, 50, CURRENT_TIMESTAMP, 'Broke brooms from casting a cleaning spell', 4, 4);
    
INSERT INTO ers_reimbursement(reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_type_id)
    VALUES(ERS_REIMBURSEMENT_ID_SEQ.nextval, 25, CURRENT_TIMESTAMP, 'Platform 9 3/4 Train Fare', 5, 3);
    
INSERT INTO ers_reimbursement(reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_type_id)
    VALUES(ERS_REIMBURSEMENT_ID_SEQ.nextval, 10, CURRENT_TIMESTAMP, 'Food from the trolley', 5, 3);

UPDATE ers_reimbursement
SET reimb_resolved = CURRENT_TIMESTAMP, reimb_resolver = 2, reimb_status_id = 3
WHERE reimb_id = 1;

UPDATE ers_reimbursement
SET reimb_resolved = CURRENT_TIMESTAMP, reimb_resolver = 3, reimb_status_id = 2
WHERE reimb_id = 2;

commit;

-- reimbursement table select statement
SELECT  r.reimb_submitted, r.reimb_amount, r.reimb_description, rt.reimb_type,
    emp.user_first_name, emp.user_last_name, emp.user_email, r.reimb_resolved,
    man.user_first_name, man.user_last_name, rs.reimb_status FROM ers_reimbursement r
LEFT JOIN ers_reimbursement_type rt USING (reimb_type_id)
LEFT JOIN ers_reimbursement_status rs USING (reimb_status_id)
LEFT JOIN ers_users emp ON (r.reimb_author = emp.ers_users_id)
LEFT JOIN ers_users man ON (r.reimb_resolver = man.ers_users_id)
ORDER BY r.reimb_submitted desc;

