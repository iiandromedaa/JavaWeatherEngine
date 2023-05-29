package com.androbohij;

import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;

import okhttp3.*;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.FloatNdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.types.TFloat32;
import com.google.gson.*;

public class JWEL {
    public static float[] mean, std;

    public SavedModelBundle jwel;

    public static float day0Max, day1Max, day2Max, day3Max, day4Max, day5Max, day6Max;

    public static float day1Pres, day2Pres, day3Pres, day4Pres, day5Pres, day6Pres;

    public static float day1Prcp, day2Prcp, day3Prcp, day4Prcp, day5Prcp, day6Prcp;

    public static float trueDay2Max, trueDay3Max, trueDay4Max, trueDay5Max, trueDay6Max;

    JWEL() {
        try {
            loadModel("javaweatherengine/src/main/resources/com/androbohij/JWEL");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        refresh();
    }
    public static String getPast(String lat, String lon, String startDate, String endDate) {
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

    public static String getPastVisualCrossing(String lat, String lon, String startDate, String endDate) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
            .url("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+lat+"%2C"+lon+"/"+startDate+"/"+endDate+"?unitGroup=metric&include=days&key="+Secrets.getVCkey()+"&contentType=json")
            .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch(java.net.SocketException socketException) {
            return "sock";
        }
        catch(Exception unkException) {
            return "fuck";
        }
    }

    public static String getFuture(String lat, String lon) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+lat+"%2C"+lon+"&days=3")
                .get()
                .addHeader("X-RapidAPI-Key", Secrets.getMeteoKey())
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(Exception e) {
            return e.toString();
        }
    }

    public static String getIPgeo(String ip){
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
	        .url("http://ip-api.com/json/"+ip+"?fields=status,message,country,regionName,city,lat,lon,query")
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

    private String[] getSixty() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        Period daysThr = Period.ofDays( 59 );
        LocalDate agoThr = today.minus( daysThr );
        return new String[] {today.toString(), agoThr.toString()};
    }

    public static Date[] getNextFive() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        return new Date[] {Date.from(today.atStartOfDay(z).toInstant()), Date.from(today.plus(Period.ofDays(1)).atStartOfDay(z).toInstant()), 
            Date.from(today.plus(Period.ofDays(2)).atStartOfDay(z).toInstant()), 
            Date.from(today.plus(Period.ofDays(3)).atStartOfDay(z).toInstant()), 
            Date.from(today.plus(Period.ofDays(4)).atStartOfDay(z).toInstant()), 
            Date.from(today.plus(Period.ofDays(5)).atStartOfDay(z).toInstant())};
    }

    public static String[] getNextFiveStr() {
        ZoneId z = ZoneId.systemDefault();
        LocalDate today = LocalDate.now( z );
        return new String[] {today.toString(), today.plus(Period.ofDays(1)).toString(), 
            today.plus(Period.ofDays(2)).toString(), 
            today.plus(Period.ofDays(3)).toString(), 
            today.plus(Period.ofDays(4)).toString(), 
            today.plus(Period.ofDays(5)).toString()};
    }

    private void loadModel(String path) {
        jwel = SavedModelBundle.load(path, "serve");
        // System.out.println(jwel.metaGraphDef().getSignatureDefMap().get("serving_default"));
    }

    public void refresh() {
        URL whatismyip;
        try {
            App.ONLINE = true;
            whatismyip = new URL("http://checkip.amazonaws.com");
            URLConnection urlConn = whatismyip.openConnection();
            urlConn.setConnectTimeout(3000);
            urlConn.setReadTimeout(3000);
            urlConn.setAllowUserInteraction(false);         
            urlConn.setDoOutput(true);
            InputStream inStream = urlConn.getInputStream();
            String ip = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
            // System.out.println(ip);
            //new BufferedReader(new InputStreamReader(inStream));
            JsonObject parsed = new Gson().fromJson(getIPgeo(ip), JsonObject.class);
            @SuppressWarnings("unused")
            String country = parsed.get("country").toString();
            @SuppressWarnings("unused")
            String state = parsed.get("regionName").toString();
            @SuppressWarnings("unused")
            String city = parsed.get("city").toString();
            String lat = parsed.get("lat").toString();
            String lon = parsed.get("lon").toString();
            if (PreferenceHandler.getAutoLoc()) {
                PreferenceHandler.setLat(Double.parseDouble(lat));
                PreferenceHandler.setLon(Double.parseDouble(lon));
            } else {
                lat = String.valueOf(PreferenceHandler.getLat());
                lon = String.valueOf(PreferenceHandler.getLon());
            }
            System.out.println(PreferenceHandler.getLat());
            System.out.println(PreferenceHandler.getLon());
            predict(createInput(lat, lon));
        } catch (UnknownHostException uhe) {
            System.out.println(uhe.toString());
            System.out.println("Internet may not be available, please restart the application when your device is online again");
            App.ONLINE = false;
        } catch (SocketTimeoutException socke) {
            System.out.println(socke.toString());
            System.out.println("Internet may not be available, please restart the application when your device is online again");
            App.ONLINE = false;
        } catch (Exception mue) {
            System.out.println(mue.toString());
        }
    }

    private void predict(TFloat32 inputTensor) {
        try (Session session = jwel.session()) {
            TFloat32 output = (TFloat32)session.runner()
            .feed("serving_default_batch_norm_0_input", inputTensor)
            .fetch("StatefulPartitionedCall").run().get(0);

            long[] shapeArray = {1, 5, 6, 1, 1};
            FloatNdArray results = NdArrays.ofFloats(Shape.of(shapeArray));
            output.copyTo(results);
            FloatNdArray denorm = denormalizeOutput(output, mean, std);

            day2Max = denorm.getFloat(0, 0, 2, 0, 0); day3Max = denorm.getFloat(0, 1, 2, 0, 0); day4Max = denorm.getFloat(0, 2, 2, 0, 0); day5Max = denorm.getFloat(0, 3, 2, 0, 0); day6Max = denorm.getFloat(0, 4, 2, 0, 0);
            day2Prcp = denorm.getFloat(0, 0, 3, 0, 0); day3Prcp = denorm.getFloat(0, 1, 3, 0, 0); day4Prcp = denorm.getFloat(0, 2, 3, 0, 0); day5Prcp = denorm.getFloat(0, 3, 3, 0, 0); day6Prcp = denorm.getFloat(0, 4, 3, 0, 0);
            day2Pres = denorm.getFloat(0, 0, 5, 0, 0); day3Pres = denorm.getFloat(0, 1, 5, 0, 0); day4Pres = denorm.getFloat(0, 2, 5, 0, 0); day5Pres = denorm.getFloat(0, 3, 5, 0, 0); day6Pres = denorm.getFloat(0, 4, 5, 0, 0);
            System.out.println(getPastVisualCrossing(String.valueOf(PreferenceHandler.getLat()), String.valueOf(PreferenceHandler.getLon()), getNextFive()[1].toString(), getNextFive()[5].toString()));
            JsonObject dayData = new Gson().fromJson(getPastVisualCrossing(String.valueOf(PreferenceHandler.getLat()), String.valueOf(PreferenceHandler.getLon()), getNextFive()[1].toString(), getNextFive()[5].toString()), JsonObject.class);
            JsonArray forecastArray = dayData.getAsJsonArray("days");
            trueDay2Max = forecastArray.get(0).getAsJsonObject().get("tempmax").getAsFloat();
            trueDay3Max = forecastArray.get(1).getAsJsonObject().get("tempmax").getAsFloat();
            trueDay4Max = forecastArray.get(2).getAsJsonObject().get("tempmax").getAsFloat();
            trueDay5Max = forecastArray.get(3).getAsJsonObject().get("tempmax").getAsFloat();
            trueDay6Max = forecastArray.get(4).getAsJsonObject().get("tempmax").getAsFloat();
        }
    }

    private TFloat32 createInput(String lat, String lon) {
        // JsonObject dayData = new Gson().fromJson(getPast(lat, lon, getSixty()[1], getSixty()[0]), JsonObject.class);
        // JsonArray forecastArray = dayData.getAsJsonArray("data");
        JsonObject dayData = new Gson().fromJson(getPastVisualCrossing(lat, lon, getSixty()[1], getSixty()[0]), JsonObject.class);
        JsonArray forecastArray = dayData.getAsJsonArray("days");
        float[][][][][] inputData = new float[1][60][6][1][1];
        float prevTavg = 0.0f;
        float prevTmin = 0.0f;
        float prevTmax = 0.0f;
        float prevPrcp = 0.0f;
        float prevSnow = 0.0f;
        float prevPres = 0.0f;
        for (int i = 0; i < 60; i++) {
            JsonObject forecastDay = forecastArray.get(i).getAsJsonObject();
            // if (forecastDay.has("tavg") && forecastDay.has("tmin") && forecastDay.has("tmax")
            // && forecastDay.has("prcp") && forecastDay.has("snow") && forecastDay.has("pres")) {
            if (forecastDay.has("temp") && forecastDay.has("tempmin") 
            && forecastDay.has("tempmax") && forecastDay.has("precip") 
            && forecastDay.has("snow") && forecastDay.has("pressure")) {
                // JsonElement tavgElement = forecastDay.get("tavg");
                JsonElement tavgElement = forecastDay.get("temp");
                float tavg = tavgElement.isJsonNull() ? prevTavg : tavgElement.getAsFloat();
                prevTavg = tavg;

                // JsonElement tminElement = forecastDay.get("tmin");
                JsonElement tminElement = forecastDay.get("tempmin");
                float tmin = tminElement.isJsonNull() ? prevTmin : tminElement.getAsFloat();
                prevTmin = tmin;

                // JsonElement tmaxElement = forecastDay.get("tmax");
                JsonElement tmaxElement = forecastDay.get("tempmax");
                float tmax = tmaxElement.isJsonNull() ? prevTmax : tmaxElement.getAsFloat();
                prevTmax = tmax;

                // JsonElement prcpElement = forecastDay.get("prcp");
                JsonElement prcpElement = forecastDay.get("precip");
                float prcp = prcpElement.isJsonNull() ? prevPrcp : prcpElement.getAsFloat();
                prevPrcp = prcp;

                // JsonElement snowElement = forecastDay.get("snow");
                JsonElement snowElement = forecastDay.get("snow");
                float snow = snowElement.isJsonNull() ? prevSnow : snowElement.getAsFloat();
                prevSnow = snow;

                // JsonElement presElement = forecastDay.get("pres");
                JsonElement presElement = forecastDay.get("pressure");
                float pres = presElement.isJsonNull() ? prevPres : presElement.getAsFloat();
                prevPres = pres;

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
        day0Max = floatNdArray.getFloat(0, 58, 2, 0, 0);
        day1Max = floatNdArray.getFloat(0, 59, 2, 0, 0);
        day1Pres = floatNdArray.getFloat(0, 59, 5, 0, 0);
        try {
            FloatNdArray normed = normalizeInput(floatNdArray);
            System.out.println("high today " + floatNdArray.getFloat(0, 59, 2, 0, 0) + " c " + toF(floatNdArray.getFloat(0, 59, 2, 0, 0))+" f");
            TFloat32 inputTensor = Tensor.of(TFloat32.class, shape, normed::copyTo);
            return inputTensor;
        } catch (Exception e) {
            TFloat32 zero = Tensor.of(TFloat32.class, shape);
            return zero;
        }
    }

    public static FloatNdArray normalizeInput(FloatNdArray inputData) {
        mean = new float[(int) inputData.shape().get(2)];
        std = new float[(int) inputData.shape().get(2)];
    
        long steps = inputData.shape().get(1);
        long features = inputData.shape().get(2);
    

        for (long step = 0; step < steps; step++) {
            for (long feature = 0; feature < features; feature++) {
                mean[(int) feature] += inputData.getFloat(0, step, feature, 0, 0);
            }
        }
        for (long feature = 0; feature < features; feature++) {
            mean[(int) feature] /= steps;
        }

        for (long step = 0; step < steps; step++) {
            for (long feature = 0; feature < features; feature++) {
                float value = inputData.getFloat(0, step, feature, 0, 0);
                std[(int) feature] += Math.pow(value - mean[(int) feature], 2);
            }
        }
        for (long feature = 0; feature < features; feature++) {
            std[(int) feature] = (float) Math.sqrt(std[(int) feature] / steps);
        }

        FloatNdArray normalizedData = NdArrays.ofFloats(inputData.shape());

        for (long step = 0; step < steps; step++) {
            for (long feature = 0; feature < features; feature++) {
                float value = inputData.getFloat(0, step, feature, 0, 0);
                float normalizedValue = (value - mean[(int) feature]) / std[(int) feature];
                normalizedData.setFloat(normalizedValue, 0, step, feature, 0, 0);
            }
        }
    
        return normalizedData;
    }

    public static FloatNdArray denormalizeOutput(FloatNdArray outputData, float[] mean, float[] std) {

        FloatNdArray denormalizedData = NdArrays.ofFloats(outputData.shape());
    
        long steps = outputData.shape().get(1);
        long features = outputData.shape().get(2);
    
        for (long step = 0; step < steps; step++) {
            for (long feature = 0; feature < features; feature++) {
                float normalizedValue = outputData.getFloat(0, step, feature, 0, 0);
                float denormalizedValue = (normalizedValue * std[(int) feature]) + mean[(int) feature];
                denormalizedData.setFloat(denormalizedValue, 0, step, feature, 0, 0);
            }
        }
    
        return denormalizedData;
    }

    public static float toF(float cel) {
        return (cel * 1.8f) + 32.0f;
    }

    public static float toC(float far) {
        return (far - 32.0f) * (5.0f/9.0f);
    }
}