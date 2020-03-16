package the.one.aqtour.ui.view;

import java.util.List;

import the.one.aqtour.bean.QSPCategory;
import the.one.base.base.view.BaseView;

public interface QSPCategoryView extends BaseView {

    void onComplete(List<QSPCategory> QSPCategoryList);

}
