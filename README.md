# rock7-challenge
Completed challenge of https://github.com/rock7/stache-lag-backend.

## Tackling The Challenge
### 1. Parsing
I used a neat open source project called [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) to extract some POJOs from the provided JSON document. The library and scripts used can be found at src/main/resources. Some notes:
* Command line arguments `--omit-tostring --omit-hashcode-and-equals` were used because IntelliJ generated `toString()`, `hashCode()`, and `equals()` methods are much cleaner.
* The field `gpsAtMillis` in `Position.java` was changed after generation to `Long` because millisecond values exceed the maximum Java integer value
