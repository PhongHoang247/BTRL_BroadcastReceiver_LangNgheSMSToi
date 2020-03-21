package com.phong.btrl_broadcastreceiver_langnghesmstoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView txtPhone, txtTime, txtSms;
    IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            xuLyDocTinNhan(context,intent);
        }
    };

    private void xuLyDocTinNhan(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object []arrMessage = (Object[]) bundle.get("pdus");
        //ta đang test chỉ có 1 tin nhắn:
        byte []arrByteMessage = (byte[]) arrMessage[0];
        SmsMessage message = SmsMessage.createFromPdu(arrByteMessage);
        //Lấy nội dung tin nhắn:
        String noiDung = message.getMessageBody();
        //Lấy số điện thoại:
        String phone = message.getDisplayOriginatingAddress();
        //Thời gian:
        long time = message.getTimestampMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date(time);
        String sTime = sdf.format(date);
        //Show lên UI:
        txtPhone.setText(phone);
        txtTime.setText(sTime);
        txtSms.setText(noiDung);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        txtPhone = findViewById(R.id.txtPhone);
        txtTime = findViewById(R.id.txtTime);
        txtSms = findViewById(R.id.txtSms);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(smsReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsReceiver);
    }
}
