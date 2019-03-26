package com.duolingo.challenges;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.duolingo.challenges.data.Challenge;
import com.duolingo.challenges.service.ChallengeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Challenge> mChallenges;
    private int mCurrentChallenge;

    private Retrofit mRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))//解析方法
            .baseUrl("https://s3.amazonaws.com/duolingo-data/s3/js2/")
            .client(new OkHttpClient.Builder().build())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ChallengeService service = mRetrofit.create(ChallengeService.class);
        Call<JsonElement> call = service.getChallenges();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement body = response.body();
                if (body != null) {
                    mChallenges = new Gson().fromJson(body.isJsonArray() ? body.toString() : ("[" + body.toString() + "]"),
                            new TypeToken<List<Challenge>>() {
                            }.getType());
                    showChallenge();
                }
                Log.d("zheng", "response:" + new Gson().toJson(mChallenges));
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
                Log.d("zheng", "exception:" + t.getMessage());
            }
        });
    }

    private void showChallenge() {
        Challenge challenge = mChallenges.get(mCurrentChallenge);
        tvWord.setText(challenge.word);

    }
}
