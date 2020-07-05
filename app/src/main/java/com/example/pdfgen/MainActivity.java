package com.example.pdfgen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
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
    @BindView(R.id.button3)
    Button customerInfoBtn;

    String[] infoArray = new String[]{"Name","Company Name","Address","Phone","Email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

    }

    @OnClick({R.id.button, R.id.button2})
    public void onViewClicked(View view) {
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

    private void createButton1Pdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas myCanvas = myPage1.getCanvas();
        myCanvas.drawText("Simple pdf", 40, 50, myPaint);
        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/FirstButtonPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();

    }

    private void createButton2Pdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas myCanvas = myPage1.getCanvas();
        myCanvas.drawText("This should be first page", 40, 50, myPaint);
        myPdfDocument.finishPage(myPage1);

        PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page myPage2 = myPdfDocument.startPage(myPageInfo2);
        Canvas myCanvas2 = myPage2.getCanvas();
        myCanvas2.drawText("This should be second page", 40, 50, myPaint);
        myPdfDocument.finishPage(myPage2);

        File file = new File(Environment.getExternalStorageDirectory(), "/SecondButtonPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();
    }


    @OnClick(R.id.button3)
    public void onViewClicked() {
        try {
            createCustomerInfoPdf();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCustomerInfoPdf() throws IOException {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage1.getCanvas();

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(12.0f);
        canvas.drawText("Masum Enterprise",myPageInfo.getPageHeight()/2,30,paint);

        paint.setTextSize(6.0f);
        paint.setColor(Color.rgb(122,119,119));
        canvas.drawText("Ramchandrapur, Muradnagar, Cumilla",myPageInfo.getPageHeight()/2,40,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(9.0f);
        paint.setColor(Color.rgb(122,118,118));
        canvas.drawText("Customer Information",10,70,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(8.0f);
        paint.setColor(Color.BLACK);
        int startXpos = 10;
        int endXpos = myPageInfo.getPageWidth()-10;
        int startYpos = 100;
        for (int i =0; i<infoArray.length;i++)
        {
            canvas.drawText(infoArray[i],startXpos,startYpos,paint);
            canvas.drawLine(startXpos,startYpos+3,endXpos,startYpos+3,paint);
            startYpos+=20;
        }
        canvas.drawLine(80,92,80,190,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(10,200,myPageInfo.getPageWidth()-10,300,paint);
        canvas.drawLine(85,200,85,300,paint);
        canvas.drawLine(163,200,163,300,paint);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("Photo",35,250,paint);
        canvas.drawText("Photo",110,250,paint);
        canvas.drawText("Photo",190,250,paint);

        canvas.drawText("Note",10,320,paint);
        canvas.drawLine(35,325,myPageInfo.getPageWidth()-10,325,paint);
        canvas.drawLine(10,345,myPageInfo.getPageWidth()-10,345,paint);
        canvas.drawLine(10,345,myPageInfo.getPageWidth()-10,345,paint); //365

        myPdfDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/CustomerInfoPdf.pdf");
        myPdfDocument.writeTo(new FileOutputStream(file));
        myPdfDocument.close();
    }
}