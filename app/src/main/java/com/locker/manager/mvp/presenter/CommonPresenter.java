package com.locker.manager.mvp.presenter;

import com.example.http_lib.bean.CreateDeviceBoxRequestBean;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.example.http_lib.bean.DeviceBoxInfoRequestBean;
import com.example.http_lib.bean.DeviceInfoRequestBean;
import com.example.http_lib.bean.UpdateDeviceBoxStatusRequestBean;
import com.locker.manager.mvp.model.CommomModel;
import com.yidao.module_lib.base.BasePressPlus;
import com.yidao.module_lib.base.ibase.IBaseView;

public class CommonPresenter extends BasePressPlus {

    private CommomModel mCommomModel;

    public CommonPresenter(IBaseView view) {
        super(view);
        mCommomModel = new CommomModel(this);
    }

    /**
     *  创建设备
     */
    public void createDevice(String deviceId){
        CreateDeviceRequestBean requestBean = new CreateDeviceRequestBean();
        requestBean.device_id = deviceId;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }

    /**
     *  根据设备id创建箱门
     */
    public void createDeviceBox(String deviceId,String boxNum){
        CreateDeviceBoxRequestBean requestBean = new CreateDeviceBoxRequestBean();
        requestBean.device_id = deviceId;
        requestBean.box_num = boxNum;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }

    /**
     *  获取设备信息
     */
    public void getDeviceInfo(String deviceId){
        DeviceInfoRequestBean requestBean = new DeviceInfoRequestBean();
        requestBean.device_id = deviceId;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }

    /**
     *  获取设备箱门信息
     */
    public void getDeviceBoxInfo(String deviceId,String boxNo){
        DeviceBoxInfoRequestBean requestBean = new DeviceBoxInfoRequestBean();
        requestBean.device_id = deviceId;
        requestBean.boxno = boxNo;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }

    /**
     *  更新设备箱门状态
     */
    public void updateDeviceBoxStatus(UpdateDeviceBoxStatusRequestBean requestBean){
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }
}
