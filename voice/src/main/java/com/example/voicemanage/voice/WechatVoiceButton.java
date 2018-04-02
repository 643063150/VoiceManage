package com.example.voicemanage.voice;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WechatVoiceButton extends Button implements View.OnTouchListener {

    private Dialog recordIndicator;
    private ImageView mVolumeIv, mIvPauseContinue, mIvComplete;
    private VoiceLineView voicLine;
    private TextView mRecordHintTv;
    private Context mContext;
    private EnRecordVoiceListener enRecordVoiceListener;
    private VoiceManager voiceManager;
    private String filePath = Environment.getExternalStorageDirectory().getPath() + "/VoiceManager/audio";
    private float downY;
    private boolean isCanceled = false; // 是否取消录音
    public WechatVoiceButton(Context context) {
        super(context);
        init();
    }

    public WechatVoiceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    public WechatVoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        voiceManager = VoiceManager.getInstance(mContext);
        setOnTouchListener(this);
    }

    /**
     * 设置监听
     *
     * @param enRecordVoiceListener
     */
    public void setEnrecordVoiceListener(EnRecordVoiceListener enRecordVoiceListener) {
        this.enRecordVoiceListener = enRecordVoiceListener;
    }

    /*
     *获取voiceManager
     */
    public VoiceManager getVoiceManage() {
        return voiceManager;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 启动录音dialog
     */
    private void startRecordDialog() {
        recordIndicator = new Dialog(getContext(), R.style.record_voice_dialog);
        recordIndicator.setContentView(R.layout.dialog_record_voice);
        recordIndicator.setCanceledOnTouchOutside(false);
        recordIndicator.setCancelable(false);
        mVolumeIv = (ImageView) recordIndicator.findViewById(R.id.iv_voice);
        voicLine = (VoiceLineView) recordIndicator.findViewById(R.id.voicLine);
        mRecordHintTv = (TextView) recordIndicator.findViewById(R.id.tv_length);
        mRecordHintTv.setText("00:00:00");
        mIvPauseContinue = (ImageView) recordIndicator.findViewById(R.id.iv_continue_or_pause);
        mIvComplete = (ImageView) recordIndicator.findViewById(R.id.iv_complete);
        mIvPauseContinue.setVisibility(GONE);
        mIvComplete.setVisibility(GONE);
        recordIndicator.show();
//        //暂停或继续
//        mIvPauseContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (voiceManager != null) {
//                    voiceManager.pauseOrStartVoiceRecord();
//                }
//            }
//        });
//        //完成
//        mIvComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (voiceManager != null) {
//                    voiceManager.stopVoiceRecord();
//                }
//                recordIndicator.dismiss();
//            }
//        });
    }

//    @Override
//    public void onClick(View view) {
//        startRecordDialog();
//        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
//            @Override
//            public void recDoing(long time, String strTime) {
//                mRecordHintTv.setText(strTime);
//            }
//
//            @Override
//            public void recVoiceGrade(int grade) {
//                voicLine.setVolume(grade);
//            }
//
//            @Override
//            public void recStart(boolean init) {
//                mIvPauseContinue.setImageResource(R.drawable.icon_pause);
//                voicLine.setContinue();
//            }
//
//            @Override
//            public void recPause(String str) {
//                mIvPauseContinue.setImageResource(R.drawable.icon_continue);
//                voicLine.setPause();
//            }
//
//
//            @Override
//            public void recFinish(long length, String strLength, String path) {
//                if (enRecordVoiceListener != null) {
//                    enRecordVoiceListener.onFinishRecord(length, strLength, path);
//                }
//            }
//        });
//        voiceManager.startVoiceRecord(filePath);
//    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = motionEvent.getY();
                setText("上划取消录音");
                startRecordDialog();
                voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
                    @Override
                    public void recDoing(long time, String strTime) {
                        mRecordHintTv.setText(strTime);
                    }

                    @Override
                    public void recVoiceGrade(int grade) {
                        voicLine.setVolume(grade);
                    }

                    @Override
                    public void recStart(boolean init) {
//                        mIvPauseContinue.setImageResource(R.drawable.icon_pause);
                        voicLine.setContinue();
                    }

                    @Override
                    public void recPause(String str) {
//                        mIvPauseContinue.setImageResource(R.drawable.icon_continue);
                        voicLine.setPause();
                    }


                    @Override
                    public void recFinish(long length, String strLength, String path) {
                        if (enRecordVoiceListener != null) {
                            enRecordVoiceListener.onFinishRecord(length, strLength, path);
                        }
                    }
                });
                voiceManager.startVoiceRecord(filePath);
                break;
            case MotionEvent.ACTION_UP:
                setText("录音");
                if (isCanceled) {
                    if (voiceManager != null) {
                        voiceManager.cancelVoiceRecord();
                    }
                    recordIndicator.dismiss();
                } else {
                    if (voiceManager != null) {
                        voiceManager.stopVoiceRecord();
                    }
                    recordIndicator.dismiss();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (voiceManager != null) {
                    voiceManager.stopVoiceRecord();
                }
                recordIndicator.dismiss();
                break;
            case MotionEvent.ACTION_MOVE: // 滑动手指
                float moveY = motionEvent.getY();
                if (downY - moveY > 100) {
                    isCanceled = true;
                    setText("松开手指可取消录音");
                }
                if (downY - moveY < 20) {
                    isCanceled = false;
                }
                break;
        }
        return true;
    }

    /**
     * 结束回调监听
     */
    public interface EnRecordVoiceListener {
        void onFinishRecord(long length, String strLength, String filePath);
    }
}
