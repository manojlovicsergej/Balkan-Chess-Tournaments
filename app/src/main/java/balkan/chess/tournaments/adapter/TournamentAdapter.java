package balkan.chess.tournaments.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import balkan.chess.R;
import balkan.chess.tournaments.model.TournamentModel;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder>  implements Filterable {

    List<TournamentModel> tournamentModelList;
    List<TournamentModel> tournamentModelListFull;
    private ItemClickListener itemClickListener;

    public TournamentAdapter(List<TournamentModel> tournamentModelList,ItemClickListener itemClickListener){
        this.tournamentModelList = tournamentModelList;
        tournamentModelListFull = new ArrayList<>(tournamentModelList);
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_tournament_item,parent,false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //#dbdee9 #f4f7fd
//        if(position%2==0){
//            holder.itemView.setBackgroundColor(Color.parseColor("#dbdee9"));
//        }
//        else{
//            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//        }
        holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        setDefaultFederation(holder,tournamentModelList.get(position).getFederation());
        holder.tournamentName.setText(tournamentModelList.get(position).getTournamentName());
        holder.lastChangedTextView.setText(tournamentModelList.get(position).getTournamentDetailChange());


        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClick(tournamentModelList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return tournamentModelList.size();
    }

    @Override
    public Filter getFilter() {
        return tournamentListFilter;
    }

    private Filter tournamentListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TournamentModel> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                filterList.addAll(tournamentModelListFull);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                tournamentModelListFull.forEach((e)->{
                    if(e.getTournamentName().toLowerCase().trim().contains(filterPattern)){
                        filterList.add(e);
                    }

                });
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            tournamentModelList.clear();
            tournamentModelList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView flagImage;
        TextView tournamentName;
        TextView lastChangedTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flagImage = itemView.findViewById(R.id.flagImage);
            tournamentName = itemView.findViewById(R.id.followingTournamentNameTextView);
            lastChangedTextView = itemView.findViewById(R.id.followingLastChangeTextView);

        }
    }

    public void updateData(List<TournamentModel> tournamentModels) {
        tournamentModelList.clear();
        tournamentModelList.addAll(tournamentModels);
        notifyDataSetChanged();
    }

    public void setDefaultFederation(ViewHolder viewHolder,String federation){
        if(federation.equals("SRB")){
            viewHolder.flagImage.setImageResource(R.drawable.serbia);
        }
        if(federation.equals("CRO")){
            viewHolder.flagImage.setImageResource(R.drawable.croatia);
        }
        if(federation.equals("BIH")){
            viewHolder.flagImage.setImageResource(R.drawable.bosnia);
        }
        if(federation.equals("MKD")){
            viewHolder.flagImage.setImageResource(R.drawable.former);
        }
        if(federation.equals("MNE")){
            viewHolder.flagImage.setImageResource(R.drawable.montenegro);
        }

    }

    public void uploadTemporaryList(List<TournamentModel> lista){
        this.tournamentModelListFull = lista;
    }

    public interface ItemClickListener{
        void onItemClick(TournamentModel tournamentModel);
    }
}
