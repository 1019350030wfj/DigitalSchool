/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.onesoft.jaydenim.database;

import android.content.Context;

import com.onesoft.jaydenim.domain.EMMessage;

import java.util.Map;

public class LastMessgeDao {

	public static final String TABLE_NAME = "lastmessages";
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_CONTENT = "content";
	public static final String COLUMN_NAME_CONVERSATION_ID = "conversationId";
	public static final String COLUMN_NAME_AVATAR = "avatar";
	public static final String COLUMN_NAME_TIME = "time";
	public static final String COLUMN_NAME_FROM_ID = "fromId";
	public static final String COLUMN_NAME_FROM_NAME = "fromName";
	public static final String COLUMN_NAME_DIRECT = "direct";
	public static final String COLUMN_NAME_TYPE = "messageType";
	public static final String COLUMN_NAME_LENGTH = "length";
	public static final String COLUMN_NAME_LOCALPATH = "localPath";
	public static final String COLUMN_NAME_FILESIZE = "filesize";
	public static final String COLUMN_NAME_FILENAME = "filename";
	public static final String COLUMN_NAME_THUMBNAIL = "thumbnailLocalPath";
	public static final String COLUMN_NAME_COMMA = "comma";

	public LastMessgeDao(Context context){
	}
	
	/**
	 * save message
	 * @param message
	 * @return  return cursor of the message
	 */
	public void saveMessage(EMMessage message){
		DemoDBManager.getInstance().saveLastMessage(message);
	}

	public Map<String, EMMessage> getLastEMMessageList() {
		return DemoDBManager.getInstance().getAllLastMessage();
	}

	public void deleteMessage(String from){
	    DemoDBManager.getInstance().deleteLastMessage(from);
	}
}
