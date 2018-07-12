package chopin.pzy.com.baidumap.Activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chopin.pzy.com.baidumap.R;

/**
 * 热力图
 */
public class HeatMapDemo extends AppCompatActivity {
    private static final String TAG=HeatMapDemo.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private HeatMap mHeatMap;
    private Button mAdd;
    private Button mRemove;
    private boolean isDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat_map_demo);
        initView();
        initListner();
    }

    private void addHeatMap() {
        @SuppressLint("HandlerLeak") final Handler mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 111&&!isDestroy){
                    mBaiduMap.addHeatMap(mHeatMap);
                }
                mAdd.setEnabled(false);
                mRemove.setEnabled(true);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                mHeatMap=new HeatMap.Builder().data(getLocations()).build();
                mHandler.sendEmptyMessage(111);
            }
        }).start();
    }

    private void initListner() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHeatMap();
            }
        });
        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeatMap.removeHeatMap();
                mAdd.setEnabled(true);
                mRemove.setEnabled(false);            }
        });
    }

    private void initView() {
        mMapView =findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(5));
        mAdd = findViewById(R.id.add);
        mRemove = findViewById(R.id.remove);
        mAdd.setEnabled(false);
        mRemove.setEnabled(false);

        addHeatMap();
    }

    private List<LatLng> getLocations() {
        List<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(R.raw.locations);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array;
        try {
            array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("lat");
                double lng = object.getDouble("lng");
                list.add(new LatLng(lat, lng));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }
}
