package com.example.pete.amc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountDialogFragment.OnCompleteListener {

    EditText editTextUser, editTextEmail, editTextPw, editTextRePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setTitle("AMC");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        editTextUser = (EditText)findViewById(R.id.editTextCreateUser);
        editTextEmail = (EditText)findViewById(R.id.editTextCreateEmail);
        editTextPw = (EditText)findViewById(R.id.editTextCreatePw);
        editTextRePw = (EditText)findViewById(R.id.editTextRePw);
        Button button = (Button)findViewById(R.id.buttonCreateAcc);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextUser.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Does your user name look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!isValidEmail(editTextEmail.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Does your email address look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(editTextPw.getText().toString().isEmpty() & editTextRePw.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Does your password look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!editTextPw.getText().toString().equals(editTextRePw.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Does your password look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(editTextPw.getText().toString().length() < 4)
                {
                    Toast.makeText(getApplicationContext(), "Four characters required!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", editTextUser.getText().toString());
                    editor.putString("email", editTextEmail.getText().toString());
                    editor.putString("identification", "1");
                    editor.commit();

                    CreateAccountDialogFragment createAccountDialogFragment = new CreateAccountDialogFragment();
                    createAccountDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                    Bundle bundle = new Bundle();
                    bundle.putString("emailquestion", editTextEmail.getText().toString());
                    createAccountDialogFragment.setArguments(bundle);
                }
            }
        });

    }

    @Override
    public void onComplete(String time) {

        editTextEmail.setText(time);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
