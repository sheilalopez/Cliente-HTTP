package com.example.clientehttp;

public class Track {
    String id;
    String singer;
    String title;

    public Track(String singer, String title) {
        this.singer = singer;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getSinger() {
        return singer;
    }

    public String getTitle() {
        return title;
    }
}
