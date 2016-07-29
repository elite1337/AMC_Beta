package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    EditText editTextUser, editTextEmail, editTextDescription;

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
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("AMC");

        editTextUser = (EditText)findViewById(R.id.editTextEditUser);
        editTextEmail = (EditText)findViewById(R.id.editTextEditEmail);
        editTextDescription = (EditText)findViewById(R.id.editTextEditDescription);
        Button button = (Button)findViewById(R.id.buttonDeleteAcc);

        button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        editTextUser.setFilters(new InputFilter[] {inputFilter});

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "default");
        String email = sharedPreferences.getString("email", "default");
        String description = sharedPreferences.getString("description", "default");

        editTextUser.setText(user);
        editTextEmail.setText(email);
        editTextDescription.setText(description);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditProfileDialogFragment editProfileDialogFragment = new EditProfileDialogFragment();
                editProfileDialogFragment.show(getFragmentManager(), "DialogFragmentShit");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_edit_profile_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.editProfile:

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("user", "default");
                String email = sharedPreferences.getString("email", "default");
                String description = sharedPreferences.getString("description", "default");

                if(!editTextUser.equals(user))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user", editTextUser.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), ManageAccountActivity.class);
                    startActivity(intent);
                }
                if(!editTextEmail.equals(email))
                {
                    if(!isValidEmail(editTextEmail.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(), "Does your email address look right?", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    else
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", editTextEmail.getText().toString());
                        editor.putString("emailverification", "0");
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), ManageAccountActivity.class);
                        startActivity(intent);
                    }
                }
                if(!editTextDescription.equals(description))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("description", editTextDescription.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), ManageAccountActivity.class);
                    startActivity(intent);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
