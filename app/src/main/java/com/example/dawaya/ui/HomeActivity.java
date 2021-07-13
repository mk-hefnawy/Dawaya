package com.example.dawaya.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.example.dawaya.R;
import com.example.dawaya.adapters.MainCategoriesAdapter;
import com.example.dawaya.databinding.ActivityHomeBinding;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.models.CategoryModel;
import com.example.dawaya.utils.CategoriesData;
import com.example.dawaya.utils.SharedPrefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import io.kommunicate.BuildConfig;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLoginHandler;

public class HomeActivity extends AppCompatActivity implements HomeItemClickInterface {

    ActivityHomeBinding homeBinding;
    public Toolbar toolBar;
    private NavigationView navigationView;
    private DrawerLayout mainMenuDrawerLayout;

    SearchFrag searchFragment = new SearchFrag();
    ImageView searchButton;
    EditText searchBar;
    FragmentTransaction fragmentTransaction;

    GridView categoriesGridView;
    MainCategoriesAdapter mainCategoriesAdapter;
    ArrayList<CategoryModel> categories = new ArrayList<>();

    ImageView mainMenuIcon, cartIcon;

    View header;
    TextView userFirstName;
    CategoriesData categoriesData;
    ArrayList<String> categoriesNames;

    FloatingActionButton chooseImage;
    final int CODE_GALLERY_REQUEST = 999;
    ImageView prescriptionPlaceholder;
    Bitmap bitmap;

    Button testNotification;
    public static final String APP_ID = "3d8a8b7f07fa6b94e02fbefcb69918908";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout Binding
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeBinding.setLifecycleOwner(this);
        // toolBar Binding
        toolBar = findViewById(R.id.tool_bar);
        // Setting the action bar for this activity
        setSupportActionBar(toolBar);
        mainMenuDrawerLayout =  homeBinding.drawerLayout;   //  findViewById(R.id.drawer_layout);

        //Json Testing
        testJson();

        //Notification Channel Creation
        testNotification = findViewById(R.id.test_notification);
        testNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ChatBotActivity.class));
            }
        });

        /*createNotificationChannel();
        testNotification = findViewById(R.id.test_notification);
        testNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FeedBackBroadCastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long timeNow = System.currentTimeMillis();
                long timeToWait = timeNow + 10000;  //after 10 seconds the alarm will go off

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeToWait, pendingIntent);


            }
        });*/

        // to draw the nav view when swipe
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainMenuDrawerLayout, toolBar,
                                                                                R.string.open_nav_drawer, R.string.close_nav_drawer);
        mainMenuDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        /** User Info  **/
        SharedPrefs.init();

        //Notice to successfully find any widget in Navigation Drawer's header,
        // you need to obtain header view first. This can be achieved by using getHeaderView() method.
        header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        userFirstName = header.findViewById(R.id.user_first_name);
        Log.v("gender", SharedPrefs.read(SharedPrefs.GENDER, "User"));
        userFirstName.setText(SharedPrefs.read(SharedPrefs.FIRST_NAME, "User"));


        /**   Testing  **/


        /**************** Search View ******************/
        searchBar = findViewById(R.id.search_view);
        searchButton = findViewById(R.id.img_search_button);
        handleSearchViewClick();
        handleSearchButtonClick();

        /** Categories Grid View**/
        categoriesGridView = findViewById(R.id.categories_grid_view);

        categories.add(new CategoryModel("Oral Care", R.mipmap.drug_img));
        categories.add(new CategoryModel("Respiratory", R.mipmap.drug_img));
        categories.add(new CategoryModel("Baby Care", R.mipmap.drug_img));
        categories.add(new CategoryModel("Eye", R.mipmap.drug_img));




        mainCategoriesAdapter = new MainCategoriesAdapter(categories, this);
        categoriesGridView.setAdapter(mainCategoriesAdapter);


        /** Choose Image Fab **/

        prescriptionPlaceholder = homeBinding.testPres;
        chooseImage = homeBinding.chooseImageFab;
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letUserChooseImage();
            }
        });

       //Section Cart
        cartIcon = findViewById(R.id.action_cart);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });


        /** * **********  Navigation View  **********************/
        // onCreateMenuOptions --> to me its useless
        navigationView = homeBinding.navView;
        navigationView.bringToFront();
        handleNavItemClick();
                                       /*  ***************************    */
        // menu icon opens the main menu
        mainMenuIcon = findViewById(R.id.action_main_menu);
        mainMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenuDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        /* Static Categories Container*/


    }

    private void testJson() {

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject2 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonObject.put("name", "Panadol");
                jsonObject.put("main_category", "main_category1");
                jsonObject.put("secondary_category", "secondary_category1");
                jsonObject.put("position", "position1");
                jsonObject.put("image_url", "url1");
                jsonObject.put("price", "price1");
                jsonObject.put("quantity", "quantity1");

                jsonObject2.put("name", "Panadol2");
                jsonObject2.put("main_category", "main_category2");
                jsonObject2.put("secondary_category", "secondary_category2");
                jsonObject2.put("position", "position2");
                jsonObject2.put("image_url", "url2");
                jsonObject2.put("price", "price2");
                jsonObject2.put("quantity", "quantity2");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
            jsonArray.put(jsonObject2);
            System.out.println(jsonArray);

    }


    @Override
    protected void onResume() {
        // SharedPrefs.init();
        header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        userFirstName = header.findViewById(R.id.user_first_name);
        Log.v("gender", SharedPrefs.read(SharedPrefs.GENDER, "User"));
        userFirstName.setText(SharedPrefs.read(SharedPrefs.FIRST_NAME, "User"));

        //homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        super.onResume();
    }
    // to avoid closing the app when back is pressed and the drawer is open
    @Override
    public void onBackPressed() {
        if(mainMenuDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mainMenuDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (searchFragment.isVisible()){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            searchBar.setText(null);
            searchBar.clearFocus();



        }
        else  {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();

                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private void letUserChooseImage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Select your prescribtion"), CODE_GALLERY_REQUEST);
            }else Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show();
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                prescriptionPlaceholder.setImageBitmap(bitmap);

                //Notice save the Bitmap to disk then load it up in the next activity.
                //Write file
                String filename = "bitmap.png";
                FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                /*//Cleanup
                if (bitmap != null && !bitmap.isRecycled()) {
                    stream.close();
                    bitmap.recycle();
                    bitmap = null;
                }*/
                startPrescriptionActivity(filename);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }




        super.onActivityResult(requestCode, resultCode, data);
    }
    private void createNotificationChannel() {

        // Since notification channels were introduced in 2017 in oreo version of android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String name = "FeedBackChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String description = "Channel For User FeedBack Notifications";

            NotificationChannel feedBackChannel = new NotificationChannel("FeedBack", name, importance);
            feedBackChannel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(feedBackChannel);

        }
    }

    private void startPrescriptionActivity(String filename) {
        Intent intent = new Intent(this, PrescriptionActivity.class);
        intent.putExtra("prescription", filename);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void handleSearchButtonClick() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchButtonClicked();
            }
        });
    }

    private void logOut() {
        SharedPrefs.clearAll();
        startActivityFromHome(9);
    }

    private void handleNavItemClick(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.log_out_button:
                        logOut();
                        return true;

                    case R.id.test_menu_item:
                        startActivityFromHome(5);
                        return true;

                    case R.id.phone_numbers_menu_item:
                        startActivityFromHome(4);
                        return true;

                    case R.id.wishlist_menu_item:
                        startActivityFromHome(3);
                        return true;

                    case R.id.settings_menu_item:
                        startActivityFromHome(2);
                        return true;

                    case R.id.address_book_menu_item:
                        startActivityFromHome(1);
                        return true;

                    case R.id.my_orders_menu_item:
                        startActivityFromHome(0);
                        return true;

                    default: return false;

                }
            }});
    }

    private void startActivityFromHome(int activityFlag){
        //0 MyOrdersActivity
        //1 AddressBookActivity
        //2 SettingsActivity
        //3 WishListActivity
        //4 PhoneNumbersActivity
        //5 TestingActivity
        //9 SignInActivity
        Intent intent;
        if (activityFlag == 0){
            intent = new Intent(this, MyOrdersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else if (activityFlag == 1){
            intent = new Intent(this, AddressBookActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else if (activityFlag == 2){
            intent = new Intent(this, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        else if (activityFlag == 3){
            intent = new Intent(this, WishlistActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        else if (activityFlag == 4){
            intent = new Intent(this, PhoneNumbersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        else if (activityFlag == 5){
            intent = new Intent(this, WebScrapingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        else if (activityFlag == 9){
            intent = new Intent(this, SignInActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            finishAffinity();
        }
        else intent = null;

        startActivity(intent);
    }

    private void handleSearchViewClick(){
        homeBinding.searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startSearchFragment();
            }
        });
    }

    /** Search Fragment**/
    private void startSearchFragment(){

        handleSearchContainer();


        searchFragment.setSearchBar(searchBar);

        ConstraintLayout homeRoot = (ConstraintLayout) findViewById(R.id.home_root);
        homeRoot.setBackground(null);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.search_fragment_placeholder, searchFragment);
        fragmentTransaction.addToBackStack("fragmentTransaction");
        fragmentTransaction.commit();
    }

    private void handleSearchContainer() {
        searchButton = findViewById(R.id.img_search_button);

        searchButton.setVisibility(View.VISIBLE);



    }
    private void onSearchButtonClicked(){
        String searchKey = searchBar.getText().toString();
        if (TextUtils.isEmpty(searchKey)) {
            Toast.makeText(HomeActivity.this, "Enter a product name", Toast.LENGTH_LONG).show();
        }
        else {
            searchFragment.setSearchKey(searchKey);
        }


    }

    @Override
    public void onItemClicked(String categoryName) {
        startChosenCategoryActivity(categoryName);
        Log.v("---", categoryName);
    }

    private void startChosenCategoryActivity(String categoryName) {
        Intent intent = new Intent(HomeActivity.this, SubCategoriesActivity.class);
        categoriesData = CategoriesData.getInstance();
        categoriesData.getReady();

        categoriesNames = categoriesData.getCategories(categoryName);

        intent.putStringArrayListExtra("categoriesNames", categoriesNames);
        Log.v("categories", categoriesNames.get(1));
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}