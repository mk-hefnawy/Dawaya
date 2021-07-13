package com.example.dawaya.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dawaya.R;
import com.example.dawaya.adapters.ChatBotAdapter;
import com.example.dawaya.chatbot.ChatItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import static com.example.dawaya.chatbot.ChatItem.TextLayout;
import static com.example.dawaya.chatbot.ChatItem.ServiceLayout;

public class ChatBotActivity extends AppCompatActivity {
    RecyclerView chatRecyclerView;
    Button addChipBtn;
    ChatBotAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_bot_services);

        ArrayList<String> services = new ArrayList<>();
        services.add("Cancel Order");
        services.add("Browsing");
        services.add("View Cart");

        ArrayList<ChatItem> chatItems = new ArrayList<>();
        chatItems.add(new ChatItem(TextLayout, "Hello! How can i help you?"));
        chatItems.add(new ChatItem(ServiceLayout, services));



        chatRecyclerView = findViewById(R.id.chat_bot_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ChatBotAdapter(chatItems, this);
        chatRecyclerView.setAdapter(adapter);

        addChipBtn = findViewById(R.id.add_chip);
        addChipBtn.setOnClickListener(view -> {
            ArrayList<ChatItem> newItems = new ArrayList<>();
            newItems.add(new ChatItem(TextLayout, "Hi"));
            newItems.add(new ChatItem(ServiceLayout, services));
            int insertIndex = chatItems.size();
            chatItems.addAll(insertIndex, newItems);
            adapter.notifyItemRangeInserted(insertIndex, newItems.size());
        });


    }
}