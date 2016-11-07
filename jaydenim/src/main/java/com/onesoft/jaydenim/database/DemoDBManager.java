package com.onesoft.jaydenim.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.EaseConstant;
import com.onesoft.jaydenim.EaseUI;
import com.onesoft.jaydenim.domain.EMConversation;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.domain.InviteMessage;
import com.onesoft.jaydenim.utils.EaseCommonUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DemoDBManager {
    private static DemoDBManager dbMgr = new DemoDBManager();
    private DbOpenHelper dbHelper;

    private DemoDBManager() {
        dbHelper = DbOpenHelper.getInstance(EaseUI.getInstance().getContext());
    }

    public static synchronized DemoDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }

    /**
     * save contact list
     *
     * @param contactList
     */
    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(UserDao.COLUMN_NAME_ID, user.user_id);
                if (user.getNick() != null)
                    values.put(UserDao.COLUMN_NAME_NICK, user.real_name);
                if (user.getAvatar() != null)
                    values.put(UserDao.COLUMN_NAME_AVATAR, user.photo);
                if (user.user_name != null)
                    values.put(UserDao.COLUMN_NAME_UN, user.user_name);
                if (user.user_type != null)
                    values.put(UserDao.COLUMN_NAME_TYPE, user.user_type);
                if (user.sex != null)
                    values.put(UserDao.COLUMN_NAME_SEX, user.sex);
                if (user.mobilephone != null)
                    values.put(UserDao.COLUMN_NAME_PHONE, user.mobilephone);
                if (user.province != null)
                    values.put(UserDao.COLUMN_NAME_PROVINCE, user.province);
                db.replace(UserDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * get contact list
     *
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                String userid = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
                String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_UN));
                String type = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_TYPE));
                String sex = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SEX));
                String phone = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_PHONE));
                String province = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_PROVINCE));
                EaseUser user = new EaseUser(userid);
                user.setNick(nick);
                user.setAvatar(avatar);
                user.user_name = username;
                user.user_type = type;
                user.sex = sex;
                user.mobilephone = phone;
                user.province = province;
                if (userid.equals(EaseConstant.NEW_FRIENDS_USERNAME)) {
                    user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                users.put(userid, user);
            }
            cursor.close();
        }
        return users;
    }

    /**
     * delete a contact
     *
     * @param username
     */
    synchronized public void deleteContact(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }

    /**
     * save a contact
     *
     * @param user
     */
    synchronized public void saveContact(EaseUser user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.COLUMN_NAME_ID, user.user_id);
        if (user.getNick() != null)
            values.put(UserDao.COLUMN_NAME_NICK, user.real_name);
        if (user.getAvatar() != null)
            values.put(UserDao.COLUMN_NAME_AVATAR, user.photo);
        if (user.user_name != null)
            values.put(UserDao.COLUMN_NAME_UN, user.user_name);
        if (user.user_type != null)
            values.put(UserDao.COLUMN_NAME_TYPE, user.user_type);
        if (user.sex != null)
            values.put(UserDao.COLUMN_NAME_SEX, user.sex);
        if (user.mobilephone != null)
            values.put(UserDao.COLUMN_NAME_PHONE, user.mobilephone);
        if (user.province != null)
            values.put(UserDao.COLUMN_NAME_PROVINCE, user.province);
        if (db.isOpen()) {
            db.replace(UserDao.TABLE_NAME, null, values);
        }
    }

    /**
     * save a message
     *
     * @param message
     * @return return cursor of the message
     */
    public synchronized Integer saveMessage(InviteMessage message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_Name, message.getGroupName());
            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessgeDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUPINVITER, message.getGroupInviter());
            db.insert(InviteMessgeDao.TABLE_NAME, null, values);

            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }

            cursor.close();
        }
        return id;
    }

    /**
     * update message
     *
     * @param msgId
     * @param values
     */
    synchronized public void updateMessage(int msgId, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(InviteMessgeDao.TABLE_NAME, values, InviteMessgeDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }

    synchronized public int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(InviteMessgeDao.TABLE_NAME, null, InviteMessgeDao.COLUMN_NAME_ID + "=?", new String[]{String.valueOf(EMClient.getInstance().getCurrentUser())}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT));
                cursor.close();
            }
        }
        return count;
    }

    synchronized public void setUnreadNotifyCount(int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InviteMessgeDao.COLUMN_NAME_ID, EMClient.getInstance().getCurrentUser());
        values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);
        if (db.isOpen()) {
            db.replace(InviteMessgeDao.TABLE_NAME, null, values);
        }
    }

    /*====================== 会话（历史消息） ==============================*/
    public void saveConversationtList(List<EMConversation> conversationList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(ConversationDao.TABLE_NAME, null, null);
            for (EMConversation user : conversationList) {
                ContentValues values = new ContentValues();
                values.put(ConversationDao.COLUMN_NAME_ID, user.getId());
                if (!TextUtils.isEmpty(user.getUserName())) {
                    values.put(ConversationDao.COLUMN_NAME_NICK, user.getUserName());
                }
                values.put(ConversationDao.COLUMN_NAME_GROUP, user.isGroup());
                values.put(ConversationDao.COLUMN_NAME_TYPE, user.getType());
                values.put(ConversationDao.COLUMN_NAME_ALL_MESSAGE, user.getAllMsgCount());
                values.put(ConversationDao.COLUMN_NAME_UNREAD_MESSAGE, user.getUnreadMsgCount());
                db.replace(ConversationDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * 获取历史消息
     *
     * @return
     */
    public Map<String, EMConversation> getConversationList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EMConversation> conversations = new Hashtable<String, EMConversation>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + ConversationDao.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_ID));
                String nickname = cursor.getString(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_NICK));
                int isGroup = cursor.getInt(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_GROUP));
                int msgType = cursor.getInt(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_TYPE));
                int allMsgCount = cursor.getInt(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_ALL_MESSAGE));
                int unReadMsgCount = cursor.getInt(cursor.getColumnIndex(ConversationDao.COLUMN_NAME_UNREAD_MESSAGE));
                EMConversation conversation = new EMConversation();
                conversation.setId(id);
                conversation.setUsername(nickname);
                conversation.setGroup(isGroup);
                conversation.setConversationType(msgType);
                conversation.setAllMsgCount(allMsgCount);
                conversation.setUnReadMsgCount(unReadMsgCount);
                conversations.put(id, conversation);
            }
            cursor.close();
        }
        return conversations;
    }

    /**
     * 删除历史消息
     *
     * @param id
     */

    public void deleteConversation(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(ConversationDao.TABLE_NAME, ConversationDao.COLUMN_NAME_ID + " = ?", new String[]{id});
        }
    }

    /**
     * 更新或者插入数据
     *
     * @param user
     */
    synchronized public void saveConversation(EMConversation user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ConversationDao.COLUMN_NAME_ID, user.getId());
        if (user.getUserName() != null) {
            values.put(ConversationDao.COLUMN_NAME_NICK, user.getUserName());
        }
        values.put(ConversationDao.COLUMN_NAME_GROUP, user.isGroup());
        values.put(ConversationDao.COLUMN_NAME_TYPE, user.getType());
        values.put(ConversationDao.COLUMN_NAME_ALL_MESSAGE, user.getAllMsgCount());
        values.put(ConversationDao.COLUMN_NAME_UNREAD_MESSAGE, user.getUnreadMsgCount());
        if (db.isOpen()) {
            db.replace(ConversationDao.TABLE_NAME, null, values);
        }
    }
    /*====================== 会话（历史消息） ==============================*/

    /*====================== 会话的最后一条消息 ==============================*/

    /**
     * 获取所有聊天记录
     *
     * @return
     */
    synchronized public Map<String, EMMessage> getAllLastMessage() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EMMessage> users = new Hashtable<String, EMMessage>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + LastMessgeDao.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                String lastMsgId = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_ID));
                String content = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_CONTENT));
                String conversationID = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_CONVERSATION_ID));
                String avatar = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_AVATAR));
                long time = cursor.getLong(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_TIME));
                String msgId = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FROM_ID));
                String msgNick = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FROM_NAME));
                int direct = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_DIRECT));
                int type = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_TYPE));
                int length = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_LENGTH));
                String localpath = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_LOCALPATH));
                long filesize = cursor.getLong(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FILESIZE));
                String filename = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FILENAME));
                String thumbnail = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_THUMBNAIL));
                String comma = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_COMMA));

                EMMessage emMessage = new EMMessage();
                emMessage.setMsgId(lastMsgId);
                emMessage.setContent(content);
                emMessage.setConversationId(conversationID);
                emMessage.setAvatar(avatar);
                emMessage.setMsgTime(time);
                emMessage.setMsgFrom(msgId);
                emMessage.setMsgFromNick(msgNick);
                emMessage.setMsgDirect(direct);
                emMessage.setMsgType(type);
                emMessage.setLength(length);
                emMessage.setLocalUrl(localpath);
                emMessage.setFileSize(filesize);
                emMessage.setFilename(filename);
                emMessage.setThumbnailLocalPath(thumbnail);
                emMessage.setCommaStr(comma);
                users.put(conversationID, emMessage);
            }
            cursor.close();
        }
        return users;
    }

    /**
     * 获取指定会话的最后一条消息
     *
     * @return
     */
    public EMMessage getLastMessage(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(LastMessgeDao.TABLE_NAME, null, LastMessgeDao.COLUMN_NAME_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        EMMessage emMessage = null;
        if (cursor != null && cursor.moveToFirst()) {
            String lastMsgId = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_ID));
            String content = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_CONTENT));
            String conversationID = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_CONVERSATION_ID));
            String avatar = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_AVATAR));
            long time = cursor.getLong(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_TIME));
            String msgId = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FROM_ID));
            String msgNick = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FROM_NAME));
            int direct = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_DIRECT));
            int type = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_TYPE));
            int length = cursor.getInt(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_LENGTH));
            String localpath = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_LOCALPATH));
            long filesize = cursor.getLong(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FILESIZE));
            String filename = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_FILENAME));
            String thumbnail = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_THUMBNAIL));
            String comma = cursor.getString(cursor.getColumnIndex(LastMessgeDao.COLUMN_NAME_COMMA));

            emMessage = new EMMessage();
            emMessage.setMsgId(lastMsgId);
            emMessage.setContent(content);
            emMessage.setConversationId(conversationID);
            emMessage.setAvatar(avatar);
            emMessage.setMsgTime(time);
            emMessage.setMsgFrom(msgId);
            emMessage.setMsgFromNick(msgNick);
            emMessage.setMsgDirect(direct);
            emMessage.setMsgType(type);
            emMessage.setLength(length);
            emMessage.setLocalUrl(localpath);
            emMessage.setFileSize(filesize);
            emMessage.setFilename(filename);
            emMessage.setThumbnailLocalPath(thumbnail);
            emMessage.setCommaStr(comma);
            cursor.close();
        }
        return emMessage;
    }

    /**
     * 删除指定会话的最后一条消息
     *
     * @param id 会话id
     */
    public void deleteLastMessage(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(LastMessgeDao.TABLE_NAME, LastMessgeDao.COLUMN_NAME_CONVERSATION_ID + " = ?", new String[]{id});
        }
    }

    /**
     * 更新或者插入数据
     * 指定会话的最后一条消息
     *
     * @param user
     */
    synchronized public void saveLastMessage(EMMessage user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LastMessgeDao.COLUMN_NAME_ID, user.getConversationId());
        if (!TextUtils.isEmpty(user.getContent())) {
            values.put(LastMessgeDao.COLUMN_NAME_CONTENT, user.getContent());
        }
        values.put(LastMessgeDao.COLUMN_NAME_CONVERSATION_ID, user.getConversationId());
        if (!TextUtils.isEmpty(user.getAvatar())) {
            values.put(LastMessgeDao.COLUMN_NAME_AVATAR, user.getAvatar());
        }
        values.put(LastMessgeDao.COLUMN_NAME_TIME, user.getMsgTime());
        values.put(LastMessgeDao.COLUMN_NAME_FROM_ID, user.getMsgFrom());
        if (!TextUtils.isEmpty(user.getMsgFromNick())) {
            values.put(LastMessgeDao.COLUMN_NAME_FROM_NAME, user.getMsgFromNick());
        }
        values.put(LastMessgeDao.COLUMN_NAME_DIRECT, user.direct());
        values.put(LastMessgeDao.COLUMN_NAME_TYPE, user.getType());
        values.put(LastMessgeDao.COLUMN_NAME_LENGTH, user.getLength());
        if (!TextUtils.isEmpty(user.getLocalUrl())) {
            values.put(LastMessgeDao.COLUMN_NAME_LOCALPATH, user.getLocalUrl());
        }
        values.put(LastMessgeDao.COLUMN_NAME_FILESIZE, user.getFileSize());
        if (!TextUtils.isEmpty(user.getFileName())) {
            values.put(LastMessgeDao.COLUMN_NAME_FILENAME, user.getFileName());
        }
        if (!TextUtils.isEmpty(user.thumbnailLocalPath())) {
            values.put(LastMessgeDao.COLUMN_NAME_THUMBNAIL, user.thumbnailLocalPath());
        }
        if (!TextUtils.isEmpty(user.getCommaStr())) {
            values.put(LastMessgeDao.COLUMN_NAME_COMMA, user.getCommaStr());
        }
        if (db.isOpen()) {
            db.replace(LastMessgeDao.TABLE_NAME, null, values);
        }
    }
    /*====================== 会话的最后一条消息 ==============================*/


    /*====================== 聊天消息 ==============================*/

    /**
     * 获取所有聊天记录
     *
     * @return
     */
    synchronized public List<EMMessage> getAllMessage(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<EMMessage> users = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.query(MessgeDao.TABLE_NAME, null, MessgeDao.COLUMN_NAME_CONVERSATION_ID + "=?", new String[]{id}, null, null, null);
            while (cursor.moveToNext()) {
                String lastMsgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_ID));
                String content = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONTENT));
                String conversationID = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_ID));
                String conversationName = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_NAME));
                String avatar = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_AVATAR));
                long time = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TIME));
                String msgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_ID));
                String msgNick = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_NAME));
                int direct = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_DIRECT));
                int type = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TYPE));
                int length = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LENGTH));
                String localpath = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LOCALPATH));
                long filesize = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILESIZE));
                String filename = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILENAME));
                String thumbnail = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_THUMBNAIL));
                String comma = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_COMMA));

                EMMessage emMessage = new EMMessage();
                emMessage.setMsgId(lastMsgId);
                emMessage.setContent(content);
                emMessage.setConversationId(conversationID);
                emMessage.setConversationName(conversationName);
                emMessage.setAvatar(avatar);
                emMessage.setMsgTime(time);
                emMessage.setMsgFrom(msgId);
                emMessage.setMsgFromNick(msgNick);
                emMessage.setMsgDirect(direct);
                emMessage.setMsgType(type);
                emMessage.setLength(length);
                emMessage.setLocalUrl(localpath);
                emMessage.setFileSize(filesize);
                emMessage.setFilename(filename);
                emMessage.setThumbnailLocalPath(thumbnail);
                emMessage.setCommaStr(comma);
                users.add(emMessage);
            }
            cursor.close();
        }
        return users;
    }
    /**
     * 获取所有聊天记录
     *
     * @return
     */
    synchronized public List<EMMessage> getAllMessageByPage(String id,int from,int to) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<EMMessage> users = new ArrayList<>();
        if (db.isOpen()) {
            //select * from tbname order by lastMsgId desc limit 0,20;
            Cursor cursor = db.query(MessgeDao.TABLE_NAME, null, MessgeDao.COLUMN_NAME_CONVERSATION_ID + "=?",
                    new String[]{id}, null, null, MessgeDao.COLUMN_NAME_ID + " desc",from+","+to);
            while (cursor.moveToNext()) {
                String lastMsgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_ID));
                String content = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONTENT));
                String conversationID = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_ID));
                String conversationName = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_NAME));
                String avatar = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_AVATAR));
                long time = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TIME));
                String msgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_ID));
                String msgNick = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_NAME));
                int direct = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_DIRECT));
                int type = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TYPE));
                int length = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LENGTH));
                String localpath = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LOCALPATH));
                long filesize = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILESIZE));
                String filename = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILENAME));
                String thumbnail = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_THUMBNAIL));
                String comma = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_COMMA));

                EMMessage emMessage = new EMMessage();
                emMessage.setMsgId(lastMsgId);
                emMessage.setContent(content);
                emMessage.setConversationId(conversationID);
                emMessage.setConversationName(conversationName);
                emMessage.setAvatar(avatar);
                emMessage.setMsgTime(time);
                emMessage.setMsgFrom(msgId);
                emMessage.setMsgFromNick(msgNick);
                emMessage.setMsgDirect(direct);
                emMessage.setMsgType(type);
                emMessage.setLength(length);
                emMessage.setLocalUrl(localpath);
                emMessage.setFileSize(filesize);
                emMessage.setFilename(filename);
                emMessage.setThumbnailLocalPath(thumbnail);
                emMessage.setCommaStr(comma);
                users.add(emMessage);
            }
            cursor.close();
        }
        return users;
    }

    /**
     * 获取指定一条消息
     *
     * @param id 会话id
     * @return
     */
    public EMMessage getMessage(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(MessgeDao.TABLE_NAME, null, MessgeDao.COLUMN_NAME_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        EMMessage emMessage = null;
        if (cursor != null && cursor.moveToFirst()) {
            String lastMsgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_ID));
            String content = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONTENT));
            String conversationID = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_ID));
            String conversationName = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_CONVERSATION_NAME));
            String avatar = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_AVATAR));
            long time = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TIME));
            String msgId = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_ID));
            String msgNick = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FROM_NAME));
            int direct = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_DIRECT));
            int type = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_TYPE));
            int length = cursor.getInt(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LENGTH));
            String localpath = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_LOCALPATH));
            long filesize = cursor.getLong(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILESIZE));
            String filename = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_FILENAME));
            String thumbnail = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_THUMBNAIL));
            String comma = cursor.getString(cursor.getColumnIndex(MessgeDao.COLUMN_NAME_COMMA));

            emMessage = new EMMessage();
            emMessage.setMsgId(lastMsgId);
            emMessage.setContent(content);
            emMessage.setConversationId(conversationID);
            emMessage.setConversationName(conversationName);
            emMessage.setAvatar(avatar);
            emMessage.setMsgTime(time);
            emMessage.setMsgFrom(msgId);
            emMessage.setMsgFromNick(msgNick);
            emMessage.setMsgDirect(direct);
            emMessage.setMsgType(type);
            emMessage.setLength(length);
            emMessage.setLocalUrl(localpath);
            emMessage.setFileSize(filesize);
            emMessage.setFilename(filename);
            emMessage.setThumbnailLocalPath(thumbnail);
            emMessage.setCommaStr(comma);
            cursor.close();
        }
        return emMessage;
    }

    /**
     * 删除一条消息
     *
     * @param id 消息id
     */
    synchronized public void deleteMessage(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(MessgeDao.TABLE_NAME, MessgeDao.COLUMN_NAME_ID + " = ?", new String[]{id});
        }
    }
    /**
     * 删除历史消息
     *
     * @param id 消息id
     */
    synchronized public void deleteMessageByConversationId(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(MessgeDao.TABLE_NAME, MessgeDao.COLUMN_NAME_CONVERSATION_ID + " = ?", new String[]{id});
        }
    }

    /**
     * 更新或者插入数据
     * 指定一条消息
     *
     * @param user
     */
    synchronized public void saveMessage(EMMessage user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MessgeDao.COLUMN_NAME_ID, user.getMsgId());
        if (!TextUtils.isEmpty(user.getContent())) {
            values.put(MessgeDao.COLUMN_NAME_CONTENT, user.getContent());
        }
        values.put(MessgeDao.COLUMN_NAME_CONVERSATION_ID, user.getConversationId());
        if (!TextUtils.isEmpty(user.getConversationName())) {
            values.put(MessgeDao.COLUMN_NAME_CONVERSATION_NAME, user.getConversationName());
        }
        if (!TextUtils.isEmpty(user.getAvatar())) {
            values.put(MessgeDao.COLUMN_NAME_AVATAR, user.getAvatar());
        }
        values.put(MessgeDao.COLUMN_NAME_TIME, user.getMsgTime());
        values.put(MessgeDao.COLUMN_NAME_FROM_ID, user.getMsgFrom());
        if (!TextUtils.isEmpty(user.getMsgFromNick())) {
            values.put(MessgeDao.COLUMN_NAME_FROM_NAME, user.getMsgFromNick());
        }
        values.put(MessgeDao.COLUMN_NAME_DIRECT, user.direct());
        values.put(MessgeDao.COLUMN_NAME_TYPE, user.getType());
        values.put(MessgeDao.COLUMN_NAME_LENGTH, user.getLength());
        if (!TextUtils.isEmpty(user.getLocalUrl())) {
            values.put(MessgeDao.COLUMN_NAME_LOCALPATH, user.getLocalUrl());
        }
        values.put(MessgeDao.COLUMN_NAME_FILESIZE, user.getFileSize());
        if (!TextUtils.isEmpty(user.getFileName())) {
            values.put(MessgeDao.COLUMN_NAME_FILENAME, user.getFileName());
        }
        if (!TextUtils.isEmpty(user.thumbnailLocalPath())) {
            values.put(MessgeDao.COLUMN_NAME_THUMBNAIL, user.thumbnailLocalPath());
        }
        if (!TextUtils.isEmpty(user.getCommaStr())) {
            values.put(MessgeDao.COLUMN_NAME_COMMA, user.getCommaStr());
        }
        if (db.isOpen()) {
            db.replace(MessgeDao.TABLE_NAME, null, values);
        }
    }
    /*====================== 聊天消息 ==============================*/

    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
            dbHelper = null;
        }
        dbMgr = null;
    }
}
