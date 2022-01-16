package com.applesauce.concealvpn;

import static com.applesauce.concealvpn.utils.Helper.getResourceAsUrl;

import android.net.Uri;
import android.os.Bundle;

import com.applesauce.concealvpn.adapters.AdapterServersRV;
import com.applesauce.concealvpn.databinding.ActivityMainBinding;
import com.applesauce.concealvpn.fragments.MainFragment;
import com.applesauce.concealvpn.interfaces.NavObjectClickListener;
import com.applesauce.concealvpn.interfaces.ServerSwitch;
import com.applesauce.concealvpn.model.VpnServer;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavObjectClickListener {
    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    private Fragment fragment;
    private RecyclerView serverListRv;
    private ArrayList<VpnServer> serverLists;
    private AdapterServersRV serverListRVAdapter;
    private DrawerLayout drawer;
    private ServerSwitch changeServer;

    public static final String TAG = "CakeVPN";
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize all variable
        initializeAll();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawer.addDrawerListener(toggle);

        transaction.add(R.id.container, fragment);
        transaction.commit();
        // Server List recycler view initialize
        if (serverLists != null) {
            serverListRVAdapter = new AdapterServersRV(serverLists, this);
            serverListRv.setAdapter(serverListRVAdapter);
        }
    }

    /**
     * Initialize all object, listener etc
     */
    private void initializeAll() {
        fragment = new MainFragment();
        drawer = binding.drawerLayout;
        serverListRv = binding.recyclerView;
        serverListRv.setHasFixedSize(true);

        serverListRv.setLayoutManager(new LinearLayoutManager(this));

        serverLists = getServerList();
        changeServer = (ServerSwitch) fragment;
    }

    /**
     * Close navigation drawer
     */
    public void closeDrawer(){
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    /**
     * Generate server array list
     */
    private ArrayList getServerList() {

        ArrayList<VpnServer> servers = new ArrayList<>();

        servers.add(new VpnServer("United States",
                getResourceAsUrl(R.drawable.us_flag),
                "us.ovpn",
                "freeopenvpn",
                "416248023"
        ));
        servers.add(new VpnServer("Japan",
                getResourceAsUrl(R.drawable.japan_flag),
                "japan.ovpn",
                "vpn",
                "vpn"
        ));
        servers.add(new VpnServer("Sweden",
                getResourceAsUrl(R.drawable.sweden_flag),
                "sweden.ovpn",
                "vpn",
                "vpn"
        ));
        servers.add(new VpnServer("Korea",
                getResourceAsUrl(R.drawable.korea_flag),
                "korea.ovpn",
                "vpn",
                "vpn"
        ));

        return servers;
    }

    /**
     * On navigation item click, close drawer and change server
     * @param index: server index
     */
    @Override
    public void itemClicked(int index) {
        closeDrawer();
        changeServer.changeServer(serverLists.get(index));
    }
}