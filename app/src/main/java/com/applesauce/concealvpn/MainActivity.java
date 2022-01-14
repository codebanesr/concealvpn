package com.applesauce.concealvpn;

import static com.applesauce.concealvpn.utils.Helper.getResourceAsUrl;

import android.net.Uri;
import android.os.Bundle;

import com.applesauce.concealvpn.databinding.ActivityMainBinding;
import com.applesauce.concealvpn.fragments.MainFragment;
import com.applesauce.concealvpn.interfaces.NavObjectClickListener;
import com.applesauce.concealvpn.interfaces.ServerSwitch;
import com.applesauce.concealvpn.model.VpnServer;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavObjectClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ServerSwitch serverSwitch;
    private DrawerLayout drawer;


    private static final String TAG = "ConcealVPN";
    private MainFragment fragment;
    private RecyclerView recyclerView;
    private ArrayList<VpnServer> serverList;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        drawer = binding.drawerLayout;
        fragment = new MainFragment();
        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        serverList = getServersList();


        ImageButton topRightButton = binding.topRightButton;
        toolbar = binding.toolbar;


        // @Todo might need a separate toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                R.string.drawer_open,
                R.string.drawer_closed
        );

        drawer.addDrawerListener(toggle);
        topRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }

    @Override
    public void itemClicked(int index) {
        closeDrawer();
        serverSwitch.changeServer(getServersList().get(index));
    }

    private void closeDrawer() {
        if(drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
            return;
        }

        drawer.openDrawer(GravityCompat.END);
    }

    private ArrayList<VpnServer> getServersList() {
        ArrayList<VpnServer> servers = new ArrayList<>();
        servers.add(new VpnServer("US", getResourceAsUrl(R.drawable.us_flag), "us.ovpn", "vpn", "vpn"));
        servers.add(new VpnServer("JAPAN", getResourceAsUrl(R.drawable.japan_flag), "japan.ovpn", "vpn", "vpn"));
        servers.add(new VpnServer("KOREA", getResourceAsUrl(R.drawable.korea_flag), "korea.ovpn", "vpn", "vpn"));
        servers.add(new VpnServer("SWEDEN", getResourceAsUrl(R.drawable.sweden_flag), "sweden.ovpn", "vpn", "vpn"));
        return servers;
    }
}