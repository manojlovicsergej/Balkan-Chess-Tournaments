package balkan.chess.tournaments.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import balkan.chess.R;
import balkan.chess.tournaments.details.model.TournamentDetailsModel;

public class TournamentDetailsAdapter extends RecyclerView.Adapter<TournamentDetailsAdapter.ViewHolder> {

    List<TournamentDetailsModel> list;

    public TournamentDetailsAdapter(List<TournamentDetailsModel> list){
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewLeftSide;
        TextView textViewRightSide;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLeftSide = itemView.findViewById(R.id.textViewLeftSide);
            textViewRightSide = itemView.findViewById(R.id.textViewRightSide);

        }
    }

    @NonNull
    @Override
    public TournamentDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tournament_details_listview_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentDetailsAdapter.ViewHolder holder, int position) {

        holder.textViewLeftSide.setText(list.get(position).getLeftSide());
        holder.textViewRightSide.setText(list.get(position).getRightSide());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
