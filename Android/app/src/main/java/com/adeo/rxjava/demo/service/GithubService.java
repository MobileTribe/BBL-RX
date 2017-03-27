package com.adeo.rxjava.demo.service;

import com.adeo.rxjava.demo.model.GithubRelease;
import com.adeo.rxjava.demo.model.GithubRepoSearch;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by paul-hubert on 02/01/2017.
 */

public interface GithubService {

    @GET("/search/repositories")
    Observable<GithubRepoSearch> search(@Query("q") String search, @Query("sort") String sortFilter);

    @GET
    Observable<List<GithubRelease>> getReleases(@Url String url);

    @GET
    Observable<GithubRelease> getRelease(@Url String url);
}
