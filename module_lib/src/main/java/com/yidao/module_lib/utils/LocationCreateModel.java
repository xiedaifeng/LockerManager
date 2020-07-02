package com.yidao.module_lib.utils;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

public class LocationCreateModel implements Serializable, IPickerViewData {


    //省
    public String city_code;
    public String city_name;
    public String grade;
    public String parent_code;
    public List<CityListBean> cityList;

    @Override
    public String getPickerViewText() {
        return city_name;
    }
    //市
    public static class CityListBean implements IPickerViewData {
        public String city_code;
        public String city_name;
        public String grade;
        public String parent_code;
        public List<CountyListBean> countyList;
        @Override
        public String getPickerViewText() {
            return city_name;
        }
        //区
        public static class CountyListBean implements IPickerViewData {

            public String city_code;
            public String city_name;
            public String grade;
            public String parent_code;
            @Override
            public String getPickerViewText() {
                return city_name;
            }
        }
    }
}
