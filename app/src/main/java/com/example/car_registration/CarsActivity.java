package com.example.car_registration;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CarsActivity extends AppCompatActivity {
    private Button btn, saveBtn;
    private ImageView imageView;
    EditText regNo;
    private static final String FILE_NAME = "example.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        btn = findViewById(R.id.openCameraBtn);
        saveBtn = findViewById(R.id.SaveBtn);
        regNo=findViewById(R.id.capturedRegNo);
        imageView=findViewById(R.id.capturedImage);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return source.toString().toUpperCase();
            }
        };
        regNo.setFilters(new InputFilter[]{filter});

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open_camera, 100);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







//                printPdf();
//                Toast.makeText(CarsActivity.this, " pdf printed successfully", Toast.LENGTH_SHORT).show();



                saveData();
                Toast.makeText(CarsActivity.this, " data saved successfully", Toast.LENGTH_SHORT).show();

                try {
                    saveImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(CarsActivity.this, " image saved", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(photo);


    }


    private void saveImage() throws IOException {
        ImageView imageView = findViewById(R.id.capturedImage);
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        String filename = "image.png";
        FileOutputStream stream = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.close();
    }

    public void saveData()  {

        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase database = dbHandler.getWritableDatabase();
        String RegisNo=regNo.getText().toString();
        String CurrentDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()).format(new Date());

        ContentValues contentValues=new ContentValues();
        contentValues.put(" number",RegisNo);
        contentValues.put("time",CurrentDate);

        database.insert("vehicleTable",null,contentValues);
        database.update("vehicleTable",contentValues,null,null);

    }

}