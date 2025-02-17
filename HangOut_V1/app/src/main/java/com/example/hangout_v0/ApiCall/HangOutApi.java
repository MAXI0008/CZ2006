package com.example.hangout_v0.ApiCall;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HangOutApi {
    public static final String baseUrl = "http://10.27.254.210:9090/api/";
    public static Long userId = 1l;
    public static String accessToken = "4lTf+NWRlPqs0KeNyI8ZmA==";
    public static String vendorAT = "UPXDxfiMf2SNu/D/GQBkAg==";

    private static final String key = "Bar12345Bar12345";
    public static final MediaType JSON = MediaType.parse("application/json");

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    public static String getUserId(String accessToken) {

        String decrypted = "";
        String[] info = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(accessToken.getBytes("UTF8"));
            decrypted = new String(cipher.doFinal(cipherText), "UTF-8");
            info = decrypted.split("\\|");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info[0];
    }

    public static void getCustomer(Long customerId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject customer = new JSONObject(myResponse);
                        // Deal with this object, below is an example
                        //textView.setText(customer.toString());
                        HangOutData.setCustomer(customer);

                    } catch (JSONException e) {
                        //textView.setText("Error");
                    }
                }
            }
        });
    }

    public static void getVendor(Long vendorId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "vendor";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("vendorId", vendorId.toString());
        Request request = new Request.Builder().url(httpBuilder.build()).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject vendor = new JSONObject(myResponse);
                        HangOutData.setVendor(vendor);

                    } catch (JSONException e) {
                        //textView.setText("Error");
                    }
                }
            }
        });
    }

    public static void getAllShops() {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "shops";
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONArray object = new JSONArray(myResponse);
                        HangOutData.setShopList(object);
                    } catch (JSONException e) {
                        //textView.setText("Error");
                    }
                }
            }
        });
    }

    public static void getShopByShopId(final Long shopId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "shop";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("shopId", shopId.toString());

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    try {
                        JSONObject object = new JSONObject(myResponse);
                        HangOutData.setShop(object);
                        //textView.setText("Shop with id = " + shopId + " is " + object);

                    } catch (JSONException e) {
                        //textView.setText("Error");
                    }
                }
            }
        });
    }

    public static void getCustomerPlan(Long customerId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    try {
                        JSONObject customer = new JSONObject(myResponse);
                        JSONArray plans = (JSONArray) customer.get("plans");
                        HangOutData.setPlanList(plans);

                        // Deal with this object, below is an example
                        //textView.setText(plans.toString());

                    } catch (JSONException e) {
                        //textView.setText("Error");
                    }
                }
            }
        });
    }

    public static void signInCustomer(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer/signin";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("email", email);
        httpBuilder.addQueryParameter("password", password);
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    HangOutData.setAccessToken(myResponse);
                }
            }
        });
    }

    public static void signInVendor(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "vendor/signin";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("email", email);
        httpBuilder.addQueryParameter("password", password);
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    HangOutData.setAccessToken(myResponse);
                }
            }
        });
    }

    public static void signUpCustomer(String newCustomer) {
        RequestBody body = RequestBody.create(JSON, newCustomer);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    HangOutData.setAccessToken(myResponse);
                }
            }
        });
    }

    public static void signUpVendor(String newVendor) {
        RequestBody body = RequestBody.create(JSON, newVendor);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "vendor";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    HangOutData.setAccessToken(myResponse);
                }
            }
        });
    }

    public static void updateCustomerInfo(Long customerId, String updatedCustomer) {
        RequestBody body = RequestBody.create(JSON, updatedCustomer);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .put(body)
                .addHeader("Authorization", HangOutData.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                }
            }
        });
    }

    public static void updateVendorInfo(Long vendorId, String updatedVendor) {
        RequestBody body = RequestBody.create(JSON, updatedVendor);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "vendor";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("vendorId", vendorId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .put(body)
                .addHeader("Authorization", accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                }
            }
        });
    }

    public static void vendorAddShop(Long vendorId, String newShop) {
        RequestBody body = RequestBody.create(JSON, newShop);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "vendor/shop";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("vendorId", vendorId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(body)
                .addHeader("Authorization", HangOutData.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject shop = new JSONObject(myResponse);
                        JSONArray shopList = HangOutData.getShopList();
                        shopList.put(shop);
                        HangOutData.setShopList(shopList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void customerMakeReservation(Long customerId, Long shopId, String newReservation) {
        RequestBody body = RequestBody.create(JSON, newReservation);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "reservation";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        httpBuilder.addQueryParameter("shopId", shopId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(body)
                .addHeader("Authorization", HangOutData.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject myObject = new JSONObject(myResponse);
                        JSONArray reservationList = HangOutData.getReservationList();
                        reservationList.put(myObject);
                        HangOutData.setReservationList(reservationList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void vendorGetAllReservations(Long vendorId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "reservation-vendor";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("vendorId", vendorId.toString());
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONArray vendorReservations = new JSONArray(myResponse);
                        HangOutData.setReservationList(vendorReservations);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void customerGetAllReservation(Long customerId) {
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "reservation-customer";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONArray customerReservations = new JSONArray(myResponse);
                        HangOutData.setReservationList(customerReservations);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void customerCreatePlan(Long customerId, String newPlan) {
        RequestBody body = RequestBody.create(JSON, newPlan);
        OkHttpClient client = new OkHttpClient();
        String url = baseUrl + "customer/plan";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", customerId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(body)
                .addHeader("Authorization", HangOutData.getAccessToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject plan = new JSONObject(myResponse);
                        JSONArray planList = HangOutData.getPlanList();
                        planList.put(plan);
                        HangOutData.setPlanList(planList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
