package com.example.mohsin.bitmoonv3.CustomerProfile;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.mohsin.bitmoonv3.R;

public class Test extends Activity {

    private static final int SELECTED_PICTURE=1;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);

        iv=(ImageView)findViewById(R.id.imageView1);
    }
    public void btnClick(View v){
        Intent i=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK){
                    Uri uri=data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex=cursor.getColumnIndex(projection[0]);
                    String filePath=cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage=BitmapFactory.decodeFile(filePath);
                    Drawable d=new BitmapDrawable(yourSelectedImage);

                    iv.setBackground(d);

                }
                break;

            default:
                break;
        }

    }

}
