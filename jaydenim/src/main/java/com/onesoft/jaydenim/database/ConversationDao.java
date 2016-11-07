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

import com.onesoft.jaydenim.domain.EMConversation;

import java.util.List;
import java.util.Map;

public class ConversationDao {
	public static final String TABLE_NAME = "conversation";
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_NICK = "nickname";
	public static final String COLUMN_NAME_GROUP = "isGroup";
	public static final String COLUMN_NAME_TYPE = "conversationType";
	public static final String COLUMN_NAME_ALL_MESSAGE = "allMsgCount";
	public static final String COLUMN_NAME_UNREAD_MESSAGE = "unReadMsgCount";

	public ConversationDao(Context context) {
	}

	/**
	 * save Conversation list
	 * 
	 * @param conversationList
	 */
	public void saveConversationtList(List<EMConversation> conversationList) {
	    DemoDBManager.getInstance().saveConversationtList(conversationList);
	}

	/**
	 * get Conversation list
	 * 
	 * @return
	 */
	public Map<String, EMConversation> getConversationList() {
	    return DemoDBManager.getInstance().getConversationList();
	}
	
	/**
	 * delete a Conversation
	 * @param username
	 */
	public void deleteConversation(String username){
	    DemoDBManager.getInstance().deleteConversation(username);
	}
	
	/**
	 * save a Conversation
	 * @param user
	 */
	public void saveConversation(EMConversation user){
	    DemoDBManager.getInstance().saveConversation(user);
	}

}
