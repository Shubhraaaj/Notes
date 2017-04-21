package com.example.shubhraj.notes;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LoginFragment.OnDataPass, ListsFragment.OnHeadlineSelectedListener
{
    public static final String MYPREFERENCES = "MyPrefs";
    public static final String Name = "usernameKey";
    public static final String Password = "passwordKey";
    private int securityStatus = -1;
    private int positionToEdit = -10;
    SharedPreferences sharedPreferences;
    private Button newNote;
    private boolean deleteStatus = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, "Shubhraj");
        editor.putString(Password, "12345678");
        editor.commit();

        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("USER", "Shubhraj");
        bundle.putString("PASS", "12345678");
        loginFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.list_container,new LoginFragment()).commit();

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.list_container);
        if(securityStatus == 1)
        {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ListFragment list = new ListFragment();
            fragmentTransaction.replace(R.id.list_container, list);
            fragmentTransaction.commit();
            newNote = (Button) findViewById(R.id.addNoteButton);
            newNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentTransaction.replace(R.id.list_container, new AddNoteFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            newNote.setVisibility(View.VISIBLE);
            if(positionToEdit != -1)
            {
                Intent intentEditorFragment = new Intent(this,EditorFragment.class);
                String toEdit = String.valueOf(positionToEdit);
                newNote.setVisibility(View.VISIBLE);
                newNote.setText("DELETE");
                Uri noteUri = Uri.parse(toEdit);
                intentEditorFragment.putExtra("NOTEURI", noteUri);
                newNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteStatus = true;
                    }
                });
                if(deleteStatus == true)
                    intentEditorFragment.putExtra("DELETE", "delete");
                else
                    intentEditorFragment.putExtra("DELETE", "No delete");
                fragmentTransaction.replace(R.id.list_container, new EditorFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                startActivity(intentEditorFragment);
                this.finish();
            }
        }
        else if(securityStatus == 0)
        {
            Toast.makeText(this, "Sorry, Wrong Username/Password",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onArticleSelected(int position) {
        positionToEdit = position;
    }

    @Override
    public void onDataPass(int x) {
        securityStatus = x;
    }
}
