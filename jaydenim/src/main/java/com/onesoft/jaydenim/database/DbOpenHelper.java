/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.onesoft.jaydenim.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onesoft.jaydenim.EMClient;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static DbOpenHelper instance;

    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_UN + " TEXT, "
            + UserDao.COLUMN_NAME_TYPE + " TEXT, "
            + UserDao.COLUMN_NAME_SEX + " TEXT, "
            + UserDao.COLUMN_NAME_PHONE + " TEXT, "
            + UserDao.COLUMN_NAME_PROVINCE + " TEXT);";

    private static final String CONVERSATION_TABLE_CREATE = "CREATE TABLE "
            + ConversationDao.TABLE_NAME + " ("
            + ConversationDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY , "
            + ConversationDao.COLUMN_NAME_NICK + " TEXT , "
            + ConversationDao.COLUMN_NAME_GROUP + " INTEGER, "
            + ConversationDao.COLUMN_NAME_TYPE + " INTEGER, "
            + ConversationDao.COLUMN_NAME_ALL_MESSAGE + " INTEGER, "
            + ConversationDao.COLUMN_NAME_UNREAD_MESSAGE + " INTEGER);";

    private static final String MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + MessgeDao.TABLE_NAME + " ("
            + MessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY , "
            + MessgeDao.COLUMN_NAME_CONTENT + " TEXT, "
            + MessgeDao.COLUMN_NAME_CONVERSATION_ID + " TEXT, "
            + MessgeDao.COLUMN_NAME_CONVERSATION_NAME + " TEXT, "
            + MessgeDao.COLUMN_NAME_AVATAR + " TEXT, "
            + MessgeDao.COLUMN_NAME_TIME + " TEXT, "
            + MessgeDao.COLUMN_NAME_FROM_ID + " TEXT, "
            + MessgeDao.COLUMN_NAME_FROM_NAME + " TEXT, "
            + MessgeDao.COLUMN_NAME_DIRECT + " INTEGER, "
            + MessgeDao.COLUMN_NAME_TYPE + " INTEGER, "
            + MessgeDao.COLUMN_NAME_LENGTH + " INTEGER, "
            + MessgeDao.COLUMN_NAME_LOCALPATH + " TEXT, "
            + MessgeDao.COLUMN_NAME_FILESIZE + " INTEGER, "
            + MessgeDao.COLUMN_NAME_FILENAME + " TEXT, "
            + MessgeDao.COLUMN_NAME_THUMBNAIL + " TEXT, "
            + MessgeDao.COLUMN_NAME_COMMA + " TEXT);";

    private static final String LAST_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + LastMessgeDao.TABLE_NAME + " ("
            + LastMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY , "
            + LastMessgeDao.COLUMN_NAME_CONTENT + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_CONVERSATION_ID + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_AVATAR + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_TIME + " INTEGER, "
            + LastMessgeDao.COLUMN_NAME_FROM_ID + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_FROM_NAME + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_DIRECT + " INTEGER, "
            + LastMessgeDao.COLUMN_NAME_TYPE + " INTEGER, "
            + LastMessgeDao.COLUMN_NAME_LENGTH + " INTEGER, "
            + LastMessgeDao.COLUMN_NAME_LOCALPATH + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_FILESIZE + " INTEGER, "
            + LastMessgeDao.COLUMN_NAME_FILENAME + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_THUMBNAIL + " TEXT, "
            + LastMessgeDao.COLUMN_NAME_COMMA + " TEXT);";

    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessgeDao.TABLE_NAME + " ("
            + InviteMessgeDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";


    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return EMClient.getInstance().getCurrentUser() + "_im.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERNAME_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(CONVERSATION_TABLE_CREATE);
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(LAST_MESSAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                instance = null;
            }
        }
    }

}
