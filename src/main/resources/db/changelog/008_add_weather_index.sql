CREATE INDEX idx_recorded_at_location_id_weather ON weather.weather (recorded_at, location_id);
CREATE INDEX idx_location_id_weather ON weather.weather (location_id);