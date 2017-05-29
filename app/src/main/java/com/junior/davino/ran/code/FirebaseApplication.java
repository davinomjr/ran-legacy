package com.junior.davino.ran.code;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
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

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                            ((SignUpActivity)context).onSignupFailed();
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

    public void updateUser(String name, String email, String password){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "TestUser profile updated.");
                        }
                    }
                });

        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });

        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    private void createReferenceUser(String name){
        DatabaseReference userReference = database.getReference("users");
        String key = firebaseAuth.getCurrentUser().getUid();
        User user = new User();
        user.setUserId(key);
        user.setName(name);
        userReference.child(key).setValue(user);
    }

    public DatabaseReference getUsersQuery(){
        return database.getReference("users");
    }

    public void loginAUser(final Context context, String email, String password, final String errorMessage){
        getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Exception exception = task.getException();
                            if(exception instanceof FirebaseAuthInvalidUserException ||
                                    exception instanceof FirebaseAuthInvalidCredentialsException){
                                ((HomeActivity)context).onLoginFailed(context.getString(R.string.err_msg_wrong_auth_data));
                            }
                       else{
                                ((HomeActivity)context).onLoginFailed();
                            }
                        }else {
                            Log.i(TAG, "SUCESSO");
                            ((HomeActivity)context).onLoginSuccess();
                        }
                    }
                });
    }

    public void logoff(){
        getFirebaseAuth().signOut();
    }

    public void sendEmailRecoveryPassword(String emailAddress){
        Log.i(TAG, "Trying to send email.");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Email sent.");
                        }
                    }
                });
    }

}