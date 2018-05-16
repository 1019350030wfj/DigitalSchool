package com.onesoft.digitaledu.view.iview;

import com.onesoft.digitaledu.model.MenuTitle;

import java.util.List;

/**
 * Created by Jayden on 2016/11/22.
 */

public interface IListView<D> extends IBaseView {

    void onSuccess(List<MenuTitle> titles, List<D> details);
}
