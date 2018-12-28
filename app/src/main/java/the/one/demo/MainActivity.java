package the.one.demo;

import me.yokeyword.fragmentation.SupportFragment;
import the.one.base.base.ProxyActivity;

public class MainActivity extends ProxyActivity {

    @Override
    protected SupportFragment setDelegate() {
        return new IndexDelegate();
    }

}
