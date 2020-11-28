package com.almusand.kawfira.chat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.MsgModel;
import com.almusand.kawfira.WebServices.RetroWeb;
import com.almusand.kawfira.WebServices.ServiceApi;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatVM extends BaseViewModel<ChatNavigator> {

    private static final String TAG = "ChatVM";
    String user_id;
    String kwafira_id;
    String roomId;

    public void addMessageToChatRoom(FirebaseFirestore db,
                                     GlobalPreferences auth,
                                     String senderId,
                                     String message,
                                     final OnSuccessListener<DocumentReference> successCallback,
                                     final OnFailureListener failureCallback) {
        Map<String, Object> chat = new HashMap<>();
        chat.put("sender_id", senderId);
        chat.put("message", message);
        chat.put("flag", senderId==kwafira_id?0:1);
        chat.put("sent", System.currentTimeMillis());
        Log.e("room id",roomId);
        db.collection("rooms").document(roomId).collection("chats")
                .add(chat)
                .addOnSuccessListener(documentReference -> {
                    successCallback.onSuccess(documentReference);
                    sendNotification(auth.getUserAuth(),senderId==kwafira_id?user_id:kwafira_id, auth.getUserName()+","+senderId, auth.getUSER_Imge());
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureCallback.onFailure(e);
                    }
                });
    }

    public void getRoomId(FirebaseFirestore db) {

        db.collection("rooms")
                .whereEqualTo("client_id", user_id)
                .whereEqualTo("kwafira_id", kwafira_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()>0){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    roomId = document.getId();
                                    getNavigator().showChatMessages(roomId);
                                    Log.d(TAG, "Room id: "+roomId);

                                }}
                            else{
                                Log.d(TAG, "No Room is found Creating new one");
                                createRoom(db);
                            }
                        } else {

                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void createRoom(FirebaseFirestore db) {
        Map<String, Object> room = new HashMap<>();
        room.put("client_id", user_id);
        room.put("kwafira_id", kwafira_id);
        // Add a new document with a generated ID
        db.collection("rooms")
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        roomId = documentReference.getId();
                        getNavigator().showChatMessages(roomId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    public void getChats(FirebaseFirestore db, EventListener<QuerySnapshot> listener) {
        db.collection("rooms").document(roomId).collection("chats")
                .orderBy("sent", Query.Direction.DESCENDING)
                .addSnapshotListener(listener);
    }

    public void setIds(String role, String id, String receiverId) {
        switch (role){
            case "client":
                user_id = id;
                kwafira_id = receiverId;
                break;
            default:
                kwafira_id = id;
                user_id = receiverId;
                break;
        }
    }

    public void sendNotification(String userAuth, String userId, String title, String body ) {
        RetroWeb.getClient().create(ServiceApi.class).sendNotification("Bearer "+userAuth,
                userId,title,body,"chat").enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                Log.e("response", ""+t.getCause());
                Log.e("response", ""+t.getMessage());

                setIsLoading(false);

            }

        });
    }



}
