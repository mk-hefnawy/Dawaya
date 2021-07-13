package com.example.dawaya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dawaya.R;
import com.example.dawaya.chatbot.ChatItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.dawaya.chatbot.ChatItem.TextLayout;
import static com.example.dawaya.chatbot.ChatItem.ServiceLayout;


public class ChatBotAdapter extends RecyclerView.Adapter {

    ArrayList<ChatItem> chatItems = new ArrayList<>();
    Context context;

    public ChatBotAdapter(ArrayList<ChatItem> chatItems, Context context) {
        this.chatItems = chatItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType){
            case TextLayout:
                 itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_text_item,parent, false);
                return new ChatBotTextViewHolder(itemView);
            case ServiceLayout:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_services_item, parent, false);
                return new ChatBotServicesViewHolder(itemView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder  holder, int position) {
        switch (chatItems.get(position).getViewType()){
            case TextLayout:
                ((ChatBotTextViewHolder)holder).setMessage(chatItems.get(position).getMessage());
                break;
            case ServiceLayout:
                ((ChatBotServicesViewHolder)holder).setServices(chatItems.get(position).getServicesNames());
            default:

        }
    }

    @Override
    public int getItemCount() {
        return chatItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatItems.get(position).getViewType()){
            case 0:
                return TextLayout;
            case 1:
                return ServiceLayout;
            default:
                return -1;
        }

    }

    public class ChatBotTextViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV;
        public ChatBotTextViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.chat_bot_message);
        }
        private void setMessage(String message){
            this.messageTV.setText(message);
        }
    }

    public  class ChatBotServicesViewHolder extends RecyclerView.ViewHolder {
        ChipGroup servicesGroup;
        public ChatBotServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            servicesGroup = itemView.findViewById(R.id.chat_bot_services_group);
        }

        private void setServices(ArrayList<String> services){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (String service: services) {
                Chip chip = new Chip(context);
                chip.setText(service);
                servicesGroup.addView(chip, params);
            }
        }
    }

    public void setChatItems(ArrayList<ChatItem> chatItems) {
        this.chatItems = chatItems;
    }
}
