package com.applesauce.concealvpn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.applesauce.concealvpn.R;
import com.applesauce.concealvpn.interfaces.NavObjectClickListener;
import com.applesauce.concealvpn.model.VpnServer;
import java.util.ArrayList;

import lombok.Data;

@Data
public class AdapterServersRV extends RecyclerView.Adapter<AdapterServersRV.ServerViewHolder> {
    private ArrayList<VpnServer> servers;
    private Context context;
    private NavObjectClickListener listener;

    AdapterServersRV(ArrayList<VpnServer> servers, Context context) {
        this.servers = servers;
        this.context = context;
        listener = (NavObjectClickListener) context;
    }

    @NonNull
    @Override
    public ServerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.server_list_view, parent, false);
        return new ServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerViewHolder holder, int position) {

    }

    protected class ServerViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout server_container;
        ImageView server_icon;
        TextView server_country;
        public ServerViewHolder(@NonNull View view) {
            super(view);
            server_container = view.findViewById(R.id.server_container);
            server_icon = view.findViewById(R.id.server_icon);
            server_country = view.findViewById(R.id.server_country);
        }
    }

    @Override
    public int getItemCount() {
        return servers.size();
    }
}
