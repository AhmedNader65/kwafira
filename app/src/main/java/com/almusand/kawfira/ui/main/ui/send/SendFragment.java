package com.almusand.kawfira.ui.main.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.almusand.kawfira.R;
import com.almusand.kawfira.utils.GlobalPreferences;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    boolean sent = false;
GlobalPreferences globalPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        globalPreferences = new GlobalPreferences(getContext());
        TextView textArea = root.findViewById(R.id.textArea);
        TextView button = root.findViewById(R.id.confirm);
        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                sent =false;
                if(s.equals("Done")){
                    Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_SHORT).show();
                    textArea.setText("");
                }else{
                    Toast.makeText(getContext(), getString(R.string.places_try_again), Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(v -> {
            if(sent==false) {
                if (textArea.getText().toString().length() > 5) {
                    sent = true;
                    sendViewModel.contactUs(globalPreferences.getUserAuth(), textArea.getText().toString());
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_empty_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}