package com.example.applivestream.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

public class TokenExchangeUtil {

    public static String exchangeCodeForAccessToken(String code) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        String clientId = "..."; //Thay clientId khác
        String clientSecret = "...";  //Thay clientSecret khác -- Tạo tài khoản client ở: https://console.cloud.google.com/auth
        String redirectUri = "urn:ietf:wg:oauth:2.0:oob";

        try {
            URL url = new URL(tokenEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String postData = "code=" + URLEncoder.encode(code, "UTF-8")
                    + "&client_id=" + URLEncoder.encode(clientId, "UTF-8")
                    + "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                    + "&grant_type=authorization_code";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes());
            }

            InputStream responseStream = conn.getInputStream();
            String json = new BufferedReader(new InputStreamReader(responseStream))
                    .lines().collect(Collectors.joining("\n"));

            JSONObject obj = new JSONObject(json);
            return obj.getString("access_token");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
