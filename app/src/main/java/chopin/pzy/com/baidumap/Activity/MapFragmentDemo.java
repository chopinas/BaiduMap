package chopin.pzy.com.baidumap.Activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.SupportMapFragment;

import chopin.pzy.com.baidumap.R;

public class MapFragmentDemo extends FragmentActivity {
    private static final String TAG=MapFragmentDemo.class.getSimpleName();
    SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragment_demo);
        MapStatus mapStatus=new MapStatus.Builder().overlook(-20).zoom(15).build();
        //设置是否允许指南针，默认允许。设置是否显示缩放控件
        BaiduMapOptions baiduMapOptions=new BaiduMapOptions().mapStatus(mapStatus)
                .compassEnabled(true).zoomControlsEnabled(true);
        map=SupportMapFragment.newInstance(baiduMapOptions);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();

    }
}
