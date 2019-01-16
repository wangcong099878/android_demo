package the.one.demo;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.activity.BaseFragmentActivity;
import the.one.demo.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected BaseFragment getBaseFragment() {
        return new MainFragment();
    }
}
