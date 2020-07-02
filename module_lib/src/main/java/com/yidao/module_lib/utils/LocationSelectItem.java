package com.yidao.module_lib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocationSelectItem {

    public interface  CallBack{
       void onCallBack(LocationCreateModel locationCreateModel, LocationCreateModel.CityListBean cityListBean, LocationCreateModel.CityListBean.CountyListBean countyListBean);
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    private Handler mHandler = new Handler(Looper.myLooper());

    //  省
    private List<LocationCreateModel> options1Items = new ArrayList<LocationCreateModel>();
    //  市
    private ArrayList<ArrayList<LocationCreateModel.CityListBean>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<LocationCreateModel.CityListBean.CountyListBean>>> options3Items = new ArrayList<>();

    private Context mContext;

    public void init(Context context) {
        mContext = context;
        parseData();
    }

    public boolean  show(){
        if (isParseDataFinish) {
            if (options1Items.size()==0) {
                parseData();
                return false;
            }else {
                showPickerView();
                return true;
            }
        }else {
            return false;
        }

    }


    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).city_name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                LogUtils.d("地址选择 " + tx);

                if (mCallBack != null) {
                    mCallBack.onCallBack(options1Items.get(options1),options2Items.get(options1).get(options2),options3Items.get(options1).get(options2).get(options3));
                }
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

//        pvOptions.setPicker(options1Items);//一级选择器

//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private boolean isParseDataFinish=false;
    /**
     * 解析数据并组装成自己想要的list
     */
    private void parseData() {
        isParseDataFinish=false;
        new Thread(){
            @Override
            public void run() {
                super.run();

                //获取assets目录下的json文件数据
                String jsonStr = getJson(mContext, "province.json");
                List<LocationCreateModel> shengList = JSON.parseArray(jsonStr, LocationCreateModel.class);
                //     把解析后的数据组装成想要的list
                options1Items = shengList;

//                遍历省
                for(int i = 0; i <shengList.size() ; i++) {
                    //存放城市
                    ArrayList<LocationCreateModel.CityListBean> cityList = new ArrayList<>();
                    //存放区
                    ArrayList<ArrayList<LocationCreateModel.CityListBean.CountyListBean>> province_AreaList = new ArrayList<>();
                    //省
                    LocationCreateModel lLocationCreateModel = shengList.get(i);
                    if (lLocationCreateModel.cityList != null) {
                        //遍历市
                        for(int c = 0; c < lLocationCreateModel.cityList.size() ; c++) {
                            List<LocationCreateModel.CityListBean> lCityList = lLocationCreateModel.cityList;
                            LocationCreateModel.CityListBean lCityListBean = lCityList.get(c);
                            //String cityName = lCityList.get(c).city_name;
                            cityList.add(lCityListBean);
                            ArrayList<LocationCreateModel.CityListBean.CountyListBean> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                            if (lCityList.get(c).countyList == null || lCityList.get(c).countyList.size() == 0) {
                                //city_AreaList.add("");
                                LocationCreateModel.CityListBean.CountyListBean lCountyListBean=new LocationCreateModel.CityListBean.CountyListBean();
                                lCountyListBean.city_name=lLocationCreateModel.city_name;
                                lCountyListBean.city_code=lLocationCreateModel.city_code;
                                city_AreaList.add(lCountyListBean);
                            } else {
                                List<LocationCreateModel.CityListBean.CountyListBean> lCountyList = lCityList.get(c).countyList;
                                for (LocationCreateModel.CityListBean.CountyListBean lCountyListBean : lCountyList) {
                                    city_AreaList.add(lCountyListBean);
                                }

                            }
                            province_AreaList.add(city_AreaList);
                        }
                    }else {
                        LocationCreateModel.CityListBean lCityListBean = new LocationCreateModel.CityListBean();
                        lCityListBean.city_name=lLocationCreateModel.city_name;
                        lCityListBean.city_code=lLocationCreateModel.city_code;
                        cityList.add(lCityListBean);

                        LocationCreateModel.CityListBean.CountyListBean lCountyListBean=new LocationCreateModel.CityListBean.CountyListBean();
                        lCountyListBean.city_name=lLocationCreateModel.city_name;
                        lCountyListBean.city_code=lLocationCreateModel.city_code;
                        ArrayList<LocationCreateModel.CityListBean.CountyListBean> city_AreaList = new ArrayList<>();
                        city_AreaList.add(lCountyListBean);
                        province_AreaList.add(city_AreaList);
                    }

                    /**
                     * 添加城市数据
                     */
                    options2Items.add(cityList);
                    /**
                     * 添加地区数据
                     */
                    options3Items.add(province_AreaList);
                }

                isParseDataFinish=true;
            }
        }.start();

    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
