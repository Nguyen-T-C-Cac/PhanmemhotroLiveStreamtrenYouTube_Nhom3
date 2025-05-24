package com.example.applivestream.model;

import java.time.LocalDateTime;

public class Video {
    private String id;
    private String title;
    private String filePath;
    private LocalDateTime recordedAt;

    public Video(String id, String title, String filePath, LocalDateTime recordedAt) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.recordedAt = recordedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}