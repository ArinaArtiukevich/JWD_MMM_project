
CREATE TABLE IF NOT EXISTS public.users
(
    id_user bigserial NOT NULL PRIMARY KEY,
    first_name text NOT NULL,
    last_name text,
    email text,
    city text,
    login text NOT NULL UNIQUE,
    gender text,
    user_role text
);



 CREATE TABLE IF NOT EXISTS public.orders
 (
	 id_service bigserial PRIMARY KEY,
	 id_client bigint,
	 description text,
	 address text,
	 service_type text,
	 service_status text,
	 id_worker bigint,
	 order_creation_date text,
 	CONSTRAINT fk_orders_id_client FOREIGN KEY(id_client) REFERENCES users(id_user),
	 CONSTRAINT fk_orders_id_worker FOREIGN KEY(id_worker) REFERENCES users(id_user)
 );



CREATE TABLE IF NOT EXISTS public.user_logins
(
    id_user bigint NOT NULL DEFAULT nextval('user_logins_id_user_seq'::regclass),
    login text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT login UNIQUE (login),
    CONSTRAINT fk_user_logins_users FOREIGN KEY (id_user)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.user_logins
    OWNER to postgres;

TABLESPACE pg_default;

ALTER TABLE public.user_logins
    OWNER to postgres;


ALTER TABLE orders ADD COLUMN order_creation_date text;
CREATE TABLE service_types (
    service_type text PRIMARY KEY
);


CREATE TABLE service_types (
    service_type text PRIMARY KEY
);

INSERT INTO service_types(service_type) VALUES ('ELECTRICAL'),
('GAS'),
('ROOFING'),
('PAINTING'),
('PLUMBING');
ALTER TABLE orders ADD CONSTRAINT fk_orders_service_type FOREIGN KEY (service_type) REFERENCES service_types (service_type);

CREATE TABLE genders (
    gender text PRIMARY KEY
);
INSERT INTO genders(gender) VALUES ('UNDEFINED'),
('MALE'),
('FEMALE');

ALTER TABLE users ADD CONSTRAINT fk_users_gender FOREIGN KEY (gender) REFERENCES genders (gender);

CREATE TABLE roles (
    user_role text PRIMARY KEY
);
INSERT INTO roles(user_role) VALUES ('CLIENT'),
('WORKER');

ALTER TABLE users ADD CONSTRAINT fk_users_role FOREIGN KEY (user_role) REFERENCES roles (user_role);



CREATE TABLE service_statuses (
    service_status text PRIMARY KEY
);
INSERT INTO service_statuses(service_status) VALUES ('FREE'),
('IN_PROCESS') , ('DONE') , ('APPROVED');

ALTER TABLE orders ADD CONSTRAINT fk_service_status FOREIGN KEY (service_status) REFERENCES service_statuses (service_status);

