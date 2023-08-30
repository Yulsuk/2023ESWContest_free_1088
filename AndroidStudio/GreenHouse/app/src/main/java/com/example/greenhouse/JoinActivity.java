package com.example.greenhouse;

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

import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText userIdEditText = (EditText)findViewById(R.id.editTextUserId);
        EditText userPwEditText = (EditText)findViewById(R.id.editTextUserPw);
        EditText userNameEditText = (EditText)findViewById(R.id.editTextUserName);
        EditText userPhoneEditText = (EditText)findViewById(R.id.editTextUserPhone);
        Button JoinBtn = (Button)findViewById(R.id.btnRunJoin);

        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = userIdEditText.getText().toString();
                String userPw = userPwEditText.getText().toString();
                String userName = userNameEditText.getText().toString();
                String userPhone = userPhoneEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 회원가입이 가능한다면
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class );
                                startActivity(intent);
                                finish();//액티비티를 종료시킴(회원등록 창을 닫음)
                            }
                            else{// 회원가입이 안된다면
                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                JoinRequest registerRequest = new JoinRequest(userId, userPw, userName, userPhone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}