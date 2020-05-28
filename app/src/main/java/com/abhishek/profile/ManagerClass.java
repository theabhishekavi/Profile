package com.abhishek.profile;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

public class ManagerClass {
    public CognitoCachingCredentialsProvider getCredentials(Context context){
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-2:53760107-ca5e-468f-a9fc-7c3384234ffc", // Identity pool ID
                Regions.US_EAST_2 // Region
        );
        return credentialsProvider;
    }
}
