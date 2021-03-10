# StratuxMilTracker
MicroService to parse Military callsigns from a Stratux hosted dump1090 service cluster 

### *Work in progress*


## Change Log
| Updated | Author | Comments |
| --- | --- |--- |
| 03-MAR-2021 | d.demartini | Project initiated |
| 05-MAR-2021 | d.demartini | write test cases to validate concept and function | 
| 06-MAR-2021 | d.demartini | changed from websocket service to TCP port reader |
| 07-MAR-2021 | d.demartini | added simple caching repository to store new AircraftSBS data objects |
| 08-MAR-2021 | d.demartini | implemented range and bearing formulas (arched triangle calculations) | 
| 10-MAR-2021 | d.demartini | extended function with --milonly and --quiet features | 

## Example of local logged military aircraft traffic
 SCARY20 +MIL+ (AE1113)  9.00mi @ 176.57sº  4,000ft  187kts
 SHINR11 +MIL+ (AE6011)  7.48mi @ 185.94sº  3,475ft  154kts
 BREW46 +MIL+ (ADFF50)  8.99mi @ 176.62sº  2,925ft  267kts
 SCARY31 +MIL+ (AE01E3)  5.76mi @ 178.76sº  4,000ft  178kts
 SCARY32 +MIL+ (AE13F7)  9.44mi @ 183.23sº  3,950ft  234kts
 LYRE72 +MIL+ (AE11BD)  9.49mi @ 176.62sº  3,975ft  164kts
	 		

