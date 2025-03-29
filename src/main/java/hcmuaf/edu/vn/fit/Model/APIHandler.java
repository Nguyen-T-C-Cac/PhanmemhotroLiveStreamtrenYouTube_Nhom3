package hcmuaf.edu.vn.fit.Model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {
    private String serverUrl;

    public APIHandler(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    // Gửi yêu cầu HTTP POST để bắt đầu stream
    public boolean startStream(String streamKey) {
        String url = serverUrl + "/startStream";
        String data = "{ \"streamKey\": \"" + streamKey + "\" }";
        return sendPostRequest(url, data);
    }

    // Gửi yêu cầu HTTP POST để dừng stream
    public boolean stopStream(String streamKey) {
        String url = serverUrl + "/stopStream";
        String data = "{ \"streamKey\": \"" + streamKey + "\" }";
        return sendPostRequest(url, data);
    }

    // Cập nhật trạng thái stream
    public boolean updateStreamStatus(String streamKey, String status) {
        String url = serverUrl + "/updateStatus";
        String data = "{ \"streamKey\": \"" + streamKey + "\", \"status\": \"" + status + "\" }";
        return sendPostRequest(url, data);
    }

    // Hàm chung để gửi yêu cầu HTTP POST
    private boolean sendPostRequest(String urlString, String jsonPayload) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            return responseCode == 200; // Thành công nếu mã phản hồi là 200

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
