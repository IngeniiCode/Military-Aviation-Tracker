# NewContactsPipeline - New Contacts Solr Indexing Pipeline  
Service to parse dump1090 message traffic, and publish to Solr for indexing.

#### *Work in progress*

## Processor Objectives

## Usage
Operating the MessageParser service

### Application parameters
| Parameter | Environment Variable | Argument Type | Function |
|--- |--- | --- |--- |
| --msg-host | MILTRACK_DUMP1090_HOST | string | hostname or IP of stratux receiver  |
| --msg-port | MILTRACK_DUMP1090_PORT | number | port on which dump1090 SBS messages are published (def. 30003)  | 
| --solr-host | MILTRACK_SOLR_HOST | string | hostname or IP of solr server  |
| --solr-port | MILTRACK_SOLR_PORT | number | port used by Solr for indexing |
| --solr-core | MILTRACK_SOLR_CONTACT_COLLECTION | string | contacts collection canonical name  |
| --dryrun |   | flag | operate in dry-run mode, do not write to Solr |
| --debug |  | flag | writes all parsed message events to STDOUT | 

## Solr Search Results Examples
Some example queries from the Contacts collection `aviation_contacts`  

### All Records Search  
  `/solr/aviation_contacts/select?q=*%3A*`  
![Solr - Aviation Contacts -- wildcard](.img/Solr-Contacts-Basic-Search.png)

### Agregated Results - Top Military Callsigns Used 
  `/solr/aviation_contacts/select?facet.field=callsign&facet=on&q=milCallsign%3Atrue&rows=0`  
![Solr - Aviation Contacts -- Top Military Callsigns (faceted)](.img/Solr-Contacts-Top-Military-Callsigns.png)  
   
