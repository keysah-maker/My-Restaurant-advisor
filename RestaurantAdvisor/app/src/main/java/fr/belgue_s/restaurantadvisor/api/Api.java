package fr.belgue_s.restaurantadvisor.api;

import java.util.List;

import fr.belgue_s.restaurantadvisor.models.Menu;
import fr.belgue_s.restaurantadvisor.models.Restaurant;
import fr.belgue_s.restaurantadvisor.models.Review;
import fr.belgue_s.restaurantadvisor.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    /*
     *
     * User Part
     *
     * */
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerUser(
            @Field("login") String login,
            @Field("password") String password,
            @Field("email") String email,
            @Field("name") String name,
            @Field("firstname") String firstname,
            @Field("age") String age);

    @FormUrlEncoded
    @POST("auth")
    Call<User> authUser(
            @Field("login") String login,
            @Field("password") String password);

    /*
     *
     * Restaurant Part
     *
     * */
    @FormUrlEncoded
    @PUT("restaurant/{id}/")
    Call<String> updateRestaurant(
            @Path("id") int id,
            @Field("name") String name,
            @Field("description") String description,
            @Field("grade") float grade,
            @Field("localization") String localization,
            @Field("phone_number") String phone_number,
            @Field("website") String website,
            @Field("hours") String hours);

    @GET("restaurants")
    Call<List<Restaurant>> getRestaurants();

    @DELETE("restaurant/{id}")
    Call<String> deleteRestaurant(
            @Path("id") int id);

    /*
     *
     * Menu Part
     *
     * */
    @FormUrlEncoded
    @PUT("restaurant/{id}/menu/{menu_id}")
    Call<String> updateMenu(
            @Path("id") int id,
            @Path("menu_id") int menu_id,
            @Field("name") String name,
            @Field("description") String description,
            @Field("price") float price);

    @GET("restaurant/{id}/menus")
    Call<List<Menu>> getMenus(@Path("id") int id);

    @DELETE("restaurant/{id}/menu/{menu_id}")
    Call<String> deleteMenu(
            @Path("id") int id,
            @Path("menu_id") int menu_id);

    /*
     *
     * Review Part
     *
     * */
    @GET("restaurant/{id}/reviews")
    Call<List<Review>> getReviews(
            @Path("id") int id);

    @FormUrlEncoded
    @POST("restaurant/{id}/review")
    Call<String> addReview(
            @Path("id") int id,
            @Field("user_id") int user_id,
            @Field("user_login") String user_login,
            @Field("restaurant_id") int restaurant_id,
            @Field("rate") float rate,
            @Field("review") String review);

}
