\c weather

CREATE TABLE location (
    zip_code bigint PRIMARY KEY,
    location_code bigint NOT NULL,
    city_name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE weather
(
    id UUID PRIMARY KEY,
    temperature decimal NOT NULL,
    timestamp   timestamptz NOT NULL,
    wind_speed decimal NOT NULL,
    humidity bigint NOT NULL,
    zip_code bigint NOT NULL REFERENCES location (zip_code)
);
