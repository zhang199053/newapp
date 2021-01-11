//package com.cyb.Model.SYSFragment;
//
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.cyb.App.App;
//import com.cyb.App.BaseFragment;
//import com.cyb.Model.Customer.CustomerDetailsActivity;
//import com.cyb.Model.Customer.CustomerListActivity;
//import com.cyb.Model.CustomerPool.CustomerPoolListActivity;
//import com.cyb.Model.My.PersonalDetailsActivity;
//import com.cyb.R;
//
///**
// * Administrator  ：zhouyuru
// * 2020/9/25
// * Describe ：应用页面
// */
//public class SYSAppFragment extends BaseFragment implements View.OnClickListener {
//    private LinearLayout ll_customer;
//    private LinearLayout ll_customer_pool;
//
//    @Override
//    protected int setContentView() {
//        return R.layout.sys_app_fragment;
//    }
//
//    @Override
//    protected void init() {
//        ll_customer = rootView.findViewById(R.id.ll_customer);
//        ll_customer.setOnClickListener(this);
//        ll_customer_pool = rootView.findViewById(R.id.ll_customer_pool);
//        ll_customer_pool.setOnClickListener(this);
//    }
//
//    @Override
//    protected void lazyLoad() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_customer:
////                客户列表页面
//                App.startActivity(mContext, CustomerListActivity.class);
//                break;
//            case R.id.ll_customer_pool:
////                客户列表页面
//                App.startActivity(mContext, CustomerPoolListActivity.class);
//                break;
//            default:
//                break;
//
//
//        }
//    }
//}
