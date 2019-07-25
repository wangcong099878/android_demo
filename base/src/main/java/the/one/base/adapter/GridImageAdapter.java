package the.one.base.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.io.File;

import the.one.base.R;
import the.one.base.util.GlideUtil;


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * @author The one
 * @date 2019/7/17 0017
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GridImageAdapter extends BaseQuickAdapter<LocalMedia,BaseViewHolder> {

    public static final int TYPE_CAMERA = 100;

    private int selectMax = 9;

    public GridImageAdapter(onAddPicClickListener listener) {
        super(R.layout.item_gv_filter_image);
        mOnAddPicClickListener = listener;
    }

    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public void setSelectMax(int max){
        selectMax = max;
    }

    @Override
    public int getItemCount() {
        if (mData.size() < selectMax) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        }
        return super.getItemViewType(position);
    }

    private boolean isShowAddItem(int position) {
        return position == mData.size();
    }

    @Override
    protected void convert(final BaseViewHolder helper, LocalMedia media) {
        ImageView image = helper.getView(R.id.iv_filter);
        LinearLayout delete = helper.getView(R.id.ll_del);
        TextView tvDuration = helper.getView(R.id.tv_duration);
        if(helper.getItemViewType() == TYPE_CAMERA){
            image.setImageResource(R.drawable.addimg_1x);
            delete.setVisibility(View.INVISIBLE);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mOnAddPicClickListener)
                        mOnAddPicClickListener.onAddPicClick();
                }
            });
        }else{
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = helper.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        getData().remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, getData().size());
                    }
                }
            });
            int mimeType = media.getMimeType();
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }

            Log.i("原图地址::", media.getPath());
            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            if (media.isCut()) {
                Log.i("裁剪地址::", media.getCutPath());
            }
            long duration = media.getDuration();
            tvDuration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                tvDuration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(tvDuration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(tvDuration, drawable, 0);
            }
            tvDuration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                image.setImageResource(R.drawable.audio_placeholder);
            } else {
                GlideUtil.load(mContext,path,image);
            }
        }

    }

}
