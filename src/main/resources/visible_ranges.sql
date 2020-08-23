SELECT
    MAX(upperLatitude - lowerLatitude) AS latitudeRangeMax,
    MIN(upperLatitude - lowerLatitude) AS latitudeRangeMin,
    AVG(upperLatitude - lowerLatitude) AS latitudeRangeAvg,
    MAX(upperLongitude - lowerLongitude) AS longitudeRangeMax,
    MIN(upperLongitude - lowerLongitude) AS longitudeRangeMin,
    AVG(upperLongitude - lowerLongitude) AS longitudeRangeAvg
FROM moments;