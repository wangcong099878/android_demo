package the.one.aqtour.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.recyclerview.widget.RecyclerView;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.util.LitePalUtil;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.QMUIDialogUtil;

public class CollectionVideoFragment extends BaseVideoFragment {

    public static CollectionVideoFragment newInstance() {
        CollectionVideoFragment fragment = new CollectionVideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String[] items = {"移除收藏"};

    @Override
    protected void initView(View rootView) {
        assert getArguments() != null;
        initFragmentBack("我的收藏");
        super.initView(rootView);
    }

    @Override
    protected void requestServer() {
        empty_str = "没有任何收藏";
        onComplete(LitePalUtil.getCollectionVideoList(QSPConstant.BASE_URL));
        adapter.getLoadMoreModule().loadMoreEnd();
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        QSPVideoSection section = (QSPVideoSection) adapter.getItem(position);
        showDeleteDialog(section.t, position);
        return true;
    }

    private void showDeleteDialog(final QSPVideo video, final int position) {
        QMUIDialogUtil.showMenuDialog(_mActivity, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (LitePalUtil.deleteCollection(video)) {
                    if (position != RecyclerView.NO_POSITION) {
                        adapter.getData().remove(position);
                        int size = adapter.getData().size();
                        if (size == 0) {
                            isFirst = true;
                            onComplete(null);
                        } else {
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, size);
                        }
                    }
                } else {
                    showFailTips("操作失败");
                }
            }
        });
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
