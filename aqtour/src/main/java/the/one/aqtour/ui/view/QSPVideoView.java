package the.one.aqtour.ui.view;

import the.one.aqtour.bean.QSPContent;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.base.ui.view.BaseDataView;

public interface QSPVideoView extends BaseDataView<QSPVideoSection> {

    void onSuccess(QSPContent data);
}
