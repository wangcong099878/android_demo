package the.one.base.util;

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

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import the.one.base.model.City;
import the.one.base.model.CitySection;
import the.one.base.model.Province;

/**
 * @author The one
 * @date 2019/8/16 0016
 * @describe 得到城市数据
 * @email 625805189@qq.com
 * @remark
 */
public class CityUtil {

    private static List<CitySection> mCitySections;
    private static List<Province> mProvinces;

    public static List<CitySection> getCityDatas(Context context) {
        if (null == mCitySections || mCitySections.size() == 0) {
            List<Province> provinces = getProvinces(context);
            mCitySections = new ArrayList<>();
            // 将数据转换成CitySection
            for (Province pro : provinces) {
                String province = pro.getName();
                ArrayList<String> areas = pro.getArea();
                if (null != areas && areas.size() > 0) {
                    for (String city : areas) {
                        // 直辖市  只是 省 区
                        CitySection citySection = new CitySection(city);
                        citySection.province = province;
                        mCitySections.add(citySection);
                    }
                } else {
                    // 包含省 市 县
                    // 得到市列表
                    ArrayList<City> cities = pro.getCity();
                    for (City city : cities) {
                        String cityName = city.getName();
                        for (String county : city.getArea()) {
                            CitySection citySection = new CitySection(county);
                            citySection.province = province;
                            citySection.city = cityName;
                            mCitySections.add(citySection);
                        }
                    }
                }
            }
        }
        return mCitySections;
    }

    public static List<Province> getProvinces(Context context) {
        if (null == mProvinces || mProvinces.size() == 0) {

            StringBuilder jsonSB = new StringBuilder();
            try {
                BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(Objects.requireNonNull(context).getAssets().open("province.json")));
                String line;
                while ((line = addressJsonStream.readLine()) != null) {
                    jsonSB.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mProvinces = new ArrayList<>();
            try {
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(jsonSB.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    mProvinces.add(new Gson().fromJson(jsonArray.get(i).toString(), Province.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mProvinces;
    }

}
