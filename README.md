# rock7-challenge
Completed challenge of https://github.com/rock7/stache-lag-backend.

## 1. Parsing
I used a neat open source project called [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) to extract some POJOs from the provided JSON document.

`$> jsonschema2pojo --source .\positions.json --target ..\java --source-type JSON --omit-tostring --omit-hashcode-and-equals --target-version 1.8 --annotation-style JACKSON2 --package com.rock7.challenge.model`

## 2. Population
I used MySQL and created a single table, named Moments, for populating data, the schema for which can be found at `src/main/resources/create_moments.sql`.

## 3. Method
I took two methods for determining the number of vessels visible for any given moment, a fast naive approximation, and a slower, more precise calculation.

### Visible Distance
Both methods required knowing the maximum visible distance of another vessel, according to [howstuffworks.com](https://science.howstuffworks.com/question198.htm) this problem is solved with the use of a constant, more specifically:

`sqrt(height above surface / 0.5736) = distance to horizon`
where "height above surface" is in feet and "distance to horizon" is in miles.

From looking at pictures of sporting yachts online I estimated that a person's height above surface would be about 10 feet, that's 4 feet of yacht and 6 feet of human. Therefore the maxium visible distance is sqrt(10/0.5736) = 4.18 miles or 6.72 km. Note that this height is stored as a variable in code so can easily be changed if this was a poor estimation.

### Tuning for Search Speed
I compromised on indexing speed by doing some preprocessing in Java, in order to speed up query times. The preprocessing involved estimating the furthest point visible North, East, South and West from a vessels current position, given the maximum visible distance calculated earlier, effectively giving a square area of visibility. This ignores that a persons actual area of visibility is circular, and so yields some false positive results, but gives the advantage of faster approximation. Relevant code can be found at `getUpperAndLowerVisibleBounds()` method in `Distance.java`.

The key advantage of preprocessing here is that the calculations only need to be performed once, on indexing, rather than every time the database is queried. This also means that retreival from the database is faster, since we have reduced the complexity of the search query.

There are two algorithms I compared for calculating distance from coordinates (pythagorean and haversine), which you can find time and accuracy tests for in the tests directory, though to summarise; Pythagorean theorem is about 6x faster, and is as precise as Haversine, at least for the distances we are looking at for this challenge.

### Fast Approximation
Outputting the averages becomes a simple task after the aforementioned preprocessing, the SQL query used can be found in `src/main/resources/naive_daily_average.sql`, and output is in the table below under the heading `Naive`. This method also assumes a single sighting per team per day, meaning that if Team A see Team B briefly at 6am and then again at 6pm, that counts as a single sighting.

### Precise Calculation
This method improves on the naive method in two ways, however it requires further processing:

**Removing false positive sightings**

False positives are sightings within the approximate square of visibility, but outside of the real circle of visibility. To remove these from the results set requires using the distance algorithm again.

**Count multiple sightings per team per day**

Previously we assumed a single sighting per team per day, but this can be considered incorrect. Instead I implemented a method that separates sightings by a threshold. I tested a range of thresholds, the results from which can be seen in the table below.

### Conclusion
I have presented two methods, usage of which would depend on the more valued factor; speed or precision.

## 4. Output

|     Day    	|   15m  	| 30m    	|   1h   	|   2h   	|   6h   	|   12h  	|   24h  	|  Naive 	|
|:----------:	|:------:	|--------	|:------:	|:------:	|:------:	|:------:	|:------:	|:------:	|
| 2017-11-19 	| 809.98 	| 601.74 	| 255.53 	| 209.71 	| 187.99 	| 185.00 	| 185.00 	| 185.00 	|
| 2017-11-20 	|  10.27 	| 10.01  	|  8.73  	|  8.54  	|  8.01  	|  7.75  	|  7.66  	|  9.29  	|
| 2017-11-21 	|  3.07  	| 2.65   	|  2.63  	|  2.63  	|  2.22  	|  2.09  	|  2.09  	|  2.43  	|
| 2017-11-22 	|  2.04  	| 1.69   	|  1.58  	|  1.58  	|  1.31  	|  1.23  	|  1.23  	|  1.32  	|
| 2017-11-23 	|  1.90  	| 1.25   	|  1.20  	|  1.20  	|  1.10  	|  1.10  	|  1.10  	|  1.12  	|
| 2017-11-24 	|  2.96  	| 1.65   	|  1.65  	|  1.65  	|  1.30  	|  1.22  	|  1.22  	|  1.19  	|
| 2017-11-25 	|  2.52  	| 1.48   	|  1.48  	|  1.48  	|  1.30  	|  1.22  	|  1.22  	|  1.30  	|
| 2017-11-26 	|  5.71  	| 5.24   	|  4.95  	|  4.95  	|  4.19  	|  3.52  	|  3.52  	|  3.30  	|
| 2017-11-27 	|  3.68  	| 3.68   	|  3.68  	|  3.68  	|  3.37  	|  3.26  	|  3.26  	|  3.14  	|
| 2017-11-28 	|  3.18  	| 3.18   	|  3.18  	|  3.18  	|  2.59  	|  2.12  	|  2.12  	|  2.00  	|
| 2017-11-29 	|  4.42  	| 4.42   	|  4.42  	|  4.42  	|  2.63  	|  2.11  	|  2.11  	|  2.11  	|
| 2017-11-30 	|  3.40  	| 3.40   	|  3.40  	|  3.40  	|  2.50  	|  2.20  	|  2.10  	|  2.10  	|
| 2017-12-01 	|  1.53  	| 1.53   	|  1.53  	|  1.53  	|  1.41  	|  1.18  	|  1.18  	|  1.18  	|
| 2017-12-02 	|  1.00  	| 1.00   	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|
| 2017-12-03 	|  1.14  	| 1.14   	|  1.14  	|  1.14  	|  1.14  	|  1.14  	|  1.14  	|  1.11  	|
| 2017-12-04 	|  1.10  	| 1.10   	|  1.10  	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|  1.04  	|
| 2017-12-05 	|  1.00  	| 1.00   	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|  1.08  	|
| 2017-12-06 	|  2.46  	| 2.46   	|  1.69  	|  1.69  	|  1.54  	|  1.38  	|  1.38  	|  1.29  	|
| 2017-12-07 	|  1.74  	| 1.74   	|  1.74  	|  1.74  	|  1.57  	|  1.39  	|  1.39  	|  1.39  	|
| 2017-12-08 	|  5.95  	| 5.84   	|  4.81  	|  4.59  	|  3.89  	|  3.73  	|  3.73  	|  3.69  	|
| 2017-12-09 	|  11.82 	| 11.73  	|  9.87  	|  9.56  	|  7.64  	|  7.07  	|  7.07  	|  6.82  	|
| 2017-12-10 	|  31.50 	| 31.03  	|  25.13 	|  24.57 	|  18.80 	|  17.07 	|  17.00 	|  16.82 	|
| 2017-12-11 	|  23.45 	| 23.15  	|  17.30 	|  16.00 	|  11.95 	|  11.30 	|  11.05 	|  10.62 	|
| 2017-12-12 	|  25.29 	| 25.16  	|  20.84 	|  20.26 	|  14.65 	|  12.52 	|  12.13 	|  12.19 	|
| 2017-12-13 	|  14.08 	| 14.08  	|  11.00 	|  10.67 	|  8.92  	|  6.92  	|  6.83  	|  6.80  	|
| 2017-12-14 	|  11.63 	| 11.63  	|  10.50 	|  10.25 	|  7.25  	|  6.38  	|  6.25  	|  6.38  	|
| 2017-12-15 	|  20.86 	| 20.71  	|  16.29 	|  15.71 	|  10.29 	|  8.86  	|  8.86  	|  8.86  	|
| 2017-12-16 	|  16.89 	| 16.89  	|  13.56 	|  13.33 	|  6.44  	|  6.44  	|  6.44  	|  6.44  	|
| 2017-12-17 	|  3.33  	| 3.33   	|  3.33  	|  3.33  	|  1.33  	|  1.33  	|  1.33  	|  1.33  	|
| 2017-12-18 	|  5.00  	| 5.00   	|  5.00  	|  5.00  	|  1.00  	|  1.00  	|  1.00  	|  1.00  	|
| 2017-12-19 	|  5.00  	| 5.00   	|  5.00  	|  5.00  	|  2.00  	|  1.00  	|  1.00  	|  1.00  	|

## Extra Findings
### Ranges of Visibility
With a constant eye level, the visible distance to the horizon remains constant. Something interesting that I found is that the distances between latitude degrees were unchanging, however the distances between longitude degrees were variable. The tables below show the min, max, and average for how the visible ranges for latitide and longitude changed throughout the race.

| latitudeRangeMax  | latitudeRangeMin  | latitudeRangeAvg  |
| ----------------- | ----------------- | ----------------- |
| 0.12082           | 0.12082           | 0.12082           |

| longitudeRangeMax | longitudeRangeMin | longitudeRangeAvg |
| ----------------- | ----------------- | ----------------- |
| 0.13984           | 0.12326           | 0.12960           |

## Improvements
* Multithreaded execution
* Account for per vessel altitude differences
