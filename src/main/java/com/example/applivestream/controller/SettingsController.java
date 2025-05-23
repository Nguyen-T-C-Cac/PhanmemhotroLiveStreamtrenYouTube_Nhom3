package com.example.applivestream.controller;

import com.example.applivestream.util.AuthorizationURL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SettingsController {
    @FXML private CheckBox youtubeCheckbox;
    @FXML private CheckBox facebookCheckbox;
    @FXML
    private CheckBox twitchCheckbox;

    @FXML private VBox youtubeConfig;
    @FXML private VBox facebookConfig;
    @FXML private VBox twitchConfig;

    @FXML private TextField youtubeApiKeyField;
    @FXML private TextField facebookTokenField;
    @FXML private TextField twitchKeyField;

    // Các phần giao diện của từng tab
    @FXML private VBox streamPane;
    @FXML private VBox audioPane;
    @FXML private VBox videoPane;

    @FXML private Label youtubeAccountLabel;
    @FXML private Label facebookAccountLabel;
    @FXML private Label twitchAccountLabel;

    @FXML private Label youtubeStatusLabel;
    @FXML private Label facebookStatusLabel;
    @FXML private Label twitchStatusLabel;

    public static Map<String, String> streamKeys = new HashMap<>();
    public static Map<String, String> accountNames = new HashMap<>();

    private static Set<String> selectedPlatforms = new HashSet<>();

    public static Set<String> getSelectedPlatforms() {
        return selectedPlatforms;
   }

    @FXML
    public void initialize() {
        youtubeCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            youtubeConfig.setVisible(newVal);
            youtubeConfig.setManaged(newVal);
        });

        facebookCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            facebookConfig.setVisible(newVal);
            facebookConfig.setManaged(newVal);
        });

        twitchCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            twitchConfig.setVisible(newVal);
            twitchConfig.setManaged(newVal);
        });

        // Mặc định: chỉ hiện streamPane
        showPane("stream");
    }
    // Chuyển đổi hiển thị giữa các tab
    private void showPane(String paneName) {
        streamPane.setVisible("stream".equals(paneName));
        streamPane.setManaged("stream".equals(paneName));

        audioPane.setVisible("audio".equals(paneName));
        audioPane.setManaged("audio".equals(paneName));

        videoPane.setVisible("video".equals(paneName));
        videoPane.setManaged("video".equals(paneName));
    }

    @FXML private void showStreamSettings() {
        showPane("stream");
    }

    @FXML private void showAudioSettings() {
        showPane("audio");
    }

    @FXML private void showVideoSettings() {
        showPane("video");
    }

    @FXML
    private void handleApplyYoutube() {
        String key = youtubeApiKeyField.getText().trim();
        if (key.isEmpty()) {
            youtubeStatusLabel.setText("Vui lòng nhập API Key.");
            youtubeAccountLabel.setVisible(false);
            youtubeAccountLabel.setManaged(false);
            return;
        }

        // Lưu tạm key vào map (nếu cần dùng lại)
        streamKeys.put("YouTube", key);

        // Mở trang OAuth Google
        try {
            String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                    + "?client_id=...googleusercontent.com" //thay client thật
                    + "&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
                    + "&response_type=code"
                    + "&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fyoutube.readonly"
                    + "&access_type=offline";

            java.awt.Desktop.getDesktop().browse(new java.net.URI(authUrl));
        } catch (Exception e) {
            e.printStackTrace();
            youtubeStatusLabel.setText("Lỗi khi mở trình duyệt xác thực.");
            return;
        }

        // Gọi lớp AuthCode với các tham số phù hợp
        AuthCode.show(
                "YouTube",
                this::validateYoutubeKey,  // <-- method trả về tên kênh từ access_token
                youtubeStatusLabel,
                youtubeAccountLabel,
                streamKeys,
                accountNames
        );
    }

    public String validateYoutubeKey(String accessToken) {
        try {
            String url = "https://www.googleapis.com/youtube/v3/channels?part=snippet&mine=true";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            InputStream responseStream = (responseCode >= 200 && responseCode < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String response = new BufferedReader(new InputStreamReader(responseStream))
                    .lines().collect(Collectors.joining("\n"));

            if (responseCode == 200) {
                JSONObject json = new JSONObject(response);
                JSONArray items = json.getJSONArray("items");
                if (items.length() > 0) {
                    JSONObject snippet = items.getJSONObject(0).getJSONObject("snippet");
                    String channelTitle = snippet.getString("title");
                    System.out.println("Xác thực thành công. Tên kênh: " + channelTitle);
                    return channelTitle;
                } else {
                    System.out.println("Không tìm thấy kênh YouTube.");
                }
            } else {
                System.out.println("Lỗi xác thực API YouTube (HTTP " + responseCode + "):");
                System.out.println(response);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi YouTube API: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @FXML
    private void handleApplyFacebook() {
        String token = facebookTokenField.getText().trim();
        if (token.isEmpty()) {
            facebookStatusLabel.setText("Vui lòng nhập Token.");
            facebookAccountLabel.setVisible(false);
            facebookAccountLabel.setManaged(false);
            return;
        }

        if (validateFacebookToken(token)) {
            streamKeys.put("Facebook", token);
            facebookStatusLabel.setText("Kết nối thành công");
            facebookAccountLabel.setText("Tài khoản");
            facebookAccountLabel.setVisible(true);
            facebookAccountLabel.setManaged(true);
        } else {
            facebookStatusLabel.setText("Token không hợp lệ.");
            facebookAccountLabel.setVisible(false);
            facebookAccountLabel.setManaged(false);
        }
    }
//Xác minh token facebook
    private boolean validateFacebookToken(String token) {
        try {
            String url = "https://graph.facebook.com/me?access_token=" + token;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            InputStream responseStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

            String response = new BufferedReader(new InputStreamReader(responseStream))
                    .lines().collect(Collectors.joining("\n"));

            if (responseCode == 200) {
                JSONObject json = new JSONObject(response);
                if (json.has("id")) {
                    // Token hợp lệ
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @FXML
    private void handleApplyTwitch() {
        String key = twitchKeyField.getText().trim();
        if (key.isEmpty()) {
            twitchStatusLabel.setText("Vui lòng nhập Stream Key.");
            twitchAccountLabel.setVisible(false);
            twitchAccountLabel.setManaged(false);
            return;
        }
        if (validateTwitchToken(key)) {
            twitchStatusLabel.setText("Hợp lệ");
            twitchAccountLabel.setText("Tài khoản:");
            twitchAccountLabel.setVisible(true);
            twitchAccountLabel.setManaged(true);
        } else {
            twitchStatusLabel.setText("Stream Key không hợp lệ.");
            twitchAccountLabel.setVisible(false);
            twitchAccountLabel.setManaged(false);
        }
    }

    //Xác minh Twitch
    private boolean validateTwitchToken(String twitchToken) {
        try {
            String url = "https://api.twitch.tv/helix/users";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + twitchToken);
            conn.setRequestProperty("Client-Id", "YOUR_TWITCH_CLIENT_ID");

            int responseCode = conn.getResponseCode();
            return responseCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
