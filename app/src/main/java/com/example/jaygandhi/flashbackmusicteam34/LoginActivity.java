package com.example.jaygandhi.flashbackmusicteam34;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity  {

    public GoogleApiClient mGoogleApiClient;
    private final int RC_INTENT = 200;
    private final int RC_API_CHECK = 100;
    private static final String TAG = "LoginActivity";
    public static ArrayList<String> nameList = new ArrayList<String>();
    public static ArrayList<String> emailAddressList = new ArrayList<String>();
    private String authCode;

    //test commit
    // test again

    private int runAsync = 0;

    public static String userEmail;
    public static ArrayList<Friend> friendsList = new ArrayList<>();
    public static HashMap<String, String> displayNames = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "Login activity has been created");
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // The serverClientId is an OAuth 2.0 web client ID
                .requestServerAuthCode(getString(R.string.web_client_id))
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/contacts.readonly"))
                .build();

        // To connect with Google Play Services and Sign In
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

        SignInButton googleSignInButton = (SignInButton)findViewById(R.id.main_googlesigninbtn);



        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.main_googlesigninbtn:
                        getIdToken();
                        break;
                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    private void getIdToken() {
        // Shows an account picker to let the user choose a Google account from the device.
        // If the GoogleSignInOptions only asks for IDToken and/or profile and/or email then no
        // consent screen will be shown here.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_INTENT:
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("Sign in result is : " + result);
            if (result.isSuccess()) {
                System.out.println("We signed in successfully");
                GoogleSignInAccount acct = result.getSignInAccount();
                userEmail = acct.getEmail();
                displayNames.put(userEmail, "You");
                Log.d(TAG, "onActivityResult: current user email: " + userEmail);
                Log.d(TAG, "onActivityResult:GET_TOKEN:success:" + result.getStatus().isSuccess());
                // This is what we need to exchange with the server.
                authCode = acct.getServerAuthCode();
                runAsync++;
                new AsyncTaskRunner().execute(authCode);
            } else {
                System.out.println("We could not sign in");
                Log.d(TAG, result.getStatus().toString() + "\nmsg: " + result.getStatus().getStatusMessage());
            }
            break;
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            Log.d(TAG, "Running Async Task");
            ArrayList<Person> connections = null;
            ArrayList<Friend> friends = null;
            try {
                People peopleService = PeopleHelper.setUp(LoginActivity.this, authCode);
                System.out.println("peopleService looks like: " + peopleService);
                ListConnectionsResponse response = peopleService.people().connections()
                        .list("people/me")
                        // This line's really important! Here's why:
                        // http://stackoverflow.com/questions/35604406/retrieving-information-
                        // about-a-contact-with-google-people-api-java
                        .setRequestMaskIncludeField("person.names,person.emailAddresses")
                        .execute();

                System.out.println("response looks like: " + response);
                connections = (ArrayList<Person>)response.getConnections();

                System.out.println("What is connections: " + connections);

                for (Person person : connections) {
                    if (!person.isEmpty()) {
                        List<Name> names = person.getNames();
                        List<EmailAddress> emailAddresses = person.getEmailAddresses();
                        String currName = null;
                        String email = null;
                        if (emailAddresses != null)
                            for (EmailAddress emailAddress : emailAddresses) {
                                emailAddressList.add(emailAddress.getValue());
                                Log.d(TAG, "email: " + emailAddress.getValue());
                                email = emailAddress.getValue();
                            }


                        if (names != null) {
                            for (Name name : names) {
                                if (nameList.contains(name)) {
                                    continue;
                                } else {
                                    nameList.add(name.getDisplayName());
                                    currName = name.getDisplayName();
                                }
                            }
                        }

                        displayNames.put(email, currName);
                        friendsList.add(new Friend(currName, email));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            Log.d(TAG, "printing names for debugging");
            System.out.println("Length of name List is: " + nameList.size());
            for(String email: emailAddressList) {
                System.out.println("Email of contact is " + email);
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer n) {
//            Bundle nameBundle = new Bundle();
//            System.out.println("LoginActivity connections is: " + connections);
//            nameBundle.putSerializable("connections", connections);
            Intent homeActivityintent = new Intent(LoginActivity.this
                    , HomeActivity.class);
//            homeActivityintent.putExtra("name_bundle", nameBundle);
            startActivity(homeActivityintent);
        }


    }
}


