package com.engeniouspark.android.roro.firebasechat.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.engeniouspark.android.roro.firebasechat.R;
import com.engeniouspark.android.roro.firebasechat.core.login.LoginContract;
import com.engeniouspark.android.roro.firebasechat.core.login.LoginPresenter;
import com.engeniouspark.android.roro.firebasechat.ui.activities.ChatActivity;
import com.engeniouspark.android.roro.firebasechat.ui.activities.RegisterActivity;
import com.engeniouspark.android.roro.firebasechat.ui.activities.UserListingActivity;
import com.engeniouspark.android.roro.firebasechat.utils.Constants;
import com.engeniouspark.android.roro.firebasechat.utils.SessionData;



public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View {
    private LoginPresenter mLoginPresenter;

    private EditText mETxtEmail, mETxtPassword;
    private Button mBtnLogin, mBtnRegister;

    String emailId;
    SessionData sessionData;
    Context context;

    private ProgressDialog mProgressDialog;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mETxtEmail = (EditText) view.findViewById(R.id.edit_text_email_id);
        mETxtPassword = (EditText) view.findViewById(R.id.edit_text_password);
        mBtnLogin = (Button) view.findViewById(R.id.button_login);
        mBtnRegister = (Button) view.findViewById(R.id.button_register);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        context = getActivity();


         sessionData = new SessionData(context);
    }

    private void init() {
        mLoginPresenter = new LoginPresenter(this);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        setDummyCredentials();
    }

    private void setDummyCredentials() {
        mETxtEmail.setText("test@test.com");
        mETxtPassword.setText("123456");
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_login:
                onLogin(view);
                break;
            case R.id.button_register:
                onRegister(view);
                break;
        }
    }

    private void onLogin(View view) {
         emailId = mETxtEmail.getText().toString();
        String password = mETxtPassword.getText().toString();

        mLoginPresenter.login(getActivity(), emailId, password);
        mProgressDialog.show();
    }

    private void onRegister(View view) {
        RegisterActivity.startActivity(getActivity());
    }

    @Override
    public void onLoginSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();

//        UserListingActivity.startActivity(getActivity(),
//                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (emailId.equals("Admin@App.com"))
        {

            sessionData.setObjectAsString("user","admin");

            UserListingActivity.startActivity(getActivity(),
                    Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else
        {
            sessionData.setObjectAsString("user","user");
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra(Constants.ARG_RECEIVER, "admin@app.com");
            intent.putExtra(Constants.ARG_RECEIVER_UID, "iFnvdIOGd3a5QIVPsGioztAdRzU2");
            intent.putExtra(Constants.ARG_FIREBASE_TOKEN, "cLecWqQYKy0:APA91bFeh0AajKudiip509BFAAJniR4j3BT7LAIR_xxvcEwtFcBJur8Mm1v5p5ZGrkuuZn_khQAFOU_8VvbgbjpuedoqVFl-Y3FczqzB-7TdXGQNiOywEGCuYafeGHco9mujpmVf5ugP");
            getActivity().startActivity(intent);

        }


    }

    @Override
    public void onLoginFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
