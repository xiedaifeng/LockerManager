package com.locker.manager.mvp.presenter;

import com.example.http_lib.bean.CheckSmsRequestBean;
import com.example.http_lib.bean.CreateDeviceBoxRequestBean;
import com.example.http_lib.bean.CreateDeviceQrcodeRequestBean;
import com.example.http_lib.bean.CreateDeviceRequestBean;
import com.example.http_lib.bean.CreateOrderRequestBean;
import com.example.http_lib.bean.DeviceBoxInfoRequestBean;
import com.example.http_lib.bean.DeviceBoxTimeStatusRequestBean;
import com.example.http_lib.bean.DeviceInfoRequestBean;
import com.example.http_lib.bean.GetAllBoxDetailRequestBean;
import com.example.http_lib.bean.GetOrderInfoByPostNoRequestBean;
import com.example.http_lib.bean.GetOrderInfoRequestBean;
import com.example.http_lib.bean.GetUserInfoByMobileRequestBean;
import com.example.http_lib.bean.OpenDeviceBoxRequestBean;
import com.example.http_lib.bean.PayOrderRequestBean;
import com.example.http_lib.bean.PayQrCodeRequestBean;
import com.example.http_lib.bean.SendSmsRequestBean;
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


    /**
     *  取件
     */
    public void openDeviceBox(String device_id,String opencode){
        OpenDeviceBoxRequestBean requestBean = new OpenDeviceBoxRequestBean();
        requestBean.device_id = device_id;
        requestBean.opencode = opencode;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  平台判断箱门存取是否超时
     */
    public void getDeviceBoxTimeStatus(String device_id,String opencode){
        DeviceBoxTimeStatusRequestBean requestBean = new DeviceBoxTimeStatusRequestBean();
        requestBean.device_id = device_id;
        requestBean.opencode = opencode;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  根据手机号获取用户信息
     */
    public void getUserInfoByMobile(String mobile,String sign){
        GetUserInfoByMobileRequestBean requestBean = new GetUserInfoByMobileRequestBean();
        requestBean.mobile = mobile;
        requestBean.carry = sign;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  当前未存放的所有箱门号（大小 价格）
     */
    public void getAllBoxDetail(String device_id){
        GetAllBoxDetailRequestBean requestBean = new GetAllBoxDetailRequestBean();
        requestBean.device_id = device_id;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  创建订单
     */
    public void createOrder(CreateOrderRequestBean requestBean){
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  获取订单详情
     */
    public void getOrderInfo(String order_id){
        GetOrderInfoRequestBean requestBean = new GetOrderInfoRequestBean();
        requestBean.order_id = order_id;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  获取订单详情通过单号
     */
    public void getOrderInfoByPostNo(String post_no){
        GetOrderInfoByPostNoRequestBean requestBean = new GetOrderInfoByPostNoRequestBean();
        requestBean.post_no = post_no;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  发起支付
     */
    public void payOrder(String order_id,String pay_type){
        PayOrderRequestBean requestBean = new PayOrderRequestBean();
        requestBean.order_id = order_id;
        requestBean.pay_type = pay_type;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  获取支付二维码
     */
    public void getPayQrCode(String device_id,String opencode){
        PayQrCodeRequestBean requestBean = new PayQrCodeRequestBean();
        requestBean.device_id = device_id;
        requestBean.opencode = opencode;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }

    /**
     *  根据设备id 获取二维码
     */
    public void createDeviceQrcode(String device_id){
        CreateDeviceQrcodeRequestBean requestBean = new CreateDeviceQrcodeRequestBean();
        requestBean.device_id = device_id;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  发送短信验证码
     */
    public void sendSms(String mobile){
        SendSmsRequestBean requestBean = new SendSmsRequestBean();
        requestBean.mobile = mobile;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }


    /**
     *  验证短信验证码是否有效
     */
    public void checkSmsCode(String mobile,String code,String sign){
        CheckSmsRequestBean requestBean = new CheckSmsRequestBean();
        requestBean.mobile = mobile;
        requestBean.code = code;
        requestBean.carry = sign;
        mCommomModel.setBean(requestBean);
        mCommomModel.request(false);
    }
}
