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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Screen2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String title,name,mobileNo,gender,dobDD,dobMM,dobYYYY,dob;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView tvDD,tvMM,tvYYYY;
    String[] date = new String[31];
    String[] month= new String[12];
    String[] year = new String[71];

    ImageView profileImage,editProfileImage;

    private static final int WRITE_PERMISSION = 0x01;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);
        initializeDates();

        Button btnNext = findViewById(R.id.btnNext);
        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText etName = findViewById(R.id.etName);
        final EditText etMobileNo = findViewById(R.id.etMobile);
        tvMM = findViewById(R.id.tvMM);
        tvDD = findViewById(R.id.tvDD);
        tvYYYY = findViewById(R.id.tvYYYY);
        radioGroup = findViewById(R.id.radioGroup);
        ImageView backArrow = findViewById(R.id.edit1BackArrow);
        profileImage = findViewById(R.id.ivProfileImage);
        editProfileImage = findViewById(R.id.ivEditProfileImage);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWritePermission();
            }
        });



        Spinner spinnerDD = findViewById(R.id.spinnerDD);
        spinnerDD.setOnItemSelectedListener(this);
        ArrayAdapter dd = new ArrayAdapter(Screen2.this,android.R.layout.simple_spinner_item,date);
        dd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDD.setAdapter(dd);

        Spinner spinnerMM = findViewById(R.id.spinnerMM);
        spinnerMM.setOnItemSelectedListener(this);
        ArrayAdapter mm = new ArrayAdapter(Screen2.this,android.R.layout.simple_spinner_item,month);
        mm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMM.setAdapter(mm);


        Spinner spinnerYYYY = findViewById(R.id.spinnerYYYY);
        spinnerYYYY.setOnItemSelectedListener(this);
        ArrayAdapter yyyy = new ArrayAdapter(Screen2.this,android.R.layout.simple_spinner_item,year);
        yyyy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYYYY.setAdapter(yyyy);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(etTitle.getText().toString().equals("")||
                        etName.getText().toString().equals("")||
                        etMobileNo.getText().toString().equals("")||selectedId==-1){
                    Toast.makeText(Screen2.this,"Enter the complete Details",Toast.LENGTH_SHORT).show();

                }else if(etMobileNo.getText().toString().length()!=10){
                    Toast.makeText(Screen2.this,"Enter a valid 10 digit Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    radioButton = findViewById(selectedId);
                    title = etTitle.getText().toString();
                    name = etName.getText().toString();
                    mobileNo = etMobileNo.getText().toString();
                    gender = radioButton.getText().toString();
                    dob = dobDD+"/"+dobMM+"/"+dobYYYY;
                    Edit1Data obj = new Edit1Data(title,name,mobileNo,gender,dob,profileImage);
                    Intent intent = new Intent(Screen2.this,Screen3.class);
                    intent.putExtra("edit1Data",obj);
                    startActivity(intent);
                }
            }
        });

    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

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
                selectImage(Screen2.this);
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
                        profileImage.setImageBitmap(selectedImage);
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
                                profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void initializeDates(){
        for(int i = 1;i<=31;i++){
            date[i-1]= String.valueOf(i);
        }
        for(int i = 1;i<=12;i++){
            month[i-1]= String.valueOf(i);
        }
        int count = 0;
        for(int i = 2020;i>=1950;i--){
            year[count]=String.valueOf(i);
            count++;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId()==R.id.spinnerDD){
            dobDD = String.valueOf(position+1);
            tvDD.setText(dobDD);
        }
        else if(spinner.getId()==R.id.spinnerMM){
            dobMM = String.valueOf(position+1);
            tvMM.setText(dobMM);
        }
        else if(spinner.getId()==R.id.spinnerYYYY){
            int yyy = 2020-position;
            dobYYYY = String.valueOf(yyy);
            tvYYYY.setText(dobYYYY);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
