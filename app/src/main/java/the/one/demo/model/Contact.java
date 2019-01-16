package the.one.demo.model;

import java.util.List;

import the.one.base.Interface.IContacts;
import the.one.base.util.PinYingUtil;

/**
 * @author The one
 * @date 2018/12/18 0018
 * @describe 好友
 * @email 625805189@qq.com
 * @remark
 */
public class Contact implements IContacts {

    /**
     * 朋友名称
     */
    private String name;

    private List<String> phones;

    public String getName() {
        return name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public String getPinYin() {
        return PinYingUtil.getFirstLetter(PinYingUtil.converterToSpell(name));
    }

    public void setName(String name) {
        this.name = name;
    }

}

