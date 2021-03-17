http://192.168.1.230:8983/solr/aviation_contacts/select?q=milCallsign%3Atrue&rows=0&wt=json&json.facet=
{ "top_callsigns":
  { "type":"terms",
    "field":"callsign",
    "limit":10,
    "facet":{
      "top_airfames":{
        "type":"terms",
        "field":"icao",
        "limit":5
      }
    }
  }
}


