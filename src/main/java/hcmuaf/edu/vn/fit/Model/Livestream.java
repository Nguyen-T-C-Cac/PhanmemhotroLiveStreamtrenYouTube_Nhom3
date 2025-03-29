package hcmuaf.edu.vn.fit.Model;

public class Livestream {
    private String streamId;
    private String title;
    private String status;
    private int duration;
    private APIHandler apiHandler;

    public Livestream(String streamId, String title) {
        this.streamId = streamId;
        this.title = title;
        this.status = "Offline";
    }

    public boolean startStream() {
        if (apiHandler.startStream(streamId)) {
            status = "Streaming";
            System.out.println("Livestream started: " + title);
        } else {
            System.out.println("Failed to start livestream!");
        }
        return false;
    }

    public void stopStream() {
        if (apiHandler.stopStream(streamId)) {
            status = "Stopped";
            System.out.println("Livestream stopped.");
        } else {
            System.out.println("Failed to stop livestream!");
        }
    }

    public void endStream() {
        this.status = "Ended";
        System.out.println("Stream ended: " + title);
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}




