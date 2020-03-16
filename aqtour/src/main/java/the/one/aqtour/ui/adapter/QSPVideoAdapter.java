package the.one.aqtour.ui.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;

import the.one.aqtour.R;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.aqtour.widge.CustomRoundAngleImageView;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.GlideUtil;

public class QSPVideoAdapter extends BaseSectionQuickAdapter<QSPVideoSection,TheBaseViewHolder> {

    public QSPVideoAdapter() {
        super(R.layout.item_video, R.layout.item_video_head,null);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, QSPVideoSection item) {
        QSPVideo video = item.t;
        helper.setText(R.id.tv_title,video.name)
                .setText(R.id.tv_actors,video.actors)
                .setText(R.id.tv_des,video.remark);
        helper.setGone(R.id.tv_des,!TextUtils.isEmpty(video.remark));
        helper.setGone(R.id.tv_actors,!TextUtils.isEmpty(video.actors));
        CustomRoundAngleImageView ivCover = helper.getView(R.id.iv_cover);
        GlideUtil.load(mContext,video.cover,ivCover);
    }

    @Override
    protected void convertHead(TheBaseViewHolder helper, QSPVideoSection item) {
        helper.setText(R.id.tv_title,item.header);
        helper.setGone(R.id.tv_more, !TextUtils.isEmpty(item.moreUrl));
        AppCompatImageView ivTitle = helper.getView(R.id.iv_title_res);
        if(!TextUtils.isEmpty(item.headRes)){
            GlideUtil.load(mContext,item.headRes,ivTitle);
            ivTitle.setVisibility(View.VISIBLE);
        }else{
            ivTitle.setVisibility(View.GONE);
        }

    }
}
