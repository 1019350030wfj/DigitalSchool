package com.onesoft.digitaledu.model;

/**
 * 要上传的附件，包含路径和文件类型
 * Created by Jayden on 2016/12/29.
 */

public class FilePickBean {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_FILE = 1;

    private int type;
    private String path;

    public FilePickBean(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
