CREATE TABLE IF NOT EXISTS public.users
(
    id_client bigserial NOT NULL,
    first_name text NOT NULL,
    last_name text,
    email text,
    city text,
    login text,
    gender text,
    user_role text,
    CONSTRAINT users_pkey PRIMARY KEY (id_client)
)

CREATE TABLE IF NOT EXISTS public.user_dtos
 (
    id_client bigint NOT NULL,
 	login text,
 	password text,
 	CONSTRAINT fk_user_dtos_id_client FOREIGN KEY(id_client) REFERENCES users(id_client)
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
 	CONSTRAINT fk_orders_id_client FOREIGN KEY(id_client) REFERENCES users(id_client)
	 CONSTRAINT fk_orders_id_worker FOREIGN KEY(id_worker) REFERENCES users(id_client)
 );