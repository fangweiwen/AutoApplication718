package com.example.autoapplication.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.autoapplication.entity.SmsEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author WeiWen Fang
 * on 2019/6/5.
 */
public class SMSUtils {


    public static List<SmsEntity> getSmsInPhone(Context mContext) {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";
        final String SMS_URI_OUTBOX = "content://sms/outbox";
        final String SMS_URI_FAILED = "content://sms/failed";
        final String SMS_URI_QUEUED = "content://sms/queued";
        List<SmsEntity> list = new ArrayList<>();
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = mContext.getContentResolver().query(uri, projection, null, null, "date desc");        // 获取手机内部短信

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");

                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else if (intType == 3) {
                        strType = "草稿";
                    } else if (intType == 4) {
                        strType = "发件箱";
                    } else if (intType == 5) {
                        strType = "发送失败";
                    } else if (intType == 6) {
                        strType = "待发送列表";
                    } else if (intType == 0) {
                        strType = "所以短信";
                    } else {
                        strType = "null";
                    }
                    SmsEntity entity = new SmsEntity();
                    entity.setStrAddress(strAddress);
                    entity.setStrbody(strbody);
                    entity.setStrDate(strDate);
                    entity.setStrType(strType);
                    list.add(entity);
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                //  smsBuilder.append("no result!");
            } // end if

            //   smsBuilder.append("getSmsInPhone has executed!");

        } catch (SQLiteException ex) {
            Log.d("SQLiteException ", ex.getMessage());
        }
        Toast.makeText(mContext, list.size() + "条", Toast.LENGTH_SHORT).show();
        Log.i("TTTTTTTTTT", "list.size() = " + list.size());
        return list;
    }

    //https://blog.csdn.net/ithomer/article/details/7328321
/*    content://sms/               所有短信
    content://sms/inbox        收件箱
    content://sms/sent          已发送
    content://sms/draft         草稿
    content://sms/outbox     发件箱
    content://sms/failed       发送失败
    content://sms/queued    待发送列表

    _id => 短消息序号 如100  
    thread_id => 对话的序号 如100  
    address => 发件人地址，手机号.如+8613811810000  
    person => 发件人，返回一个数字就是联系人列表里的序号，陌生人为null  
    date => 日期  long型。如1256539465022  
    protocol => 协议 0 SMS_RPOTO, 1 MMS_PROTO   
    read => 是否阅读 0未读， 1已读   
    status => 状态 -1接收，0 complete, 64 pending, 128 failed   
    type => 类型 1是接收到的，2是已发出   
    body => 短消息内容   
    service_center => 短信服务中心号码编号。如+8613800755500  */
}
