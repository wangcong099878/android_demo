package the.one.aqtour.ui.view;

import the.one.base.base.view.BaseView;

public interface QSPVideoDetailView extends BaseView {

    void onDetailComplete();

    void onSeriesComplete(String response);

    void onSeriesError();

}
