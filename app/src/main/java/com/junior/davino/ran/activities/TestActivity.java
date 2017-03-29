package com.junior.davino.ran.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.junior.davino.ran.R;
import com.junior.davino.ran.factories.GrammarFactory;
import com.junior.davino.ran.factories.ItemBuilderFactory;
import com.junior.davino.ran.factories.WordFilterFactory;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.ResultSummary;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.speech.InputRecognizer;
import com.junior.davino.ran.speech.MatchRecognizer;
import com.junior.davino.ran.speech.SpeechService;
import com.junior.davino.ran.speech.VoiceController;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Toaster;
import com.junior.davino.ran.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class TestActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private static final int ITEMSCOUNT = 24;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private boolean isRecording = false;

    private List<TestItem> items;
    Button btnSpeak;
    Toaster toaster = new Toaster();
    TimerUtil timerUtil = new TimerUtil();
    boolean listening;
    CountDownTimer countdownTimer;
    MaterialDialog processingDialog;
    EnumTestType testType;
    InputRecognizer inputRecognizer;
    List<String> wordsRecognition = new ArrayList<>();
    Button btn_back, btn_reset;

    private SpeechService mSpeechService;
    private MatchRecognizer matchRecognizer;
    private VoiceController voiceController;
    private String audioFilePath;


    private SpeechService.Listener mSpeechServiceListener = createServiceListener();

    private SpeechService.Listener createServiceListener() {
        return new SpeechService.Listener() {
            @Override
            public void onSpeechRecognized(final String text, final boolean isFinal) {
                Log.i(TAG, "onSpeechRecognized");
                if (isFinal) {
                    Log.i(TAG, "isFinal");
                    wordsRecognition.addAll(inputRecognizer.getRecognizedWordsByType(text));
                    finalizeTest();
                }
            }
        };
    }

    private ServiceConnection mServiceConnection = createServiceConnection();

    private ServiceConnection createServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.i(TAG, "Serviço de reconhecimento de voz conectado");
                mSpeechService = SpeechService.from(binder);
                mSpeechService.addListener(mSpeechServiceListener);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mSpeechService = null;
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i(TAG, "Null Intent extras");
        }

        verifyPermissions();
        testType = (EnumTestType) getIntent().getSerializableExtra("option");
        IItemBuilder builder = ItemBuilderFactory.createItemBuilder(testType);
        items = builder.buildItems(ITEMSCOUNT);
        RanTestFragment fragment = RanTestFragment.newInstance(testType, new ArrayList<TestItem>(getItems()));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();


        btnSpeak = (Button) findViewById(R.id.btn_start);
        setClickListeners();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        mSpeechServiceListener = createServiceListener();
        mServiceConnection = createServiceConnection();
        prepareTest();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");

        // Stop Cloud Speech API
        if (mSpeechService != null) {
            stopServiceConnection();
        }

        super.onStop();
    }

    private void verifyPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "PERMISSION OK!!");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

        String[] permissions = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        };

        for (String permission : permissions) {
            if (!Util.isPermissionOk(this, permission)) {
                Log.i(TAG, "Permission not granted: " + permission);
                finish();
            }
        }
    }

    private void setClickListeners() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_back.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btnSpeak.setOnClickListener(this);
    }


    public void onFragmentInteraction(Uri uri) {
    }


    private void reset() {
        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
        listening = false;
        if(voiceController != null){
            voiceController.stop();
        }

        if(mSpeechService != null){
            mSpeechService.stopSelf();
        }

        wordsRecognition.clear();
        timerUtil.reset();
    }

    private void stopRecording(){
        if(voiceController != null){
            voiceController.stopVoiceRecorder();
            voiceController = null;
        }
    }

    private void stopServiceConnection(){
        if(mServiceConnection != null && mSpeechService != null){
            unbindService(mServiceConnection);
            mSpeechService.stopSelf();
            mSpeechService.removeListener(mSpeechServiceListener);
        }
    }

    private void stopTest() {
        Log.i(TAG, "STOP LISTENENING");
        listening = false;
        timerUtil.stop();
        stopRecording();
        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);

        processingDialog = new MaterialDialog.Builder(TestActivity.this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        processingDialog.show();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
//        } else {
//            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
//        }
    }

    public void startTest() {
        btn_reset.setClickable(false);
        btn_reset.setBackgroundColor(getResources().getColor(R.color.light_red));

        if (voiceController == null) {
            voiceController = new VoiceController(mSpeechService, audioFilePath, GrammarFactory.createGrammar(this, testType).getGrammarItems());
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
//        } else {
//            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//        }

        countdownTimer = new CountDownTimer(3 * 1000, 750) {
            TextView countdownView = (TextView) findViewById(R.id.countdownTimer);
            boolean firstTick = true;


            @Override
            public void onTick(long millisUntilFinished) {
                if (firstTick) {
                    countdownView.setVisibility(View.VISIBLE);
                    performTick(3000);
                    firstTick = false;
                } else {
                    performTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                btn_reset.setClickable(true);
                btn_reset.setBackgroundColor(getResources().getColor(R.color.red_accent));
                Log.i(TAG, "START LISTENING");
                listening = true;
                countdownView.setVisibility(View.INVISIBLE);
                voiceController.startVoiceRecorder();
                changeStartButtonState(R.string.btFinalize, R.color.md_material_blue_600);
                timerUtil.start();
            }

            void performTick(long millisUntilFinished) {
                countdownView.setText("" + String.valueOf(Math.round(millisUntilFinished * 0.001f)));
            }
        };

        countdownTimer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            if (!listening) {
                startTest();
            } else {
                stopTest();
            }
        } else if (v.getId() == R.id.btn_back) {
            finish();
        } else {
            reset();
            toaster.showToastMessage(this, getString(R.string.reseted));
        }
    }

    private void changeStartButtonState(int stringResourceId, int colorId) {
        Button button = (Button) TestActivity.this.findViewById(R.id.btn_start);
        button.setText(getString(stringResourceId));
        button.setBackgroundColor(ContextCompat.getColor(this, colorId));
    }

    private void finalizeTest() {
        Log.i(TAG, "Finalizando teste");
        if (processingDialog != null && !processingDialog.isCancelled()) {
            processingDialog.dismiss();
        }

        int ellapsedTime = timerUtil.getLastResult();
        ResultSummary result = matchRecognizer.processTestResult(getItems(), wordsRecognition, ellapsedTime);

        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("option", testType);
        intent.putExtra("items", new ArrayList<>(getItems()));
        intent.putExtra("audioFilePath", audioFilePath);
        startActivity(intent);
    }


    private void prepareTest() {
        IGrammar grammar = GrammarFactory.createGrammar(this,testType);
        inputRecognizer = new InputRecognizer(WordFilterFactory.createWordFilter(this, testType, grammar.getMinLength(), grammar.getMaxLength()), getCharacterSplit());
        matchRecognizer = new MatchRecognizer(grammar);
        wordsRecognition = new ArrayList<>();
        prepareVoiceRecognition();
        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ran_test.pcm";
    }

    private String getCharacterSplit() {
        if (testType == EnumTestType.DIGITS || testType == EnumTestType.LETTERS) {
            return "";
        }

        return " ";
    }

    public List<TestItem> getItems() {
        return items;
    }

    private void prepareVoiceRecognition() {
        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    private void showPermissionMessageDialog() {
        //TODO: MessageDialogFragment
        //                .newInstance(getString(R.string.permission_message))
        //                .show(getSupportFragmentManager(), FRAGMENT_MESSAGE_DIALOG);
    }


}
