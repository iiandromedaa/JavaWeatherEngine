package com.androbohij;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.*;

import okhttp3.*;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.ndarray.FloatNdArray;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.types.TFloat32;

import com.google.gson.*;

public class JWEL {
    private static int[] inputShape = {1, 60, 6, 1, 1};
    public static float[][][][][] predictions = new float[1][5][6][1][1];
    public SavedModelBundle jwel;
    JWEL() {
        try {
            loadModel("javaweatherengine/src/main/resources/com/androbohij/JWEL");
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
    private String[] getThirty() {
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

    private void loadModel(String path) {
        jwel = SavedModelBundle.load(path, "serve");
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
            createInput(lat, lon);
            predict();
            //get our 60 days
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
        try (Session session = jwel.session()) {
            
        }
    }

    private void createInput(String lat, String lon) {
        JsonObject dayData = new Gson().fromJson(getPast(lat, lon, getSixty()[1].toString(), getSixty()[0].toString()), JsonObject.class);
        JsonArray forecastArray = dayData.getAsJsonArray("data");
        float[][][][][] inputData = new float[1][60][6][1][1];
        float prevTavg = 0.0f;
        float prevTmin = 0.0f;
        float prevTmax = 0.0f;
        float prevPrcp = 0.0f;
        float prevSnow = 0.0f;
        float prevPres = 0.0f;
        for (int i = 0; i < 60; i++) {
            JsonObject forecastDay = forecastArray.get(i).getAsJsonObject();
            if (forecastDay.has("tavg") && forecastDay.has("tmin") && forecastDay.has("tmax")
            && forecastDay.has("prcp") && forecastDay.has("snow") && forecastDay.has("pres")) {
                JsonElement tavgElement = forecastDay.get("tavg");
                float tavg = tavgElement.isJsonNull() ? prevTavg : tavgElement.getAsFloat();
                prevTavg = tavg;

                JsonElement tminElement = forecastDay.get("tmin");
                float tmin = tminElement.isJsonNull() ? prevTmin : tminElement.getAsFloat();
                prevTmin = tmin;

                JsonElement tmaxElement = forecastDay.get("tmax");
                float tmax = tmaxElement.isJsonNull() ? prevTmax : tmaxElement.getAsFloat();
                prevTmax = tmax;

                JsonElement prcpElement = forecastDay.get("prcp");
                float prcp = prcpElement.isJsonNull() ? prevPrcp : prcpElement.getAsFloat();
                prevPrcp = prcp;

                JsonElement snowElement = forecastDay.get("snow");
                float snow = snowElement.isJsonNull() ? prevSnow : snowElement.getAsFloat();
                prevSnow = snow;

                JsonElement presElement = forecastDay.get("pres");
                float pres = presElement.isJsonNull() ? prevPres : presElement.getAsFloat();
                prevPres = pres;

                // Populate the input data array
                inputData[0][i][0][0][0] = tavg;
                inputData[0][i][1][0][0] = tmin;
                inputData[0][i][2][0][0] = tmax;
                inputData[0][i][3][0][0] = prcp;
                inputData[0][i][4][0][0] = snow;
                inputData[0][i][5][0][0] = pres;
            } else {
                inputData[0][i][0][0][0] = prevTavg;
                inputData[0][i][1][0][0] = prevTmin;
                inputData[0][i][2][0][0] = prevTmax;
                inputData[0][i][3][0][0] = prevPrcp;
                inputData[0][i][4][0][0] = prevSnow;
                inputData[0][i][5][0][0] = prevPres;
            }
        }
        long[] shapeArray = {1, 60, 6, 1, 1};
        Shape shape = Shape.of(shapeArray);
        FloatNdArray floatNdArray = NdArrays.ofFloats(shape);
        for (int i = 0; i < inputData.length; i++) {
            for (int j = 0; j < inputData[i].length; j++) {
                for (int k = 0; k < inputData[i][j].length; k++) {
                    for (int l = 0; l < inputData[i][j][k].length; l++) {
                        for (int m = 0; m < inputData[i][j][k][l].length; m++) {
                            float value = inputData[i][j][k][l][m];
                            floatNdArray.setFloat(value, i, j, k, l, m);
                        }
                    }
                }
            }
        }
        int size = inputData.length * inputData[0].length * inputData[0][0].length * inputData[0][0][0].length * inputData[0][0][0][0].length;
        float[] flattenedData = new float[size];
        int i2 = 0;
        for (int i = 0; i < inputData.length; i++) {
            for (int j = 0; j < inputData[i].length; j++) {
                for (int k = 0; k < inputData[i][j].length; k++) {
                    for (int l = 0; l < inputData[i][j][k].length; l++) {
                        for (int m = 0; m < inputData[i][j][k][l].length; m++) {
                            float value = inputData[i][j][k][l][m];
                            flattenedData[i2] = value;
                            i2++;
                        }
                    }
                }
            }
        }
        Tensor inputTensor = Tensor.of(TFloat32.class, shape, flattenedData);
    }
}
