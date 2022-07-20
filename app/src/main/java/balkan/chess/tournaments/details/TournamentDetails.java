package balkan.chess.tournaments.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import balkan.chess.R;
import balkan.chess.tournaments.database.TournamentDatabase;
import balkan.chess.tournaments.details.adapter.TournamentDetailsAdapter;
import balkan.chess.tournaments.details.adapter.TournamentPlayersAdapter;
import balkan.chess.tournaments.details.location.ShowTournamentLocationActivity;
import balkan.chess.tournaments.details.model.TournamentDetailsModel;
import balkan.chess.tournaments.details.model.TournamentPlayersModel;
import balkan.chess.tournaments.model.TournamentModel;

public class TournamentDetails extends AppCompatActivity {

    private TextView tournamentTitle;
    private RecyclerView tournamentDetailsRecyclerView;
    private RecyclerView tournamentPlayersRecyclerView;
    private ImageButton buttonBackTournamentDetails;
    private ImageButton buttonFavoritesTournamentDetails;
    private ImageButton buttonLocationTournamentDetails;
    private ImageButton buttonLinkTournamentDetails;

    public static List<TournamentDetailsModel> listOfTournamentDetails = new ArrayList<>();
    public static List<TournamentPlayersModel> listOfPlayersDetails = new ArrayList<>();


    private loading_tournament_details loading_tournament_details = new loading_tournament_details();
    private String tournamentDetailsLink;
    private static String tournamentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tournament_details);
        getSupportActionBar().hide();



        tournamentTitle = findViewById(R.id.tournamentTitle);
        tournamentDetailsRecyclerView = findViewById(R.id.tournamentDetailsRecyclerView);
        tournamentPlayersRecyclerView = findViewById(R.id.tournamentPlayersRecyclerView);
        buttonBackTournamentDetails = findViewById(R.id.buttonBackTournamentDetails);
        buttonFavoritesTournamentDetails = findViewById(R.id.buttonFavoritesTournamentDetails);
        buttonLinkTournamentDetails = findViewById(R.id.buttonLinkTournamentDetails);
        buttonLocationTournamentDetails = findViewById(R.id.buttonLocationTournamentDetails);


        buttonLinkTournamentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(tournamentDetailsLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        buttonLocationTournamentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address= "";

                for(int i = 0 ; i < listOfTournamentDetails.size();i++){
                    if(listOfTournamentDetails.get(i).getLeftSide().toLowerCase().contains("location")){
                        address = listOfTournamentDetails.get(i).getRightSide();

                    }
                }

                if(address.equals("")){
                    Toast.makeText(getApplicationContext(),"Location not found for selected tournament !",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ShowTournamentLocationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", address);
                    bundle.putSerializable("tournamentName",tournamentName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }


            }
        });

        buttonBackTournamentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonFavoritesTournamentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    addTournamentToFavoriteLocalDatabase();
                    Toast.makeText(getApplicationContext(),"Successfully added !",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error with local database !",Toast.LENGTH_SHORT).show();
                }


            }
        });


        loadAllDataFromProceededObject();

        loading_tournament_details.execute();


    }

    private void addTournamentToFavoriteLocalDatabase(){

        TournamentDatabase td  = Room.databaseBuilder(getApplicationContext(),TournamentDatabase.class,"followingTournaments").allowMainThreadQueries().build();
        td.tournamentDAO().insertAll((TournamentModel) getIntent().getSerializableExtra("tournamentObject"));
        td.close();


    }

    public void loadTournamentPlayersToListViewPlayers(){

        List<TournamentPlayersModel> tournamentPlayersModels = new ArrayList<>();
        for(int i  = 1 ; i < listOfPlayersDetails.size();i++){
            tournamentPlayersModels.add(listOfPlayersDetails.get(i));
        }

        TournamentPlayersAdapter tournamentPlayersAdapter = new TournamentPlayersAdapter(tournamentPlayersModels);
        tournamentPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tournamentPlayersRecyclerView.setAdapter(tournamentPlayersAdapter);


    }


    public void loadTournamentDetailsToListViewDetails(){
        List<TournamentDetailsModel> tournamentDetailsModel = new ArrayList<>();
        for(int i = 1 ; i < listOfTournamentDetails.size();i++){
            tournamentDetailsModel.add(listOfTournamentDetails.get(i));
        }

        TournamentDetailsAdapter tournamentDetailsAdapter = new TournamentDetailsAdapter(tournamentDetailsModel);
        tournamentDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tournamentDetailsRecyclerView.setAdapter(tournamentDetailsAdapter);


    }

    public void loadAllDataFromProceededObject(){
        TournamentModel tournamentObject = (TournamentModel) getIntent().getSerializableExtra("tournamentObject");
        tournamentTitle.setText(tournamentObject.getTournamentName());
        tournamentName = tournamentObject.getTournamentName();
        tournamentDetailsLink = tournamentObject.getTournamentLink();
    }

    private class loading_tournament_details extends AsyncTask<Void ,Void,Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document document = null;
            listOfTournamentDetails.clear();
            listOfPlayersDetails.clear();

            try {
                document = Jsoup.connect(tournamentDetailsLink+"&turdet=YES").get();

                for (Element row : document.select("div.defaultDialog:nth-of-type(1) tr")) {
                    if (row.select("td.CR:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else {

                         String left = row.select("td.CR:nth-of-type(1)").text();
                         String right = row.select("td.CR:nth-of-type(2)").text();


                         TournamentDetailsModel tr = new TournamentDetailsModel();
                         tr.setLeftSide(left);
                         tr.setRightSide(right);
                         listOfTournamentDetails.add(tr);


                    }


                }


            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Failed to connect to chess results !", Toast.LENGTH_LONG).show();
            }
            try {
                document = Jsoup.connect(tournamentDetailsLink+"&turdet=YES").get();

                for (Element row : document.select("div.defaultDialog:nth-of-type(2) tr")) {
                    if (row.select(".CRc").text().equals("")) {
                        continue;
                    } else {
                        String number , title,playerName,fideId,federation,rating;

                        if(row.select("td.CR:nth-of-type(6)").text().equals("")){
                             number = row.select("td.CRc:nth-of-type(1)").text();
                             title = row.select("td.CR:nth-of-type(2)").text();
                             playerName = row.select(".CRdb").text();
                             fideId = row.select("td:nth-of-type(4)").text();
                             federation = row.select("td.CR:nth-of-type(5)").text();
                             rating = row.select(".CRr").text();
                        }else{
                             number = row.select("td.CRc:nth-of-type(1)").text();
                             title = row.select("td.CR:nth-of-type(3)").text();
                             playerName = row.select("td.CR:nth-of-type(4)").text();
                             fideId = row.select("td:nth-of-type(5)").text();
                             federation = row.select("td.CR:nth-of-type(6)").text();
                             rating = row.select(".CRr").text();
                        }


                        TournamentPlayersModel tr = new TournamentPlayersModel(number,title,playerName,fideId,federation,rating);
                        listOfPlayersDetails.add(tr);


                    }


                }


            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Failed to connect to chess results !", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);


            loadTournamentDetailsToListViewDetails();
            loadTournamentPlayersToListViewPlayers();
        }
    }



}