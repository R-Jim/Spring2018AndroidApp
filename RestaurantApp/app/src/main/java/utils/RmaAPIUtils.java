package utils;

import data.remote.RmaAPIService;
import data.remote.RetrofitClient;
import service.ApiAuthenticationClient;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class RmaAPIUtils {
    public static final String LOCAL_IP = "http://25.14.138.194";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static RmaAPIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(RmaAPIService.class);
    }
}
