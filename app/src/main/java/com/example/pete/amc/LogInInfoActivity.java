package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LogInInfoActivity extends AppCompatActivity {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    String email;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_info);

        getSupportActionBar().setTitle("AMC");

        final EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        final EditText editTextPW = (EditText)findViewById(R.id.editTextPW);
        TextView textView = (TextView)findViewById(R.id.textViewForget);
        Button button = (Button)findViewById(R.id.button4RealLogIn);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEmail(editTextEmail.getText().toString());
                setPw(editTextPW.getText().toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        "https://punchinout-zencher.herokuapp.com/login?tID=" + getEmail() + "&mID=" + getPw(),

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.get("status").toString().equals("ok")) {

                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("identification", "1");
                                        editor.putString("email", getEmail());
                                        editor.putString("pw", getPw());

                                        editor.commit();

                                        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        editTextEmail.setText("");
                                        editTextPW.setText("");
                                        Toast.makeText(getApplicationContext(), "Invalid Email or Password.", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("exception", e+"");
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("jsonstringthis", error + "");
                            }
                        }
                );
                requestQueue.add(jsonObjectRequest);
            }
        });

    }
}
