package com.aml.soap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    public static String MAIN_URL = "https://myserver.org:7445/sub/myfile.asmx";
    public static final String MAIN_URL_HOST = "myserver.org";
    public static final int MAIN_URL_PORT = 7445;
    public static final String MAIN_URL_FILE = "/sub/myfile.asmx";
    public static int TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.callApi).setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please Wait");
            dialog.show();
            new Thread(() -> {
                String res = dccInfoServiceNew();
                runOnUiThread(() -> {
                    dialog.dismiss();
                    TextView textView = findViewById(R.id.result);
                    textView.setText(res);
                });
            }).start();
        });
    }


    public String dccInfoServiceNew() {
        StringBuilder builder = new StringBuilder();
        try {
            String SOAP_ACTION = "http://server.org/TerminalLookUpTxn";
            String OPERATION_NAME = "TerminalLookUpTxn";
            String WSDL_TARGET_NAMESPACE = "http://server.org/";

            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
            SoapObject saleReq = new SoapObject("", "TerminalLookUpReq");
            saleReq.addProperty("Mode", String.valueOf(3));
            saleReq.addProperty("Pan", "pan");
            saleReq.addProperty("Amount", "100.0");
            saleReq.addProperty("CurrencyCode", "784");
            saleReq.addProperty("UserID", "1111");
            saleReq.addSoapObject(TerminalDetails());
            request.addSoapObject(saleReq);

            HttpsTransportSE httpTransport = new HttpsTransportSE(MAIN_URL_HOST, MAIN_URL_PORT, MAIN_URL_FILE, TIMEOUT);
            //HttpTransportSE httpTransport = new HttpTransportSE(MAIN_URL, TIMEOUT);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.encodingStyle = "utf-8";
            envelope.setOutputSoapObject(request);
            httpTransport.debug = true;

            httpTransport.call(SOAP_ACTION, envelope);
            XmlPullParserFactory pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(httpTransport.responseDump));
            builder.append(httpTransport.responseDump);


        } catch (Exception e) {
            e.printStackTrace();
            builder.append(e.toString());
        }

        return builder.toString();
    }

    private SoapObject TerminalDetails() {
        SoapObject TMDetails = new SoapObject("", "TerminalDetails");
        TMDetails.addProperty("TerminalModel", "Poynt-P61");
        TMDetails.addProperty("SerialNumber", "P4587HY55855727");
        TMDetails.addProperty("AppVersion", "V1.0");
        return TMDetails;
    }


}