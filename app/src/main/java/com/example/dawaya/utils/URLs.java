package com.example.dawaya.utils;


public class URLs {


    // Being static has made a problem while concatenating the url with parameters

   //public  String signInUrl = "http://192.168.1.104:9090/api/customer_signIn";
   //public  String signInUrl = "http://192.168.1.104:8080/customer/signIn";
   //public  String signInUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/signIn";
   public  String signInUrl = "https://pharmacy-management-system.azurewebsites.net/customer/signIn";

   // public  String signInUrl = "localhost:9090/api/customer_signIn";


    //public static String signUpUrl = "http://192.168.1.104:8080/customer/sign_up";
    //public static String signUpUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/signUp";
    public static String signUpUrl = "https://pharmacy-management-system.azurewebsites.net/customer/sign_up";


    public static String postPhoneNumnerUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postPhoneNumber";

    //public String searchUrl = "http://192.168.1.104:80/Melegy/search.php";
    //public String searchUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/searchProducts";
    public String searchUrl = "https://pharmacy-management-system.azurewebsites.net/products/products_name_search";

    //public static String updateUserUrl = "https://e4618ac1-450e-4e4c-bf88-b4624b4917b8.mock.pstmn.io/updateUser";
    public static String updateUserUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/updateUser";

    //public static String getAddressUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getAddresses";
    public static String getAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/get_address_by_customer_id";

    public static String addAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customer/add_new_address";


    public static String editAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/editAddress";

    public static String deleteAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/delete_address_by_customer_id";



    public static String getPhoneNumbersUrl = "https://pharmacy-management-system.azurewebsites.net/customerphone/get_customerphones_bycid";




    //public static String postPrescription = "http://192.168.1.104:4444/ImageUpload/upload.php";
    public static String postPrescription = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/upload";

    public static String postFeedBackUrl;

    public String getProductsByCategoryURL = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getProductsBySubCategory";

}
