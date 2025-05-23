package com.example.applivestream.model;

public class StreamInfo {

    private static StreamInfo instance;

    private String platform; // "YouTube", "Facebook", "Twitch"
    private String streamKey;
    private String streamTitle;
    private String streamDescription;
    private String streamUrl;

    private StreamInfo() {}

    public static StreamInfo getInstance() {
        if (instance == null) {
            instance = new StreamInfo();
        }
        return instance;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getStreamKey() {
        return streamKey;
    }
    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }
    public String getStreamTitle() {
        return streamTitle;
    }
    public void setStreamTitle(String streamTitle) {
        this.streamTitle = streamTitle;
    }
    public String getStreamDescription() {
        return streamDescription;
    }
    public void setStreamDescription(String streamDescription) {
        this.streamDescription = streamDescription;
    }
    public String getStreamUrl() {
        return streamUrl;
    }
    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

}

