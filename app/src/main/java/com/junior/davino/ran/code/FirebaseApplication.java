package com.junior.davino.ran.code;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.junior.davino.ran.R;
import com.junior.davino.ran.activities.HomeActivity;
import com.junior.davino.ran.activities.SignUpActivity;
import com.junior.davino.ran.activities.TestUsersActivity;
import com.junior.davino.ran.models.User;

/**
 * Created by davin on 28/03/2017.
 */

public class FirebaseApplication extends Application {
    private static final String TAG = FirebaseApplication.class.getSimpleName();
    public FirebaseAuth firebaseAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    public FirebaseAuth getFirebaseAuth(){
        return firebaseAuth = FirebaseAuth.getInstance();
    }
    public String getFirebaseUserAuthenticateId() {
        String userId = null;
        if(getFirebaseAuth().getCurrentUser() != null){
            userId = firebaseAuth.getCurrentUser().getUid();
        }
        return userId;
    }
    public void checkUserLogin(final Context context){
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(context, TestUsersActivity.class);
            context.startActivity(intent);
        }
    }
    public void isUserCurrentlyLogin(final Context context){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = getFirebaseAuth().getCurrentUser();
                if(null != user){
                    Log.i(TAG, "onAuthStateChanged STARTING TESTUSERSACTIVITY");
                    Intent intent = new Intent(context, TestUsersActivity.class);
                    context.startActivity(intent);
                }else{
                    Log.i(TAG, "onAuthStateChanged STARTING HOMEACTIVITY");
                    Intent loginIntent = new Intent(context, HomeActivity.class);
                    context.startActivity(loginIntent);
                }
            }
        };
    }
    public void createNewUser(final Context context, final String name, final String email, final String password, final String errorMessage){
        getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.coordinator_layout), errorMessage, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        else{
                            FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            fbUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "TestUser profile updated.");
                                            }
                                        }
                                    });

                            getFirebaseAuth().signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w(TAG, "signInWithEmail", task.getException());
                                                ((SignUpActivity)context).onSignupFailed();
                                            }else {
                                                Log.i(TAG, "SUCESSO");
                                                createReferenceUser(name);
                                                ((SignUpActivity)context).onSignupSuccess();
                                            }
                                        }
                                    });

                        }
                    }
                });
    }

    private void createReferenceUser(String name){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users");
        String key = firebaseAuth.getCurrentUser().getUid();
        User user = new User();
        user.setUserId(key);
        user.setName(name);
        userReference.child(key).setValue(user);
    }

    public void loginAUser(final Context context, String email, String password, final String errorMessage){
        getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            ((HomeActivity)context).onLoginFailed();
                        }else {
                            Log.i(TAG, "SUCESSO");
                            ((HomeActivity)context).onLoginSuccess();
                        }
                    }
                });
    }
}