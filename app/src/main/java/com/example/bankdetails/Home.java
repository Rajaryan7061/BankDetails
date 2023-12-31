package com.example.bankdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    private EditText ifscCodeEdt;
    private TextView bankDetailsTV;


    String ifscCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ifscCodeEdt=findViewById(R.id.idBtnGetBankDetails);

        Button getBankDetailsBtn=findViewById(R.id.idBtnGetBankDetails);
        bankDetailsTV=findViewById(R.id.idTVBankDetails);

        mRequestQueue = Volley.newRequestQueue(Home.this);

        getBankDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifscCode=ifscCodeEdt.getText().toString();
                if (TextUtils.isEmpty(ifscCode)){
                    Toast.makeText(Home.this,"Please enter valid IFSC code",Toast.LENGTH_SHORT).show();

                }
                else {
                    getDataFraomIFSCCode(ifscCode);
                }
            }
        });
    }
    private void getDataFraomIFSCCode(String ifscCode) {
        mRequestQueue.getCache().clear();

       
        String url = "http://api.techm.co.in/api/v1/ifsc/" + ifscCode;

       
        RequestQueue queue = Volley.newRequestQueue(Home.this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("failed")) {
                       
                        bankDetailsTV.setText("Invalid IFSC Code");
                    } else {
                        
                        JSONObject dataObj = response.getJSONObject("data");
                        String state = dataObj.optString("STATE");
                        String bankName = dataObj.optString("BANK");
                        String branch = dataObj.optString("BRANCH");
                        String address = dataObj.optString("ADDRESS");
                        String contact = dataObj.optString("CONTACT");
                        String micrcode = dataObj.optString("MICRCODE");
                        String city = dataObj.optString("CITY");

                        
                        bankDetailsTV.setText("Bank Name : " + bankName + "\nBranch : " + branch + "\nAddress : " + address + "\nMICR Code : " + micrcode + "\nCity : " + city + "\nState : " + state + "\nContact : " + contact);
                    }
                } catch (JSONException e) {
                   
                    e.printStackTrace();
                    bankDetailsTV.setText("Invalid IFSC Code");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               
                bankDetailsTV.setText("Invalid IFSC Code");
            }
        });
       
        ((RequestQueue) queue).add(objectRequest);
    }
                }


