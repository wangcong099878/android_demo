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

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import the.one.base.Interface.IProvinceListener;
import the.one.base.model.Area;
import the.one.base.model.City;
import the.one.base.model.CitySection;
import the.one.base.model.Province;

/**
 * @author The one
 * @date 2020/5/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ProvinceUtil {

    private static List<CitySection> mCitySections;
    private static List<Province> mProvinces;

    private static final String GET_PROVINCE_URL = "http://www.mca.gov.cn/article/sj/xzqh/2020/2020/202003061536.html";

    public static void getProvinceList(IProvinceListener listener) {
            File file = new File(FileDirectoryUtil.getProvinceJsonPath());
            if(file.exists()){
                listener.onComplete(getProvincesFromJson());
                return;
            }
            //2020年1月中华人民共和国县以上行政区划代码网页
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(GET_PROVINCE_URL).maxBodySize(0).get();
                    } catch (IOException e) {
                        listener.onError();
                        e.printStackTrace();
                    }
                    Elements elements = doc.getElementsByClass("xl7020844");
                    //省和市
                    Elements elementsProAndCity = doc.getElementsByClass("xl7120844");
                    List<String> stringListProAndCity = elementsProAndCity.eachText();
                    List<String> stringList = elements.eachText();
                    List<String> stringName = new ArrayList<String>();
                    List<String> stringCode = new ArrayList<String>();
                    stringListProAndCity.addAll(stringList);
                    for (int i = 0; i < stringListProAndCity.size(); i++) {
                        if (i % 2 == 0) {
                            //地区代码
                            stringCode.add(stringListProAndCity.get(i));
                        } else {
                            //地区名字
                            stringName.add(stringListProAndCity.get(i));
                        }
                    }
                    //正常情况 两个 list size 应该 一样
                    System.out.println("stringName  size= " + stringName.size() + "   stringCode   size= " + stringCode.size());
                    if (stringName.size() != stringCode.size()) {
                        throw new RuntimeException("数据错误");
                    }
                    List<Province> provinceList = processData(stringName, stringCode);
                    String path = FileDirectoryUtil.getProvinceJsonPath();
                    JSONFormatUtils.jsonWriter(provinceList, path);
                    listener.onComplete(provinceList);
                }
            }).start();
    }

    /**
     * 生成省份列表数据
     *
     * @param stringName
     * @param stringCode
     * @return
     */

    private static List<Province> processData(List<String> stringName, List<String> stringCode) {
        List<Province> provinceList = new ArrayList<Province>();
        //获取省
        for (int i = 0; i < stringCode.size(); i++) {
            String provinceName = stringName.get(i);
            String provinceCode = stringCode.get(i);
            if (provinceCode.endsWith("0000")) {
                Province province = new Province();
                province.setCode(provinceCode);
                province.setName(provinceName);
                provinceList.add(province);
                List<City> cities = new ArrayList<City>();
                province.setCityList(cities);
            }
        }
        //获取市
        for (int i = 0; i < provinceList.size(); i++) {
            String provinceName = provinceList.get(i).getName();
            String provinceCode = provinceList.get(i).getCode();
            //直辖市 城市和省份名称一样
            if (provinceName.contains("北京") || provinceName.contains("上海") || provinceName.contains("天津") || provinceName.contains("重庆")) {
                City city = new City();
                List<Area> areas = new ArrayList<Area>();
                city.setName(provinceName);
                city.setCode(provinceCode);
                city.setAreaList(areas);
                provinceList.get(i).getCityList().add(city);
            } else {
                for (int j = 0; j < stringCode.size(); j++) {
                    String cityName = stringName.get(j);
                    String cityCode = stringCode.get(j);
                    if (!cityCode.equals(provinceCode)) {
                        if (cityCode.startsWith(provinceCode.substring(0, 2))) {
                            if (cityCode.endsWith("00")) {
                                City city = new City();
                                List<Area> areas = new ArrayList<Area>();
                                city.setName(cityName);
                                city.setCode(cityCode);
                                city.setAreaList(areas);
                                provinceList.get(i).getCityList().add(city);
                            }
                        }
                    }
                }
            }
        }

        //获取区县
        for (Province province : provinceList) {
            List<City> cities = province.getCityList();
            for (City city : cities) {
                //遍历获取县区
                String cityCode = city.getCode();
                String cityName = city.getName();
                for (int k = 0; k < stringCode.size(); k++) {
                    String areaName = stringName.get(k);
                    String areaCode = stringCode.get(k);
                    if (cityName.contains("北京") || cityName.contains("上海") || cityName.contains("天津") || cityName.contains("重庆")) {
                        if (!province.getCode().equals(areaCode) && areaCode.startsWith(province.getCode().substring(0, 2))) {
                            Area area = new Area();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            city.getAreaList().add(area);
                        }
                    } else {
                        if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
                            Area area = new Area();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            city.getAreaList().add(area);
                        }
                    }

                }

            }
        }


        //已经处理的数据移除
        List<String> stringNameList = new ArrayList<>(stringName);
        List<String> stringCodeList = new ArrayList<>(stringCode);
        for (Province province : provinceList) {
            stringNameList.remove(province.getName());
            stringCodeList.remove(province.getCode());
            List<City> cities = province.getCityList();
            for (City city : cities) {
                stringNameList.remove(city.getName());
                stringCodeList.remove(city.getCode());
                List<Area> listArea = city.getAreaList();
                for (Area area : listArea) {
                    stringNameList.remove(area.getName());
                    stringCodeList.remove(area.getCode());
                }
            }
        }

        //处理石河子 特殊 市，City Code 不以00结尾
        for (Province province : provinceList) {
            for (int k = 0; k < stringCodeList.size(); k++) {
                if (stringCodeList.get(k).startsWith(province.getCode().substring(0, 2))) {
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(stringNameList.get(k));
                    city.setCode(stringCodeList.get(k));
                    city.setAreaList(areas);
                    province.getCityList().add(city);
                }
            }
        }
        return provinceList;
    }

    public static List<Province> getProvincesFromJson() {
        if (null == mProvinces || mProvinces.size() == 0) {
            StringBuilder jsonSB = new StringBuilder();
            try {
                File file = new File(FileDirectoryUtil.getProvinceJsonPath());
                if(!file.exists()) return null;
                BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
