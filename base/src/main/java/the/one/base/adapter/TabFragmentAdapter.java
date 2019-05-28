package the.one.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

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
    private boolean destroyItem = true;

    public TabFragmentAdapter(FragmentManager fm, List<T> fragments, boolean destroy) {
        super(fm);
        this.fragments = fragments;
        this.destroyItem = destroy;
    }

    public TabFragmentAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
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
