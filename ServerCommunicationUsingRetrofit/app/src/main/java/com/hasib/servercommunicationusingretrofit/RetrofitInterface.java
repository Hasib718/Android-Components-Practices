package com.hasib.servercommunicationusingretrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

}
