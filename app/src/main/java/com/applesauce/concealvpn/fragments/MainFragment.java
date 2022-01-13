package com.applesauce.concealvpn.fragments;

import static com.applesauce.concealvpn.utils.InternetChecker.isInternetAvailable;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.applesauce.concealvpn.databinding.FragmentMainBinding;
import com.applesauce.concealvpn.model.VpnServer;
import com.applesauce.concealvpn.utils.VpnState;
import com.applesauce.concealvpn.utils.SharedPreference;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainFragment extends Fragment implements View.OnClickListener{

    private VpnServer server;
    private SharedPreference pref;
    private FragmentMainBinding binding;
    private boolean vpnStarted;

    @Override
    public void onClick(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        pref = new SharedPreference(getContext());
        server = pref.getServer();

        setServerFlag(server.getFlag());
        initializeVpnIntent();
        return view;
    }


    public void loadServer(VpnServer server) {
        this.server = server;
        setServerFlag(server.getFlag());

        if(vpnStarted) {
            stopVpn();
        }

        try {
            loadSelectedVpn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ActivityResultLauncher<Intent> vpnLauncherActivity;
    private void initializeVpnIntent() {
        vpnLauncherActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        //   doSomeOperations();
                    }
                });
    }
    private void loadSelectedVpn() throws IOException {
        if(!vpnStarted) {
            if(!isInternetAvailable(getContext())) {
                Intent intent = prepareIntentToStartVpnService(getContext());
                if(intent!=null) {
                    vpnLauncherActivity.launch(intent);
                } else {
                    startVpn();
                }
            } else {
                showToast("Are you connected to the internet");        
            }
        }
    }


    BroadcastReceiver bcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setVpnState(intent.getStringExtra("state"));

            String duration = intent.getStringExtra("duration");
            String lastPacketReceived = intent.getStringExtra("lastPacketReceived");
            String byteIn = intent.getStringExtra("byteIn");
            String byteOut = intent.getStringExtra("byteOut");


            if(duration == null) {
                duration = "00:00:00";
            }

            if(lastPacketReceived == null) {
                lastPacketReceived = "0";
            }

            if(byteIn == null) {
                byteIn = "0";
            }

            if(byteOut == null) {
                byteOut = "0";
            }


            setConnectionMetrics(duration, lastPacketReceived, byteIn, byteOut);
        }
    };

    private void setConnectionMetrics(String duration, String lastPacketReceived, String byteIn, String byteOut) {
        binding.durationTv.setText("Duration", duration);
        binding.lastPacketReceivedTv.setText("Last Packet Received", lastPacketReceived);
        binding.byteInTv.setText("Byte In", byteIn);
        binding.byteOut.setText("Byte Out", byteOut);
    }

    private void setVpnState(String state) {
        if(state == null) return;

        switch (state) {
            case VpnState.Disconnected: {
                vpnStarted = false;
                break;
            }
            case VpnState.Connected: {
                vpnStarted = true;
                break;
            }
            case VpnState.WAIT: {
                vpnStarted = false;
                break;
            }
            case VpnState.AUTH: {
                vpnStarted = false;
                break;
            }
            case VpnState.RECONNECTING: {
                vpnStarted = false;
                break;
            }
            case VpnState.NONETWORK: {
                vpnStarted = false;
                break;
            }
        }

        updateForState(state);
    }

    private void updateForState(String state) {
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bcastReceiver);
        super.onResume();
    }

    private void startVpn() throws IOException {
        // read openvpn config and send it over to the ovpn client; break on a new line
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getActivity().getAssets().open(server.getOvpn()))
        );

        String config = "";
        String line;

        while(true) {
            line = br.readLine();
            if(line == null) { break; }
            config += line + "\n";
        }

        br.readLine();

        // openvpn client calls here to be completed later
    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private Intent prepareIntentToStartVpnService(Context context) {
        // use vpnlibrary to create intent here
    }

    private void stopVpn() {
    }

    private void setServerFlag(String flag) {
        Glide.with(getContext()).load(flag).into(binding.selectedServerIcon);
    }
}
