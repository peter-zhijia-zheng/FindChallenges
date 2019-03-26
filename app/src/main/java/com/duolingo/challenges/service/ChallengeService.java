package com.duolingo.challenges.service;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChallengeService {
    @GET("find_challenges.txt")
    Call<JsonElement> getChallenges();
}
