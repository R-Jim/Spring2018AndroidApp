package data.remote;

import java.util.List;
import java.util.Observable;

import data.model.Item;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by elpsychris on 03/12/2018.
 */

public interface APIService {

    // Synchronous declaration
    @GET("/getItem")
    Call<Item> getItem(@Query("itemId") String id);

    @GET("/getAllItem")
    Call<List<Item>> getItemList();


}
