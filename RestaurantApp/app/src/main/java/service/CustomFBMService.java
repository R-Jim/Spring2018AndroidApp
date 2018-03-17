package service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by elpsychris on 16/03/2018.
 */

public class CustomFBMService extends FirebaseMessagingService {

    private final String TAG = "CustomFBMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        System.out.println("Received mess");
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if msg contains a data payload
        if (remoteMessage.getData().size() > 0 ) {
            Log.d(TAG, "MSG data payload: " + remoteMessage.getData());

            // TODO: Handle msg

        }

        if (remoteMessage.getNotification() != null) {
            System.out.println("Received new mess " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Msg Noti Body" + remoteMessage.getNotification().getBody());
        }
    }


}
