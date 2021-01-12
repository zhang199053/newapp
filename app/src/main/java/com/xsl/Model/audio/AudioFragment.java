package com.xsl.Model.audio;

import android.app.DatePickerDialog;
import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.maxi.audiotools.AudioPlayCallBack;
import com.maxi.audiotools.IMAudioManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xsl.App.App;
import com.xsl.App.BaseFragment;
import com.xsl.Entity.AudioBean;
import com.xsl.R;
import com.xsl.Utils.AppToast;
import com.xsl.Utils.HttpUtils.BaseCallback;
import com.xsl.Utils.HttpUtils.HttpClient;
import com.xsl.Utils.SystemUtil;
import com.xsl.Utils.UniqueIDUtils;
import com.xsl.Utils.UrlUtils.Url;
import com.xsl.ViewUtils.AbnormalView;
import com.xsl.ViewUtils.ProgressDialogUtil;

import org.xutils.common.Callback;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.codego.adapter.SingleAdapter;
import me.codego.adapter.ViewHolder;

import static com.xsl.Utils.UrlUtils.Url.AUDIO_Index;

public class AudioFragment extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView tvSelectDate;
    private int mPage = 1;
    private AbnormalView avNoDataView;
    private SingleAdapter<String> adapter;
    private String date = "";

    @Override
    protected int setContentView() {
        return R.layout.fragment_audio;
    }

    @Override
    protected void init() {
        Log.d("TEST", "init:" + Url.ip);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        avNoDataView = rootView.findViewById(R.id.av_nodata);
        tvSelectDate = rootView.findViewById(R.id.tvSelectDate);
        IMAudioManager.instance().init(getActivity());
        initAdapter();
        loadServerData(false);
        initEvent();
    }

    @Override
    protected void lazyLoad() {
    }

    private void initEvent() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadServerData(true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadServerData(false);
            }
        });
        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
    }

    private void initAdapter() {
        adapter = new SingleAdapter<String>(R.layout.list_item_audio, new Function1<ViewHolder<String>, Unit>() {
            @Override
            public Unit invoke(ViewHolder<String> viewHolder) {
                final String data = viewHolder.getData();
                TextView tvNo = viewHolder.getView(R.id.tvNo);
                TextView tvName = viewHolder.getView(R.id.tvName);
                ImageView ivPause = viewHolder.getView(R.id.ivPause);
                ImageView ivPlay = viewHolder.getView(R.id.ivPlay);
                int position = viewHolder.getPosition();
                tvNo.setText((position + 1) + "");
                tvName.setText(data);
                ivPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playAudio(data);
                    }
                });
                ivPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseAudio(data);
                    }
                });
                return Unit.INSTANCE;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void playAudio(String audioUrl) {
        ProgressDialogUtil.getInstance().startLoad(getActivity());
        IMAudioManager.instance().playSound(audioUrl, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                AppToast.showToast("播放结束");
            }
        }, new AudioPlayCallBack() {
            @Override
            public void playError(String msg) {
                AppToast.showToast(msg);
                ProgressDialogUtil.getInstance().stopLoad();
            }

            @Override
            public void startPlay() {
                ProgressDialogUtil.getInstance().stopLoad();
            }
        });
    }

    private void pauseAudio(String audioUrl) {
        IMAudioManager.instance().pause();
    }

    private void loadServerData(final boolean isLoadMore) {
        if (isLoadMore) {
            mPage += 1;
        } else {
            mPage = 1;
        }
        Log.d("TEST", "请求数据1:");
        String deviceId =  UniqueIDUtils.getUniqueID(getActivity());
        Map<String, Object> parame = new HashMap<>();
//        parame.put("token", "58a86c197b838a09415ac91139c7671c");
        parame.put("token", App.getToken());
        parame.put("page", mPage + "");
        parame.put("deviceNo", deviceId);//0bfd826810c76cd9
//        parame.put("deviceNo", "16eefaf47d0c8bdc");//0bfd826810c76cd9
        parame.put("pageLimit", "20");
        String currentDate = SystemUtil.stampToDate(System.currentTimeMillis(), "yyyy-MM-dd");
        String commitDate = TextUtils.isEmpty(date) ? currentDate : date;
        parame.put("date", commitDate);
//        parame.put("date", "2020-12-31");//2020-12-14
        Log.d("TEST", "请求参数:" + parame.toString());
        HttpClient.getInstance().post(mContext, AUDIO_Index, parame, new BaseCallback<AudioBean>(AudioBean.class) {
            @Override
            public void onSuccess(AudioBean result) {
                Log.d("TEST", "返回数据:" + result.info + "--数据2:" + result.data.file_path.toString());
                String datas = new Gson().toJson(result);
                Log.e("json=====", datas);
                if (result.info.equals("success")) {
                    List<String> file_path = result.data.file_path;
                    if (null != file_path && file_path.size() > 0) {
                        if (isLoadMore) {
                            adapter.append(file_path);
                        } else {
                            adapter.setData(file_path);
                        }
                        refreshLayout.setEnableLoadMore(file_path.size() >= 20);
                        avNoDataView.setVisibility(View.GONE);
                    } else {
                        if (isLoadMore) {
                            AppToast.showToast("暂无更多数据");
                        } else {
                            avNoDataView.setVisibility(View.VISIBLE);
                            adapter.clear();
                        }
                    }
                } else {
                    AppToast.showToast(result.info);
                }
                finishRefreshView();
            }

            @Override
            public void onError(String msg) {
                Log.d("TEST", "返回数据错误:" + msg);
                finishRefreshView();
                AppToast.showToast(msg);
            }

            @Override
            public void onCancelled(Callback.CancelledException var1) {

            }

            @Override
            public void onFinished() {
                finishRefreshView();
            }
        });
    }

    private void finishRefreshView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        IMAudioManager.instance().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMAudioManager.instance().release();
    }


    private DatePickerDialog dialog = null;
    private int year = 0;
    private int month = 0;
    private int day = 0;


    private void showDate() {
        //获取当前年月日
        Calendar calendar = Calendar.getInstance();
        if (year <= 0) {
            year = calendar.get(Calendar.YEAR);//当前年
            month = calendar.get(Calendar.MONTH);//当前月
            day = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        }
        //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
        dialog = new DatePickerDialog(mContext, dateListener, year, month, day);
        dialog.show();
    }

    /**
     * 日期选择的回调监听
     */
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year1, int monthOfYear1, int dayOfMonth1) {
            Log.i("TEST", year1 + "年" + (monthOfYear1 + 1) + "月" + dayOfMonth1 + "日");//这里月份是从0开始的,所以monthOfYear的值是0时就是1月.以此类推,加1就是实际月份了.
            int newMonth = monthOfYear1 + 1;
            String month = newMonth < 10 ? ("0" + newMonth) : (newMonth + "");
            String day = dayOfMonth1 < 10 ? ("0" + dayOfMonth1) : (dayOfMonth1 + "");
            date = year1 + "-" + month + "-" + day;
            tvSelectDate.setText(date);
            Log.d("TEST", "当前选择日期" + date);
            if (null != dialog) {
                dialog.dismiss();
            }
            loadServerData(false);
        }
    };

}
