CREATE TABLE Positions (
    alert int,
    altitude int,
    type varchar(255),
    dtfKm double,
    id int,
    gpsAt timestamp,
    sogKnots double,
    battery int,
    cog int,
    dtfNm double,
    txAt timestamp,
    longitude double,
    latitude double,
    gpsAtMillis float,
    sogKmph double,
    teamSerial int,
    PRIMARY KEY (id)
);