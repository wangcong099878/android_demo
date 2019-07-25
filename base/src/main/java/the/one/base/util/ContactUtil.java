package the.one.base.util;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import the.one.base.Interface.ReadContactCompleteListener;
import the.one.base.model.Contact;

/**
 * @author The one
 * @date 2019/1/18 0018
 * @describe 获取联系人工具
 * @email 625805189@qq.com
 * @remark
 */
public class ContactUtil {

    private static ContactUtil contactUtil;

    public static ContactUtil getInstance() {
        if (null == contactUtil)
            contactUtil = new ContactUtil();
        return contactUtil;
    }

    public void getContactList(Context context, ReadContactCompleteListener listener) {
        //获取联系人信息的Uri
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        //查询数据，返回Cursor
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<Contact> list = new ArrayList<Contact>();
        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            //获取联系人的ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //构造联系人信息
            sb.append("contactId=").append(contactId).append(",Name=").append(name);
            Contact contact = new Contact(name);
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));//联系人ID

            //查询电话类型的数据操作
            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            while (phones.moveToNext()) {
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.setPhone(phoneNumber);
            }
            phones.close();

            //查询Email类型的数据操作
            Cursor emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                    null, null);
            while (emails.moveToNext()) {
                String emailAddress = emails.getString(emails.getColumnIndex(
                        ContactsContract.CommonDataKinds.Email.DATA));
                //添加Email的信息
                contact.setEmail(emailAddress);
            }
            emails.close();
            //Log.i("=========ddddddddddd=====", sb.toString());

//            查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
            Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId,
                    null, null);
            while (address.moveToNext()) {
                String workAddress = address.getString(address.getColumnIndex(
                        ContactsContract.CommonDataKinds.StructuredPostal.DATA));
                //添加Email的信息
                contact.setAddress(workAddress);
            }
            address.close();
            //Log.i("=========ddddddddddd=====", sb.toString());

            //查询==公司名字==类型的数据操作.Organization.COMPANY  ContactsContract.Data.CONTENT_URI
            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
            String[] orgWhereParams = new String[]{id,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
            Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                    null, orgWhere, orgWhereParams, null);
            if (orgCur.moveToFirst()) {
                //组织名 (公司名字)
                String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                //职位
                String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                contact.setCompany(company);
            }
            orgCur.close();
            if (!TextUtils.isEmpty(contact.getName()) && !TextUtils.isEmpty(contact.getPhone())&&contact.getPhone().length()<15)
                list.add(contact);
        }
        cursor.close();
        listener.onComplete(list);
    }

}
