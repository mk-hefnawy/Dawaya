package com.example.dawaya.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dawaya.R;
import com.example.dawaya.models.SignUpModel;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    //for Networking
    static RequestQueue instance = null;

    // Json Converting
    public static String objToJson(Object object) {
        return new Gson().toJson(object);
    }

    public static String convertStandardJSONString(String data_json) {
        data_json = data_json.replaceAll("\\\\r\\\\n", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        data_json = data_json.replace("\\", "");
        return data_json;
    }
    // Singleton RequestQueue
    public static RequestQueue getRequestQueue(Context context){
            if(instance == null){
                instance = Volley.newRequestQueue(context);
            }
            return instance;
        }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; //Notice the +1
        int year = datePicker.getYear();
        Log.v("day of birth", String.valueOf(day));
        Log.v("month of birth", String.valueOf(month));
        Log.v("year of birth", String.valueOf(year));

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        //Log.v("calender month", calendar.)
        return calendar.getTime();
    }
    public static String convertDateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String strDate = dateFormat.format(date);
        return strDate;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateToString (LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
        return dateTime.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime stringToDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  LocalDateTime.parse(dateTime, formatter);
    }

    public static void createUserSession(SignUpModel user) {
        String userId = user.getUserId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        String phoneNumber = user.getPhoneNumber();
        String gender = user.getGender();
        String dateOfBirth = user.getDateOfBirth();

        SharedPrefs.write(SharedPrefs.USER_ID, userId);
        SharedPrefs.write(SharedPrefs.FIRST_NAME, firstName);
        SharedPrefs.write(SharedPrefs.LAST_NAME, lastName);
        SharedPrefs.write(SharedPrefs.EMAIL, email);
        SharedPrefs.write(SharedPrefs.PASSWORD, password);
        SharedPrefs.write(SharedPrefs.PHONE_NUMBER, phoneNumber);
        SharedPrefs.write(SharedPrefs.GENDER, gender);
        SharedPrefs.write(SharedPrefs.DATE_OF_BIRTH, dateOfBirth);

    }

    public static String convertImageToString(Bitmap bitmap){

        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
        byte[] imageBytes = bAOS.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;

    }

    public static Dialog getBasicDialog(Context context, int layout){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);

        return dialog;
    }


    }

