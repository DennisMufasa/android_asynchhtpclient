package com.example.register;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //create objects to represent android widgets
    EditText name, course;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.txtName);
        course = (EditText)findViewById(R.id.txtCourse);
        btnSend = (Button)findViewById(R.id.btnSend);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a progress dialog
                final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                //give the proh=gress dialog a title
                dialog.setTitle("Loading...");
                //put a message in the progress dialog
                dialog.setMessage("Please wait");
                dialog.setMax(100);
                dialog.show();


                //send captured data to a database

                //an object to handle http request i.e sends data via http verbs
                AsyncHttpClient client = new AsyncHttpClient();

                //an object that handles request parameters
                RequestParams params = new RequestParams();

                //place our parameters in the params object
                params.add("name", name.getText().toString());
                params.add("course", course.getText().toString());

                //push the parameters to the php server file
                client.post("http://192.168.0.37/databases/add.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(MainActivity.this, "Record sent successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(MainActivity.this, "Error when sending records", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

            }
        });

        Button btnfind = findViewById(R.id.btnSearch);
        final EditText txtSearch = findViewById(R.id.txtSearch);
        final TextView txtResult = findViewById(R.id.txtResult);

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a progress dialog
                final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                //give the proh=gress dialog a title
                dialog.setTitle("Loading...");
                //put a message in the progress dialog
                dialog.setMessage("Please wait");
                dialog.setMax(100);
                dialog.show();



                //send captured data to a database

                //an object to handle http request i.e sends data via http verbs
                AsyncHttpClient client = new AsyncHttpClient();

                //an object that handles request parameters
                RequestParams params = new RequestParams();

                //place our parameters in the params object
                params.add("search", txtSearch.getText().toString());
//                params.add("course", course.getText().toString());

                //push the parameters to the php server file
//                client.post("http://192.168.0.37/databases/search.php", params, new AsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        String data = new String(responseBody);
//                        txtResult.setText(data);
////                        Toast.makeText(MainActivity.this, "Record sent successfully", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        Toast.makeText(MainActivity.this, "Error when sending records", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//
//                    }
//                });
                client.post("http://192.168.0.37/databases/search.php", params,new JsonHttpResponseHandler()
                        {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                                super.onSuccess(statusCode, headers, response);
                                for (int x = 0; x < response.length(); x ++){
                                    try {
                                        JSONObject json = response.getJSONObject(x);
                                        String student_id = json.getString("student_id");
                                        String name = json.getString("name");
                                        String course = json.getString("course");
                                        String date = json.getString("doA");

                                        txtResult.setText("Adm No - " + student_id + "\nName - " + name + "\nCourse - " + course + "\nDate of Admission - " + date);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        }
                );
            }
        });

    }
}
