package com.yidao.module_lib.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.yidao.module_lib.entity.ContactHistoryBean;
import com.yidao.module_lib.entity.ContactPhoneBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/26
 */
public class PhoneUtils {

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    public final static String count = ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public PhoneUtils(Context context) {
        this.context = context;
    }

    //获取所有联系人
    public List<ContactPhoneBean> getPhone() {
        List<ContactPhoneBean> phoneDtos = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME, count}, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            ContactPhoneBean contactPhoneBean = new ContactPhoneBean();
            contactPhoneBean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            contactPhoneBean.setPhoneNum(cursor.getString(cursor.getColumnIndex(NUM)));
            contactPhoneBean.setContactTimes(cursor.getInt(cursor.getColumnIndex(count)));
            phoneDtos.add(contactPhoneBean);
        }
        if (cursor != null)
            cursor.close();
        return phoneDtos;
    }

    //模糊搜索联系人
    public ArrayList<ContactPhoneBean> queryContact(String text) {
        if (TextUtils.isEmpty(text)) return null;
        String name = null, number = null, email = null;
        ContentResolver cr = context.getContentResolver();
        String projection[] = {NAME,
                NUM
        };
        Cursor cursor = null;
        if (TextUtils.isDigitsOnly(text)) {
            cursor = cr.query(
                    phoneUri,
                    projection, NUM
                            + " like " + "'%"
                            + text + "%'", null,
                    null);
        } else {
            cursor = cr.query(
                    phoneUri,
                    projection, NAME
                            + " like " + "'%"
                            + text + "%'", null,
                    null);
        }


        Cursor cursor2 = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                projection, ContactsContract.Contacts.DISPLAY_NAME
                        + " like " + "'%"
                        + text + "%'", null,
                null);
        ArrayList<ContactPhoneBean> contactPhoneBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            ContactPhoneBean contactPhoneBean = new ContactPhoneBean();
            name = cursor.getString(cursor
                    .getColumnIndex(NAME));
            number = cursor.getString(cursor
                    .getColumnIndex(NUM));
            while (cursor2.moveToNext()) {
                email = cursor2.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            }
            contactPhoneBean.setName(name);
            contactPhoneBean.setPhoneNum(number);
            contactPhoneBeans.add(contactPhoneBean);
            LogUtils.d("------查询到的此条记录为：--------" + name + "   " + number + "  " + email
            );
        }

        cursor.close();

        return contactPhoneBeans;
    }


    private Uri callUri = CallLog.Calls.CONTENT_URI;
    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型

    //获取通话记录
    @SuppressLint("MissingPermission")
    public ArrayList<ContactHistoryBean> getContactHistory() {
        ArrayList<ContactHistoryBean> contactHistoryBeans = new ArrayList<>();
        ArrayList<String> phoneNumer = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        LogUtils.d("cursor count:" + cursor.getCount());
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));  //姓名
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)); //获取通话日期
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));//获取通话时长，值为多少秒
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)); //获取通话类型：1.呼入2.呼出3.未接
            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
            if (!phoneNumer.contains(number)) {
                phoneNumer.add(number);
                int phoneRecordTimes = getPhoneRecordTimes(context, number);


                LogUtils.d("Call log: " + "\n"
                        + "name: " + name + "\n"
                        + "phone number: " + number + "\n"
                        + "phoneRecordTimes : " + phoneRecordTimes + "\n"
                );
                ContactHistoryBean contactHistoryBean = new ContactHistoryBean();
                contactHistoryBean.setName(name);
                contactHistoryBean.setPhoneNum(number);
                contactHistoryBean.setTimes(phoneRecordTimes);
                contactHistoryBeans.add(contactHistoryBean);
            }
        }

        cursor.close();
        return contactHistoryBeans;
    }

    @SuppressLint("MissingPermission")
    public static int getPhoneRecordTimes(Context mContext, String phoneNumber) {
        Cursor cursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, null);
        int times = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {

                //如果传过来的电话号码与检测系统中通话记录相同，则times+1
                if (phoneNumber.equals(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)))) {
                    times = times + 1;
                }
                //CallLog calls =new CallLog();
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:

                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";
                        break;
                }
            } while (cursor.moveToNext());

            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//            Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
            //呼叫时间
//            String time = sfd.format(date);
            //联系人
//            String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
            //通话时间,单位:s
//            String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
        }
        return times;
    }


    public static String handerStrToNumber(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isPhone(String phone) {
        if(TextUtils.isEmpty(phone)){
            return false;
        }
        String regex = "^(1[1-9])\\d{9}$";
        if (phone.length() != 11) {
            System.out.println("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                System.out.println("请填入正确的手机号");
            }
            return isMatch;
        }
    }


    /**
     * 复制文本到粘贴板
     * @param context
     * @param text
     */
    public static void copy(Context context,String text){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }



    /**
     * 匹配银行卡
     * @param cardNo
     * @return
     */
    public static boolean matchLuhn(String cardNo) {
        try {
            int[] cardNoArr = new int[cardNo.length()];
            for (int i = 0; i < cardNo.length(); i++) {
                cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i)));
            }
            for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
                cardNoArr[i] <<= 1;
                cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
            }
            int sum = 0;
            for (int i = 0; i < cardNoArr.length; i++) {
                sum += cardNoArr[i];
            }
            return sum % 10 == 0;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 匹配银行卡
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        int cardLenth = nonCheckCodeCardId.trim().length();
        if (nonCheckCodeCardId == null || cardLenth == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new IllegalArgumentException("不是银行卡的卡号!");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';

            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context,String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

}
