package utils;

import data.remote.RmaAPIService;
import data.remote.RetrofitClient;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class RmaAPIUtils {
    public static final String LOCAL_IP = "http://192.168.100.120";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static RmaAPIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(RmaAPIService.class);
    }
}
