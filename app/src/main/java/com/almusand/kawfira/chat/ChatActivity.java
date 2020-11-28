package com.almusand.kawfira.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.almusand.kawfira.Models.Chat;
import com.almusand.kawfira.R;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ChatNavigator{
    private static final String TAG = "ChatActivityAPP";

    ChatVM vm;
    private EditText message;
    private ImageButton send;
    private FirebaseFirestore db;
    private RecyclerView chats;
    private ChatsAdapter adapter;
    GlobalPreferences gp;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        vm = ViewModelProviders.of(this).get(ChatVM.class);
        vm.setNavigator(this);
        gp = new GlobalPreferences(this);
        //FirebaseFireStore instance
        db = FirebaseFirestore.getInstance();
        chats = findViewById(R.id.chats);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        chats.setLayoutManager(manager);
        String role = gp.getUserRole();
        id = gp.getUserId();
        String receiverId = getIntent().getStringExtra("id");
        String receiverName = getIntent().getStringExtra("name");
        String receiverImg = getIntent().getStringExtra("img");
        Log.e("receiverId >" + receiverId,"receiverName> "+receiverName);
        setUpUI(receiverName,receiverImg);
        vm.setIds(role,id,receiverId);
        vm.getRoomId(db);


        message = findViewById(R.id.message_text);
        send = findViewById(R.id.send_message);

        send.setOnClickListener(v -> {
            if (message.getText().toString().isEmpty()) {
                Toast.makeText(
                        ChatActivity.this,
                        getString(R.string.error_empty_message),
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                addMessageToChatRoom();
            }
        });

    }

    private void setUpUI(String receiverName, String receiverImg) {
        Log.e("user name",receiverName);
        ImageView imageView = findViewById(R.id.recieverImg);
        TextView receiverNameTV = findViewById(R.id.receiverName);
        if(receiverImg!=null) {
            if (receiverImg.length() > 4) {

                Picasso.get().load(receiverImg)
                        .placeholder(R.drawable.userphoto).into(imageView);
            }
        }
        receiverNameTV.setText(receiverName);

    }

    private void addMessageToChatRoom() {
        String chatMessage = message.getText().toString();
        message.setText("");
        send.setEnabled(false);
        Log.e("id",id);
        vm.addMessageToChatRoom(
                db,
                gp,
                id,
                chatMessage,
                documentReference -> send.setEnabled(true),
                e -> {
                    send.setEnabled(true);
                    Toast.makeText(
                            ChatActivity.this,
                            getString(R.string.error_message_failed),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }
    private void showChatMessages() {
        vm.getChats(db, (snapshots, e) -> {
            if (e != null) {
                Log.e("ChatRoomActivity", "Listen failed.", e);
                return;
            }

            List<Chat> messages = new ArrayList<>();
            for (QueryDocumentSnapshot doc : snapshots) {
                messages.add(
                        new Chat(
                                doc.getId(),
                                doc.getString("sender_id"),
                                doc.getString("message"),
                                doc.getLong("sent")
                        )
                );
            }

            adapter = new ChatsAdapter(messages, id);
            chats.setAdapter(adapter);
        });
    }

    @Override
    public void showChatMessages(String roomId) {
        showChatMessages();
    }

}