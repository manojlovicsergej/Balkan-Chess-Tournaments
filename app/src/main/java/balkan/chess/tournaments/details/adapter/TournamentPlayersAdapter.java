package balkan.chess.tournaments.details.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import balkan.chess.R;
import balkan.chess.tournaments.details.model.TournamentPlayersModel;

public class TournamentPlayersAdapter extends RecyclerView.Adapter<TournamentPlayersAdapter.ViewHolder>{

    List<TournamentPlayersModel> list;


    public TournamentPlayersAdapter(List<TournamentPlayersModel> list){
        this.list  = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNumber;
        TextView textViewPlayerName;
        TextView textViewFideId;
        TextView textViewFederation;
        TextView textViewRating;
        TextView textViewTitle;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewFideId = itemView.findViewById(R.id.textViewFideId);
            textViewFederation = itemView.findViewById(R.id.textViewFederation);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);


        }
    }


    @NonNull
    @Override
    public TournamentPlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.tournament_players_listview_item,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TournamentPlayersAdapter.ViewHolder holder, int position) {

        holder.textViewNumber.setText(list.get(position).getNumber());
        holder.textViewPlayerName.setText(list.get(position).getPlayerName());


        if(list.get(position).getFederation().equals("")){
            holder.textViewFederation.setText("/");
        }else{
            holder.textViewFederation.setText(list.get(position).getFederation());
        }

        if(list.get(position).getFideId().equals("")){
            holder.textViewFideId.setText("/");
        }else{
            holder.textViewFideId.setText(list.get(position).getFideId());
        }

        if(list.get(position).getRating().equals("")){
            holder.textViewRating.setText("/");
        }else{
            holder.textViewRating.setText(list.get(position).getRating());
        }

        if(list.get(position).getTitle().equals("")){
            holder.textViewTitle.setText("/");
        }else{
            holder.textViewTitle.setText(list.get(position).getTitle());
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
