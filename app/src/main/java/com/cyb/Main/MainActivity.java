package com.cyb.Main;
//crmpass1 jks

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cyb.App.App;
import com.cyb.App.BaseActivity;
import com.cyb.Dialog.CurrencyDialog;
import com.cyb.Entity.SystemBalanceEntity;
import com.cyb.Model.Crm.CrmFragment;
import com.cyb.Model.Customer.CallRecListFragment;
import com.cyb.Model.Customer.CustomerListFragment;
import com.cyb.Model.CustomerPool.CustomerPoolListFragment;
import com.cyb.Model.Message.MessageFragment;
import com.cyb.Model.My.MyFragment;
import com.cyb.Model.Customer.CallRecListFragment;
import com.cyb.Model.Statistics.StatisticsFragment;
import com.cyb.Model.TrafficMeasurement.TrafficMeasurementFragment;
import com.cyb.Model.audio.AudioFragment;
import com.cyb.R;
import com.cyb.Utils.AppToast;
import com.cyb.Utils.HttpUtils.BaseCallback;
import com.cyb.Utils.HttpUtils.HttpClient;
import com.cyb.Utils.SharedPrefUtil;
import com.cyb.ViewUtils.FragmentTabHost;
import com.cyb.ViewUtils.ProgressDialogUtil;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.ExtendedListener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;

import static com.cyb.Utils.UrlUtils.Url.DOMAIN_getSystemBalance;

//主页面
public class MainActivity extends BaseActivity {
    private String mTextviewArray[] = {"客户", "我的录音", "通话记录", "公海", "统计", "我的"};
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    //  可更具自己的需求是否创建多个fragment
    private Class mFragmentArray[] = {CustomerListFragment.class, AudioFragment.class, CallRecListFragment.class, CustomerPoolListFragment.class, StatisticsFragment.class, MyFragment.class};
    private int[] itemStyles = new int[]{R.drawable.maintab_item_ico1_style, R.drawable.maintab_item_ico1_style, R.drawable.maintab_item_ico2_style, R.drawable.maintab_item_ico2_style, R.drawable.maintab_item_ico3_style, R.drawable.maintab_item_ico4_style};
    //    退出时间
    private long exitTime = 0;

    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //布局填充，动态布局用到
        layoutInflater = LayoutInflater.from(this);
        // 找到TabHost选项卡
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //选项卡容器
        mTabHost.setup(mContext, getSupportFragmentManager(), R.id.realtabcontent);
        // 添加tab名称和图标Rft = doTabChanged(currentTab, ft)
        for (int i = 0; i < mTextviewArray.length; i++) {
//            fragmentView = getTabItemView(i);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator
                    (getTabItemView(i));
            Bundle bundle = new Bundle();
            bundle.putString("title", mTextviewArray[i]);
            mTabHost.addTab(tabSpec, mFragmentArray[i], bundle);
        }
        //TabWidget样式,消除中间的划分线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        //底部信息点击监听
        mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(0);
            }
        });
        //底部客户点击监听
        mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(1);
            }
        });
        //底部客户点击监听
        mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(2);
            }
        });
        //底部客户点击监听
        mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(3);
            }
        });
        mTabHost.getTabWidget().getChildAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabHost.setCurrentTab(4);
            }
        });

        //默认选中首页
        mTabHost.setCurrentTab(0);
    }


    /**
     * 给每一个Tabbutton设置图标和文字
     */
    public View getTabItemView(int i) {
        View view = layoutInflater.inflate(R.layout.act_maintab_tabhost, null);
        ImageView imageView = view.findViewById(R.id.iv_maintab_tabhost);
        imageView.setBackgroundResource(itemStyles[i]);
        TextView textView = view.findViewById(R.id.tv__maintab_tabhost);
        textView.setText(mTextviewArray[i]);
        return view;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            AppToast.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
