package com.example.spursh.youtube;
/**
 * Created by spurs on 4/28/2016.
 */
public class QueryBuilder {
    public String getDatabaseName() {
        return "code101";
    }
    public String getApiKey() {
        return ".....PUT YOUR API KEY HERE.......";
    }
    public String getBaseUrl()
    {
        return "mongodb://tjs:password@ds013971.mlab.com:13971/sensed/"+getDatabaseName()+"/collections/";
    }
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }
    public String documentRequest()
    {
        return "docs101";
    }
    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }
    public String createSensorDetails(SensorDetails sd) {
        return String.format("{\"document\" : {\"pressure\": \"%s\", \"temperature\": \"%s\", \"humidity\": \"%s\"}," +
                " \"safe\" : true}", sd.pressure, sd.temperature, sd.humidity);
    }
}
