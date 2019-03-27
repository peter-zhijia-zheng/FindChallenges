package com.duolingo.challenges;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.service.ChallengeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvWord;
    private RecyclerView rvList;

    private List<Translation> mChallenges = new ArrayList<>();
    private int mCurrentChallenge;
    private static final String BREAK_LINE = "\n";

    private Retrofit mRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://s3.amazonaws.com/")
            .client(new OkHttpClient.Builder().build())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
//        tvWord = findViewById(R.id.tv_word);
//        rvList = findViewById(R.id.rv_list);

        ChallengeService service = mRetrofit.create(ChallengeService.class);
        service.getChallenges()
        .flatMapCompletable(it ->
            Completable.fromAction(() -> {
                Log.d("zheng", "it: " + new Gson().toJson(it));
                String responseBodyString = it.string();
                String[] lines = responseBodyString.split(BREAK_LINE);
                for (String line : lines) {
                    mChallenges.add(new Gson().fromJson(line, Translation.class));
                }
                Log.d("zheng", "challenges: " + new Gson().toJson(mChallenges));
            })
        )
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe();
    }
}
