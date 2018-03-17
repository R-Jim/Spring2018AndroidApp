package service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by elpsychris on 16/03/2018.
 */

public class CustomFBMInstIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
//        super.onTokenRefresh();

        // Get updated token
        String newToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(this.getClass().getSimpleName(), "New token" + newToken);
        System.out.println( "New token" + newToken);

        // Subscribe this app inst to server-side


    }
}
