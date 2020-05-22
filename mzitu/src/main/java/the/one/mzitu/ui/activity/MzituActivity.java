package the.one.mzitu.ui.activity;

import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.mzitu.ui.fragment.MzituFragment;

public class MzituActivity extends BaseFragmentActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new MzituFragment();
    }

}
