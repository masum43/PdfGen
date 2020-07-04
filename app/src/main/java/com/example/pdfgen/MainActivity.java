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
import android.widget.Toast;

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
    public void onViewClicked(View view)  {
        switch (view.getId()) {
            case R.id.button:
                try {
                    createButton1Pdf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Pdf Generated", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                try {
                    createButton2Pdf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Pdf Generated", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void createButton2Pdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas myCanvas = myPage1.getCanvas();
        myCanvas.drawText("This should be first page",40,50,myPaint);
        myPdfDocument.finishPage(myPage1);

        PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage2 = myPdfDocument.startPage(myPageInfo2);
        Canvas myCanvas2 = myPage2.getCanvas();
        myCanvas2.drawText("This should be second page",40,50,myPaint);
        myPdfDocument.finishPage(myPage2);

        File file = new File(Environment.getExternalStorageDirectory(),"/SecondButtonPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();
    }


    private void createButton1Pdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas myCanvas = myPage1.getCanvas();
        myCanvas.drawText("Simple pdf",40,50,myPaint);
        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(),"/FirstButtonPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();

    }


}