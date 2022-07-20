package balkan.chess.following.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import balkan.chess.R;
import balkan.chess.tournaments.adapter.TournamentAdapter;
import balkan.chess.tournaments.database.TournamentDatabase;
import balkan.chess.tournaments.model.TournamentModel;


public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder>{

    List<TournamentModel> list;
    private ItemClickListener itemClickListener;

    public FollowingAdapter(List<TournamentModel> list,ItemClickListener itemClickListener){
        this.list = list;
        this.itemClickListener = itemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton followingDeleteButton;
        TextView followingTournamentNameTextView;
        TextView followingLastChangeTextView;
        ImageView federationImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            followingDeleteButton = itemView.findViewById(R.id.followingDeleteButton);
            followingTournamentNameTextView = itemView.findViewById(R.id.followingTournamentNameTextView);
            followingLastChangeTextView = itemView.findViewById(R.id.followingLastChangeTextView);
            federationImageView = itemView.findViewById(R.id.federationImageView);
        }
    }


    @NonNull
    @Override
    public FollowingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.following_single_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        setDefaultFederation(holder,list.get(position).getFederation().trim());
        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.followingDeleteButton.setImageResource(R.drawable.ic_baseline_delete_forever_24);
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(list.get(position));
        });

        holder.followingTournamentNameTextView.setText(list.get(position).getTournamentName());

        holder.followingDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TournamentDatabase db = Room.databaseBuilder(holder.itemView.getContext(),TournamentDatabase.class,"followingTournaments").allowMainThreadQueries().build();

                db.tournamentDAO().deleteTournament(list.get(position).getTournamentLink());
                list.remove(position);

                Toast.makeText(view.getContext(), "Successfully deleted !" , Toast.LENGTH_SHORT).show();

                notifyDataSetChanged();
            }

        });

    }

    public void setDefaultFederation(FollowingAdapter.ViewHolder viewHolder, String federation){
        if(federation.equals("SRB")){
            viewHolder.federationImageView.setImageResource(R.drawable.serbia);
        }
        if(federation.equals("CRO")){
            viewHolder.federationImageView.setImageResource(R.drawable.croatia);
        }
        if(federation.equals("BIH")){
            viewHolder.federationImageView.setImageResource(R.drawable.bosnia);
        }
        if(federation.equals("MKD")){
            viewHolder.federationImageView.setImageResource(R.drawable.former);
        }
        if(federation.equals("MNE")){
            viewHolder.federationImageView.setImageResource(R.drawable.montenegro);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onItemClick(TournamentModel tournamentModel);
    }
}
