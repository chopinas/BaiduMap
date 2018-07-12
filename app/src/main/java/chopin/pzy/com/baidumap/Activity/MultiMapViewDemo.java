package chopin.pzy.com.baidumap.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

import chopin.pzy.com.baidumap.R;

/**
 * 在一个Activity中显示多个map
 */
public class MultiMapViewDemo extends AppCompatActivity {

    private static final LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
    private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    private static final LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
    private static final LatLng GEO_SHENGZHENG = new LatLng(22.560, 114.064);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_map_view_demo);
        initMap();
    }

    private void initMap() {
        MapStatusUpdate statusUpdate1= MapStatusUpdateFactory.newLatLng(GEO_BEIJING);
        SupportMapFragment map1= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        map1.getBaiduMap().setMapStatus(statusUpdate1);

        MapStatusUpdate statusUpdate2= MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
        SupportMapFragment map2= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        map2.getBaiduMap().setMapStatus(statusUpdate2);

        MapStatusUpdate statusUpdate3= MapStatusUpdateFactory.newLatLng(GEO_GUANGZHOU);
        SupportMapFragment map3= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3);
        map3.getBaiduMap().setMapStatus(statusUpdate3);

        MapStatusUpdate statusUpdate4= MapStatusUpdateFactory.newLatLng(GEO_SHENGZHENG);
        SupportMapFragment map4= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map4);
        map4.getBaiduMap().setMapStatus(statusUpdate4);
    }
}
