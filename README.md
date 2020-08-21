# rock7-challenge
Completed challenge of https://github.com/rock7/stache-lag-backend.

## Tackling The Challenge
### 1. Parsing
I used a neat open source project called [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) to extract some POJOs from the provided JSON document. The command used can be found at src/main/resources/generate-models.bat. Some notes:
* Command line arguments `--omit-tostring --omit-hashcode-and-equals` were used because IntelliJ generated `toString()`, `hashCode()`, and `equals()` methods are much cleaner.
* I changed some inferred field types to better fit the data, i.e. `gpsAtMillis` to `Long`, as well as `txAt` and `gpsAt` to `Timestamp`.

https://science.howstuffworks.com/question198.htm
SquareRoot(height above surface / 0.5736) = distance to horizon
where "height above surface" is in feet and "distance to horizon" is in miles.

sqrt(10/0.5736) = 4.18 miles or 6.72 km