package com.onesoft.jaydenim.model;

import android.text.TextUtils;

import com.onesoft.jaydenim.EaseUI;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.domain.EMMessage;
import com.onesoft.jaydenim.domain.EaseUser;
import com.onesoft.jaydenim.utils.EaseUserUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EaseAtMessageHelper {
    private List<String> toAtUserList = new ArrayList<String>();
    private Set<String> atMeGroupList = null;
    private static EaseAtMessageHelper instance = null;
    public synchronized static EaseAtMessageHelper get(){
        if(instance == null){
            instance = new EaseAtMessageHelper();
        }
        return instance;
    }
    
    
    private EaseAtMessageHelper(){
        atMeGroupList = EasePreferenceManager.getInstance().getAtMeGroups();
        if(atMeGroupList == null)
            atMeGroupList = new HashSet<String>();
        
    }
    
    /**
     * add user you want to @
     * @param username
     */
    public void addAtUser(String username){
        synchronized (toAtUserList) {
            if(!toAtUserList.contains(username)){
                toAtUserList.add(username);
            }
        }
        
    }
    
    /**
     * check if be mentioned(@) in the content
     * @param content
     * @return
     */
    public boolean containsAtUsername(String content){
        if(TextUtils.isEmpty(content)){
            return false;
        }
        synchronized (toAtUserList) {
            for(String username : toAtUserList){
                String nick = username;
                if(EaseUserUtils.getUserInfo(username) != null){
                    EaseUser user = EaseUserUtils.getUserInfo(username);
                    if (user != null) {
                        nick = user.getNick();
                    }
                }
                if(content.contains(nick)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsAtAll(String content){
        String atAll = "@" + EaseUI.getInstance().getContext().getString(R.string.all_members);
        if(content.contains(atAll)){
            return true;
        }
        return false;
    }
    
    /**
     * get the users be mentioned(@) 
     * @param content
     * @return
     */
    public List<String> getAtMessageUsernames(String content){
        if(TextUtils.isEmpty(content)){
            return null;
        }
        synchronized (toAtUserList) {
            List<String> list = null;
            for(String username : toAtUserList){
                String nick = username;
                if(EaseUserUtils.getUserInfo(username) != null){
                    EaseUser user = EaseUserUtils.getUserInfo(username);
                    if (user != null) {
                        nick = user.getNick();
                    }
                }
                if(content.contains(nick)){
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    list.add(username);
                }
            }
            return list;
        }
    }
    
    /**
     * get groups which I was mentioned
     * @return
     */
    public Set<String> getAtMeGroups(){
        return atMeGroupList;
    }
    
    /**
     * remove group from the list
     * @param groupId
     */
    public void removeAtMeGroup(String groupId){
        if(atMeGroupList.contains(groupId)){
            atMeGroupList.remove(groupId);
            EasePreferenceManager.getInstance().setAtMeGroups(atMeGroupList);
        }
    }
    
    /**
     * check if the input groupId in atMeGroupList
     * @param groupId
     * @return
     */
    public boolean hasAtMeMsg(String groupId){
        return atMeGroupList.contains(groupId);
    }

    /**
     * 判断是否是自己的消息
     * @param message
     * @return
     */
    public boolean isAtMeMsg(EMMessage message){
        return false;
    }
    
    public JSONArray atListToJsonArray(List<String> atList){
        JSONArray jArray = new JSONArray();
        int size = atList.size();
        for(int i = 0; i < size; i++){
            String username = atList.get(i);
            jArray.put(username);
        }
        return jArray;
    }

    public void cleanToAtUserList(){
        synchronized (toAtUserList){
            toAtUserList.clear();
        }
    }
}
