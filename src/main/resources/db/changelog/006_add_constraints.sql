-- import logic did not pick up null records
UPDATE location SET last_import_at = now() WHERE last_import_at IS NULL;

-- add not null constraints
ALTER TABLE location ALTER COLUMN last_import_at SET NOT NULL;
ALTER TABLE location ALTER COLUMN uuid SET NOT NULL;
ALTER TABLE location ALTER COLUMN country_code SET NOT NULL;
ALTER TABLE location ALTER COLUMN longitude SET NOT NULL;
ALTER TABLE location ALTER COLUMN latitude SET NOT NULL;

-- add pk
ALTER TABLE location ADD PRIMARY KEY (id);
ALTER TABLE weather ADD CONSTRAINT fk_weather_location FOREIGN KEY (location_id) REFERENCES location(id);

-- add unique constraints
ALTER TABLE location ADD CONSTRAINT unique_lon_lat UNIQUE (longitude, latitude);
ALTER TABLE location ADD CONSTRAINT unique_zip_code UNIQUE (local_zip_code, country_code);
ALTER TABLE location ADD CONSTRAINT unique_uuid UNIQUE (uuid);
ALTER TABLE location ADD CONSTRAINT unique_external_location_code UNIQUE (open_weather_api_location_code);