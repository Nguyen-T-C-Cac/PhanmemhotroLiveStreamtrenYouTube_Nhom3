package hcmuaf.edu.vn.fit.Model;

public class VideoSource {
    private String resolution;
    private int frameRate;

    public VideoSource(String sourceId, String resolution, int frameRate) {

        this.resolution = resolution;
        this.frameRate = frameRate;
    }
}
