package the.one.aqtour.bean;

import the.one.base.model.BaseSectionEntity;

public class QSPVideoSection extends BaseSectionEntity<QSPVideo> {

    public String moreUrl;
    public String headRes;

    public QSPVideoSection(String header,String headRes,String moreUrl) {
        super(header);
        this.headRes = headRes;
        this.moreUrl = moreUrl;
    }

    public QSPVideoSection(QSPVideo qspVideo) {
        super(qspVideo);
    }

}
