package com.onesoft.digitaledu.presenter.infomanager.docsendrec;

import android.content.Context;

import com.onesoft.digitaledu.presenter.BasePresenter;
import com.onesoft.digitaledu.view.iview.infomanager.docsendrec.IDocSendReceiveView;

/**
 * 公文收发
 * Created by Jayden on 2016/12/29.
 */

public class DocumentSendReceivePresenter extends BasePresenter<IDocSendReceiveView> {
    public DocumentSendReceivePresenter(Context context, IDocSendReceiveView iView) {
        super(context, iView);
    }
}
