package utils;

import data.remote.APIService;
import data.remote.RetrofitClient;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class ApiUtils {
    public static final String LOCAL_IP = "http://192.168.2.27";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
