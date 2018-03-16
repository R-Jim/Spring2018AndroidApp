package utils;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import data.remote.APIService;
import data.remote.RetrofitClient;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class APIUtils {
    //    public static final String LOCAL_IP = "http://192.168.0.3";
    public static final String LOCAL_IP = "http://10.82.139.247";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }


}
