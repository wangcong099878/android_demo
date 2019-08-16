package the.one.demo.ui.sample;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import java.util.Date;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.util.DatePickerUtil;
import the.one.base.util.DateUtil;
import the.one.base.widge.datePicker.DateTimePicker;


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
 * @date 2019/7/19 0019
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class DatePickerFragment extends BaseGroupListFragment
        implements DateTimePicker.ResultHandler {

    private QMUICommonListItemView mCustomDate, mDefault;
    private QMUICommonListItemView mCurrentDateType;

    private DatePickerUtil mDatePicker;

    @Override
    protected void addGroupListView() {
        initFragmentBack("DatePickerFragment");

        mDefault = CreateDetailItemView("默认选择", "从以前到今天", true);
        mCustomDate = CreateDetailItemView("自定义起始日期", "限制一个月", true);

        mDatePicker = DatePickerUtil.getInstance();
        addToGroup(mDefault,mCustomDate);
    }

    @Override
    public void onClick(View v) {
        if(v ==  mDefault){
            mDatePicker = mDatePicker.initTimePicker(_mActivity);
        }else if(v == mCustomDate){
            mDatePicker = mDatePicker.initTimePicker(_mActivity,new Date(),mDatePicker.getNextMonthDate());
        }
        showDatePick((QMUICommonListItemView) v);
    }

    private void showDatePick(QMUICommonListItemView view) {
        mCurrentDateType = view;
        mDatePicker.show(_mActivity,view.getText().toString(), this);
    }

    @Override
    public void handle(Date date) {
        String mDate = DateUtil.Date2String(date);
        mCurrentDateType.setDetailText(mDate);
    }
}
