package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountDialogFragment.OnCompleteListener {

    private EditText editTextUser, editTextEmail, editTextPw, editTextRePw;

    private String blockCharacterSet = "尻,.@&%=+-*/()!?'\":;_#^\\[]{}<>|$£€¥…~·`§¤¡¿'\":;_？！：；、…“”（）.+*=-/@<>#^£$€¥§¤¿\\|[]{}&～｀_±×÷·%©〔〕「」《》—‰※№‘’℃℉ˇˊˋ˙′″℅℡°■□▲△◆◇○◎●★☆⊙♀♂〈〉『』【】〖〗＇〃－~＿￠￣＊．≈≠≤≥←↑→↓¡¨⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑,.!?:;……";
    private final InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains((source+"")))
            {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setTitle("AMC");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollViewCreate);
        editTextUser = (EditText)findViewById(R.id.editTextCreateUser);
        editTextEmail = (EditText)findViewById(R.id.editTextCreateEmail);
        editTextPw = (EditText)findViewById(R.id.editTextCreatePw);
        editTextRePw = (EditText)findViewById(R.id.editTextRePw);
        Button button = (Button)findViewById(R.id.buttonCreateAcc);

        editTextUser.setFilters(new InputFilter[] {inputFilter});

        editTextPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                scrollView.scrollTo(0, scrollView.getBottom());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

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
                else if(editTextPw.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Does your password look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(editTextPw.getText().toString().length() < 4)
                {
                    Toast.makeText(getApplicationContext(), "Password four characters required!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(editTextRePw.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Re-enter your password below!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!editTextPw.getText().toString().equals(editTextRePw.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Does your password look right?", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", editTextUser.getText().toString());
                    editor.putString("email", editTextEmail.getText().toString());
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

    @Override
    public void onBackPressed() {

        Intent intentLogIn = new Intent(this, LogInActivity.class);
        startActivity(intentLogIn);
    }
}