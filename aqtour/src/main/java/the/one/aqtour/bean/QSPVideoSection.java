package the.one.aqtour.bean;

import the.one.base.model.BaseSectionEntity;

public class QSPVideoSection extends BaseSectionEntity<QSPVideo> {

    public String moreTitle;
    public String moreUrl;
    public String headRes;

    public QSPVideoSection(String header,String headRes,String moreUrl,String moreTitle) {
        super(header);
        this.headRes = headRes;
        this.moreUrl = moreUrl;
        this.moreTitle = moreTitle;
    }

    public QSPVideoSection(QSPVideo qspVideo) {
        super(qspVideo);
    }

}
