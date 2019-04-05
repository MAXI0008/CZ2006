package com.example.hangout_v0.ShopDetail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hangout_v0.ApiCall.HangOutApi;
import com.example.hangout_v0.ApiCall.HangOutData;
import com.example.hangout_v0.R;
import com.example.hangout_v0.Vendor.ReservationPage;
import com.example.hangout_v0.Vendor.Shop;
import com.example.hangout_v0.Vendor.VendorShop;
//import com.example.hangout_v0.Recommendation.RecShop;


import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.JarException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.hangout_v0.ApiCall.HangOutApi.JSON;
import static com.example.hangout_v0.ApiCall.HangOutData.accessToken;
import static com.example.hangout_v0.ApiCall.HangOutData.shop;

public class ShopInDetail extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    AppCompatButton addPlanButton;
    AppCompatButton reserveButton;
    //  public AppCompatButton reserveButton;
    public static String shopDateString;
    public static String shopTimeString;
    public static String shopDateTimeString = "2019-04-05 13:00:00";
    int hour, minute, hour_x, minute_x;
    JSONObject jsonRecShop;
    TextView shopNameTv, shopLocationTv, phoneNoTv, emailTv, carparkTv;
    RatingBar ratingBar;
    ImageView img1, img2, img3;
    Button addPlan;
    public Long shopId;
    public Long userId;
    String shopName;
    String shopLocation;
    String shopPhone;
    String shopEmail;
    float shopRating;
    String shopCarpark;
    Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_in_detail_user);

        Intent intent = getIntent();

        userId = HangOutApi.userId;
        shopId = Long.parseLong(intent.getStringExtra("chosenShopId"));
        shopName = intent.getStringExtra("chosenShopName");
        shopLocation = intent.getStringExtra("chosenShopLocation");
        shopPhone = intent.getStringExtra("chosenShopPhone");
        shopEmail = intent.getStringExtra("chosenShopEmail");
        shopRating = Float.parseFloat(intent.getStringExtra("chosenShopRating"));
        shopCarpark = intent.getStringExtra("chosenShopCarpark");

        this.getSupportActionBar().hide();

        // find views
        shopNameTv = findViewById(R.id.shopNameTextView);
        shopLocationTv = findViewById(R.id.shopLocationTextView);
        phoneNoTv = findViewById(R.id.shopPhoneNumberTextView);
        emailTv = findViewById(R.id.shopWebTextView);
        ratingBar = findViewById(R.id.shopRatingBar);
        img1 = findViewById(R.id.shopPhoto1);
        img2 = findViewById(R.id.shopPhoto2);
        img3 = findViewById(R.id.shopPhoto3);
        carparkTv = findViewById(R.id.shopCarParkCapacityTextView);

        if (shopId == 2l) {
            img1.setImageDrawable(getResources().getDrawable(R.drawable.huoguo));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.huoguo2));
            img3.setImageDrawable(getResources().getDrawable(R.drawable.huoguo3));
        } else {
            img1.setImageDrawable(getResources().getDrawable(R.drawable.macd1));
            img2.setImageDrawable(getResources().getDrawable(R.drawable.macd2));
            img3.setImageDrawable(getResources().getDrawable(R.drawable.macd3));
        }


        shopNameTv.setText(shopName);
        shopLocationTv.setText(shopLocation);
        phoneNoTv.setText(shopPhone);
        emailTv.setText(shopEmail);
        ratingBar.setRating(shopRating);
        carparkTv.setText("before");

        OkHttpClient client = new OkHttpClient();
        String url = HangOutApi.baseUrl + "carpark";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("carParkNumber", shopCarpark);

        Request request = new Request.Builder().url(httpBuilder.build()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    ShopInDetail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String res = myResponse;
                            carparkTv.setText(shopCarpark + " : " + myResponse);
                        }
                    });

                }
            }
        });

        // add plan button
        addPlan = findViewById(R.id.addPlanButton);
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass shop id, customer id to next intent: AddPlan page
                //....
                Intent addPlanPage = new Intent(ShopInDetail.this, AddPlan.class);
                Bundle extras = new Bundle();
                //extras.putLong("vendorId", customerId);
                // can use customer id in HangOutApi.userId
//                extras.putLong("shopId", shopId);
//                addPlanPage.putExtras(extras);
                startActivity(addPlanPage);
            }
        });


        reserve = findViewById(R.id.reserveButton);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirmReservation = new Intent(ShopInDetail.this, ReservationConfirmPage.class);
                confirmReservation.putExtra("shopId", shopId.toString());
                startActivity(confirmReservation);
            }
        });

        final FloatingActionButton addPlanFloatingActionButton = (FloatingActionButton) findViewById(R.id.addPlanFloatingActionButton);
        addPlanFloatingActionButton.setImageResource(R.drawable.ic_add_plan_not_sel);
        final FloatingActionButton addCollectionFloatingActionButton = (FloatingActionButton) findViewById(R.id.addCollectionFloatingActionButton);
        addCollectionFloatingActionButton.setImageResource(R.drawable.ic_add_collection_not_sel);

    }

    public void addToPlan(View view) {
        setDateTime();
        //add to plan backend;


        // which plan to add to? Add to an existing plan? or a new plan?
        // Should create a new page


        Long planId = 1l;


        JSONObject newPlanItem = new JSONObject();
        try {
            newPlanItem.put("scheduledVisitTime", "2019-04-05 10:00:00");
            newPlanItem.put("shopId", shopId.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(JSON, newPlanItem.toString());

        OkHttpClient client = new OkHttpClient();
        String url = HangOutApi.baseUrl + "customer/plan/addPlanItem";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("planId", planId.toString());
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("Authorization", HangOutApi.accessToken)
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
                    System.out.println(myResponse);
                    // get full plan
                } else {
                    System.out.println("");
                }
            }
        });


    }

    public void reserve(View view) {

        setDateTime();
        //add reservation backend;

        JSONObject newReservation = new JSONObject();
        try {
            newReservation.put("shopId", shopId.toString());
            newReservation.put("arrivalTime", shopDateTimeString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, newReservation.toString());
        OkHttpClient client = new OkHttpClient();
        String url = HangOutApi.baseUrl + "reservation";
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("customerId", userId.toString());
        httpBuilder.addQueryParameter("shopId", shopId.toString());
        System.out.println("userId" + userId + "-- shopId" + shopId);
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .post(body)
                .addHeader("Authorization", HangOutApi.accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("fail add");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("success add");
                    String myResponse = response.body().string();
                } else {
                    System.out.println("failed add 2");
                }
            }
        });


    }


    public void setDateTime() {
        DialogFragment datePicker = new com.example.hangout_v0.ShopDetail.DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        shopDateString = dateFormat.format(c.getTime());

        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ShopInDetail.this, ShopInDetail.this,
                hour, minute, true);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_x = hourOfDay;
        minute_x = minute;

        String hours = String.format("%02d", hour_x);
        String minutes = String.format("%02d", minute_x);

        shopTimeString = hours + ":" + minutes + ":00";
        shopDateTimeString = shopDateString + " " + shopTimeString;
        Toast.makeText(ShopInDetail.this, "Add shop successully " + shopDateTimeString, Toast.LENGTH_SHORT).show();
    }
}