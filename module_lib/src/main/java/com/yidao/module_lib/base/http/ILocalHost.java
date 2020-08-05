package com.yidao.module_lib.base.http;

/**
 * Created by dream on 2017/7/20.
 * 请求地址的配置
 */

public interface ILocalHost {


    String getVersion = "/p-80101"; //版本


    String createDevice = "/Apidevice/update_device";  //创建设备

    String createDeviceBox = "/Apidevice/update_device_box";  //根据设备id创建箱门

    String getDeviceInfo = "/Apidevice/get_device_info";  //获取设备信息

    String getDeviceBoxInfo = "/Apidevice/get_device_box_info";  //获取设备箱门信息

    String updateDeviceBoxStatus = "/Apidevice/update_device_box_openstatus";  //更新设备箱门状态

    String openDeviceBox = "/Apidevice/open_device_box";  //取件

    String getDeviceOrderStatus = "/Apidevice/get_device_order_status";  //平台判断箱门是否超时

    String getUserInfoByMobile = "/Apidevice/get_user_by_mobile";  //根据手机号获取用户信息

    String getAllBoxInfo = "/Apidevice/get_all_box_total";  //当前未存放的所有箱门号（大小 价格）

    String createOrder = "/Apiorder/create_order";  //创建订单

    String getOrderInfo = "/Apiorder/get_order_info";  //获取订单详情

    String payOrder = "/Apiorder/pay_oroder";  //发起支付

    String getpayQrCode = "/Apiorder/get_pay_qrcode";  //获取支付二维码

    String getOrderInfoByPostNo = "/Apiorder/get_order_info_by_post_no";  //根据快递单号获取订单详情

    String createDeviceQrcode = "/Apidevice/create_device_qrcode";  //根据设备id 获取二维码

    String sendSms = "/Apiindex/send_sms";  //发送短信验证码

    String checkSmsCode = "/Apiindex/check_code";  //验证短信验证码是否有效

    String backOrder = "/Apiorder/back_order";  //退回






    String myHostUrl = "http://txe.ralfqbk.xyz/index.php?s=";
}
