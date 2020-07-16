DROP TABLE IF EXISTS t_role;
CREATE TABLE IF NOT EXISTS t_role (
	id SERIAL PRIMARY KEY,
	role VARCHAR(20) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS t_user;
CREATE TABLE IF NOT EXISTS t_user (
	id SERIAL PRIMARY KEY,
	serial_code VARCHAR(7) UNIQUE NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	password VARCHAR(255) NOT NULL,
	dt_ins DATE NOT NULL DEFAULT current_date,
	dt_last_access DATE NOT NULL DEFAULT current_date,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	role_id INTEGER REFERENCES t_role(id)
);

DROP TABLE IF EXISTS t_category;
CREATE TABLE IF NOT EXISTS t_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS t_article;
CREATE TABLE IF NOT EXISTS t_article (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) UNIQUE NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    slug VARCHAR (100) UNIQUE NOT NULL,
    body TEXT NOT NULL,
    dt_created DATE NOT NULL DEFAULT current_date,
    dt_updated DATE,
    author_id INTEGER REFERENCES t_user(id),
    category_id INTEGER REFERENCES t_category(id)
);

