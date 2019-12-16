package com.kapal.dokumenkapal.util.api;

public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "https://kirana-web.000webhostapp.com/restapi/";

    // Mendeklarasikan Interface BaseApiService
    //@org.jetbrains.annotations.NotNull
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
