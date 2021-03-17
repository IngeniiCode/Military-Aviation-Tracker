# FAA Aircraft Manufacture Model Reference Service
Data provided by the FAA on a daily basis, sourced from file `ACFTREF.txt`

## Dataset Information
Data is provided in a CSV format, comprised of the following fields
|Position|Name|Description|
|---|--- |--- |
| 0 | CODE | this code can be mapped to the `MFR MDL COD` field in MASTER.txt | 
| 1 | MFR  | Manufacture (e.g.: AAR AIRLIFT GROUP INC  ) | 
| 2 | MODEL | Common model name (e.g. UH-60A ) | 
| 3 | TYPE-ACFT | Aircraft Type | 
| 4 | TYPE-ENG | Engine Type | 
| 5 | AC-CAT | Category Code | 
| 6 | BUILD-CERT-IND | Type Certification Code | 
| 7 | NO-ENG | Number of engines | 
| 8 | NO-SEATS | Number of seats | 
| 9 | AC-WEIGHT |  Weight Classification | 
| 10 | SPEED | Average Cruise Speed, when specified | 
| 11 | TC-DATA-SHEET | Type Certificate Data Sheet | 
| 12 | TC-DATA-HOLDER | Type Certificat Data Holder | 

### Aircraft Types
	1 - Glider
	2 - Balloon
	3 - Blimp/Dirigible
	4 - Fixed wing single engine
	5 - Fixed wing multi engine
	6 - Rotorcraft
	7 - Weight-shift-control
	8 - Powered Parachute
	9 - Gyroplane
	H - Hybrid Lift
	O - Other

### Engine Types 
	0 - None
	1 - Reciprocating
	2 - Turbo-prop
	3 - Turbo-shaft
	4 - Turbo-jet
	5 - Turbo-fan
	6 - Ramjet
	7 - 2 Cycle
	8 - 4 Cycle
	9 – Unknown
	10 – Electric
	11 - Rotary

### Category Code
	1 - Land
	2 - Sea
	3 - Amphibian

### Type Certification Code
	0 – Type Certificated
	1 – Not Type Certificated
	2 – Light Sport

### Weight Classification (max gross takeoff weight)
	1 - Up to 12,499
	2 - 12,500 - 19,999
	3 - 20,000 and over.
	4 – UAV up to 55


