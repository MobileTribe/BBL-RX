package com.adeo.rxjava.demo.model;

/**
 * Created by paul-hubert on 02/01/2017.
 */

public class GithubRepo {
    private int id;
    private String full_name;
    private String releases_url;

    public int getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getReleases_url() {
        return releases_url.replace("{/id}", "");
    }
}
