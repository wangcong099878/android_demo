package the.one.kotlin.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moxun.tagcloudlib.view.TagsAdapter
import java.util.*


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
 * @date 2019/3/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
class TagCloudAdapter(dataSet: ArrayList<String>) : TagsAdapter() {


    private var dataSet = ArrayList<String>()

    init {
        this.dataSet = dataSet
    }

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getView(context: Context, position: Int, parent: ViewGroup): View {
        val tv = TextView(context)
        tv.text = dataSet[position]
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        return tv
    }

    override fun getItem(position: Int): Any {
        return dataSet[position]
    }

    override fun getPopularity(position: Int): Int {
        return position % 7
    }

    override fun onThemeColorChanged(view: View, themeColor: Int) {
        //        view.setBackgroundColor(themeColor);
    }
}
