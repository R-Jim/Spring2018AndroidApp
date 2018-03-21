package data.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import data.model.Category;
import data.model.Item;
import data.model.OrderRequest;
import data.model.Receipt;
import data.model.Request;
import data.model.Table;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("/requests")
    Call<List<Request>> getRequestList();

    @GET("/tables")
    Call<List<Table>> getTableList();

    @GET("/categories")
    Call<List<Category>> getCategoryList();

    @GET("/requests")
    Call<List<OrderRequest>> getRequestOrderList();

    @POST("/orders")
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendReceiptToServer(@Body OrderRequest orderRequest);

    @GET("/receipts/{id}")
    Call<Receipt> getReceiptByReceiptId(@Path("id") Integer receiptSeq);

    @GET("/tables/{id}/receipts")
    Call<Receipt> getReceiptByTableId(@Path("id") int tableSeq);
}
