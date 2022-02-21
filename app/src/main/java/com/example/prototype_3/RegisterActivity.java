package com.example.prototype_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_id, txt_pass, txt_name, txt_age;
    private Button button_register, button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_id = findViewById(R.id.txt_id);
        txt_pass = findViewById(R.id.txt_pass);
        txt_name = findViewById(R.id.txt_name);
        txt_age = findViewById(R.id.txt_age);

        button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = txt_id.getText().toString();
                String userPassword = txt_pass.getText().toString();
                String userName = txt_name.getText().toString();
                int userAge = Integer.parseInt(txt_age.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                Toast.makeText(getApplicationContext(),"회원가입 되었습니다. 로그인해주세요", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"회원가입 실패했습니다. 다시해주세요", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}