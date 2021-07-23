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




    //public String searchUrl = "http://192.168.1.104:80/Melegy/search.php";
    //public static String searchUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/searchProducts";
    public static String searchUrl = "https://pharmacy-management-system.azurewebsites.net/products/products_name_search";
    public static String searchByCodeUrl = "https://pharmacy-management-system.azurewebsites.net/products/products_by_code";

    //public static String updateUserUrl = "https://e4618ac1-450e-4e4c-bf88-b4624b4917b8.mock.pstmn.io/updateUser";
    public static String updateUserUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/updateUser";

    //public static String getAddressUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getAddresses";
    public static String getAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/get_address_by_customer_id";
    public static String addAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customer/add_new_address";
    public static String editAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/editAddress";
    public static String deleteAddressUrl = "https://pharmacy-management-system.azurewebsites.net/customeraddress/delete_address_by_customer_id";

    public static String getPhoneNumbersUrl = "https://pharmacy-management-system.azurewebsites.net/customerphone/get_customerphones_bycid";
    public static String postPhoneNumnerUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postPhoneNumber";
    public static String updatePhoneNumbersUrl = "https://pharmacy-management-system.azurewebsites.net/customerphone/editphone";
    public static String deletePhoneNumbersUrl = "https://pharmacy-management-system.azurewebsites.net/customerphone/delete_phone_by_id";

    public static String getProductsByCategoryURL = "https://pharmacy-management-system.azurewebsites.net/products/products_category_search//";


    public static String putPassword = "";

    public static String getOrdersUrl = "https://pharmacy-management-system.azurewebsites.net/bill/get_customer_Bills_bycid";

    //public static String postOrderUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postOrder";
    public static String postOrderUrl = "https://pharmacy-management-system.azurewebsites.net/bill/add_new_mob_bill";

    //public static String postOrderProducts = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postOrderProducts";
    public static String postOrderProducts = "https://pharmacy-management-system.azurewebsites.net/";

    //public static String postPrescription = "http://192.168.1.104:4444/ImageUpload/upload.php";
    //public static String postPrescription = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/";
    public static String postPrescription = "https://pharmacy-management-system.azurewebsites.net/blob/";


    public static String getPrescriptionProducts = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getPrescriptionProducts";
    //public static String getAllPrescriptions = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/getAllPrescriptions";
    public static String getAllPrescriptions = "https://pharmacy-management-system.azurewebsites.net/customersPrescripts/get_by_userId";

    public static String postFeedBackUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postFeedBack";
    public static String postMessageFeedBackUrl = "https://b68ab667-18bb-4ab9-bfe7-97843785619b.mock.pstmn.io/postFeedBack";



}
