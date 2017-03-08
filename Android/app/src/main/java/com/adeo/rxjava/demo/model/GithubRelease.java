package com.adeo.rxjava.demo.model;

import java.util.Date;

/**
 * Created by paul-hubert on 02/01/2017.
 */

public class GithubRelease {
    private int id;
    private String url;
    private String tag_name;
    private Date published_at;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTag_name() {
        return tag_name;
    }

    public Date getPublished_at() {
        return published_at;
    }
}
