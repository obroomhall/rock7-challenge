CREATE TABLE Moments (
    id int,
    latitude decimal(8, 5),
    longitude decimal(8, 5),
    gpsAt timestamp,
    gpsAtMillis bigint,
    teamSerial int,
    upperLatitude decimal(8, 5),
    lowerLatitude decimal(8, 5),
    upperLongitude decimal(8, 5),
    lowerLongitude decimal(8, 5),
    PRIMARY KEY (id)
);