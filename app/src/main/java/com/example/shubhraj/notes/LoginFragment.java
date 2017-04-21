package com.example.shubhraj.notes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shubhraj on 19-04-2017.
 */

public class LoginFragment extends Fragment
{
    OnDataPass dataPasser;

    private TextView loginID, password;
    private Button button;
    String userName, passWord;

    public interface OnDataPass {
        public void onDataPass(int x);
    }

    public LoginFragment()
    {
        //Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.login, container, false);
        loginID = (TextView) rootView.findViewById(R.id.userID);
        Bundle bundle = this.getArguments();
        if(bundle!=null)
        {
            userName = bundle.getString("USER");
            passWord = bundle.getString("PASS");
        }
        else
        {
            Toast.makeText(getContext(),"Nothing in Bundle",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getActivity(),"USER:"+userName+"\nPASSWORD:"+passWord,Toast.LENGTH_LONG).show();
        password = (TextView) rootView.findViewById(R.id.password);
        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkstatus();
            }
        });
        return rootView;
    }

    public void checkstatus()
    {
        String user = loginID.getText().toString();
        String pass = password.getText().toString();
        if(user.equals(userName)&&(pass.equals(passWord)))
            dataPasser.onDataPass(1);
        else
            dataPasser.onDataPass(0);
    }

}
