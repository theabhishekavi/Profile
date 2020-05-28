package com.abhishek.profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


public class Screen3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] year = new String[31];
    TextView tvYoe;
    String yoe;
    ImageView hospitalImage, editHospitalImage;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);
        ImageView backArrow = findViewById(R.id.edit2BackArrow);
        tvYoe = findViewById(R.id.tvYoe);
        Intent intent = getIntent();
        Edit1Data edit1Data = (Edit1Data)intent.getSerializableExtra("edit1Data");
        Button btnSave = findViewById(R.id.btnSave);
        EditText etDesignation = findViewById(R.id.etDesignation);
        EditText etHospital = findViewById(R.id.etHospital);
        EditText etCity = findViewById(R.id.etCity);
        EditText etCountry = findViewById(R.id.etStateCountry);

        hospitalImage = findViewById(R.id.ivHospitalImage);
        editHospitalImage = findViewById(R.id.ivEditHospitalImage);
        editHospitalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWritePermission();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        AWSMobileClient.getInstance().initialize(this).execute();
//
//        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
//        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();
//
//
//        // Add code to instantiate a AmazonDynamoDBClient
//        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
//
//        this.dynamoDBMapper = DynamoDBMapper.builder()
//                .dynamoDBClient(dynamoDBClient)
//                .awsConfiguration(configuration)
//                .build();


        initializeYears();
        Spinner spinnerYOE = findViewById(R.id.spinnerYOE);
        spinnerYOE.setOnItemSelectedListener(this);
        ArrayAdapter yoe = new ArrayAdapter(Screen3.this,android.R.layout.simple_spinner_item,year);
        yoe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYOE.setAdapter(yoe);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etDesignation.getText().toString()==""||
                etHospital.getText().toString()==""||
                etCity.getText().toString()==""||
                etCountry.getText().toString()==""){
                    Toast.makeText(Screen3.this,"Enter the complete Details",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Screen3.this,"Updating database",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Screen3.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });


    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose A Hospital Picture/Logo");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }


    private void requestWritePermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                selectImage(Screen3.this);
                Log.e("taggg","Permission granted");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        hospitalImage.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                hospitalImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void initializeYears(){
        for (int i = 0;i<=30;i++){
            if(i==0||i==1)
                year[i]= i+" yr";
            else
            year[i] = i+" yrs";
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0||position==1)
            yoe = position+" year";
        else
            yoe = position+" years";
        tvYoe.setText(yoe);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class updateTable extends AsyncTask<Void,Integer,Integer>{
        @Override
        protected Integer doInBackground(Void... voids) {
            ManagerClass managerClass = new ManagerClass();
            CognitoCachingCredentialsProvider credentialsProvider = managerClass.getCredentials(Screen3.this);
//            MapperClass mapperClass = new MapperClass();
//            if(credentialsProvider!=null){
//                DynamoDBMapper dynamoDBMapper = new
//            }
            return null;
        }
    }
}
