package com.example.cashdesk.api;

import com.example.cashdesk.models.BaseResponse;
import com.example.cashdesk.models.Comments;
import com.example.cashdesk.models.LoginRequest;
import com.example.cashdesk.models.LoginResponse;
import com.example.cashdesk.models.Order;
import com.example.cashdesk.models.OrdersStatusResponse;
import com.example.cashdesk.models.ReesonResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


interface ApiInterface {



    @Headers("access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76")
    @POST("api/login")
    Observable<BaseResponse<LoginResponse>> userLogin(@Body LoginRequest request);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @GET("api/status-list")
    Observable<BaseResponse<List<OrdersStatusResponse>>> getOrdersStatus(@Header("auth-key") String token,
                                                                         @Query("query") String query);


    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @GET("/api/orders")
    Observable<BaseResponse<List<Order>>> getOrders(@Header("auth-key") String token,
                                                    @Query("offset") int offset,
                                                    @Query("limit") int limit,
                                                    @Query("status") String status,
                                                    @Query("query") String query);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @GET("/api/get-orders-pdf")
    Observable<BaseResponse<String>> getPDFURL(@Header("auth-key") String token,
                                               @Query("orders_ids[]") List<String> offset,
                                               @Query("sort") Integer sort);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/add-comment")
    Observable<BaseResponse<List<Comments>>> addComments(@Header("auth-key") String token,
                                                         @Query("order_id") String orderID,
                                                         @Query("message") String comment);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/set-image-not-match/{product_id}")
    Observable<BaseResponse<Boolean>> sendNotCorrectImage(@Header("auth-key") String token,
                                                          @Path("product_id") Integer id);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/set-status")
    Observable<BaseResponse<Order>> setOrderStatus(@Header("auth-key") String token,
                                                   @Query("order_id") String orderID,
                                                   @Query("status") String status,
                                                   @Query("reason_code") Integer reason,
                                                   @Query("reason_text") String text);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @GET("/api/get-cancel-reasons")
    Observable<BaseResponse<List<ReesonResponse>>> getReasonsList(@Header("auth-key") String token);


    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/out-of-stock")
    Observable<BaseResponse<Order>> setOutStock(@Header("auth-key") String token,
                                                @Query("order_id") Integer orderID,
                                                @Query("product_id") Integer productId,
                                                @Query("is_obtainable") Integer isObtainable);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/product-refused")
    Observable<BaseResponse<Order>> setIsRefused(@Header("auth-key") String token,
                                                 @Query("order_id") Integer orderID,
                                                 @Query("product_id") Integer productId,
                                                 @Query("is_refused") Integer isRefused);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/product-refused")
    Observable<BaseResponse<Order>> removeItem(@Header("auth-key") String token,
                                               @Query("order_id") Integer orderID,
                                               @Query("product_id") Integer productId);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @POST("/api/set-quantity")
    Observable<BaseResponse<Order>> changeQTY(@Header("auth-key") String token,
                                              @Query("order_id") Integer orderID,
                                              @Query("product_id") Integer productId,
                                              @Query("quantity") Integer qty);

    @Headers({"access-key: jkHUHjyBfvThbNHGbfgb6546ubVHJnhGHhb76"})
    @GET("/api/order/{id}")
    Observable<BaseResponse<Order>> getOrder(@Header("auth-key") String token,
                                             @Path("id") Integer orderID);


}
