package com.example.kendra.tardiness;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.wearable.Wearable.MessageApi;

public class MainActivity extends Activity
        implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int CLIENT_CONNECTION_TIMEOUT = 15000;
    private GoogleApiClient mGoogleApiClient;
    private ExecutorService executorService;
    private TextView mTextView;
    private Button switchButton;
    private Button realizeButton;
    private Button hereButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchButton = (Button) findViewById(R.id.button1);
        realizeButton = (Button) findViewById(R.id.button2);
        hereButton = (Button) findViewById(R.id.button3);


        mGoogleApiClient = new GoogleApiClient.Builder(this.getApplicationContext()).addApi(Wearable.API).build();

        executorService = Executors.newCachedThreadPool();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    protected void switchClick(View view) {
        switchButton.setBackgroundColor(Color.RED);
        if (mGoogleApiClient == null)
            return;
        send(1);
        /*final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                if (nodes != null) {
                    for (int i = 0; i < nodes.size(); i++) {
                        final Node node = nodes.get(i);
                        Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), "/MESSAGE", null);
                    }
                }
            }
        });*/
    }
    protected void realizeClick(View view) {
        realizeButton.setBackgroundColor(Color.RED);
    }
    protected void hereClick(View view) {
        hereButton.setBackgroundColor(Color.RED);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed(): " + connectionResult.getErrorMessage());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended(): connection to location client suspended");
    }

    private boolean validateConnection() {
        if (mGoogleApiClient.isConnected()) {
            return true;
        }
        ConnectionResult result = mGoogleApiClient.blockingConnect(CLIENT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        return result.isSuccess();
    }

    private void send(final int type) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                PutDataMapRequest dataMap = PutDataMapRequest.create("/logs");
                dataMap.getDataMap().putLong("Time", System.currentTimeMillis());
                dataMap.getDataMap().putInt("Log", type);

                PutDataRequest putDataRequest = dataMap.asPutDataRequest();
                if (validateConnection()) {
                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            Log.v(TAG, "Sending sensor data: " + dataItemResult.getStatus().isSuccess());
                        }
                    });
                }
            }
        });
    }
}
