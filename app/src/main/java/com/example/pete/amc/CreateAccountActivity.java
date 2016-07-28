package com.example.pete.amc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.AsyncTask;
import android.util.Log;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

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

        editTextUser = (EditText)findViewById(R.id.editTextCreateUser);
        editTextEmail = (EditText)findViewById(R.id.editTextCreateEmail);
        editTextPw = (EditText)findViewById(R.id.editTextCreatePw);
        editTextRePw = (EditText)findViewById(R.id.editTextRePw);
        Button button = (Button)findViewById(R.id.buttonCreateAcc);

        editTextUser.setFilters(new InputFilter[] {inputFilter});

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
                    Toast.makeText(getApplicationContext(), "Four characters required!", Toast.LENGTH_LONG).show();
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
                    editor.putString("descriptio", "");
                    editor.putString("identification", "1");
                    editor.putString("emailverification", "0");
                    editor.putString("emailagain", "0");
                    editor.commit();

                    CreateAccountDialogFragment createAccountDialogFragment = new CreateAccountDialogFragment();
                    createAccountDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                    Bundle bundle = new Bundle();
                    bundle.putString("emailquestion", editTextEmail.getText().toString());
                    createAccountDialogFragment.setArguments(bundle);

                    sendMessage();
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



    private void sendMessage() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailUser = sharedPreferences.getString("email", "default");
        String user = sharedPreferences.getString("user", "default");

        String[] recipients = {emailUser};
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.activity = this;
        email.m = new Mail("petealwayslovesu@gmail.com", "afgur6urtu08020802");
        email.m.set_from("petealwayslovesu@gmail.com");
        email.m.setBody("Hey " + user + ", welcome to AMC =] Here is your verification code: " + pinGenerator());
        email.m.set_to(recipients);
        email.m.set_subject("Hey " + user + ", welcome to AMC =]");
        email.execute();
    }

    public String pinGenerator()
    {
        int x = (int)(Math.random() * 9);
        x = x + 1;
        String pin = (x +"") + (((int)(Math.random()*1000))+"");
        return pin;
    }
}



class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    CreateAccountActivity activity;

    public SendEmailAsyncTask() {}

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                //Email sent.
            } else {
                //Email failed to send.
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            //Authentication failed.
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            //Email failed to send.
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            //Unexpected error occured.
            return false;
        }
    }
}