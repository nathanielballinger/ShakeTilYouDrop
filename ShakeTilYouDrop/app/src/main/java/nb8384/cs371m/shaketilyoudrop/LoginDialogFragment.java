package nb8384.cs371m.shaketilyoudrop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nathaniel on 12/6/2016.
 */

public class LoginDialogFragment extends DialogFragment {

    public interface LoginDialogListener {
        void onCreateAccountClick(String username, String password);
        void onLoginClick(String username, String password);
    }

    private LoginDialogListener mListener;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button createAccountButton;

    public void setLoginDialogListener(LoginDialogListener listener) {
        mListener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.firebase_login, container, false);
        username = (EditText) v.findViewById(R.id.username);
        password = (EditText) v.findViewById(R.id.password);
        loginButton = (Button) v.findViewById(R.id.loginButton);
        createAccountButton = (Button) v.findViewById(R.id.createAccountButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoginClick(username.getText().toString(), password.getText().toString());
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreateAccountClick(username.getText().toString(), password.getText().toString());
            }
        });
        return v;
    }

}
