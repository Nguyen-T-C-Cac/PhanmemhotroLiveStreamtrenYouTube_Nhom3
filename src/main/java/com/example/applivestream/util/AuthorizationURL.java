package com.example.applivestream.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AuthorizationURL {

    //-- Tạo tài khoản client ở: https://console.cloud.google.com/auth
    private final String clientId = "...";//Thay clientId khác
    private final String redirectUri = "urn:ietf:wg:oauth:2.0:oob";
    private final String scope = "https://www.googleapis.com/auth/youtube.readonly";

    public void printAuthorizationUrl() {
        try {
            String encodedScope = URLEncoder.encode(scope, "UTF-8");

            String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                    + "?client_id=" + clientId
                    + "&redirect_uri=" + redirectUri
                    + "&response_type=code"
                    + "&scope=" + encodedScope
                    + "&access_type=offline";

            System.out.println("Mở đường dẫn sau để cấp quyền cho ứng dụng:");
            System.out.println(authUrl);

        } catch (UnsupportedEncodingException e) {
            System.err.println("Lỗi mã hóa URL: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AuthorizationURL auth = new AuthorizationURL();
        auth.printAuthorizationUrl();
    }
}
