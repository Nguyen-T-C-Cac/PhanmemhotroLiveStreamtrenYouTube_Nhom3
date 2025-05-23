package com.example.applivestream.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StreamingController {
    public void startMultiPlatformStream(Set<String> platforms) {
        System.out.println("Đang phát livestream trên: " + platforms);

        String inputSource = "input.mp4";

        for (String platform : platforms) {
            String streamUrl = getStreamUrl(platform);
            if (streamUrl != null) {
                List<String> command = Arrays.asList(
                        "ffmpeg",
                        "-re",
                        "-i", inputSource,
                        "-c:v", "copy",
                        "-c:a", "aac",
                        "-f", "flv",
                        streamUrl
                );

                try {
                    new ProcessBuilder(command)
                            .inheritIO()
                            .start();
                    System.out.println("Đang phát tới " + platform);
                } catch (IOException e) {
                    System.err.println("Lỗi khi phát tới " + platform + ": " + e.getMessage());
                }
            }
        }
    }

    private String getStreamUrl(String platform) {
        String key = SettingsController.streamKeys.get(platform);
        if (key == null) return null;

        switch (platform) {
            case "YouTube":
                return "rtmp://a.rtmp.youtube.com/live2/" + key;//
            case "Facebook":
                return "rtmps://live-api-s.facebook.com:443/rtmp/" + key;
            case "Twitch":
                return "rtmp://live.twitch.tv/app/" + key;
            default:
                return null;
        }
    }

}
