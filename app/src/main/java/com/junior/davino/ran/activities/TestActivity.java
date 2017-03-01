package com.junior.davino.ran.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.junior.davino.ran.R;
import com.junior.davino.ran.adapters.ListViewAdapter;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.models.ResultItem;
import com.junior.davino.ran.utils.ColorBuilder;
import com.junior.davino.ran.utils.TimerUtil;
import com.junior.davino.ran.utils.Toaster;
import com.junior.davino.ran.utils.VoiceUtil;
import com.junior.davino.ran.view.VerticalSpaceItemDecoration;

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
    Toaster toaster = new Toaster();
    TimerUtil timerUtil = new TimerUtil();
    int qtdAcertos = 0;
    boolean listening;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        if (VoiceUtil.isPermissionOk(this)) {
            btnSpeak.setOnClickListener(this);
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.junior.davino.ran.activities");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            createRecognizer();
        }


    }

    private void createRecognizer(){
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new VoiceRecognitionListener());
    }

    @Override
    public void onDestroy(){
        sr.destroy();
        super.onDestroy();
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

    private void reset(){
        listening = false;
        timerUtil.reset();
        sr.destroy();
        createRecognizer();
        alterStateButtonStart(R.string.btStart, R.color.md_material_blue_800);
        toaster.showToastMessage(this, getString(R.string.reseted), Toast.LENGTH_SHORT);
    }

    private void stop(){
        listening = false;
        sr.stopListening();
        Log.i(TAG, "STOP LISTENENING");
        alterStateButtonStart(R.string.btStart, R.color.md_material_blue_800);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            if(!listening) {
                listening = true;
                sr.startListening(intent);
                Log.i(TAG, "START LISTENENING");
                alterStateButtonStart(R.string.btFinalize, R.color.md_material_blue_600);
                timerUtil.start();
            }
            else{
                stop();
            }
        }
        else if(v.getId() == R.id.btn_back){
            sr.destroy();
            finish();
        }
        else{ //Reset
            reset();
        }
    }

    private void alterStateButtonStart(int stringResourceId, int colorId){
        Button button = (Button)TestActivity.this.findViewById(R.id.btn_start);
        button.setText(getString(stringResourceId));
        button.setBackgroundColor(getResources().getColor(colorId));
    }


    public class VoiceRecognitionListener implements RecognitionListener {

        MaterialDialog processingDialog;

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
            Log.i(TAG, "onEnndOfSpeech");
            timerUtil.stop();
            listening = false;
            alterStateButtonStart(R.string.btStart, R.color.md_material_blue_800);
            processingDialog = new MaterialDialog.Builder(TestActivity.this)
                    .title(R.string.progress_dialog)
                    .content(R.string.please_wait)
                    .progress(true, 0)
                    .build();

            processingDialog.show();
        }

        public void onError(int error) {
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

            Log.i(TAG, "error " + error);
            toaster.showToastMessage(TestActivity.this, toastMessage);
            if(processingDialog != null && !processingDialog.isCancelled()){
                processingDialog.dismiss();
            }
        }

        public void onResults(Bundle results) {
            qtdAcertos = 0;
            processingDialog.dismiss();
            int ellapsedTime = timerUtil.getLastResult();

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


                buildResults(qtdAcertos,ellapsedTime);
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


        private void buildResults(int qtdAcertos, int ellapsedTime){
            List<ResultItem> resultsItems = new ArrayList<>();
            ResultItem resultItem1 = new ResultItem();
            resultItem1.setTitle(getString(R.string.time));
            resultItem1.setResult(String.valueOf(ellapsedTime + "s"));
            resultItem1.setIcon(getDrawable(R.drawable.ic_timer_black_24dp));
            resultsItems.add(resultItem1);

            ResultItem resultItem2 = new ResultItem();
            resultItem2.setTitle(getString(R.string.matches));
            resultItem2.setResult(String.valueOf(qtdAcertos));
            resultItem2.setIcon(getDrawable(R.drawable.ic_check_circle_black_24dp));
            resultsItems.add(resultItem2);

            buildDialog(resultsItems);

        }

        private void buildDialog(List<ResultItem> items){
            MaterialDialog dialog = new MaterialDialog.Builder(TestActivity.this)
                    .title(getString(R.string.result))
                    .adapter(new ListViewAdapter(TestActivity.this, items), null)
                    .positiveText(getString(R.string.Ok))
                    .titleColorRes(R.color.dialogTextColor)
                    .dividerColorRes(R.color.dialogTextColor)
                    .positiveColorRes(R.color.dialogTextColor)
                    .build();

            RecyclerView rcView = dialog.getRecyclerView();
            rcView.addItemDecoration(new VerticalSpaceItemDecoration(24));
            android.support.v7.widget.DividerItemDecoration dividerItemDecoration = new android.support.v7.widget.DividerItemDecoration(rcView.getContext(), LinearLayoutManager.VERTICAL);
            dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
            rcView.addItemDecoration(dividerItemDecoration);

            dialog.show();
        }

    }

}
