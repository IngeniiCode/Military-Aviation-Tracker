# StratuxMilTracker
MicroService to parse Military callsigns from a Stratux hosted dump1090 service cluster 

#### *Work in progress*

## Stratux Mil Tracker Objectives 
Process data streamed from a Stratux ADS-B receiver to acheive the following objectives.
* Identity all aircraft within 5 miles of Canyon Lake, feeding a dashboard displaying the current over-head aircraft.
* Created a database of aircraft the frequent the Canyon Lake area (sightseeing), including type, elevation, speed. etc.
* Catalog which of the USAF Training Command aircraft are transiting the area, or using area for maneuvers, with an eye
toward determining distribution of callsigns and airframes observed, and which hours they are typically overhead.
* Collect range and bearing data to determine antenna location performance.
* Enable detection of rapidly approaching low-altitude aircraft (e.g. high-speed T38's) to improve success in photographing aircraft.


## Usage

### Application parameters
| Parameter | Environment Variable | Argument Type | Function |
|--- | --- | --- |--- |
| --myhost | MILTRACK_PUBLISHER_HOST | string | hostname or IP address to bind publish ports to |
| --host | MILTRACK_DUMP1090_HOST | string | hostname or IP of stratux receiver  |
| --port | MILTRACK_DUMP1090_PORT | number | port on which dump1090 SBS messages are published (def. 30003)  | 
| --lat | MILTRACK_FIXED_LATITUDE | number | latitude in degrees  (e.g. 29.88058)  |
| --long | MILTRACK_FIXED_LONGITUDE | number | longitude in degrees (e.g. -98.23455) |
| --range | MILTRACK_MAX_DETECTION_RANGE | number | maximum distance in miles for contacts |
| --milonly |   | flag | only process contacts exhibiting military attributes |
| --quiet |   | flag | only display contacts meeting filter requirement |


### Startup without geolocation
Startup without setting fixed station location will provide limited function, the the messages can still be processed

  --host =  hostname or IP of stratux receiver  
  --port =  port on which dump1090 SBS messages are published (def. 30003)   

      java -jar target/StratuxMilTracker-1.0-SNAPSHOT-jar-with-dependencies.jar  --host=192.168.1.20 --port=30003


### Startup with geolocation
Provided basic funtion with range and bearing (azimuth) calculations.  Setting the fixed location also enables other features such as maxmum range filtering

  --host =  hostname or IP of stratux receiver  
  --port =  port on which dump1090 SBS messages are published (def. 30003)  
  --lat = latitude in degrees  (e.g. 29.88058)
  --long = longitude in degrees (e.g. -98.23455)

    java -jar target/StratuxMilTracker-1.0-SNAPSHOT-jar-with-dependencies.jar  --host=192.168.1.20 --port=30003 --lat=29.88058 --lon=-98.23455 

### Startup with geolocation, show only military contacts within 8 miles
Provided basic funtion with range and bearing (azimuth) calculations.  Setting the fixed location also enables other features such as maxmum range filtering
  
  --host =  hostname or IP of stratux receiver  
  --port =  port on which dump1090 SBS messages are published (def. 30003)  
  --lat = latitude in degrees  (e.g. 29.88058)
  --long = longitude in degrees (e.g. -98.23455)

    java -jar target/StratuxMilTracker-1.0-SNAPSHOT-jar-with-dependencies.jar  --host=192.168.1.20 --port=30003 --lat=29.88058 --lon=-98.23455 --range=8 --milonly 
  
## Example of local logged military aircraft traffic

Each aircraft contact is derived from aggregating 8 seperate dump1090 message transmissions, which are published in a CSV format.
Messages are parsed to determine the ICAO hex string, which is then used as the primary key to create 
or update the AircraftSBS data model.   

    SCARY20 +MIL+ (AE1113)  9.00mi @ 176.57sº  4,000ft  187kts
    SHINR11 +MIL+ (AE6011)  7.48mi @ 185.94sº  3,475ft  154kts
    BREW46 +MIL+ (ADFF50)  8.99mi @ 176.62sº  2,925ft  267kts
    SCARY31 +MIL+ (AE01E3)  5.76mi @ 178.76sº  4,000ft  178kts
    SCARY32 +MIL+ (AE13F7)  9.44mi @ 183.23sº  3,950ft  234kts
    LYRE72 +MIL+ (AE11BD)  9.49mi @ 176.62sº  3,975ft  164kts
    SPAR99 +MIL+ (AE0978)  9.27mi @ 182.08sº  7,650ft  261kts


## Data Publishing Ports
Json data streams are available on the following ports.

#### Debugging
You can easily varify connectivity and monitor output using `telent`  
    ex:   `telnet localhost 9105` 

|Service Name|Port|Description|
|--- |--- |--- |
|New Contacts | 9101 | Json data - with new ICAO  detected |
|Nearby Contact (in range X) | 9102 | Json data – contact w/ range, bearing, altitude and speed within a defined range (ex.  only contacts within 5 miles) |
|Military Style Contacts | 9103 | Json data – contacts w/ range, bearing, altitude, speed & mil flag |
|Military Style (in range X) | 9104 | Json data – contacts w/ range, bearing, altitude, speed & mil flag within a defined range (ex.  only contacts within 5 miles) |
|Real-Time All Contacts | 9105 | Josn data - all contacts with at least known callsign |

## Change Log
| Updated | Author | Comments |
| --- | --- |--- |
| 03-MAR-2021 | d.demartini | Project initiated |
| 05-MAR-2021 | d.demartini | write test cases to validate concept and function | 
| 06-MAR-2021 | d.demartini | changed from websocket service to TCP port reader |
| 07-MAR-2021 | d.demartini | added simple caching repository to store new AircraftSBS data objects |
| 08-MAR-2021 | d.demartini | implemented range and bearing formulas (arched triangle calculations) | 
| 10-MAR-2021 | d.demartini | extended function with --milonly and --quiet features | 
| 11-MAR-2021 | d.demartini | Added New Contact Json feed on port 9101 |
| 11-MAR-2021 | d.demartini | Added new Military Contact Json feed on port 9103 |
| 11-MAR-2021 | d.demartini | Added new In-Range (aka fly-by) Contact Json feed on port 9102 |
| 11-MAR-2021 | d.demartini | Added new In-Range (aka fly-by) Military Contact Json feed on port 9104 |
| 12-MAR-2021 / d.demartini | Added capability to set Environt Variables to configured service |
| 12-MAR-2021 | d.demartini | Added new port (9105) to publish all available data in real time |

----
## Other Information  
### What does the device running Dump1090 consist of?

* Raspberry Pi 3b+ (1GB model)
* NooElec  SDR (RTL)  tuned to 1090 MHz
* NooElec tuned 1090 Mhz antenna (not in photo)
* 5v / 2A  micro USB power supply

![Raspberry PI ADS-B Decoder](./.img/RaspberryPi3_Dump1090_Receiver.jpg)

_Note:_ the black box to left of Pi enclosure is not related to the Dump1090 / Stratux 
project.  It is an FM stereo transmitter, powered by the Pi's USB used to transmit P25 Phase II decoded LEO repeater traffic.
The rebroadcast of the decoded audio allows monitoring of the local LEO communications from
most of the property.  

### Why the interest?
I enjoy tech projects in general, and any that deal with aviation are of special interest.
Add into that work with radio systems and digital signal processing, and it's just something
that I'm compelled to do.

There are other motivations, one of which is getting a heads-up when one of the USAF
training flights are heading into the area.  Occasionally they turn the lake into a
sort of sight-seeing / high-performance envelope training sorte.  You can see a recent
visit to the area by one of the T38's in this image (snapped by ADS-B Exchange -- awesome data tool!)

![T38 FlyOver -- 2021-02-24 @ 14.18.12](./.img/T38-FlyOver_2021-02-24_14.18.12.png)  

