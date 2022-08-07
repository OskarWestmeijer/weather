\c weather

CREATE TABLE weather
(
    id          UUID,
    temperature decimal,
    timestamp   timestamptz,
    wind_speed decimal,
    humidity bigint,
    zip_code bigint
);


CREATE TABLE location (
    zip_code bigint PRIMARY KEY,
    location_code bigint,
    city_name VARCHAR(255),
    country VARCHAR(255)
);