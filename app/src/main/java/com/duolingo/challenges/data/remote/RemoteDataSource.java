package com.duolingo.challenges.data.remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface RemoteDataSource {

    @Streaming
    @GET("duolingo-data/s3/js2/find_challenges.txt")
    Observable<ResponseBody> getTranslations();
}
