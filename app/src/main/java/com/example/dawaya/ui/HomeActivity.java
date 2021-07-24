package com.example.dawaya.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dawaya.R;
import com.example.dawaya.adapters.CategoriesAdapter;
import com.example.dawaya.databinding.ActivityHomeBinding;
import com.example.dawaya.interfaces.HomeItemClickInterface;
import com.example.dawaya.models.CategoryModel;
import com.example.dawaya.models.ProductModel;
import com.example.dawaya.utils.CategoriesData;
import com.example.dawaya.utils.SharedPrefs;
import com.example.dawaya.utils.StaticAppData;
import com.example.dawaya.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeItemClickInterface, BottomNavigationView.OnItemSelectedListener {

    ActivityHomeBinding homeBinding;
    public Toolbar toolBar;
    private NavigationView navigationView;
    private DrawerLayout mainMenuDrawerLayout;

    SearchFrag searchFragment = new SearchFrag();
    ImageView searchButton;
    EditText searchBar;
    FragmentTransaction fragmentTransaction;

    RecyclerView categoriesRecyclerView;
    CategoriesAdapter categoriesAdapter;
    ArrayList<CategoryModel> categories = new ArrayList<>();

    ImageView mainMenuIcon, cartIcon;

    View header;
    TextView userFirstName;
    CategoriesData categoriesData;
    ArrayList<String> categoriesNames;

    Bitmap bitmap;
    BottomNavigationView bottomNavigationView;
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



        //React to different Intents
        reactToDifferentIntents();

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


        /**************** Search View ******************/
        searchBar = findViewById(R.id.search_view);
        searchButton = findViewById(R.id.img_search_button);
        handleSearchViewClick();
        handleSearchButtonClick();

        /** Categories Grid View**/
        categoriesRecyclerView = findViewById(R.id.categories_grid_view);

        /*categories.add(new CategoryModel("RESPIRATORY", R.mipmap.drug_img));
        categories.add(new CategoryModel("Oral Care", R.mipmap.drug_img));*/

        categories.add(new CategoryModel(StaticAppData.RESPIRATORY_SYSTEM, "https://www.thoughtco.com/thmb/xUJ3TEiRfnkPV1CP5Mdl1dP-dt8=/1500x844/smart/filters:no_upscale()/respiratory_system-578d72f73df78c09e96906ff.jpg"));
        categories.add(new CategoryModel(StaticAppData.DIGESTIVE_SYSTEM, "https://www.factsjustforkids.com/images/illustration-of-the-human-digestive-system.png"));
        categories.add(new CategoryModel(StaticAppData.SKIN, "data:image/jpeg;base64,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"));
        categories.add(new CategoryModel(StaticAppData.EYE, "https://static.dailymedicalinfo.com/2017/07/1-51.jpg"));
        categories.add(new CategoryModel(StaticAppData.EAR, "data:image/jpeg;base64,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"));

        categories.add(new CategoryModel(StaticAppData.BABY, "https://images.unsplash.com/photo-1610122748280-d0ae76b10750?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Y3V0ZSUyMGJhYnl8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80"));
        categories.add(new CategoryModel(StaticAppData.MOUTH_CARE, "https://dentistry.co.uk/wp-content/uploads/2020/05/child-teeth-nightmares-e1590752406133-600x400.jpg"));
        categories.add(new CategoryModel(StaticAppData.HAIR_CARE, "https://www.appclonescript.com/wp-content/uploads/2021/02/The-Best-Hair-Care-Tips-for-Men.jpg"));

        categories.add(new CategoryModel(StaticAppData.MEDICAL_SUPPLIES, "https://www.fouda.com/sites/all/themes/custom/fp/img/category/medical-supplies.jpg"));
        categories.add(new CategoryModel(StaticAppData.NUTRITION_SUPPLEMENTS, "https://www.fouda.com/sites/all/themes/custom/fp/img/category/supplements.jpg"));
        categories.add(new CategoryModel(StaticAppData.HOME_PRODUCTS, "https://www.fouda.com/sites/all/themes/custom/fp/img/category/general-hygiene.jpg"));


        categoriesAdapter = new CategoriesAdapter(categories, this);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoriesRecyclerView.setAdapter(categoriesAdapter);


        /** Choose Image Fab **/

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

        /*
        * BottomNavigation
        *
        */
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(this);
        /* Static Categories Container*/


    }

    private void reactToDifferentIntents() {
        Intent intent = getIntent();
        if (intent.getStringExtra("Product") != null){
            //Home activity is fired from  another order notification

            ProductModel product = new Gson().fromJson(intent.getStringExtra("Product"), ProductModel.class);

            Dialog dialog = Utils.getBasicDialog(this, R.layout.dialog_add_product_to_cart);

            //populate layout views with data
            ((TextView)dialog.findViewById(R.id.product_name)).setText(product.getName());
            ((TextView)dialog.findViewById(R.id.product_price)).setText(String.valueOf(product.getPrice()));
            dialog.findViewById(R.id.add_to_cart_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // add that product to cart
                }
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetAnimation;

            dialog.getWindow().setGravity(Gravity.CENTER);

        }
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
        SharedPrefs.clear(SharedPrefs.USER_ID);
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
            intent = new Intent(this, WishListActivity.class);
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
        startSubCategoriesActivity(categoryName);
        Log.v("---", categoryName);
    }

    private void startSubCategoriesActivity(String categoryName) {
        Intent intent = new Intent(HomeActivity.this, SubCategoriesActivity.class);
        /*categoriesData = CategoriesData.getInstance();
        categoriesData.getReady();*/
        //categoriesNames = categoriesData.getCategories(categoryName);
      /*  intent.putStringArrayListExtra("categoriesNames", categoriesNames);
        Log.v("categories", categoriesNames.get(1));*/
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom_nav_home:
                // start home if home is not open
                return true;

            case R.id.bottom_nav_prescriptions:
                startActivity(new Intent(this, PrescriptionActivity.class));
                return true;

            case R.id.bottom_nav_reminders:
                startActivity(new Intent(this, RemindersActivity.class));
        }
        return false;
    }
}