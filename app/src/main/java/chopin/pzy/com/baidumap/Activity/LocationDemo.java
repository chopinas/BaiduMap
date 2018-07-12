package chopin.pzy.com.baidumap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import chopin.pzy.com.baidumap.R;

/**
 * 此demo需要使用到百度定位SDKjar包
 * 此demo用来展示如何结合定位SDK实现定位
 * 使用MyLocationOverlay绘制定位位置
 * 展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class LocationDemo extends AppCompatActivity {

    //定位相关
    LocationClient mLocaClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    MapView mMapView;
    BaiduMap mBaiduMap;

    //UI
    RadioGroup.OnCheckedChangeListener radioListner;
    Button requestButton;
    RadioGroup group;
    boolean isFirstLoc=true;//确认是否第一次定位


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_demo);
        initView();
        initListner();
    }

    private void initListner() {
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mCurrentMode){
                    case NORMAL:
                        requestButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfiguration(
                                new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker)
                        );
                        break;
                    case COMPASS:
                        requestButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfiguration(
                                new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker)
                        );
                        break;
                    case FOLLOWING:
                        requestButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfiguration(
                                new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker)
                        );
                        break;
                }
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.defaulticon:
                        //传入null，恢复默认图标
                        mCurrentMarker=null;
                        mBaiduMap.setMyLocationConfiguration(
                                new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker)
                        );
                        break;
                    case R.id.customicon:
                        mCurrentMarker= BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
                        mBaiduMap.setMyLocationConfiguration(
                                new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker)
                        );
                        break;

                }
            }
        });
    }

    private void initView() {

        mMapView=findViewById(R.id.bmapView);
        mBaiduMap=mMapView.getMap();
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocaClient=new LocationClient(this);
        mLocaClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocaClient.setLocOption(option);
        mLocaClient.start();


        requestButton = findViewById(R.id.button1);
        requestButton.setText("普通");
        group = findViewById(R.id.radioGroup);

        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    }


    /**
     * accuracy:设置定位数据的精度信息（米 ）
     * direction:设置定位数据的速度
     * latitude:设置定位数据的纬度
     * longitude:设置定位i数据的经度
     */
    public class MyLocationListenner implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation==null||mMapView==null){
                return;
            }
            MyLocationData locaData=new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                                                .direction(100).latitude(bdLocation.getLatitude())
                                                .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locaData);
            if(isFirstLoc){
                isFirstLoc=false;
                LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.setMapStatus(update);
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocaClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
