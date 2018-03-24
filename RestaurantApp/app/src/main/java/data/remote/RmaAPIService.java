package data.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import data.model.Category;
import data.model.Item;
import data.model.OrderRequest;
import data.model.Request;
import data.model.Table;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by elpsychris on 03/12/2018.
 */

public interface RmaAPIService {

    // Synchronous declaration
    @GET("/getItem")
    Call<Item> getItem(@Query("itemId") String id);

    @GET("/items")
    Call<List<Item>> getItemList();
//
//    @GET("/requestList")
//    Call<List<OrderRequest>> getRequestList();

    @GET("/tables")
    Call<List<Table>> getTableList();

    @GET("/categories")
    Call<List<Category>> getCategoryList();

    @GET("/requests")
    Call<List<Request>> getRequestOrderList();

    @POST("/orders")
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendReceiptToServer(@Body OrderRequest orderRequest);

    @HTTP(method = "DELETE", path = "/requests", hasBody = true)
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendDismissRequest(@Body List<Request> dismissList);
}
