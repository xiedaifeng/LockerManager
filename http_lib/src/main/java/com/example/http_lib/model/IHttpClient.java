package com.example.http_lib.model;

import java.util.Map;

public interface IHttpClient {

    void setCookie(Map<String,String> cookie);

    Map<String,String> getCookie();
}
