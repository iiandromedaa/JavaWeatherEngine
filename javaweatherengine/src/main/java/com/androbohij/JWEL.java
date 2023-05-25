package com.androbohij;
import okhttp3.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.*;
import com.google.gson.*;

public class JWEL {
    private static int[] inputShape = {1, 60, 6, 1, 1};
    // public static double[][][][][] predictions;
    InputStream path;
    JWEL() {
        try {
            path = App.class.getResourceAsStream("JWEL");

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        refresh();
    }
    public static String getPast(String lat, String lon, String startDate, String endDate){
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

    public static String getIPgeo(String ip){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
	        .url("http://ip-api.com/json/"+"75.72.246.132"+"?fields=status,message,country,regionName,city,lat,lon,query")
	        // .get()
	        // .addHeader("content-type", "application/octet-stream")
	        .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(java.net.SocketException socketException) {
            return /*new String[]{*/"sock"/* }*/;
        }
        catch(Exception unkException) {
            return /*new String[]{*/"fuck"/* }*/;
        }
    }

    @Deprecated
    public String[] getThirty() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 30 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    }

    private String[] getSixty() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 60 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    }

    public void refresh() {
        URL whatismyip;
        try {
            App.ONLINE = true;
            whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
            whatismyip.openStream()));
            String ip = in.readLine();
            System.out.println(ip);
            JsonObject parsed = new Gson().fromJson(getIPgeo(ip), JsonObject.class);
            String country = parsed.get("country").toString();
            String state = parsed.get("regionName").toString();
            String city = parsed.get("city").toString();
            String lat = parsed.get("lat").toString();
            String lon = parsed.get("lon").toString();
            if (PreferenceHandler.autoLoc) {
                PreferenceHandler.setLat(Double.parseDouble(lat));
                PreferenceHandler.setLon(Double.parseDouble(lon));
            }
        } catch (UnknownHostException uhe) {
            //cant reach host / unresolved host
            System.out.println(uhe.toString());
            System.out.println("Internet may not be available, please restart the application when your device is online again");
            App.ONLINE = false;
        } catch (Exception mue) {
            //all other exceps
            System.out.println(mue.toString());
        }
    }

    private void predict() {

    }
}
