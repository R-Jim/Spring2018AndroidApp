package data.remote;

import java.util.List;

import data.model.Category;
import data.model.Item;
import data.model.OrderRequest;
import data.model.Request;
import data.model.Receipt;
import data.model.Table;
import data.model.ReceiptDetail;
import data.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<Item>> getItemList(@Header("Authorization") String token);


    @GET("/requests")
    Call<List<Request>> getRequestList(@Header("Authorization") String token);

    @GET("/tables")
    Call<List<Table>> getTableList(@Header("Authorization") String token);

    @GET("/categories")
    Call<List<Category>> getCategoryList(@Header("Authorization") String token);

    @GET("/requests")
    Call<List<Request>> getRequestOrderList();


    @POST("/orders")
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendReceiptToServer(@Header("Authorization") String token, @Body OrderRequest orderRequest);

    @HTTP(method = "DELETE", path = "/requests", hasBody = true)
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendDismissRequest(@Header("Authorization") String token, @Body List<Request> dismissList);

    @GET("/receipts/{id}")
    Call<Receipt> getReceiptByReceiptId(@Header("Authorization") String token, @Path("id") Integer receiptSeq);

    @GET("/tables/{id}/receipts")
    Call<Receipt> getReceiptByTableId(@Header("Authorization") String token, @Path("id") int tableSeq);

    @GET("/ordered-request/{seq}")
    Call<List<ReceiptDetail>> getReceiptDetailsByReceiptSeq(@Header("Authorization") String token, @Path("seq") Integer receiptSeq);

    @POST("/login")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> getAuthenticate(@Body User user);

    @GET("/ordering-request/{seq}")
    Call<List<Request>> getRequestDetailsByReceiptSeq(@Header("Authorization") String token, @Path("seq") Integer receiptSeq);

    @PUT("/ordering-request")
    @Headers({"Content-Type: application/json"})
    Call<Boolean> sendRequestDetail(@Header("Authorization") String token, @Body Request requestDetail);

    @DELETE("/checkout/{receiptSeq}")
    Call<Boolean> checkOutReceipt(@Header("Authorization") String token, @Path("receiptSeq") Integer receiptSeq);
}
