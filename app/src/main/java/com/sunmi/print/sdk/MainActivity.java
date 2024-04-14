//package com.sunmi.print.sdk;
//
//import static com.example.javademo.Constants.FAST_BANK_APPID;
//import static com.example.javademo.Constants.WX_ALIPAY_APPID;
//import static com.example.javademo.Constants.url;
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.activity.ComponentActivity;
//import androidx.annotation.Nullable;
//
//import com.google.gson.Gson;
//import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import java.io.IOException;
//import java.util.Comparator;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class MainActivity extends ComponentActivity {
//    String appId = "wx196e8699161c87cb";//指定小程序id
////    IWXAPI wxapi;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//         wxapi = WXAPIFactory.createWXAPI(getApplicationContext(), appId);
//
//         //点击微信支付
//        findViewById(R.id.pay_wx).setOnClickListener(view -> {
//            pay(WX_ALIPAY_APPID,PayType.WX_LITE_H5, Constants.WX_ALIPAY_KEY);
//        });
//
//        //点击支付宝支付
//        findViewById(R.id.pay_alipay).setOnClickListener(view -> {
//            pay(WX_ALIPAY_APPID,PayType.ALI_LITE_H5, Constants.WX_ALIPAY_KEY);
//        });
//
//        //点击银联支付
//        findViewById(R.id.pay_bank).setOnClickListener(view -> {
//            //银联支付的签名跟微信支付宝不同 注意!
//            pay(FAST_BANK_APPID,PayType.BANK_FAST_JUMP, Constants.FAST_BANK_KEY);
//        });
//    }
//
//    public void pay(String appId, PayType payType, String signKey) {
//        new Thread() {
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//
//                MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");//设置请求类型
//
//                Map<String, Object> body = new TreeMap<>(new Comparator<String>() {
//                    @Override
//                    public int compare(String s, String t1) {
//                        return s.compareTo(t1);//按照key值升序
//                    }
//                });
//                body.put("mch_no", "1680160079");//商户号
//                body.put("app_id", appId);//应用ID 微信支付宝为同一id 银联支付是另一个id
//                body.put("mch_order_no", System.currentTimeMillis());//商户生成的订单号
//                body.put("way_code", payType.name());//支付方式
//                body.put("amount", (int) (0.01 * 100));//支付金额 单位分
//                body.put("currency", "cny");//三位货币代码,人民币
//                body.put("subject", "商品标题");//商品标题
//                body.put("body", "商品描述");//商品描述
//                body.put("req_time", System.currentTimeMillis());//请求接口时间,13位时间戳
//                body.put("version", "1.0");//接口版本号，固定：1.0
//                body.put("sign_type", "MD5");//签名类型，目前只支持MD5方式
//                body.put("sign", Sign.sign(body, signKey));//签名值
//
//                //拼接请求体
//                StringBuilder bodyStr = new StringBuilder();
//                Set<String> keySet = body.keySet();
//                for (String key : keySet) {
//                    Object value = body.get(key);
//                    bodyStr.append(key).append("=").append(value).append("&");
//                }
//                bodyStr.deleteCharAt(bodyStr.length() - 1);//amount=1&app_id=330794791220543488&body=商品描述&currency=cny&mch_no=1680160079&mch_order_no=1693741916985&req_time=1693741916985&sign=3B6231BF9ADD9B676CD1B63C8A1983FF&sign_type=MD5&subject=商品标题&version=1.0&way_code=WX_LITE_H5
//
//                Gson gson = new Gson();
//                RequestBody requestBody = RequestBody.create(bodyStr.toString(), mediaType);
//                Request request = new Request.Builder().url(url).post(requestBody).build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    if (response.isSuccessful() && response.body() != null) {
//                        String data = response.body().string();
//
//                        switch (payType) {
//                            case WX_LITE_H5:
//                                //服务器返回数据
//                                System.out.println("服务器返回数据 "  + data);
//                                //{"status":200,"msg":"成功","data":{"mch_order_no":"1693812544874","pay_order_id":"8430025781289156608","amount":1,"order_state":0,"pay_info":{"gh_id":"gh_99c0536eb852","path":"pages\/cashier\/cashier","scheme_code":"weixin:\/\/dl\/business\/?t=iYZ8wUz0Vhs"},"trans_status":"S"}}
//
//                                //根据服务器返回调用微信sdk
//                                LSPayResponse lsPayResponse = gson.fromJson(data, LSPayResponse.class);
//                                LSPayResponse.Data respData = lsPayResponse.getData();
//                                String userName = respData.getPay_info().getGh_id();
//                                //拼接地址 s=app 固定写死
//                                String path = respData.getPay_info().getPath() + "?pay_order_id=" + respData.getPay_order_id() + "&s=app";
//
//
//                                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//                                req.userName = userName;
//                                req.path = path;
//                                wxapi.sendReq(req);
//                                break;
//                            case ALI_LITE_H5:
//                                //服务器返回数据
//                                System.out.println("服务器返回数据 "  + data);
//                                //{"status":200,"msg":"成功","data":{"mch_order_no":"1693813320182","pay_order_id":"8430029033313402880","amount":1,"order_state":0,"pay_info":{"jump_url":"alipays:\/\/platformapi\/startapp?appId=2021004103603088&thirdPartSchema=app跳转链接&page=pages\/cashier\/cashier?pay_order_id%3D8430029033313402880%26s%3Dapp"},"trans_status":"S"}}
//                                //webView加载地址 把支付宝地址传入
//                                //"jump_url":"alipays:\/\/platformapi\/startapp?appId=2021004103603088&thirdPartSchema=app跳转链接&page=pages\/cashier\/cashier?pay_order_id%3D8430029033313402880%26s%3Dapp"
//
//                                //do something....
//                                break;
//                            case BANK_FAST_JUMP:
//                                //服务器返回数据
//                                System.out.println("服务器返回数据 "  + data);
//                                //{"status":200,"msg":"成功","data":{"pay_info":"https:\/\/spin.cloudpnr.com\/quickpay\/?hfSeqId=00470topo1A230904155519P523ac1363b700000&templateId=1","mch_order_no":"1693814118182","pay_order_id":"8430032380414132224","amount":1,"order_state":1,"trans_status":"S"}}
//
//                                //使用pay_info跳转指定地址
//                                //do something....
//                                break;
//                        }
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        }.start();
//    }
//
//
//
//}
