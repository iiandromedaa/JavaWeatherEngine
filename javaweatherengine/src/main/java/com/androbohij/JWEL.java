package com.androbohij;
import okhttp3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;

public class JWEL {
    private static int[] inputShape = {1, 60, 6, 1, 1};
    public static int[] predictions;
    static MultiLayerNetwork JWEL;
    String path;
    JWEL() {
        URL whatismyip;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
            whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
            IPGeolocationAPI api = new IPGeolocationAPI(Secrets.getLocoKey());
            System.out.println(getLatLon(api, ip));
        } catch (UnknownHostException uhe) {
            System.out.println(uhe.toString());
            System.out.println("Internet may not be available, please restart the application when your device is online again");
        } catch (MalformedURLException mue) {
            System.out.println(mue.toString());
        } catch (SocketException se) {
            System.out.println(se.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        try {
            path = new ClassPathResource("/resources/JWEL.h5").getFile().getPath();
            JWEL = KerasModelImport.importKerasSequentialModelAndWeights(path, inputShape, false);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (Preferences.autoLoc) {
            
        }
    }
    public String getPast(String lat, String lon, String startDate, String endDate){
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
    public String[] getThirty() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 30 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    }

    public String[] getSixty() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 60 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    }

    public String[] getLatLon(IPGeolocationAPI api, String ip) {
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,latitude,longitude");

        Geolocation geolocation = api.getGeolocation(geoParams);

        // Check if geolocation lookup was successful
        if(geolocation.getStatus() == 200) {
            System.out.println(geolocation.getCountryName());
            System.out.println(geolocation.getStateProvince());
            System.out.println(geolocation.getCity());
            System.out.println(geolocation.getLatitude());
            System.out.println(geolocation.getLongitude());
            return new String[]{geolocation.getLatitude(), geolocation.getLongitude()};
        } else {
            System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
            return new String[]{"",""};
        }
    }

    public void refresh() {
        System.out.println(getPast("0","0",getSixty()[1],getSixty()[0]));
    }
}
