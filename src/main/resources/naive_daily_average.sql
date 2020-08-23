SELECT
    day, AVG(visibleTeams)
FROM
    (SELECT
        day,
            observingTeam AS team,
            COUNT(visibleTeam) AS visibleTeams
    FROM
        (SELECT
			DATE(m1.gpsAt) AS day,
				m1.gpsAtMillis,
				m1.teamSerial AS observingTeam,
				m1.latitude AS observingTeamLatitude,
				m1.longitude AS observingTeamLongitude,
				m2.teamSerial AS visibleTeam,
				m2.latitude AS visibleTeamLatitude,
				m2.longitude AS visibleTeamLongitude
		FROM
			moments m1
		JOIN moments m2 ON m1.gpsAtMillis = m2.gpsAtMillis
			AND m1.teamSerial != m2.teamSerial
			AND m2.latitude BETWEEN m1.lowerLatitude AND m1.upperLatitude
			AND m2.longitude BETWEEN m1.lowerLongitude AND m1.upperLongitude
		GROUP BY DATE(m1.gpsAt) , m1.teamSerial , m2.teamSerial
		ORDER BY m1.gpsAtMillis) AS visibleTeamsPerDay
    GROUP BY day , observingTeam) AS visibleTeams
GROUP BY day;