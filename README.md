# VoiceManage
两种形式的录音：
1.第一种是RecordVoiceButton  传统的录音方式，支持暂停。
2.第二种是WechatVoiceButton  类似与微信的录音方式。
参考了https://blog.csdn.net/QQ55214/article/details/50834069和https://github.com/jaydenxiao2016/VoiceManager 的代码进行了改写

导入：
### Gradle

```java
compile 'com.googfily:voicemanage:1.1.0'
```
具体使用方法
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    android:orientation="vertical">
    <com.example.voicemanage.voice.RecordVoiceButton
        android:id="@+id/button_rec"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_margin="10dp"
        android:background="@drawable/round_selector"
        android:text="录音"
        android:textColor="#ffffff"
        android:padding="8dp" />

    <com.example.voicemanage.voice.WechatVoiceButton
        android:id="@+id/button_rec1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_margin="10dp"
        android:background="@drawable/round_selector"
        android:text="录音"
        android:textColor="#ffffff"
        android:padding="8dp"/>
    <ListView
        android:id="@+id/lv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="10dp"
        ></ListView>
```
Activity里
```java
package com.example.voicemanage.voicemanage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.voicemanage.voice.RecordVoiceButton;
import com.example.voicemanage.voice.Voice;
import com.example.voicemanage.voice.VoiceAdapter;
import com.example.voicemanage.voice.VoiceManager;
import com.example.voicemanage.voice.WechatVoiceButton;

public class MainActivity extends AppCompatActivity {
    /**
     * 语音列表
     */
    private ListView listView;
    /**
     * 开始录音
     */
    private VoiceAdapter adapter;
    private RecordVoiceButton mBtRec;
    VoiceManager voiceManager;
    private WechatVoiceButton mBtRec1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtRec = (RecordVoiceButton) findViewById(R.id.button_rec);
        mBtRec1 = (WechatVoiceButton) findViewById(R.id.button_rec1);
        listView = (ListView) findViewById(R.id.lv);
        adapter=new VoiceAdapter(this);
        listView.setAdapter(adapter);
        //获取voiceManager
        voiceManager=mBtRec.getVoiceManage();
        //设置文件位置，文件名是自动生成的
        mBtRec.setFilePath(Environment.getExternalStorageDirectory().getPath() + "/TestVoice/audio");
        mBtRec.setEnrecordVoiceListener(new RecordVoiceButton.EnRecordVoiceListener() {
            @Override
            public void onFinishRecord(long length, String strLength, String filePath) {
                adapter.add(new Voice(length, strLength, filePath) )                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             ;
            }
        });
        mBtRec1.setEnrecordVoiceListener(new WechatVoiceButton.EnRecordVoiceListener() {
            @Override
            public void onFinishRecord(long length, String strLength, String filePath) {
                adapter.add(new Voice(length, strLength, filePath) );
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出时停止播放
        voiceManager.stopPlay();
    }
}

```

</LinearLayout>
