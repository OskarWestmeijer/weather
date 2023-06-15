
create TABLE location (
    local_zip_code bigint PRIMARY KEY,
    location_code bigint NOT NULL,
    city_name varchar(255) NOT NULL,
    country varchar(255) NOT NULL,
    created_at timestamptz NOT NULL default now(),
    modified_at timestamptz NOT NULL default now()
);

create TABLE weather
(
    id UUID PRIMARY KEY,
    temperature decimal NOT NULL,
    recorded_at   timestamptz NOT NULL,
    wind_speed decimal NOT NULL,
    humidity bigint NOT NULL,
    local_zip_code bigint NOT NULL REFERENCES location (local_zip_code),
    created_at timestamptz NOT NULL default now(),
    modified_at timestamptz NOT NULL default now()
);
