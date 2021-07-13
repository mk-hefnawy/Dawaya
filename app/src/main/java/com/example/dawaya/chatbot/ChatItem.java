package com.example.dawaya.chatbot;

import java.util.ArrayList;

public class ChatItem {

    public static final int TextLayout = 0;
    public static final int ServiceLayout = 1;
    private int viewType;



    //TextLayout
    private String message;

    public ChatItem(int viewType, String message) {
        this.viewType = viewType;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }





    //ServiceLayout
    private ArrayList<String> servicesNames = new ArrayList<>();

    public ChatItem(int viewType, ArrayList<String> servicesNames) {
        this.viewType = viewType;
        this.servicesNames = servicesNames;
    }
    public ArrayList<String> getServicesNames() {
        return servicesNames;
    }
    public void setServicesNames(ArrayList<String> servicesNames) {
        this.servicesNames = servicesNames;
    }


    public int getViewType() {
        return viewType;
    }
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
