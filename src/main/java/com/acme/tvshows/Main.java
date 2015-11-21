package com.acme.tvshows;

import spark.SparkBase;

public class Main {

    private static void initializePort() {
        String port = new ProcessBuilder().environment().get("PORT");
        if (port != null) {
            SparkBase.setPort(Integer.parseInt(port));
        }
    }

    public static void main(String[] args) {
        initializePort();
        new com.acme.tvshows.api.v2.TvShowController(new com.acme.tvshows.api.v2.TvShowService());
	}
}
