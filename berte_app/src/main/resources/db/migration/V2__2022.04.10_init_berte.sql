CREATE SCHEMA berte_security;

CREATE SCHEMA berte_game;

CREATE SCHEMA berte_person;

CREATE SCHEMA berte_agreement;

CREATE SCHEMA berte_metric;

CREATE TABLE berte_security.security_user (
	id uuid NOT NULL,
	login varchar(512) NOT NULL,
	password varchar(512) NOT NULL,
	account_non_expired boolean NOT NULL DEFAULT false,
	account_non_locked boolean NOT NULL DEFAULT false,
	credentials_non_expired boolean NOT NULL DEFAULT false,
	enabled boolean NOT NULL DEFAULT false,
	iv_vector varchar(512) NOT NULL,
	CONSTRAINT berte_user_pk PRIMARY KEY (id)
);

CREATE TABLE berte_security.security_role (
	id smallserial NOT NULL,
	code varchar(255) NOT NULL,
	CONSTRAINT user_role_pk PRIMARY KEY (id)
);

CREATE TABLE berte_security.security_authority (
	id smallserial NOT NULL,
	code varchar(255) NOT NULL,
	CONSTRAINT user_authority_pk PRIMARY KEY (id)
);

CREATE TABLE berte_security.auth_provider (
	id smallserial NOT NULL,
	code varchar(255) NOT NULL,
	CONSTRAINT auth_provider_pk PRIMARY KEY (id)
);

CREATE TABLE berte_security.user_auth_provider (
	id uuid NOT NULL,
	id_security_user uuid NOT NULL,
	id_auth_provider smallint NOT NULL,
	link_date timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT user_auth_provider_pk PRIMARY KEY (id)
);

ALTER TABLE berte_security.user_auth_provider ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_security.user_auth_provider ADD CONSTRAINT auth_provider_fk FOREIGN KEY (id_auth_provider)
REFERENCES berte_security.auth_provider (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE berte_security.security_role_security_authority (
	id uuid NOT NULL,
	id_security_authority smallint NOT NULL,
	id_security_role smallint NOT NULL,
	CONSTRAINT security_role_security_authority_pk PRIMARY KEY (id)
);

ALTER TABLE berte_security.security_role_security_authority ADD CONSTRAINT security_authority_fk FOREIGN KEY (id_security_authority)
REFERENCES berte_security.security_authority (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_security.security_role_security_authority ADD CONSTRAINT security_role_fk FOREIGN KEY (id_security_role)
REFERENCES berte_security.security_role (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE berte_security.security_user_security_role (
	id uuid NOT NULL,
	id_security_user uuid NOT NULL,
	id_security_role smallint NOT NULL,
	link_date timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT security_user_security_role_pk PRIMARY KEY (id)
);

ALTER TABLE berte_security.security_user_security_role ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_security.security_user_security_role ADD CONSTRAINT security_role_fk FOREIGN KEY (id_security_role)
REFERENCES berte_security.security_role (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE berte_game.berte_character (
	id uuid NOT NULL,
	id_security_user uuid NOT NULL,
	name varchar(512) NOT NULL,
	CONSTRAINT character_pk PRIMARY KEY (id)
);

ALTER TABLE berte_game.berte_character ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_game.berte_character ADD CONSTRAINT character_uq UNIQUE (id_security_user);

CREATE TABLE berte_person.personal_info (
	id uuid NOT NULL,
	email varchar(512) NOT NULL,
	email_confirmed boolean NOT NULL DEFAULT false,
	country varchar(64) NOT NULL,
	language varchar(64) NOT NULL,
	id_security_user uuid NOT NULL,
	registration_date timestamptz NOT NULL DEFAULT now(),
	birthday timestamptz,
	CONSTRAINT personal_info_pk PRIMARY KEY (id)
);

ALTER TABLE berte_person.personal_info ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_person.personal_info ADD CONSTRAINT personal_info_uq UNIQUE (id_security_user);

CREATE TABLE berte_agreement.agreement (
	id uuid NOT NULL,
	value boolean NOT NULL DEFAULT false,
	id_agreement_type smallint NOT NULL,
	id_security_user uuid NOT NULL,
	CONSTRAINT user_agreement_pk PRIMARY KEY (id)
);

CREATE TABLE berte_agreement.agreement_type (
	id smallserial NOT NULL,
	code varchar(512) NOT NULL,
	CONSTRAINT agreement_type_pk PRIMARY KEY (id)
);

ALTER TABLE berte_agreement.agreement ADD CONSTRAINT agreement_type_fk FOREIGN KEY (id_agreement_type)
REFERENCES berte_agreement.agreement_type (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_agreement.agreement ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE berte_security.reset_password_token (
	id uuid NOT NULL,
	value varchar(512) NOT NULL,
	expiration_date timestamptz NOT NULL,
	id_security_user uuid NOT NULL,
	CONSTRAINT reset_password_token_pk PRIMARY KEY (id)
);

ALTER TABLE berte_security.reset_password_token ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE berte_security.reset_password_token ADD CONSTRAINT reset_password_token_uq UNIQUE (id_security_user);

CREATE TABLE berte_metric.metric (
	id uuid NOT NULL,
	id_security_user uuid NOT NULL,
	CONSTRAINT metric_pk PRIMARY KEY (id)
);

ALTER TABLE berte_metric.metric ADD CONSTRAINT security_user_fk FOREIGN KEY (id_security_user)
REFERENCES berte_security.security_user (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;


