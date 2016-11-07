package com.onesoft.jaydenim.model;

import java.util.Observable;
import java.util.Observer;

/**
 * 红点消息管理类
 * 需要红点变化的类注册
 * 当有红点消息时，通知观察者变化
 * Created by Jayden on 2016/9/24.
 */

public class RedPointManager extends Observable {

    public abstract static class RedPointObserver implements Observer {

        @Override
        public void update(Observable observable, Object data) {
            updateRedPoint(data);
        }

        public abstract void updateRedPoint(Object info);
    }

    private static RedPointManager redPointManager;

    public synchronized static RedPointManager getInstance() {
        if (redPointManager == null) {
            redPointManager = new RedPointManager();
        }
        return redPointManager;
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
    }

    /**
     * 当有数据变化时通知变化
     * @param object
     */
    public void onDataChange(Object object){
        setChanged();
        notifyObservers();
    }
}
