package com.junior.davino.ran.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.junior.davino.ran.R;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.utils.ColorBuilder;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Util;
import com.junior.davino.ran.utils.VoiceUtil;

import org.apache.commons.lang3.StringUtils;

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
    SpeechRecognizer sr;
    Button btnSpeak;
    public int qtdAcertos;
    Activity activity;
    TimerUtil timerUtil = new TimerUtil();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        activity = this;
        items = ColorBuilder.createListOfColors(20);
        RanTestFragment fragment = RanTestFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();



//        TypefaceProvider.registerDefaultIconSets();


        btnSpeak = (Button) findViewById(R.id.btn_start);
        checkVoiceRecognition(btnSpeak);
        if (VoiceUtil.isPermissionOk(this)) {
            btnSpeak.setOnClickListener(this);
            sr = SpeechRecognizer.createSpeechRecognizer(this);
            sr.setRecognitionListener(new VoiceRecognitionListener());
        }


    }

    @Override
    public void onDestroy(){
        sr.destroy();
    }

    public void checkVoiceRecognition(Button btnSpeak) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            btnSpeak.setEnabled(false);
            Toast.makeText(this, R.string.voiceRecognitionNotPresent, Toast.LENGTH_LONG).show();
        }
    }


    public void onFragmentInteraction(Uri uri){

    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            Log.i(TAG, "Starting listening");
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            sr.startListening(intent);
            timerUtil.start();
        }
    }


    public class VoiceRecognitionListener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.i(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.i(TAG, "onBeginningOfSpeech");

        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.i(TAG, "onEndofSpeech");
//            timer.stop();
        }

        public void onError(int error) {
            String toastMessage = "";
            switch (error) {
                case 1:
                    toastMessage = String.valueOf(R.string.networkTimeoutError);
                    break;
                case 2:
                    toastMessage = String.valueOf(R.string.networkError);
                    break;
                case 3:
                    toastMessage = String.valueOf(R.string.audioError);
                    break;
                case 4:
                    toastMessage = String.valueOf(R.string.serverError);
                    break;
                case 5:
                    toastMessage = String.valueOf(R.string.clientError);
                    break;
                case 6:
                    toastMessage = String.valueOf(R.string.speechTimeoutError);
                    break;
                case 7:
                    toastMessage = String.valueOf(R.string.noMatchError);
                    break;
                case 8:
                    toastMessage = String.valueOf(R.string.recoginizerBusyError);
                    break;
                default:
                    toastMessage = String.valueOf(R.string.genericError);
                    break;
            }

            Log.i(TAG, "error " + error);
            Util.showToastMessage(activity, toastMessage);
        }

        public void onResults(Bundle results) {
            List<Item> itemsClone = new ArrayList<Item>(getItems());
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


            if (data.size() > 1) {
                List<String> words = new ArrayList<String>(Arrays.asList(data.get(0).toString().split(" ")));
                Iterator<Item> iterator = itemsClone.iterator();
                Iterator<String> itWords = words.iterator();

                while(iterator.hasNext()){
                    Item item = iterator.next();
                    while(itWords.hasNext()) {
                        String nextWord = itWords.next();
                        if (StringUtils.stripAccents(item.getName()).equalsIgnoreCase(StringUtils.stripAccents(nextWord))) {
                            qtdAcertos++;
                            Log.i(TAG + " ACERTOS:", String.valueOf(qtdAcertos));
                            Log.i(TAG, " ITEMS LEFT:" + String.valueOf(itemsClone.size()));
                            iterator.remove();
                            itWords.remove();
                            break;
                        }
                    }
                }



                Util.showToastMessage(activity, "Acertos = " + String.valueOf(qtdAcertos) + " TIMER = " + String.valueOf(timerUtil.stop()));
                timerUtil.reset();
                qtdAcertos = 0;

                new MaterialDialog.Builder(activity)
                        .title("Resultados")
                        .customView(R.layout.result_view, true)
                        .positiveText(R.string.Ok)
                        .show();




//                for (String word : splittedWords) {
//                    Log.i(TAG + "FALADO:", word);
//                    while(iterator.hasNext()){
//                        Item item = iterator.next();
//                        if(StringUtils.stripAccents(item.getName()).equalsIgnoreCase(StringUtils.stripAccents(word))){
//                            qtdAcertos++;
//                            Log.i(TAG + " ACERTOS:", String.valueOf(qtdAcertos));
//                            Log.i(TAG, " ITEMS LEFT:" + String.valueOf(itemsClone.size()));
//                            iterator.remove();
//                        }
//                    }
//                }
            }
        }


        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.i(TAG, "onPartialResults " + partialResults);
            ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.i(TAG, "PARTIAL RESULT = " + data.get(i));
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

}
