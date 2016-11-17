package com.onesoft.digitaledu.presenter.message;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.message.IRecipientView;
import com.onesoft.digitaledu.widget.treerecyclerview.model.TreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jayden on 2016/11/8.
 */

public class RecipientPresenter extends BasePresenter<IRecipientView> {
    public RecipientPresenter(Context context, IRecipientView iView) {
        super(context, iView);
    }

    public void getListRecipient() {
        List<TreeItem> treeItems = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TreeItem treeItem = new TreeItem();
            if (i == 0) {//教师
                treeItem.id = "0";
                treeItem.uuid = UUID.randomUUID().toString();
                treeItem.isSelect = false;
                treeItem.name = "教师";
                treeItem.number = "教师工号：9865472";
                treeItem.total = 3;
                treeItem.type = TreeItem.ITEM_TYPE_PARENT1;
                treeItem.isExpand = false;
                List<TreeItem> mChildren = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    TreeItem treeItem1 = new TreeItem();
                    if (j == 0) {//汽修学院
                        treeItem1.id = "1";
                        treeItem1.uuid = UUID.randomUUID().toString();
                        treeItem1.isSelect = false;
                        treeItem1.name = "汽修学院";
                        treeItem1.number = "教师工号：9865472";
                        treeItem1.total = 10;
                        treeItem1.type = TreeItem.ITEM_TYPE_PARENT2;
                        treeItem1.isExpand = false;
                        List<TreeItem> mChildren1 = new ArrayList<>();
                        for (int s = 0; s < 10; s++) {
                            TreeItem treeItem2 = new TreeItem();
                            treeItem2.id = "1" + s;
                            treeItem2.uuid = UUID.randomUUID().toString();
                            treeItem2.isSelect = false;
                            treeItem2.name = "凤凰创壹" + s;
                            treeItem2.number = "教师工号：9865472";
                            treeItem2.type = TreeItem.ITEM_TYPE_CHILD;
                            treeItem2.isExpand = false;
                            treeItem2.imgUrl = "";
                            mChildren1.add(treeItem2);
                        }
                        treeItem1.mChildren = mChildren1;
                    } else if (j == 1) {//软件学院
                        treeItem1.id = "2";
                        treeItem1.uuid = UUID.randomUUID().toString();
                        treeItem1.isSelect = false;
                        treeItem1.name = "软件学院";
                        treeItem1.number = "教师工号：9865472";
                        treeItem1.total = 3;
                        treeItem1.type = TreeItem.ITEM_TYPE_PARENT2;
                        treeItem1.isExpand = false;
                        List<TreeItem> mChildren1 = new ArrayList<>();
                        for (int s = 0; s < 3; s++) {
                            TreeItem treeItem2 = new TreeItem();
                            treeItem2.id = "2" + s;
                            treeItem2.uuid = UUID.randomUUID().toString();
                            treeItem2.isSelect = false;
                            treeItem2.name = "软件创壹" + s;
                            treeItem2.number = "教师工号：9865472";
                            treeItem2.type = TreeItem.ITEM_TYPE_CHILD;
                            treeItem2.isExpand = false;
                            treeItem2.imgUrl = "";
                            mChildren1.add(treeItem2);
                        }
                        treeItem1.mChildren = mChildren1;
                    } else {//机械自动化
                        treeItem1.id = "3";
                        treeItem1.uuid = UUID.randomUUID().toString();
                        treeItem1.isSelect = false;
                        treeItem1.name = "机械自动化";
                        treeItem1.number = "教师工号：9865472";
                        treeItem1.total = 0;
                        treeItem1.type = TreeItem.ITEM_TYPE_PARENT2;
                        treeItem1.isExpand = false;
                    }
                    mChildren.add(treeItem1);
                }
                treeItem.mChildren = mChildren;
            } else {//学生
                treeItem.id = "1";
                treeItem.uuid = UUID.randomUUID().toString();
                treeItem.isSelect = false;
                treeItem.name = "学生";
                treeItem.number = "学号：600389";
                treeItem.total = 2;
                treeItem.type = TreeItem.ITEM_TYPE_PARENT1;
                treeItem.isExpand = false;
                List<TreeItem> mChildren = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    TreeItem treeItem1 = new TreeItem();
                    if (j == 0) {//2014级
                        treeItem1.id = "1";
                        treeItem1.uuid = UUID.randomUUID().toString();
                        treeItem1.isSelect = false;
                        treeItem1.name = "2014级";
                        treeItem1.number = "学号：600389";
                        treeItem1.total = 2;
                        treeItem1.type = TreeItem.ITEM_TYPE_PARENT2;
                        treeItem1.isExpand = false;
                        List<TreeItem> mChildren1 = new ArrayList<>();
                        for (int s = 0; s < 2; s++) {
                            TreeItem treeItem2 = new TreeItem();
                            if (s == 0) {//14汽修一班
                                treeItem2.id = "1" + s;
                                treeItem2.uuid = UUID.randomUUID().toString();
                                treeItem2.isSelect = false;
                                treeItem2.name = "14汽修一班";
                                treeItem2.number = "学号：600389";
                                treeItem2.type = TreeItem.ITEM_TYPE_PARENT3;
                                treeItem2.isExpand = false;
                                treeItem2.imgUrl = "";
                                List<TreeItem> mChildrent = new ArrayList<>();
                                for (int t = 0; t < 3; t++) {
                                    TreeItem treeItemt = new TreeItem();
                                    treeItemt.id = "2" + s;
                                    treeItemt.uuid = UUID.randomUUID().toString();
                                    treeItemt.isSelect = false;
                                    treeItemt.name = "姚海泉" + s;
                                    treeItemt.number = "学号：600389";
                                    treeItemt.type = TreeItem.ITEM_TYPE_CHILD;
                                    treeItemt.isExpand = false;
                                    treeItemt.imgUrl = "";
                                    mChildrent.add(treeItemt);
                                }
                                treeItem2.mChildren = mChildrent;
                            } else if (s == 1) {//马克思主义专业理论一班
                                treeItem2.id = "1" + s;
                                treeItem2.uuid = UUID.randomUUID().toString();
                                treeItem2.isSelect = false;
                                treeItem2.name = "马克思主义专业理论一班";
                                treeItem2.number = "学号：600389";
                                treeItem2.type = TreeItem.ITEM_TYPE_PARENT3;
                                treeItem2.isExpand = false;
                                treeItem2.imgUrl = "";
                                List<TreeItem> mChildrent = new ArrayList<>();
                                for (int t = 0; t < 3; t++) {
                                    TreeItem treeItemt = new TreeItem();
                                    treeItemt.id = "3" + s;
                                    treeItemt.uuid = UUID.randomUUID().toString();
                                    treeItemt.isSelect = false;
                                    treeItemt.name = "马克思" + s;
                                    treeItemt.number = "学号：600389";
                                    treeItemt.type = TreeItem.ITEM_TYPE_CHILD;
                                    treeItemt.isExpand = false;
                                    treeItemt.imgUrl = "";
                                    mChildrent.add(treeItemt);
                                }
                                treeItem2.mChildren = mChildrent;
                            }

                            mChildren1.add(treeItem2);
                        }
                        treeItem1.mChildren = mChildren1;
                    } else if (j == 1) {//2013级
                        treeItem1.id = "1";
                        treeItem1.uuid = UUID.randomUUID().toString();
                        treeItem1.isSelect = false;
                        treeItem1.name = "2013级";
                        treeItem1.number = "学号：600389";
                        treeItem1.total = 2;
                        treeItem1.type = TreeItem.ITEM_TYPE_PARENT2;
                        treeItem1.isExpand = false;
                        List<TreeItem> mChildren1 = new ArrayList<>();
                        for (int s = 0; s < 2; s++) {
                            TreeItem treeItem2 = new TreeItem();
                            if (s == 0) {//13汽修一班
                                treeItem2.id = "1" + s;
                                treeItem2.uuid = UUID.randomUUID().toString();
                                treeItem2.isSelect = false;
                                treeItem2.name = "13汽修一班";
                                treeItem2.number = "学号：600389";
                                treeItem2.type = TreeItem.ITEM_TYPE_PARENT3;
                                treeItem2.isExpand = false;
                                treeItem2.imgUrl = "";
                                List<TreeItem> mChildrent = new ArrayList<>();
                                for (int t = 0; t < 3; t++) {
                                    TreeItem treeItemt = new TreeItem();
                                    treeItemt.id = "2" + s;
                                    treeItemt.uuid = UUID.randomUUID().toString();
                                    treeItemt.isSelect = false;
                                    treeItemt.name = "姚海泉" + s;
                                    treeItemt.number = "学号：600389";
                                    treeItemt.type = TreeItem.ITEM_TYPE_CHILD;
                                    treeItemt.isExpand = false;
                                    treeItemt.imgUrl = "";
                                    mChildrent.add(treeItemt);
                                }
                                treeItem2.mChildren = mChildrent;
                            } else if (s == 1) {//马克思主义专业理论一班
                                treeItem2.id = "1" + s;
                                treeItem2.uuid = UUID.randomUUID().toString();
                                treeItem2.isSelect = false;
                                treeItem2.name = "马克思主义专业理论一班";
                                treeItem2.number = "学号：600389";
                                treeItem2.type = TreeItem.ITEM_TYPE_PARENT3;
                                treeItem2.isExpand = false;
                                treeItem2.imgUrl = "";
                                List<TreeItem> mChildrent = new ArrayList<>();
                                for (int t = 0; t < 3; t++) {
                                    TreeItem treeItemt = new TreeItem();
                                    treeItemt.id = "3" + s;
                                    treeItemt.uuid = UUID.randomUUID().toString();
                                    treeItemt.isSelect = false;
                                    treeItemt.name = "马克思" + s;
                                    treeItemt.number = "学号：600389";
                                    treeItemt.type = TreeItem.ITEM_TYPE_CHILD;
                                    treeItemt.isExpand = false;
                                    treeItemt.imgUrl = "";
                                    mChildrent.add(treeItemt);
                                }
                                treeItem2.mChildren = mChildrent;
                            }

                            mChildren1.add(treeItem2);
                        }
                        treeItem1.mChildren = mChildren1;
                    }
                    mChildren.add(treeItem1);
                }
                treeItem.mChildren = mChildren;
            }
            treeItems.add(treeItem);
        }
        iView.onSuccess(treeItems);
    }
}
