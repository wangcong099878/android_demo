package the.one.aqtour.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class QSPSeriesSection extends SectionEntity<QSPSeries> {


    public QSPSeriesSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public QSPSeriesSection(QSPSeries qspSeries) {
        super(qspSeries);
    }


}
