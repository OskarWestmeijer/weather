ALTER TABLE weather DROP COLUMN local_zip_code;

ALTER TABLE weather ALTER COLUMN location_id SET NOT NULL;