package com.example.pdfgen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button1;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) throws IOException {
        switch (view.getId()) {
            case R.id.button:
                createButton1Pdf();
                break;
            case R.id.button2:
                break;
        }
    }

    private void createButton1Pdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);

        Canvas myCanvas = myPage1.getCanvas();
        myCanvas.drawText("First Button click",40,50,myPaint);
        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(),"/FirstButtonPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();

    }
}