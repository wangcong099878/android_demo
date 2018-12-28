package the.one.demo;

import the.one.base.base.BaseFragment;
import the.one.base.base.BaseFragmentActivity;

public class Main2Activity extends BaseFragmentActivity {


    @Override
    protected BaseFragment getBaseFragment() {
        return new IndexFragment();
    }
}
