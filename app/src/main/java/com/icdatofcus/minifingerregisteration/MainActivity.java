package com.icdatofcus.minifingerregisteration;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.testing.aramis.sourceafis.FingerprintMatcher;
import com.testing.aramis.sourceafis.FingerprintTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import asia.kanopi.fingerscan.Fingerprint;
import asia.kanopi.fingerscan.Status;

public class MainActivity extends AppCompatActivity {


    EditText Name, Email, Username, Password, Accountbalance, Sex, Dept, Level, DOB;

    ImageView LeftThumbImage, LeftIndexImage, RightThumbImage, RightIndexImage;

    Button LeftThumbCapture, LeftIndexCapture, RightThumbCapture, RightIndexCapture;
    Button SavetoDB;

    TextView ConfirmFingerMatch, ScannerStatus;

    String UserEmail;

    TextView Strin;

    String LeftThumbFirst, LeftThumbConfirmed;
    String LeftIndexFirst, LeftIndexConfirmed;
    String RightThumbFirst, RightThumbConfirmed;
    String RightIndexFirst, RightIndexConfirmed;
    String register_with_finger = "http://128.0.1.2/register_with_fingerprint_and_rest.php";

    Fingerprint fingerprint;

    AlertDialog.Builder quantumElevation;

    byte[] imgg;
    Bitmap bitmapLeftThumb, bitmapLeftIndex, bitmapRightThumb, bitmapRightIndex;
    private static final int SCAN_LEFT_THUMB_FINGER = 0;
    private static final int SCAN_LEFT_INDEX_FINGER = 1;
    private static final int SCAN_RIGHT_THUMB_FINGER = 2;
    private static final int SCAN_RIGHT_INDEX_FINGER = 3;
    private byte[] bytesObjectLeftThumb;
    private byte[] bytesObjectLeftIndex;
    private byte[] bytesObjectRightThumb;
    private byte[] bytesObjectRightIndex;

    StringBuilder sb;


    private FingerprintTemplate probeLeftThumbTemplate, candidateLeftThumbTemplate;
    private FingerprintTemplate probeLeftIndexTemplate, candidateLeftIndexTemplate;
    private FingerprintTemplate probeRightThumbTemplate, candidateRightThumbTemplate;
    private FingerprintTemplate probeRightIndexTemplate, candidateRightIndexTemplate;

    private FingerprintMatcher pleaseMatch = new FingerprintMatcher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Username = findViewById(R.id.user_name);
        Password = findViewById(R.id.password);
        Accountbalance = findViewById(R.id.accountbalance);
        Sex = findViewById(R.id.sex);
        Dept = findViewById(R.id.dept);
        Level = findViewById(R.id.level);
        DOB = findViewById(R.id.dob);

        LeftThumbImage = findViewById(R.id.leftThumb_fp);
        LeftIndexImage = findViewById(R.id.leftIndex_fp);
        RightThumbImage = findViewById(R.id.rightThumb_fp);
        RightIndexImage = findViewById(R.id.rightIndex_fp);

        LeftThumbCapture = findViewById(R.id.left_Thumb_Capture);
        LeftIndexCapture = findViewById(R.id.left_Index_Capture);
        RightThumbCapture = findViewById(R.id.right_Thumb_Capture);
        RightIndexCapture = findViewById(R.id.right_Index_Capture);

        SavetoDB = findViewById(R.id.saveDB);

        ConfirmFingerMatch = findViewById(R.id.fingerMatch);
        ScannerStatus = findViewById(R.id.scannerStatus);
        Strin = findViewById(R.id.strin);
        Strin.setTextIsSelectable(true);
        Strin.setSelectAllOnFocus(true);
        Strin.requestFocus();

        fingerprint = new Fingerprint();

        quantumElevation = new AlertDialog.Builder(MainActivity.this);

    }




    public void onBackPressed() {
        backButtonHandler();
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);
        alertDialog.setTitle("Leave Activity?");
        alertDialog.setMessage("Are you sure you want to leave the Enrollment Activity?");
        alertDialog.setPositiveButton("YES",
                (dialog, which) -> finish());
        alertDialog.setNegativeButton("NO",
                (dialog, which) -> {
                    dialog.cancel();
                });
        alertDialog.show();
    }



    //    @Override
//    protected void onStart() {
//
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        fingerprint.turnOffReader();
//        super.onStop();
//    }

    Handler printLeftThumbHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);


            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapLeftThumb = BitmapFactory.decodeByteArray(image, 0, image.length);
                LeftThumbImage.setImageBitmap(bitmapLeftThumb);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured Left Thumb");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmapLeftThumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytesObjectLeftThumb = baos.toByteArray();
                LeftThumbFirst = Base64.encodeToString(bytesObjectLeftThumb, Base64.DEFAULT);

//                probeLeftThumbTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectLeftThumb);



//                BigInteger biggi = new BigInteger(bytesObjectLeftThumb);


//                Strin.setText(LeftThumbFirst);

//                Strin.setText("okay");



//                sb = new StringBuilder(bytesObjectLeftThumb.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObjectLeftThumb.length; i++ ) {
//                    sb.append((bytesObjectLeftThumb[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }

            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateLeftThumbHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan Left Thumb Finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("Error");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    /**

     Handler printAnotherLeftThumbHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    byte [] image;
    String errorMessage = "empty";
    int status = msg.getData().getInt("status");
    Intent intent = new Intent();
    intent.putExtra("status", status);


    if (status == Status.SUCCESS) {



    image = msg.getData().getByteArray("img");
    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
    LeftThumbImage.setImageBitmap(bitmap);
    intent.putExtra("img", image);
    ConfirmFingerMatch.setText("Fingerprint captured wella");

    //                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    bytesObject = baos.toByteArray();
    //                LeftThumbFirst = Base64.encodeToString(b, Base64.DEFAULT);

    candidateLeftThumbTemplate = new FingerprintTemplate()
    .dpi(500).create(bytesObject);



    BigInteger biggi = new BigInteger(bytesObject);


    Strin.setText(LeftThumbFirst);

    Strin.setText("okay");



    sb = new StringBuilder(bytesObject.length * Byte.SIZE);
    for( int i = 0; i < Byte.SIZE * bytesObject.length; i++ ) {
    sb.append((bytesObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    sb.toString();
    }

    } else {
    errorMessage = msg.getData().getString("errorMessage");
    intent.putExtra("errorMessage", errorMessage);
    }
    // setResult(RESULT_OK, intent);
    //  finish();
    }
    };

     Handler updateAnotherLeftThumbHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    int status = msg.getData().getInt("status");

    switch (status) {
    case Status.INITIALISED:
    ScannerStatus.setText("Setting up reader");
    break;
    case Status.SCANNER_POWERED_ON:
    ScannerStatus.setText("Reader powered on");
    break;
    case Status.READY_TO_SCAN:
    ScannerStatus.setText("Ready to scan finger");
    break;
    case Status.FINGER_DETECTED:
    ScannerStatus.setText("Finger Detected");
    break;
    case Status.RECEIVING_IMAGE:
    ScannerStatus.setText("Receiving Image");
    break;
    case Status.FINGER_LIFTED:
    ScannerStatus.setText("Finger has been lifted off reader");
    break;
    case Status.SCANNER_POWERED_OFF:
    ScannerStatus.setText("Reader is off");
    break;
    case Status.SUCCESS:
    ScannerStatus.setText("Fingerprint Successfully Captured");
    break;
    case Status.ERROR:
    ScannerStatus.setText("Erroring");
    break;
    default:
    ScannerStatus.setText(String.valueOf(status));
    break;
    }

    }
    };

     */

    Handler printLeftIndexHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);



            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapLeftIndex = BitmapFactory.decodeByteArray(image, 0, Objects.requireNonNull(image).length);
                LeftIndexImage.setImageBitmap(bitmapLeftIndex);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured Left Index");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

//
//                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
//                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


                //   try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmapLeftIndex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytesObjectLeftIndex = baos.toByteArray();
                LeftIndexFirst = Base64.encodeToString(bytesObjectLeftIndex, Base64.DEFAULT);

//                probeLeftIndexTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectLeftIndex);



//                BigInteger biggi = new BigInteger(bytesObjectLeftIndex);


//                Makes it slower with 4 secs we could use a spinnerview here
//                LeftThumbFirst = biggi.toString();

//                Strin.setText(Arrays.toString(bytesObject));

//                Strin.setText("okay");



//                sb = new StringBuilder(bytesObjectLeftIndex.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObjectLeftIndex.length; i++ ) {
//                    sb.append((bytesObjectLeftIndex[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }





//                    return LeftThumbFirst;
//                } catch (NullPointerException e) {
//                    return null;
//                } catch (OutOfMemoryError e) {
//                    return null;
//                }

                //  To Decode
//                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
//                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
//                RightThumbImage.setImageBitmap(wide);



            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateLeftIndexHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan Left Index Finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("Error");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    /**

     Handler printAnotherLeftIndexHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    byte [] image;
    String errorMessage = "empty";
    int status = msg.getData().getInt("status");
    Intent intent = new Intent();
    intent.putExtra("status", status);



    if (status == Status.SUCCESS) {



    image = msg.getData().getByteArray("img");
    bitmapAnother = BitmapFactory.decodeByteArray(image, 0, image.length);
    LeftIndexImage.setImageBitmap(bitmapAnother);
    intent.putExtra("img", image);
    ConfirmFingerMatch.setText("Fingerprint captured");

    //                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

    //
    //                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
    //                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


    //   try {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmapAnother.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byteAnotherObject = baos.toByteArray();
    LeftIndexFirst = Base64.encodeToString(byteAnotherObject, Base64.DEFAULT);

    candidateLeftIndexTemplate = new FingerprintTemplate()
    .dpi(500).create(byteAnotherObject);



    BigInteger biggi = new BigInteger(byteAnotherObject);


    //                Makes it slower with 4 secs we could use a spinnerview here
    //                LeftThumbFirst = biggi.toString();

    //                Strin.setText(Arrays.toString(bytesObject));

    Strin.setText("okay");



    sb = new StringBuilder(byteAnotherObject.length * Byte.SIZE);
    for( int i = 0; i < Byte.SIZE * byteAnotherObject.length; i++ ) {
    sb.append((byteAnotherObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    sb.toString();
    }





    //                    return LeftThumbFirst;
    //                } catch (NullPointerException e) {
    //                    return null;
    //                } catch (OutOfMemoryError e) {
    //                    return null;
    //                }

    //  To Decode
    //                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
    //                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
    //                RightThumbImage.setImageBitmap(wide);



    } else {
    errorMessage = msg.getData().getString("errorMessage");
    intent.putExtra("errorMessage", errorMessage);
    }
    // setResult(RESULT_OK, intent);
    //  finish();
    }
    };

     Handler updateAnotherLeftIndexHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    int status = msg.getData().getInt("status");

    switch (status) {
    case Status.INITIALISED:
    ScannerStatus.setText("Setting up reader");
    break;
    case Status.SCANNER_POWERED_ON:
    ScannerStatus.setText("Reader powered on");
    break;
    case Status.READY_TO_SCAN:
    ScannerStatus.setText("Ready to scan finger");
    break;
    case Status.FINGER_DETECTED:
    ScannerStatus.setText("Finger Detected");
    break;
    case Status.RECEIVING_IMAGE:
    ScannerStatus.setText("Receiving Image");
    break;
    case Status.FINGER_LIFTED:
    ScannerStatus.setText("Finger has been lifted off reader");
    break;
    case Status.SCANNER_POWERED_OFF:
    ScannerStatus.setText("Reader is off");
    break;
    case Status.SUCCESS:
    ScannerStatus.setText("Fingerprint Successfully Captured");
    break;
    case Status.ERROR:
    ScannerStatus.setText("Erroring");
    break;
    default:
    ScannerStatus.setText(String.valueOf(status));
    break;
    }

    }
    };

     */

    Handler printRightThumbHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);


            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapRightThumb = BitmapFactory.decodeByteArray(image, 0, image.length);
                RightThumbImage.setImageBitmap(bitmapRightThumb);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured Right Thumb");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bitmapRightThumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
                bytesObjectRightThumb = baos.toByteArray();
                RightThumbFirst = Base64.encodeToString(bytesObjectRightThumb, Base64.DEFAULT);

//                probeRightThumbTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectRightThumb);



//                BigInteger biggi = new BigInteger(bytesObjectRightThumb);


//                Strin.setText(RightThumbFirst);

//                Strin.setText("okay");



//                sb = new StringBuilder(bytesObjectRightThumb.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObjectRightThumb.length; i++ ) {
//                    sb.append((bytesObjectRightThumb[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }

            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateRightThumbHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan Right Thumb Finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("No Fingerprint Reader Inserted");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };

    /**

     Handler printAnotherRightThumbHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    byte [] image;
    String errorMessage = "empty";
    int status = msg.getData().getInt("status");
    Intent intent = new Intent();
    intent.putExtra("status", status);


    if (status == Status.SUCCESS) {



    image = msg.getData().getByteArray("img");
    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
    LeftThumbImage.setImageBitmap(bitmap);
    intent.putExtra("img", image);
    ConfirmFingerMatch.setText("Fingerprint captured wella");

    //                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    bytesObject = baos.toByteArray();
    //                LeftThumbFirst = Base64.encodeToString(b, Base64.DEFAULT);

    candidateRightThumbTemplate = new FingerprintTemplate()
    .dpi(500).create(bytesObject);



    BigInteger biggi = new BigInteger(bytesObject);


    Strin.setText(LeftThumbFirst);

    Strin.setText("okay");



    sb = new StringBuilder(bytesObject.length * Byte.SIZE);
    for( int i = 0; i < Byte.SIZE * bytesObject.length; i++ ) {
    sb.append((bytesObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    sb.toString();
    }

    } else {
    errorMessage = msg.getData().getString("errorMessage");
    intent.putExtra("errorMessage", errorMessage);
    }
    // setResult(RESULT_OK, intent);
    //  finish();
    }
    };

     Handler updateAnotherRightThumbHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    int status = msg.getData().getInt("status");

    switch (status) {
    case Status.INITIALISED:
    ScannerStatus.setText("Setting up reader");
    break;
    case Status.SCANNER_POWERED_ON:
    ScannerStatus.setText("Reader powered on");
    break;
    case Status.READY_TO_SCAN:
    ScannerStatus.setText("Ready to scan finger");
    break;
    case Status.FINGER_DETECTED:
    ScannerStatus.setText("Finger Detected");
    break;
    case Status.RECEIVING_IMAGE:
    ScannerStatus.setText("Receiving Image");
    break;
    case Status.FINGER_LIFTED:
    ScannerStatus.setText("Finger has been lifted off reader");
    break;
    case Status.SCANNER_POWERED_OFF:
    ScannerStatus.setText("Reader is off");
    break;
    case Status.SUCCESS:
    ScannerStatus.setText("Fingerprint Successfully Captured");
    break;
    case Status.ERROR:
    ScannerStatus.setText("Erroring");
    break;
    default:
    ScannerStatus.setText(String.valueOf(status));
    break;
    }

    }
    };

     */

    Handler printRightIndexHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            byte [] image;
            String errorMessage = "empty";
            int status = msg.getData().getInt("status");
            Intent intent = new Intent();
            intent.putExtra("status", status);



            if (status == Status.SUCCESS) {



                image = msg.getData().getByteArray("img");
                bitmapRightIndex = BitmapFactory.decodeByteArray(image, 0, image.length);
                RightIndexImage.setImageBitmap(bitmapRightIndex);
                intent.putExtra("img", image);
                ConfirmFingerMatch.setText("Fingerprint captured Right Index");

//                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

//
//                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
//                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


                //   try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapRightIndex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bytesObjectRightIndex = baos.toByteArray();
                RightIndexFirst = Base64.encodeToString(bytesObjectRightIndex, Base64.DEFAULT);

//                probeRightIndexTemplate = new FingerprintTemplate()
//                        .dpi(500).create(bytesObjectRightIndex);



//                BigInteger biggi = new BigInteger(bytesObjectRightIndex);


//                Makes it slower with 4 secs we could use a spinnerview here
//                LeftThumbFirst = biggi.toString();

//                Strin.setText(Arrays.toString(bytesObject));

//                Strin.setText("okay");



//                sb = new StringBuilder(bytesObjectRightIndex.length * Byte.SIZE);
//                for( int i = 0; i < Byte.SIZE * bytesObjectRightIndex.length; i++ ) {
//                    sb.append((bytesObjectRightIndex[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
//                    sb.toString();
//                }





//                    return LeftThumbFirst;
//                } catch (NullPointerException e) {
//                    return null;
//                } catch (OutOfMemoryError e) {
//                    return null;
//                }

                //  To Decode
//                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
//                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
//                RightThumbImage.setImageBitmap(wide);



            } else {
                errorMessage = msg.getData().getString("errorMessage");
                intent.putExtra("errorMessage", errorMessage);
            }
            // setResult(RESULT_OK, intent);
            //  finish();
        }
    };

    Handler updateRightIndexHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int status = msg.getData().getInt("status");

            switch (status) {
                case Status.INITIALISED:
                    ScannerStatus.setText("Setting up reader");
                    break;
                case Status.SCANNER_POWERED_ON:
                    ScannerStatus.setText("Reader powered on");
                    break;
                case Status.READY_TO_SCAN:
                    ScannerStatus.setText("Ready to scan Right Index Finger");
                    break;
                case Status.FINGER_DETECTED:
                    ScannerStatus.setText("Finger Detected");
                    break;
                case Status.RECEIVING_IMAGE:
                    ScannerStatus.setText("Receiving Image");
                    break;
                case Status.FINGER_LIFTED:
                    ScannerStatus.setText("Finger has been lifted off reader");
                    break;
                case Status.SCANNER_POWERED_OFF:
                    ScannerStatus.setText("Reader is off");
                    break;
                case Status.SUCCESS:
                    ScannerStatus.setText("Fingerprint Successfully Captured");
                    break;
                case Status.ERROR:
                    ScannerStatus.setText("No Fingerprint Reader Inserted");
                    break;
                default:
                    ScannerStatus.setText(String.valueOf(status));
                    break;
            }

        }
    };


    /**

     Handler printAnotherRightIndexHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    byte [] image;
    String errorMessage = "empty";
    int status = msg.getData().getInt("status");
    Intent intent = new Intent();
    intent.putExtra("status", status);



    if (status == Status.SUCCESS) {



    image = msg.getData().getByteArray("img");
    bitmapAnother = BitmapFactory.decodeByteArray(image, 0, image.length);
    LeftIndexImage.setImageBitmap(bitmapAnother);
    intent.putExtra("img", image);
    ConfirmFingerMatch.setText("Fingerprint captured");

    //                LeftThumbFirst = ConfirmFingerMatch.getText().toString();

    //
    //                byte[] decodedByte = Base64.decode(LeftThumbFirst, 0);
    //                bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);


    //   try {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmapAnother.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byteAnotherObject = baos.toByteArray();
    LeftIndexFirst = Base64.encodeToString(byteAnotherObject, Base64.DEFAULT);

    candidateRightIndexTemplate = new FingerprintTemplate()
    .dpi(500).create(byteAnotherObject);



    BigInteger biggi = new BigInteger(byteAnotherObject);


    //                Makes it slower with 4 secs we could use a spinnerview here
    //                LeftThumbFirst = biggi.toString();

    //                Strin.setText(Arrays.toString(bytesObject));

    Strin.setText("okay");



    sb = new StringBuilder(byteAnotherObject.length * Byte.SIZE);
    for( int i = 0; i < Byte.SIZE * byteAnotherObject.length; i++ ) {
    sb.append((byteAnotherObject[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    sb.toString();
    }





    //                    return LeftThumbFirst;
    //                } catch (NullPointerException e) {
    //                    return null;
    //                } catch (OutOfMemoryError e) {
    //                    return null;
    //                }

    //  To Decode
    //                byte [] cake = Base64.decode(LeftThumbConfirmed, Base64.DEFAULT);
    //                Bitmap wide = BitmapFactory.decodeByteArray(cake, 0, cake.length);
    //                RightThumbImage.setImageBitmap(wide);



    } else {
    errorMessage = msg.getData().getString("errorMessage");
    intent.putExtra("errorMessage", errorMessage);
    }
    // setResult(RESULT_OK, intent);
    //  finish();
    }
    };

     Handler updateAnotherRightIndexHandler = new Handler(Looper.getMainLooper()) {
    @Override
    public void handleMessage(Message msg) {
    int status = msg.getData().getInt("status");

    switch (status) {
    case Status.INITIALISED:
    ScannerStatus.setText("Setting up reader");
    break;
    case Status.SCANNER_POWERED_ON:
    ScannerStatus.setText("Reader powered on");
    break;
    case Status.READY_TO_SCAN:
    ScannerStatus.setText("Ready to scan finger");
    break;
    case Status.FINGER_DETECTED:
    ScannerStatus.setText("Finger Detected");
    break;
    case Status.RECEIVING_IMAGE:
    ScannerStatus.setText("Receiving Image");
    break;
    case Status.FINGER_LIFTED:
    ScannerStatus.setText("Finger has been lifted off reader");
    break;
    case Status.SCANNER_POWERED_OFF:
    ScannerStatus.setText("Reader is off");
    break;
    case Status.SUCCESS:
    ScannerStatus.setText("Fingerprint Successfully Captured");
    break;
    case Status.ERROR:
    ScannerStatus.setText("Erroring");
    break;
    default:
    ScannerStatus.setText(String.valueOf(status));
    break;
    }

    }
    };

     */


    public void  LeftThumbCapture (View view) {

//        fingerprint.scan(this, printLeftThumbHandler, updateLeftThumbHandler);

//        Intent intent = new Intent(this, ScanActivity.class);
//        startActivityForResult(intent, SCAN_LEFT_THUMB_FINGER);
    }

    public void  LeftIndexCapture (View view) {

//        fingerprint.scan(this, printLeftIndexHandler, updateLeftIndexHandler);

//        Intent intent = new Intent(this, ScanActivity.class);
//        startActivityForResult(intent, SCAN_LEFT_INDEX_FINGER);
    }

    public void  RightThumbCapture (View view) {

        fingerprint.scan(this, printRightThumbHandler, updateRightThumbHandler);

//        Intent intent = new Intent(this, ScanActivity.class);
//        startActivityForResult(intent, SCAN_RIGHT_THUMB_FINGER);
    }

    public void  RightIndexCapture (View view) {

        fingerprint.scan(this, printRightIndexHandler, updateRightIndexHandler);

//        Intent intent = new Intent(this, ScanActivity.class);
//        startActivityForResult(intent, SCAN_RIGHT_INDEX_FINGER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int status;
        String errorMesssage;
        switch(requestCode) {
            case (SCAN_LEFT_THUMB_FINGER) : {
                if (resultCode == RESULT_OK) {
                    byte [] image;
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS) {
                        ConfirmFingerMatch.setText("Fingerprint captured Left Thumb");
                        image = data.getByteArrayExtra("img");
                        bitmapLeftThumb = BitmapFactory.decodeByteArray(image, 0, image.length);
                        LeftThumbImage.setImageBitmap(bitmapLeftThumb);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        bitmapLeftThumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        bytesObjectLeftThumb = baos.toByteArray();
                        LeftThumbFirst = Base64.encodeToString(bytesObjectLeftThumb, Base64.DEFAULT);

//                        probeLeftThumbTemplate = new FingerprintTemplate()
//                                .dpi(500).create(bytesObjectLeftThumb);
                    } else {
                        errorMesssage = data.getStringExtra("errorMessage");
                        ConfirmFingerMatch.setText("-- Error: " +  errorMesssage + " --");
                    }
                }
                break;
            }
            case (SCAN_LEFT_INDEX_FINGER) : {
                if (resultCode == RESULT_OK) {
                    byte [] image_two;
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS) {
                        ConfirmFingerMatch.setText("Fingerprint captured Left Index");
                        image_two = data.getByteArrayExtra("img");
                        bitmapLeftIndex = BitmapFactory.decodeByteArray(image_two, 0, image_two.length);
                        LeftIndexImage.setImageBitmap(bitmapLeftIndex);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        bitmapLeftIndex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        bytesObjectLeftIndex = baos.toByteArray();
                        LeftThumbFirst = Base64.encodeToString(bytesObjectLeftIndex, Base64.DEFAULT);

//                        probeLeftThumbTemplate = new FingerprintTemplate()
//                                .dpi(500).create(bytesObjectLeftThumb);
                    } else {
                        errorMesssage = data.getStringExtra("errorMessage");
                        ConfirmFingerMatch.setText("-- Error: " +  errorMesssage + " --");
                    }
                }
                break;
            }
            case (SCAN_RIGHT_THUMB_FINGER) : {
                if (resultCode == RESULT_OK) {
                    byte [] image_three;
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS) {
                        ConfirmFingerMatch.setText("Fingerprint captured Right Thumb");
                        image_three = data.getByteArrayExtra("img");
                        bitmapRightThumb = BitmapFactory.decodeByteArray(image_three, 0, image_three.length);
                        RightThumbImage.setImageBitmap(bitmapRightThumb);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        bitmapRightThumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        bytesObjectRightThumb = baos.toByteArray();
                        RightThumbFirst = Base64.encodeToString(bytesObjectRightThumb, Base64.DEFAULT);

//                        probeLeftThumbTemplate = new FingerprintTemplate()
//                                .dpi(500).create(bytesObjectLeftThumb);
                    } else {
                        errorMesssage = data.getStringExtra("errorMessage");
                        ConfirmFingerMatch.setText("-- Error: " +  errorMesssage + " --");
                    }
                }
                break;
            }
            case (SCAN_RIGHT_INDEX_FINGER) : {
                if (resultCode == RESULT_OK) {
                    byte [] image_four;
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS) {
                        ConfirmFingerMatch.setText("Fingerprint captured Right Index");
                        image_four = data.getByteArrayExtra("img");
                        bitmapRightIndex = BitmapFactory.decodeByteArray(image_four, 0, image_four.length);
                        RightIndexImage.setImageBitmap(bitmapRightIndex);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        bitmapRightIndex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        bytesObjectRightIndex = baos.toByteArray();
                        RightIndexFirst = Base64.encodeToString(bytesObjectRightIndex, Base64.DEFAULT);

//                        probeLeftThumbTemplate = new FingerprintTemplate()
//                                .dpi(500).create(bytesObjectLeftThumb);
                    } else {
                        errorMesssage = data.getStringExtra("errorMessage");
                        ConfirmFingerMatch.setText("-- Error: " +  errorMesssage + " --");
                    }
                }
                break;
            }

        }
    }


    public void  saveToDB (View view) {

        UserEmail = Email.getText().toString();

        if (
//                LeftThumbImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.thumb_left).getConstantState() ||
//                LeftIndexImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.index_left).getConstantState() ||
                RightThumbImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.thumb_right).getConstantState() ||
                RightIndexImage.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.index_right).getConstantState())
            {

                Toast toast = Toast.makeText(this, "Please, capture the remaining fingerprint(s)", Toast.LENGTH_LONG);
                toast.show();
            }
            else if (!Email.getText().toString().contains(".")) {
                Toast toast = Toast.makeText(this, "The Email address is invalid. No '.' sign", Toast.LENGTH_LONG);
                toast.show();
            }
            else if (Email.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(this, "Please, the Recipient's Email address is required", Toast.LENGTH_LONG);
                toast.show();
            }

            else {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Save?");
                alertDialog.setMessage("Please, ensure the quality of the fingerprints before saving");
                alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                alertDialog.setPositiveButton("Ensured", (dialog, which) -> {
                    StringRequest Minutae = new StringRequest(Request.Method.POST, register_with_finger,
                            response -> {
                                try {
                                    JSONArray may = new JSONArray(response);
                                    JSONObject god = may.getJSONObject(0);
                                    String code = god.getString("code");
                                    switch (code) {
                                        case "email_failed":
                                            quantumElevation.setTitle("There's a mismatch somewhere");
                                            exhibitElevation(god.getString("message"));
                                            break;
                                        default:
                                            Email.setText("");
//                                            LeftThumbImage.setImageDrawable(getDrawable(R.drawable.thumb_left));
//                                            LeftIndexImage.setImageDrawable(getDrawable(R.drawable.index_left));
                                            RightThumbImage.setImageDrawable(getDrawable(R.drawable.thumb_right));
                                            RightIndexImage.setImageDrawable(getDrawable(R.drawable.index_right));


                                            Toast toast = Toast.makeText(getApplicationContext(), "Thank you, " + UserEmail + "'s fingerprints are saved", Toast.LENGTH_LONG);
                                            toast.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }, error -> {
                        try {
                            AlertDialog.Builder weightBuilder = new AlertDialog.Builder(MainActivity.this);
                            weightBuilder.setTitle("Connection disconnected");
                            weightBuilder.setMessage("You can do it. \n Swipe down from the very top and restart the wifi from its icon");
                            weightBuilder.setPositiveButton("Okay", (dialog1, which1) -> dialog1.dismiss());
                            weightBuilder.create().show();
                            error.printStackTrace();
                        } catch (Exception ignored) {

                        }

                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String, String> params = new HashMap<>();

                            params.put("email", Email.getText().toString());
//                            params.put("left_thumb_fingerprint", LeftThumbFirst);
//                            params.put("left_index_fingerprint", LeftIndexFirst);
                            params.put("right_thumb_fingerprint", RightThumbFirst);
                            params.put("right_index_fingerprint", RightIndexFirst);

//                    params.put("name", Name.getText().toString());
//                params.put("user_name", Username.getText().toString());
//                params.put("password", Password.getText().toString());

//                params.put("accountbalance", Accountbalance.getText().toString());
//                params.put("sex", Sex.getText().toString());
//                params.put("department", Dept.getText().toString());
//                params.put("level", Level.getText().toString());
//                params.put("d_o_b", DOB.getText().toString());
                            return params;
                        }
                    };
                    MyCountlesston.getmInstance(MainActivity.this).addToRequestQueue(Minutae);
                    Minutae.setRetryPolicy(new DefaultRetryPolicy(
                            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                            0,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    ));
                    dialog.dismiss();
                });

                alertDialog.create().show();

            }

    }

    private void exhibitElevation(String message) {

        quantumElevation.setMessage(message);
        quantumElevation.setPositiveButton("Okay", (dialog, which) -> Email.setText(""));
        AlertDialog alertDialog = quantumElevation.create();
        alertDialog.show();
    }


}
