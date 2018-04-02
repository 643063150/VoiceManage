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
        voiceManager=mBtRec.getVoiceManage();
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
        voiceManager.stopPlay();
    }
}
