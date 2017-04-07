package com.junior.davino.ran.speech;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by davin on 19/03/2017.
 */

public class VoiceController {

    private static final String TAG = "VoiceController";
    private static final int RECORDER_SAMPLERATE = 16000;
    private static final int RECORDER_CHANNELS_IN = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_CHANNELS_OUT = AudioFormat.CHANNEL_OUT_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private boolean isRecording = false;
    private List<String> wordsLookup;

    // Initialize minimum buffer size in bytes.
    private int bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING);

    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private String filePath;
    private SpeechService speechService;


    public VoiceController(SpeechService speechService, String filePath, List<String> wordsLookup){
        this.speechService = speechService;
        this.filePath = filePath;
        this.wordsLookup = wordsLookup;
    }


    public void startVoiceRecorder() {
        Log.i(TAG, "Starting voice recorder");
        if( bufferSize == AudioRecord.ERROR_BAD_VALUE)
            Log.e( TAG, "Bad Value for \"bufferSize\", recording parameters are not supported by the hardware");

        if( bufferSize == AudioRecord.ERROR )
            Log.e( TAG, "Bad Value for \"bufferSize\", implementation was unable to query the hardware for its output properties");

        Log.e( TAG, "\"bufferSize\"="+bufferSize);

        // Initialize Audio Recorder.
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS_IN, RECORDER_AUDIO_ENCODING, bufferSize);
        // Starts recording from the AudioRecord instance.
        recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    public void stopAndDestroy(){
        isRecording = false;
        if(recorder != null){
            recorder.stop();
        }

        if(recordingThread != null && recordingThread.isAlive()){
            recordingThread.interrupt();
            recordingThread = null;
        }

        if(recorder != null){
            recorder.release();
            recorder = null;
        }
    }

    public void stop(){
        isRecording = false;

        recorder.stop();
        recorder.release();
        recorder = null;
        recordingThread = null;
    }

    public void stopVoiceRecorder()  {
        Log.i(TAG, "Stopping voice recorder");
        stop();

        try {
            if (filePath ==null )
                return;

            //Reading the file..
            //Reading the file..
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, filePath);
            byte[] byteData = new byte[(int) file.length()];
            Log.d(TAG, (int) file.length()+"");
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                in.read( byteData );
                in.close();
            } catch (FileNotFoundException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }
            // Set and push to audio track..
            int intSize = android.media.AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING);
            Log.d(TAG, intSize+"");
            speechService.recognize(byteData, byteData.length, RECORDER_SAMPLERATE, wordsLookup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeAudioDataToFile() {
        //Write the output audio in byte
        byte saudioBuffer[] = new byte[bufferSize];

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, filePath);
        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(recorder != null){
            while (isRecording) {
                // gets the voice output from microphone to byte format
                recorder.read(saudioBuffer, 0, bufferSize);
                try {
                    //  writes the data to file from buffer stores the voice buffer
                    fileOutput.write(saudioBuffer, 0, bufferSize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void playAudio(String filePath) throws IOException{
        if (filePath==null)
            return;

        //Reading the file..
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, filePath);
        byte[] byteData = new byte[(int) file.length()];

        Log.d(TAG, (int) file.length()+"");


        FileInputStream in = null;
        try {
            if(!file.exists()){
                Log.i(TAG, "ARQ N EXISTE!!! = " + filePath);
            }

            in = new FileInputStream(file);
            in.read( byteData );
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Set and push to audio track..
        int intSize = android.media.AudioTrack.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING);
        Log.d(TAG, intSize+"");

        AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, RECORDER_SAMPLERATE, RECORDER_CHANNELS_OUT, RECORDER_AUDIO_ENCODING, intSize, AudioTrack.MODE_STREAM);
        if (at!=null) {
            at.play();
            // Write the byte array to the track
            at.write(byteData, 0, byteData.length);
            at.stop();
            at.release();
        }
        else {
            Log.d(TAG, "audio track is not initialised ");
        }
    }
}