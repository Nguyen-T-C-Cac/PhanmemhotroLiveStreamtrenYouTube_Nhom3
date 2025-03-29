package hcmuaf.edu.vn.fit.Model;

public class StreamServer {
    private String serverUrl;
    private String streamKey;

    public StreamServer(String serverUrl, String streamKey) {
        this.serverUrl = serverUrl;
        this.streamKey = streamKey;
    }

    public boolean connect() {
        System.out.println("Connecting to server: " + serverUrl);
        return false;
    }

    public void disconnect() {
        System.out.println("Disconnecting from server.");
    }

    public void sendStreamData() {
        System.out.println("Sending stream data...");
    }
}
