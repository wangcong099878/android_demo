package the.one.aqtour.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

public class QSPVideoSection extends SectionEntity<QSPVideo> {

    public String moreUrl;
    public String headRes;

    public QSPVideoSection(boolean isHeader, String header,String headRes,String moreUrl) {
        super(isHeader, header);
        this.headRes = headRes;
        this.moreUrl = moreUrl;
    }

    public QSPVideoSection(QSPVideo qspVideo) {
        super(qspVideo);
    }


}
