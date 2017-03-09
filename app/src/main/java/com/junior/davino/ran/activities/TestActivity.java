package com.junior.davino.ran.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.junior.davino.ran.R;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.models.ResultItem;
import com.junior.davino.ran.models.ResultSummary;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Toaster;
import com.junior.davino.ran.utils.Util;
import com.junior.davino.ran.utils.VoiceUtil;
import com.junior.davino.ran.utils.builders.FactoryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class TestActivity extends FragmentActivity implements RanTestFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "TestActivity";
    private static final int ITEMSCOUNT = 24;

    private List<Item> items;
    SpeechRecognizer testRecognizer;
    Button btnSpeak;
    Toaster toaster = new Toaster();
    TimerUtil timerUtil = new TimerUtil();
    int qtdAcertos = 0;
    boolean listening;
    Intent intent;
    boolean testFinalized = false;
    CountDownTimer countdownTimer;
    MaterialDialog processingDialog;
    int current_volume;
    EnumTestType testType;
    String characterSplit;

    RecognitionListener testRecognitionnListener;

    private AudioManager mAudioManager;
    private int mStreamVolume = 0;

    List<String> wordsRecognition = new ArrayList<>();
    Button btn_back, btn_reset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            Log.i(TAG, "Null Intent extras");
        }

        prepareTest();
        RanTestFragment fragment = RanTestFragment.newInstance(testType);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();


        btnSpeak = (Button) findViewById(R.id.btn_start);
        setClickListeners();
        checkVoiceRecognition(btnSpeak);
        if (!VoiceUtil.isPermissionOk(this)) {
            toaster.showToastMessage(this, getString(R.string.voiceRecognitionNotPresent));
            finish();
        }

        btnSpeak.setOnClickListener(this);
        intent = buildVoiceRecognitionIntent();
        testRecognitionnListener = new VoiceRecognitionListener();
        testRecognizer = createRecognizer(testRecognitionnListener);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // getting system volume into var for later un-muting
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0); // setting system volume to zero, muting
    }

    private void setClickListeners(){
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_back.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }


    private Intent buildVoiceRecognitionIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra("android.speech.extra.DICTATION_MODE", true);
        return intent;
    }

    private SpeechRecognizer createRecognizer(RecognitionListener listener) {
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(listener);
        return recognizer;
    }

    private void restartVoiceTestRecognizer() {
        if (testRecognizer == null) {
            testRecognizer = createRecognizer(testRecognitionnListener);
        }

        testRecognizer.cancel();
        testRecognizer.startListening(intent);
    }


    private void destroyListeningRecognizer(SpeechRecognizer recognizer) {
        if (recognizer != null) {
            Log.i(TAG, "Destroying recognizer");
            recognizer.destroy();
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (testRecognizer != null) {
            testRecognizer.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        if (testRecognizer != null) {
            testRecognizer.destroy();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        if (testRecognizer == null) {
            testRecognizer = createRecognizer(testRecognitionnListener);
        }

        reset();
        super.onResume();
    }

    public void checkVoiceRecognition(Button btnSpeak) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            btnSpeak.setEnabled(false);
            Toast.makeText(this, R.string.voiceRecognitionNotPresent, Toast.LENGTH_LONG).show();
        }
    }

    public void onFragmentInteraction(Uri uri) {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private void reset() {
        listening = false;
        wordsRecognition.clear();
        destroyListeningRecognizer(testRecognizer);
        testRecognizer = createRecognizer(testRecognitionnListener);
        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
        timerUtil.reset();
    }

    private void stopTest() {
        Log.i(TAG, "STOP LISTENENING");
        listening = false;
        timerUtil.stop();
        testRecognizer.stopListening();

        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
        processingDialog = new MaterialDialog.Builder(TestActivity.this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        processingDialog.show();

    }

    public void startTest() {
        btn_reset.setClickable(false);
        btn_reset.setBackgroundColor(getResources().getColor(R.color.light_red));
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
                Log.i(TAG, "START LISTENENING");
                listening = true;
                countdownView.setVisibility(View.INVISIBLE);
                testRecognizer.startListening(intent);
                changeStartButtonState(R.string.btFinalize, R.color.md_material_blue_600);
                timerUtil.start();
                testFinalized = false;
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
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() { // Tempo para realizacao de reconhecimento (onResults)
                        if(!testFinalized){
                            finalizeTest();
                        }
                    }
                },2500);
            }
        } else if (v.getId() == R.id.btn_back) {
            destroyListeningRecognizer(testRecognizer);
            finish();
        } else {
            reset();
            toaster.showToastMessage(this, getString(R.string.reseted));
        }
    }

    private void changeStartButtonState(int stringResourceId, int colorId) {
        Button button = (Button) TestActivity.this.findViewById(R.id.btn_start);
        button.setText(getString(stringResourceId));
        button.setBackgroundColor(getResources().getColor(colorId));
    }

    private void finalizeTest() {
        Log.i(TAG, "Finalizando teste");
        testFinalized = true;
        if (processingDialog != null && !processingDialog.isCancelled()) {
            processingDialog.dismiss();
        }

        int ellapsedTime = timerUtil.getLastResult();

        List<Item> itemsClone = new ArrayList<Item>(getItems());

        List<String> words = new ArrayList<String>(wordsRecognition);
        List<String> matchWords = new ArrayList();
        List<String> missesWords = new ArrayList();

        Iterator<Item> iterator = itemsClone.iterator();
        Iterator<String> itWords = words.iterator();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            while (itWords.hasNext()) {
                String nextWord = itWords.next();
                Log.i(TAG, "ITEM SENDO COMPARADO = " + item.getName());
                Log.i(TAG, "PALAVRA SENDO COMPARADO = " + nextWord);
                if (Util.isEqualStripAccentsIgnoreCase(item.getName(), nextWord)) {
                    matchWords.add(nextWord.toLowerCase());
                    Log.i(TAG, " ITEMS LEFT:" + String.valueOf(itemsClone.size()));
                    iterator.remove();
                    itWords.remove();
                    break;
                } else {
                    missesWords.add(nextWord.toLowerCase());
                }
            }
        }

        ResultSummary resultSummary = new ResultSummary();
        resultSummary.setResultTime(ellapsedTime);
        resultSummary.setWords(wordsRecognition);
        resultSummary.setMatchWords(matchWords);
        resultSummary.setMissesWords(missesWords);
        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra("Result", resultSummary);
        intent.putExtra("option", testType);
        startActivity(intent);
//        finish(); Descomentar apenas se necessario voltar para a tela de testes
    }

    private void prepareTest(){
        testType = (EnumTestType) getIntent().getSerializableExtra("option");
        IItemBuilder builder = FactoryBuilder.createItemBuilder(testType);
        items = builder.buildItems(ITEMSCOUNT);
        setCharacterSplit();
    }

    private void setCharacterSplit(){
        if(testType == EnumTestType.DIGITS){
            characterSplit = "";
        }
        else{
            characterSplit = " ";
        }
    }

    public class VoiceRecognitionListener implements RecognitionListener {


        public void onReadyForSpeech(Bundle params) {
            Log.i(TAG, "onReadyForSpeech TEST");
        }

        public void onBeginningOfSpeech() {
            Log.i(TAG, "onBeginningOfSpeech TEST");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged TEST");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived TEST");
        }

        public void onEndOfSpeech() {
            Log.i(TAG, "onEnndOfSpeech TEST");
            if (listening) {
                return;
            }
        }

        public void onError(int error) {
            Log.i(TAG, "onError TEST");
            if (listening && error == SpeechRecognizer.ERROR_NO_MATCH) {
                restartVoiceTestRecognizer();
                return;
            }

            String toastMessage = "";
            switch (error) {
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    toastMessage = getString(R.string.networkTimeoutError);
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    toastMessage = getString(R.string.networkError);
                    break;
                case SpeechRecognizer.ERROR_AUDIO:
                    toastMessage = getString(R.string.audioError);
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    toastMessage = getString(R.string.serverError);
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    toastMessage = getString(R.string.clientError);
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    toastMessage = getString(R.string.speechTimeoutError);
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    toastMessage = getString(R.string.noMatchError);
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    toastMessage = getString(R.string.recoginizerBusyError);
                    break;
                default:
                    toastMessage = getString(R.string.genericError);
                    break;
            }

            Log.i(TAG, "Error: " + toastMessage);
            if (processingDialog != null && !processingDialog.isCancelled()) {
                processingDialog.dismiss();
            }
        }

        private List<String> getRecognizedWordsByType(String sentenceRecognized){
            return Arrays.asList(sentenceRecognized.split(characterSplit));
        }

        public void onResults(Bundle results) {
            Log.i(TAG, "onResults");
            if (listening) {
                restartVoiceTestRecognizer();
            }

            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data.size() > 1) {
                List<String> words = getRecognizedWordsByType(data.get(0).toString());
                boolean foundMagicWord = false;
                for (String word : words) {
                    if(!word.isEmpty()){
                        wordsRecognition.add(word.toLowerCase());
                    }
                }
            }
        }


        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.i(TAG, "onPartialResults " + partialResults);
            ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.i(TAG, "PARTIAL RESULT: " + data.get(i));
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }


        private void buildResults(int qtdAcertos, int ellapsedTime) {
            List<ResultItem> resultsItems = new ArrayList<>();
            for (String word : wordsRecognition) {
                ResultItem resultItem1 = new ResultItem();
                resultItem1.setTitle("Palavra");
                resultItem1.setResult(word);
                resultItem1.setIcon(getDrawable(R.drawable.ic_timer_black_24dp));
                resultsItems.add(resultItem1);
            }

//            ResultItem resultItem1 = new ResultItem();
//            resultItem1.setTitle(getString(R.string.time));
//            resultItem1.setResult(String.valueOf(ellapsedTime + "s"));
//            resultItem1.setIcon(getDrawable(R.drawable.ic_timer_black_24dp));
//            resultsItems.add(resultItem1);
//
//            ResultItem resultItem2 = new ResultItem();
//            resultItem2.setTitle(getString(R.string.matches));
//            resultItem2.setResult(String.valueOf(qtdAcertos));
//            resultItem2.setIcon(getDrawable(R.drawable.ic_check_circle_black_24dp));
//            resultsItems.add(resultItem2);

//            buildDialog(resultsItems);
        }

//        private void buildDialog(List<ResultItem> items){
//            MaterialDialog dialog = new MaterialDialog.Builder(TestActivity.this)
//                    .title(getString(R.string.result))
//                    .adapter(new ListViewAdapter(TestActivity.this, items), null)
//                    .positiveText(getString(R.string.Ok))
//                    .titleColorRes(R.color.dialogTextColor)
//                    .dividerColorRes(R.color.dialogTextColor)
//                    .positiveColorRes(R.color.dialogTextColor)
//                    .build();
//
//            RecyclerView rcView = dialog.getRecyclerView();
//            rcView.addItemDecoration(new VerticalSpaceItemDecoration(24));
//            android.support.v7.widget.DividerItemDecoration dividerItemDecoration = new android.support.v7.widget.DividerItemDecoration(rcView.getContext(), LinearLayoutManager.VERTICAL);
//            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
//            rcView.addItemDecoration(dividerItemDecoration);
//
//            dialog.show();
//            reset();
//        }

    }

}
