package com.yhj.Model.Customer;

import android.accessibilityservice.AccessibilityService;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yhj.Adpter.Customer.CustomerAdaper;
import com.yhj.Adpter.CustomerPool.CustomerPoolAdaper;
import com.yhj.App.App;
import com.yhj.App.BaseActivity;
import com.yhj.Entity.CustomerBean;
import com.yhj.Model.MakeCall.MakeCallActivity;
import com.yhj.R;
import com.yhj.Utils.AppToast;
import com.yhj.Utils.HttpUtils.BaseCallback;
import com.yhj.Utils.HttpUtils.HttpClient;
import com.yhj.ViewUtils.AbnormalView;

import org.xutils.common.Callback;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Index;
import static com.yhj.Utils.UrlUtils.Url.DOMAIN_Resource;

/**
 * Administrator  ：zhouyuru
 * 2020/10/22
 * Describe ：
 */
public class SearchActivity extends BaseActivity implements OnRefreshListener, OnRefreshLoadMoreListener {
    //    TextView title;
    String titles;
    //    输入框
    EditText search_et;
    //    内容
    String content;
    //   返回
    ImageView iv_fh;
    //    搜索
    LinearLayout ll_ss;
    private SmartRefreshLayout srl_refresh;
    private List<CustomerBean.Custome_list> custome_lists = new ArrayList<CustomerBean.Custome_list>();
    /**
     * 客户适配器
     */
    private CustomerAdaper customerAdaper;
    //    客户列表
    private RecyclerView rv_list;
    private int mPage = 1;
    private String url;
    //    客户池适配器
    private CustomerPoolAdaper customerPoolAdaper;
    AbnormalView av_nodata;
    ImageView iv_bd;

    @Override
    protected int getContentView() {
        return R.layout.search_activity;
    }

    @Override
    protected void initView() {
        titles = getIntent().getStringExtra("title");
        iv_bd = findViewById(R.id.iv_bd);
        iv_bd.setOnClickListener(this);
        srl_refresh = findViewById(R.id.srl_refresh);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.setOnRefreshLoadMoreListener(this);
        iv_fh = findViewById(R.id.iv_fh);
        iv_fh.setOnClickListener(this);
        ll_ss = findViewById(R.id.ll_ss);
        ll_ss.setOnClickListener(this);
        search_et = findViewById(R.id.search_et);
        rv_list = findViewById(R.id.rv_list);
        av_nodata = findViewById(R.id.av_nodata);
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //点击搜索要做的操作
                    content = search_et.getText().toString();
                    if (!TextUtils.isEmpty(content)) {
                        inData(1, content);
                    } else {
                        AppToast.showToast("请输入内容");
                    }
                    return true;

                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fh:
                finish();
                break;
            case R.id.ll_ss:
                content = search_et.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    inData(1, content);
                } else {
                    AppToast.showToast("请输入内容");
                }
                break;
            case R.id.iv_bd:
//                拨打电话页面
                App.startActivity(mContext, MakeCallActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        inData(1, content);

    }


    public void inData(int page, String content) {
        if (!TextUtils.isEmpty(App.getToken()) && !TextUtils.isEmpty(content)) {
            Map<String, Object> parame = new HashMap<>();
            parame.put("token", App.getToken());
            parame.put("p", page);
            parame.put("order_field", "create_time");
            parame.put("order_type", "asc");
            parame.put("csname", content);
            if (titles.equals("客户列表")) {
                url = DOMAIN_Index;

            } else if (titles.equals("公海列表")) {
                url = DOMAIN_Resource;
            }
            HttpClient.getInstance().post(mContext, url, parame, new BaseCallback<CustomerBean>(CustomerBean.class) {
                @Override
                public void onSuccess(CustomerBean result) {
                    if (result.getInfo().equals("success")) {
                        if (!srl_refresh.isLoading()) {
                            if (mPage == 1) {
                                custome_lists.clear();
                            }
                        }
                        if (result.getList() != null) {
                            custome_lists.addAll(result.getList());
                        }
                    } else {
                        AppToast.showToast(result.getInfo());
                    }


                }

                @Override
                public void onError(String msg) {
                    if (!srl_refresh.isLoading()) {
                        custome_lists.clear();
                    }
                    AppToast.showToast(msg);
                }

                @Override
                public void onCancelled(Callback.CancelledException var1) {
                }

                @Override
                public void onFinished() {
                    ShowView();
                }
            });
        }
    }

    private void ShowView() {
        if (srl_refresh.isRefreshing()) {
            srl_refresh.finishRefresh(500);
        }

        if (srl_refresh.isLoading()) {
            srl_refresh.finishLoadMore();
        }
        if (custome_lists != null && custome_lists.size() > 0) {
            if (titles.equals("客户列表")) {
                customerAdaper = new CustomerAdaper(mContext, R.layout.customer_item_activity, custome_lists, this);
                rv_list.setLayoutManager(new LinearLayoutManager(mContext));
                rv_list.setAdapter(customerAdaper);
            } else if (titles.equals("公海列表")) {
                customerPoolAdaper = new CustomerPoolAdaper(mContext, R.layout.customer_pool_item_activity, custome_lists);
                rv_list.setLayoutManager(new LinearLayoutManager(mContext));
                rv_list.setAdapter(customerPoolAdaper);
            }
            av_nodata.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);

        } else {
            av_nodata.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        }
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mPage++;
        inData(mPage, content);
    }

    @Override
    public void onResume() {
        super.onResume();
        inData(mPage, content);
    }
}
