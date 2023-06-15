
INSERT INTO location (local_zip_code, location_code, city_name, country)
VALUES
('23552', '2875601', 'Lübeck', 'Germany'),
('20095', '2911298', 'Hamburg', 'Germany'),
('46286', '2935530', 'Dorsten', 'Germany'),
('00100', '658225', 'Helsinki', 'Finland'),
('36100', '654440', 'Kangasala', 'Finland');

INSERT INTO weather (id, temperature, humidity, wind_speed, recorded_at, local_zip_code)
VALUES
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 11.45, 50, 2.57,'2022-08-29T21:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 12.45, 45, 2.57,'2022-08-28T23:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 13.45, 45, 2.57,'2022-08-28T22:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', 13.45, 45, 2.57,'2022-08-28T21:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', 13.45, 45, 2.57,'2022-08-28T10:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 14.45, 45, 2.57,'2022-08-28T01:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 15.45, 45, 2.57,'2022-08-27T22:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 16.45, 45, 2.57,'2022-08-27T21:56:18.602546Z', '23552'),
('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 17.45, 45, 2.57,'2022-08-27T21:16:18.056453Z', '23552'),
('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 20.75, 45, 2.57,'2022-08-27T20:21:17.330786Z', '23552');
