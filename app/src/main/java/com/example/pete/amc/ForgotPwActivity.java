package com.example.pete.amc;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

        getSupportActionBar().setTitle("AMC");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final EditText editText = (EditText)findViewById(R.id.editText4got);
        Button button = (Button)findViewById(R.id.button4got);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isValidEmail(editText.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Does your email address look right?", Toast.LENGTH_LONG).show();
                    editText.setText("");
                }
                else
                {
                    ForgotPwDialogFragment forgotPwDialogFragment = new ForgotPwDialogFragment();
                    forgotPwDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                    Bundle bundle = new Bundle();
                    bundle.putString("email", editText.getText().toString());
                    forgotPwDialogFragment.setArguments(bundle);
                }
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}