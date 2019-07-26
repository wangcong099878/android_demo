package the.one.base.model;

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

import com.qmuiteam.qmui.widget.section.QMUISection;

import the.one.base.Interface.IContacts;
import the.one.base.util.PinYingUtil;

/**
 * @author The one
 * @date 2019/7/19 0019
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LetterSearchSection implements QMUISection.Model<LetterSearchSection>,IContacts {

    public String name;
    private String pinyin = null;
    private String firstPinYin = null;
    public int position;

    public LetterSearchSection(String name) {
        this.name = name;
    }

    @Override
    public LetterSearchSection cloneForDiff() {
        return new LetterSearchSection(name);
    }

    @Override
    public boolean isSameItem(LetterSearchSection other) {
        return name == other.name || (name != null && name.equals(other.name));
    }

    @Override
    public boolean isSameContent(LetterSearchSection other) {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPinYin() {
        if(null == pinyin){
            pinyin = PinYingUtil.converterToSpell(name);
        }
        return pinyin;
    }

    @Override
    public String getFirstPinYin() {
        if(null == firstPinYin){
            firstPinYin = PinYingUtil.getFirstLetter(name);
        }
        return firstPinYin;
    }
}