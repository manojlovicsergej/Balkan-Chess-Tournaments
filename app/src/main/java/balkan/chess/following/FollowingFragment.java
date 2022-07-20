package balkan.chess.following;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import balkan.chess.R;
import balkan.chess.following.adapter.FollowingAdapter;
import balkan.chess.tournaments.database.TournamentDatabase;
import balkan.chess.tournaments.details.TournamentDetails;
import balkan.chess.tournaments.model.TournamentModel;

public class FollowingFragment extends Fragment {

    private RecyclerView followingTournamentRecyclerView;
    private static List<TournamentModel> followingTournamentList = new ArrayList<>();
    private FollowingAdapter followingAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_fragment,container,false);


        followingTournamentRecyclerView = view.findViewById(R.id.followingTournamentsRecyclerView);
        loadFollowingDatabaseList();
        followingTournamentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followingAdapter = new FollowingAdapter(followingTournamentList, new FollowingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TournamentModel tournamentModel) {
                Intent intent = new Intent(getContext(), TournamentDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tournamentObject", tournamentModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        followingTournamentRecyclerView.setAdapter(followingAdapter);





        return view;
    }


    private void loadFollowingDatabaseList(){
        TournamentDatabase td = Room.databaseBuilder(getContext(),TournamentDatabase.class,"followingTournaments").allowMainThreadQueries().build();
        followingTournamentList = td.tournamentDAO().getAllTournaments();
        td.close();
    }
}
