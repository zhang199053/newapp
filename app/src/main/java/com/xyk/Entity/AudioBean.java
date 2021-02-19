package com.xyk.Entity;

import java.io.Serializable;
import java.util.List;

public class AudioBean implements Serializable {
//"file_path": [oss录音路径<array>
//"http://mi-mp3.oss-cn-shanghai.aliyuncs.com/audio/0bfd826810c76cd9/13305195270/2020-12-14/13305195270 2020-12-14 09-50-46.m4a"
//        ],
//        "total": 65总数 <number>
//},
//        "info": "success",... <string>
//"status": 11：成功 <number>


    public String status;
    public String info;
    //    public List<String> data;
    public AudioItemBean data;


}
