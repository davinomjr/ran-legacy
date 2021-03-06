package com.junior.davino.ran.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DatabaseReference;
import com.junior.davino.ran.R;
import com.junior.davino.ran.factories.GrammarFactory;
import com.junior.davino.ran.factories.ItemBuilderFactory;
import com.junior.davino.ran.factories.WordFilterFactory;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.TestResult;
import com.junior.davino.ran.models.TestUser;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.speech.InputRecognizer;
import com.junior.davino.ran.speech.MatchRecognizer;
import com.junior.davino.ran.speech.SpeechService;
import com.junior.davino.ran.speech.VoiceController;
import com.junior.davino.ran.utils.Constants;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Util;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;
    private static final int REQUEST_INTERNET_PERMISSION = 2;
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION = 3;
    private static final int REQUEST_READ_EXTERNAL_PERMISSION = 4;

    private static final int MULTIPLE_CODE_PERMISSION = 5;

    private TestUser currentTestUser;
    private List<TestItem> items;
    private Button btnSpeak;
    private TimerUtil timerUtil = new TimerUtil();
    private boolean listening;
    private CountDownTimer countdownTimer;
    private MaterialDialog processingDialog;
    private EnumTestType testType;
    private InputRecognizer inputRecognizer;
    private List<String> wordsRecognition = new ArrayList<>();
    private Button btn_back, btn_reset;

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
        currentTestUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        IItemBuilder builder = ItemBuilderFactory.createItemBuilder(testType);
        items = builder.buildItems(Constants.ITEMSCOUNT);
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
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO
        };

        ActivityCompat.requestPermissions(this, permissions, MULTIPLE_CODE_PERMISSION);
//
//        for (String permission : permissions) {
//            if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
//                Log.i(TAG, "PERMISSION " + permission +  " OK!");
//            }
//            else{
//                Log.i(TAG, "Permission not granted, requesting permission...");
//                ActivityCompat.requestPermissions(this, new String[]{permission}, getPermissionCode(permission));
//            }
//        }
    }

    private int getPermissionCode(String permission){
        switch(permission){
            case Manifest.permission.RECORD_AUDIO:
                return REQUEST_RECORD_AUDIO_PERMISSION;
            case Manifest.permission.INTERNET:
                return REQUEST_INTERNET_PERMISSION;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return REQUEST_READ_EXTERNAL_PERMISSION;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return REQUEST_WRITE_EXTERNAL_PERMISSION;
            default: throw new IndexOutOfBoundsException("permission");
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
            showSnackBar(getString(R.string.reseted));
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
        TestResult result = matchRecognizer.processTestResult(getItems(), testType, wordsRecognition, ellapsedTime, audioFilePath);
        addTestResult(result);

        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("result", Parcels.wrap(result));
        intent.putExtra("items", Parcels.wrap(new ArrayList<>(getItems())));
        startActivity(intent);
        finish();
    }

    private void addTestResult(TestResult result){
        currentTestUser.addResult(result);
        DatabaseReference testResultsReference = firebaseApp.getUsersQuery().child(currentTestUser.getUserId()).child("testUsers").child(currentTestUser.getTestUserId()).child("testResults");
        String key = testResultsReference.push().getKey();
        result.setResultId(key);
        testResultsReference.child(key).setValue(result);
        testResultsReference.child(key).child("testItems").setValue(items);
    }


    private void prepareTest() {
        IGrammar grammar = GrammarFactory.createGrammar(this,testType);
        inputRecognizer = new InputRecognizer(WordFilterFactory.createWordFilter(this, testType, grammar.getMinLength(), grammar.getMaxLength()), getCharacterSplit());
        matchRecognizer = new MatchRecognizer(grammar);
        wordsRecognition = new ArrayList<>();
        prepareVoiceRecognition();
        audioFilePath = generateAudioUserPath();
        Log.i(TAG, "FILEPATHGENERATED = " + audioFilePath);
    }

    private String generateAudioUserPath(){
        String currentDate = Util.getCurrentDateTimeFormatted();
        String currentUserTestName = currentTestUser.getName().trim();
        String prefix = "/";
        String extension = ".pcm";

        StringBuilder pathBuilder = new StringBuilder(prefix);
        pathBuilder.append(Constants.RANTEST);
        pathBuilder.append("_");
        pathBuilder.append(currentUserTestName);
        pathBuilder.append("_");
        pathBuilder.append(currentDate.replace(":", "").replace("/", "_"));
        pathBuilder.append(extension);
        return pathBuilder.toString();
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
