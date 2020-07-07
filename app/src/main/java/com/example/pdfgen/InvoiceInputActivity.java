package com.example.pdfgen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvoiceInputActivity extends AppCompatActivity {

    @BindView(R.id.nameEdt)
    TextInputLayout nameEdt;
    @BindView(R.id.phoneEdt)
    TextInputLayout phoneEdt;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.input1Edt)
    TextInputLayout input1Edt;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.input2Edt)
    TextInputLayout input2Edt;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_input);
        ButterKnife.bind(this);


    }

    @OnClick( R.id.btnSubmit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                try {
                    createInvoice();
                    Toast.makeText(this, "Generated....", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void createInvoice() throws IOException {
        int pageWidth = 1200;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dp);
        Bitmap bmpScale = Bitmap.createScaledBitmap(bmp, 1200, 518, false);

        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage1.getCanvas();

        canvas.drawBitmap(bmpScale,0,0,paint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        titlePaint.setTextSize(70);
        canvas.drawText("This is tile",pageWidth/2,270,titlePaint);

        paint.setColor(Color.rgb(0,113,188));
        paint.setTextSize(30f);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Orden de pedido",1160,40,paint);
        canvas.drawText("N0. 000001",1160,80,paint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
        titlePaint.setTextSize(70);
        canvas.drawText("Invoice",pageWidth/2,500,titlePaint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(35f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Customer Name: "+nameEdt.getEditText().getText().toString(),20,590,paint);
        canvas.drawText("Contact No. : "+phoneEdt.getEditText().getText().toString(),20,640,paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(35f);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Invoice No. 221569",pageWidth-20,590,paint);

        //main
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20,760,pageWidth-20,860,paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Sl. No.",40,830,paint);
        canvas.drawText("Item Description",200,830,paint);
        canvas.drawText("Price",700,830,paint);
        canvas.drawText("Qty",900,830,paint);
        canvas.drawText("Total",1050,830,paint);

        canvas.drawLine(180,790,180,840, paint);
        canvas.drawLine(680,790,680,840, paint);
        canvas.drawLine(880,790,880,840, paint);
        canvas.drawLine(1030,790,1030,840, paint);

        float total1 = 0;

        canvas.drawText("1",40,950,paint);
        canvas.drawText("Apple juice",200,950,paint);
        canvas.drawText("100",700,950,paint);
        canvas.drawText(input1Edt.getEditText().getText().toString(),900,950,paint);
        total1 = Float.parseFloat(input1Edt.getEditText().getText().toString());
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf(total1),pageWidth-40,950,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("2",40,1050,paint);
        canvas.drawText("Mango juice",200,1050,paint);
        canvas.drawText("700",700,1050,paint);
        canvas.drawText(input1Edt.getEditText().getText().toString(),900,1050,paint);
        total1 = Float.parseFloat(input1Edt.getEditText().getText().toString());
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.valueOf(total1),pageWidth-40,1050,paint);

        canvas.drawLine(680,1200,pageWidth-20,1200,paint);
        canvas.drawText("Sub Total",900,1250,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("1000",pageWidth-40,1250,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.rgb(247,147,30));
        canvas.drawRect(680,1350,pageWidth-20,1450,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Total:",700,1415,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("25000",pageWidth-40,1415,paint);



        myPdfDocument.finishPage(myPage1);

//        File file = new File(Environment.getExternalStorageDirectory(), "/CustomerInvoicePdf.pdf");
//        myPdfDocument.writeTo(new FileOutputStream(file));
//        myPdfDocument.close();

        String folder = Environment.getExternalStorageDirectory().getPath() + "/documents";
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        String path = folder + "/Calcuradora_" + System.currentTimeMillis() + ".pdf";
        File myFile = new File(path);
        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
        myPdfDocument.writeTo(fOut);
        myPdfDocument.close();
        myOutWriter.close();
        fOut.close();
        Toast.makeText(getBaseContext(), "File Saved on " + path, Toast.LENGTH_LONG).show();
        openPdfViewer(myFile);

    }

    private void openPdfViewer(File file) { //need to add provider in manifest and filepaths.xml
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider", file), "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 101);
    }
}