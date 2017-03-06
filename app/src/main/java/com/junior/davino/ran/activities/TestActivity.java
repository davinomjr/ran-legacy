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
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.models.ResultItem;
import com.junior.davino.ran.models.ResultSummary;
import com.junior.davino.ran.utils.ColorBuilder;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Toaster;
import com.junior.davino.ran.utils.Util;
import com.junior.davino.ran.utils.VoiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class TestActivity extends FragmentActivity implements RanTestFragment.OnFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "TestActivity";
    private List<Item> items;
    SpeechRecognizer testRecognizer, startRecognizer;
    Button btnSpeak;
    Toaster toaster = new Toaster();
    TimerUtil timerUtil = new TimerUtil();
    int qtdAcertos = 0;
    boolean listening;
    Intent intent;
    boolean testFinalized = false;
    CountDownTimer countdownTimer;
    MaterialDialog processingDialog;

    RecognitionListener startRecognitionListener, testRecognitionnListener;

    List<String> wordsRecognition = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        items = ColorBuilder.createListOfColors(24);
        RanTestFragment fragment = RanTestFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();


        btnSpeak = (Button) findViewById(R.id.btn_start);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        checkVoiceRecognition(btnSpeak);
        if (!VoiceUtil.isPermissionOk(this)) {
            toaster.showToastMessage(this, getString(R.string.voiceRecognitionNotPresent));
            finish();
        }

        btnSpeak.setOnClickListener(this);
        intent = buildVoiceRecognitionIntent();
        startRecognitionListener = new StartVoiceRecognitionListener();
        testRecognitionnListener = new VoiceRecognitionListener();
        startRecognizer = createRecognizer(startRecognitionListener);
        testRecognizer = createRecognizer(testRecognitionnListener);

        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);

        startRecognizer.startListening(intent);
    }

    private Intent buildVoiceRecognitionIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra("android.speech.extra.DICTATION_MODE", true);
        return intent;
    }

    private SpeechRecognizer createRecognizer(RecognitionListener listener) {
        SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(listener);
        return recognizer;
    }


    private void restartStartRecognizer() {
        if (startRecognizer == null) {
            startRecognizer = createRecognizer(startRecognitionListener);
        }

        startRecognizer.cancel();
        startRecognizer.startListening(intent);
    }

    private void restartVoiceTestRecognizer() {
        if (testRecognizer == null) {
            testRecognizer = createRecognizer(testRecognitionnListener);
        }

        testRecognizer.cancel();
        testRecognizer.startListening(intent);
    }


    private void destroyListeningRecognizer(SpeechRecognizer recognizer) {
        Log.i(TAG, "Destroying recognizer");
        if (recognizer != null) {
            recognizer.destroy();
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (testRecognizer != null) {
            testRecognizer.destroy();
        }

        if (startRecognizer != null) {
            startRecognizer.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        if (testRecognizer != null) {
            testRecognizer.destroy();
        }

        if (startRecognizer != null) {
            startRecognizer.destroy();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        if (startRecognizer == null) {
            startRecognizer = createRecognizer(startRecognitionListener);
        }

        if (testRecognizer == null) {
            testRecognizer = createRecognizer(testRecognitionnListener);
        }

        Log.i(TAG, "STARTRECOGNIZER = NULL ? " + String.valueOf(startRecognitionListener == null));
        Log.i(TAG, "INTENT = NULL ? " + String.valueOf(intent == null));
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
        destroyListeningRecognizer(startRecognizer);
        destroyListeningRecognizer(testRecognizer);
        startRecognizer = createRecognizer(startRecognitionListener);
        testRecognizer = createRecognizer(testRecognitionnListener);
        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
        timerUtil.reset();
        startRecognizer.startListening(intent);
    }

    private void stop() {
        listening = false;
        timerUtil.stop();
        Log.i(TAG, "STOP LISTENENING");
        changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
    }

    public void startTest() {
        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);

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
                Log.i(TAG, "START LISTENENING");
                listening = true;
                countdownView.setVisibility(View.INVISIBLE);
                if (startRecognizer != null) {
                    startRecognizer.destroy();
                }
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
                stop();
                Log.i(TAG, "Finalizando teste");
                testRecognizer.stopListening();
            }
        } else if (v.getId() == R.id.btn_back) {
            destroyListeningRecognizer(startRecognizer);
            destroyListeningRecognizer(testRecognizer);
            finish();
        } else { //Reset
            reset();
        }
    }

    private void changeStartButtonState(int stringResourceId, int colorId) {
        Button button = (Button) TestActivity.this.findViewById(R.id.btn_start);
        button.setText(getString(stringResourceId));
        button.setBackgroundColor(getResources().getColor(colorId));
    }

    private void finalizeTest() {
        testFinalized = true;
        if (processingDialog != null && !processingDialog.isCancelled()) {
            processingDialog.dismiss();
        }

        int ellapsedTime = timerUtil.getLastResult();
        Log.i(TAG, "TEMPO PASSADO = " + ellapsedTime);

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
        startActivity(intent);
    }

    class StartVoiceRecognitionListener implements RecognitionListener {

        public void onReadyForSpeech(Bundle params) {
            Log.i(TAG, "onReadyForSpeech startRecognizer");
        }

        public void onBeginningOfSpeech() {
            Log.i(TAG, "onBeginningOfSpeech startRecognizer");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged startRecognizer");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived startRecognizer");
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(TAG, "onEnndOfSpeech startRecognizer");
        }

        @Override
        public void onError(int error) {
            if (error == 7 && !listening) {
                Log.i(TAG, "onError startRecognizer");
                restartStartRecognizer();
            }
        }

        @Override
        public void onResults(Bundle results) {
            restartStartRecognizer();
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data.size() > 1) {
                List<String> words = new ArrayList<String>(Arrays.asList(data.get(0).toString().split(" ")));
                for (String word : words) {
                    if (Util.isEqualStripAccentsIgnoreCase(getString(R.string.startWord1), word)) {
                        Log.i(TAG, "ACHOU PALAVRA INICIAR");
                        startRecognizer.destroy();
                        startTest();
                        break;
                    }
                }
            }
        }


        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

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

            if (!testFinalized) {
                timerUtil.stop();
                changeStartButtonState(R.string.btStart, R.color.md_material_blue_800);
                processingDialog = new MaterialDialog.Builder(TestActivity.this)
                        .title(R.string.progress_dialog)
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .build();

                processingDialog.show();
            }
        }

        public void onError(int error) {
            Log.i(TAG, "onError TEST");
            if (listening && error == 7) {
                restartVoiceTestRecognizer();
                return;
            }

            String toastMessage = "";
            switch (error) {
                case 1:
                    toastMessage = getString(R.string.networkTimeoutError);
                    break;
                case 2:
                    toastMessage = getString(R.string.networkError);
                    break;
                case 3:
                    toastMessage = getString(R.string.audioError);
                    break;
                case 4:
                    toastMessage = getString(R.string.serverError);
                    break;
                case 5:
                    toastMessage = getString(R.string.clientError);
                    break;
                case 6:
                    toastMessage = getString(R.string.speechTimeoutError);
                    break;
                case 7:
                    toastMessage = getString(R.string.noMatchError);
                    break;
                case 8:
                    toastMessage = getString(R.string.recoginizerBusyError);
                    break;
                default:
                    toastMessage = getString(R.string.genericError);
                    break;
            }

            Log.i(TAG, "Error " + error);
            toaster.showToastMessage(TestActivity.this, toastMessage);
            if (processingDialog != null && !processingDialog.isCancelled()) {
                processingDialog.dismiss();
            }
        }

        public void onResults(Bundle results) {
            Log.i(TAG, "onResults");
            if (listening) {
                restartVoiceTestRecognizer();
            }

            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data.size() > 1) {
                List<String> words = new ArrayList<String>(Arrays.asList(data.get(0).toString().split(" ")));
                for (String word : words) {
                    if (Util.isEqualStripAccentsIgnoreCase(getString(R.string.endWord1), word)) {
                        stop();
                        destroyListeningRecognizer(testRecognizer);
                        Log.i(TAG, "Finalizando teste");
                        finalizeTest();
                        break;
                    } else {
                        Log.i(TAG, "ADDED RESULT WORD");
                        wordsRecognition.add(word.toLowerCase());
                    }
                }
            }

            if(!listening){
                finalizeTest();
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
