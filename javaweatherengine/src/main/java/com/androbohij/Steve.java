package com.androbohij;
import okhttp3.*;
import java.time.*;

public class Steve {
    boolean autoLoc;
    Steve() {
        
    }
    public String getThirty(String lat, String lon, String startDate, String endDate){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
	        .url("https://meteostat.p.rapidapi.com/point/daily?lat="+lat+"&lon="+lon+"&start="+startDate+"&end="+endDate)
	        .get()
	        .addHeader("content-type", "application/octet-stream")
	        .addHeader("X-RapidAPI-Key", Secrets.getMeteoKey())
	        .addHeader("X-RapidAPI-Host", "meteostat.p.rapidapi.com")
	        .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(java.net.SocketException socketException) {
            return "sock";
        }
        catch(Exception unkException) {
            return "fuck";
        }
    }
    public String[] getDates() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 30 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    } 
    
    public int loadModel() {
        return 0;
    }
}
