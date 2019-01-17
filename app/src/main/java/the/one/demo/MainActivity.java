package the.one.demo;

import the.one.base.base.activity.BaseFragmentActivity;
import the.one.base.base.fragment.BaseFragment;
import the.one.demo.fragment.MainFragment;
import the.one.library.BaseHttpRequest;
import the.one.library.OkHttpHttpRequestCore;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected BaseFragment getBaseFragment() {
        return new MainFragment();
    }
}
