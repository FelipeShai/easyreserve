CREATE TABLE restaurants (
    ID BIGINT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    LOCATION VARCHAR(255) NOT NULL,
    CUISINE_TYPE VARCHAR(50) NOT NULL,
    OPENING_HOURS VARCHAR(50) NOT NULL,
    CAPACITY INT NOT NULL
);

INSERT INTO restaurants (ID, NAME, LOCATION, CUISINE_TYPE, OPENING_HOURS, CAPACITY)
VALUES
    (1, 'La Bella', 'Downtown', 'Italian', '10:00-22:00', 50),
    (2, 'Berimbau', 'Midtown', 'Brazilian', '10:00-00:00', 40),
    (3, 'Koba Korean Bbq', 'Chinatown', 'Korean', '10:00-02:00', 30);
