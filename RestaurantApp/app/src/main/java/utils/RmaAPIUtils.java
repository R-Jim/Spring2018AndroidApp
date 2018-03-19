package utils;

import data.remote.RmaAPIService;
import data.remote.RetrofitClient;
import service.ApiAuthenticationClient;

/**
 * Created by elpsychris on 03/12/2018.
 */

public class RmaAPIUtils {
    public static final String LOCAL_IP = "http://192.168.100.161";
    public static final String PORT = "8080";
    public static final String BASE_URL = LOCAL_IP + ":" + PORT;

    public static RmaAPIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(RmaAPIService.class);
    }

    public boolean authentication(String username, String password) {
        ApiAuthenticationClient apiAuthenticationClient =
                new ApiAuthenticationClient(
                        BASE_URL
                        , username
                        , password
                );
        return apiAuthenticationClient.execute().equals("true");
    }
}
