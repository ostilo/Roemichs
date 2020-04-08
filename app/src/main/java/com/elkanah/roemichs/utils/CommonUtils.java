package com.elkanah.roemichs.utils;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.roemichs.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CommonUtils {
    public static boolean textIsEmpty(EditText edt) {
        if (TextUtils.isEmpty(edt.getText()))
            return true;
        else
            return false;
    }

    public static boolean textViewTextIsEmpty(TextView txt) {
        if (TextUtils.isEmpty(txt.getText()))
            return true;
        else
            return false;
    }


    public static String textOfEditText(EditText edt) {
        return edt.getText().toString().trim();
    }

    public static String textOfTextView(TextView tv) {
        return tv.getText().toString().trim();
    }

    public static boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(context.getResources().getString(R.string.app_name), text);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean stringIsEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.isEmpty()) {
            return true;
        } else if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static String toCurrencyString(int amount) {
        return "\u20a6" + amount;
    }

    public static double toCurrencyInt(String amountString) {
        return Integer.parseInt(amountString.substring(1));
    }

    public static String millisToDateString(long dateInMillis) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH);
            return formatter.format(new Date(dateInMillis));
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        boolean flag = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connectivityManager != null;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return " " + sdf.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return " " + sdf.format(new Date());
    }

    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        return " " + sdf.format(cal.getTime());
    }

    private static boolean isRunning = false;
    private static int resetInTime = 500;
    private static int counter = 0;

    public static boolean isDoubleClick() {
        boolean flag = false;
        if (isRunning) {
            if (counter == 1)
                flag = true;
        }

        counter++;

        if (!isRunning) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return flag;
    }

  /*  public void createPDF(ArrayList<String> pdfStringList)
    {
        PdfDocument doc = new PdfDocument();
        try {
            final File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test_assignment.pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();


            Paragraph p1 = new Paragraph("Hi! I am generating my first PDF using DroidText");
            Font paraFont= new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);

            Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
            Font paraFont2= new Font(Font.COURIER,14.0f,Color.GREEN);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);

            doc.add(p2);
            //set footer
            Phrase footerText = new Phrase("This is an example of a footer");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            doc.setFooter(pdfFooter);




            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            doc.close();
        }

//adding image
//  ByteArrayOutputStream stream = new ByteArrayOutputStream();
//             Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ic_launcher);
//             bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
//             Image myImg = Image.getInstance(stream.toByteArray());
//             //myImg.setAlignment(Image.MIDDLE);
//             add image to document
//             doc.add(myImg);

//should be
//Image myImg = Image.getInstance(stream.toByteArray());
//doc.add(myImg);
    }*/

  /*  private void savePdf() {
        //create object of Document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            //open the document for writing
            mDoc.open();
            //get text from EditText i.e. mTextEt
            String mText = mTextEt.getText().toString();

            //add author of the document (optional)
            mDoc.addAuthor("Atif Pervaiz");

            //add paragraph to the document
            mDoc.add(new Paragraph(mText));

            //close the document
            mDoc.close();
            //show message that file is saved, it will show file name and file path too
            Toast.makeText(this, mFileName +".pdf\nis saved to\n"+ mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //if any thing goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
*/

    public static void generatePDF(RecyclerView view, Context context) {
        RecyclerView.Adapter adapter = view.getAdapter();
        //for grid layout, add this
//    mRecyclerView = findViewById(R.id.rvNumbers);
//    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//    List<String> list = new ArrayList(); list.add("A"); list.add("B"); list.add("C");
//    MyAdapter myAdapter = new MyAdapter(this, list);
//    mRecyclerView.setAdapter(myAdapter);
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            com.itextpdf.text.Document document = new Document(PageSize.A4);
            final File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test_assignment.pdf");
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < size; i++) {
                try {
                    //Adding the content to the document
                    Bitmap bmp = bitmaCache.get(String.valueOf(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(stream.toByteArray());
                    image.scalePercent(70);
                    image.setAlignment(Image.LEFT);
                    if (!document.isOpen()) {
                        document.open();
                    }
                    document.add(image);

                } catch (Exception ex) {
                    Toast.makeText(context, "TAG-ORDER PRINT ERROR " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if (document.isOpen()) {
                document.close();
            }
            Toast.makeText(context, "Saved to: "+ file.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openFile(File url, Context context) {
        try {
            Uri uri = Uri.fromFile(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip")) {
                // ZIP file
                intent.setDataAndType(uri, "application/zip");
            } else if (url.toString().contains(".rar")) {
                // RAR file
                intent.setDataAndType(uri, "application/x-rar-compressed");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                intent.setDataAndType(uri, "*/*");
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

//    public static void setAllFileIntent(Intent intent){
//        intent.setDataAndType(uri, "application/msword");
//        // PDF file
//        intent.setDataAndType(uri, "application/pdf");
//        // Powerpoint file
//        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
//        // Excel file
//        intent.setDataAndType(uri, "application/vnd.ms-excel");
//        // ZIP file
//        intent.setDataAndType(uri, "application/zip");
//        // RAR file
//        intent.setDataAndType(uri, "application/x-rar-compressed");
//        // RTF file
//        intent.setDataAndType(uri, "application/rtf");
//        // WAV audio file
//        intent.setDataAndType(uri, "audio/x-wav");
//        // GIF file
//        intent.setDataAndType(uri, "image/gif");
//        // JPG file
//        intent.setDataAndType(uri, "image/jpeg");
//        // Text file
//        intent.setDataAndType(uri, "text/plain");
//        // Video files
//        intent.setDataAndType(uri, "video/*");
//        intent.setDataAndType(uri, "*/*");
//    }

}
