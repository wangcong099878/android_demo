package the.one.aqtour.bean;

import the.one.base.model.BaseSectionEntity;

public class QSPSeriesSection extends BaseSectionEntity<QSPSeries> {


    public QSPSeriesSection(String header) {
        super( header);
    }

    public QSPSeriesSection(QSPSeries qspSeries) {
        super(qspSeries);
    }


}
