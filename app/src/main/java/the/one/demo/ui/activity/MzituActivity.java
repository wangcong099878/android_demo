package the.one.demo.ui.activity;

import the.one.base.base.activity.BaseFragmentActivity;
import the.one.base.base.fragment.BaseFragment;
import the.one.demo.ui.fragment.mzitu.MzituFragment;

public class MzituActivity extends BaseFragmentActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new MzituFragment();
    }
}
