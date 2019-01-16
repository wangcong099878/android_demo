package the.one.demo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import the.one.demo.model.Contact;

public class ContactsUtil {

    public static List<Contact> getContacts(Context context) {
        List<Contact> contacts = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        // 获得所有的联系人
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);

            int displayNameColumn = cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            do {
                Contact contact = new Contact();
                // 获得联系人的ID号
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                String disPlayName = cursor
                        .getString(displayNameColumn);
                contact.setName(disPlayName);
                // 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = cursor
                        .getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                //在联系人数量不为空的情况下执行
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    Cursor phonesCursor = context.getContentResolver()
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    if (phonesCursor.moveToFirst()) {
                        List<String> phones = new ArrayList<>();
                        do {
                            // 遍历所有的电话号码
                            String phoneNumber = phonesCursor.getString(phonesCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phones.add(phoneNumber);
                        } while (phonesCursor.moveToNext());
                        contact.setPhones(phones);
                    }
                }

                contacts.add(contact);

            } while (cursor.moveToNext());
        }

        return contacts;
    }

}


