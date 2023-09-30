-- set new primary key for location table
ALTER TABLE weather DROP CONSTRAINT weather_local_zip_code_fkey;
ALTER TABLE weather ADD COLUMN location_id integer;

ALTER TABLE location DROP CONSTRAINT location_pkey;
ALTER TABLE location ADD COLUMN id integer;
ALTER TABLE location ADD PRIMARY KEY (id);

-- add additional columns
ALTER TABLE location RENAME COLUMN location_code TO open_weather_api_location_code;
ALTER TABLE location ADD COLUMN uuid uuid;
ALTER TABLE location ADD COLUMN country_code varchar(3);
ALTER TABLE location ADD COLUMN latitude varchar(20);
ALTER TABLE location ADD COLUMN longitude varchar(20);

-- add unique constraints
ALTER TABLE location ADD UNIQUE (open_weather_api_location_code);
ALTER TABLE location ADD UNIQUE (local_zip_code, country_code);
ALTER TABLE location ADD UNIQUE (latitude, longitude);