package the.one.base.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TabFragmentAdapter<T> extends FragmentPagerAdapter {

    private List<T> fragments;
    /**
     * 是否销毁
     */
    private boolean destroyItem;

    public TabFragmentAdapter(FragmentManager fm, List<T> fragments) {
        this(fm,fragments,true);
    }

    @SuppressLint("WrongConstant")
    public TabFragmentAdapter(FragmentManager fm, List<T> fragments, boolean destroy) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.destroyItem = destroy;
    }

    public void setFragments(List<T> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 如果滑动过后不进行销毁，则将下面这句注释掉
        if (destroyItem)
            super.destroyItem(container, position, object);
    }
}
