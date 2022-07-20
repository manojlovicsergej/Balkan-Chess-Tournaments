package balkan.chess.tournaments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import balkan.chess.R;
import balkan.chess.tournaments.adapter.TournamentAdapter;
import balkan.chess.tournaments.details.TournamentDetails;
import balkan.chess.tournaments.model.TournamentModel;

public class TournamentsFragment extends Fragment implements Serializable {

    public static List<TournamentModel> tournamentList = new ArrayList<>();
    List<TournamentModel> temporaryTournamentList = new ArrayList<>();
    private RecyclerView recyclerViewTournaments;
    public static TournamentAdapter tournamentAdapter;
    private Button refreshAllButton;
    private SearchView searchView;
    private Spinner federation;
    private loading_balkan_federation balkan_federation = new loading_balkan_federation();

    @Override
    public void onStart() {
        super.onStart();
        try {
            balkan_federation.execute();
        }
        catch(Exception e1){
            Log.e("Error","the task has already been executed (a task can be executed only once)");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tournaments_fragment,container,false);
        recyclerViewTournaments = view.findViewById(R.id.recyclerViewTournaments);
        refreshAllButton = view.findViewById(R.id.refreshAllButton);
        searchView = view.findViewById(R.id.searchView);
        federation = view.findViewById(R.id.federationSpinner);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tournamentAdapter.uploadTemporaryList(temporaryTournamentList);
                tournamentAdapter.getFilter().filter(s);
                return true;
            }
        });

        federation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chooseFederation(federation.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        refreshAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFederation(federation.getSelectedItem().toString());
            }
        });


        recyclerViewTournaments.clearOnChildAttachStateChangeListeners();
        recyclerViewTournaments.setLayoutManager(new LinearLayoutManager(getContext()));


        temporaryTournamentList.clear();
        for (TournamentModel tournamentModel : tournamentList) {
            if(tournamentModel.equals(federation.getSelectedItem().toString())){
                temporaryTournamentList.add(tournamentModel);
            }
        }

        tournamentAdapter = new TournamentAdapter(temporaryTournamentList, new TournamentAdapter.ItemClickListener() {
            @Override
            public void onItemClick(TournamentModel tournamentModel) {
                Intent intent = new Intent(getContext(), TournamentDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tournamentObject", tournamentModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerViewTournaments.setAdapter(tournamentAdapter);




        return view;
    }

    public void chooseFederation(String federation){

        List<TournamentModel> listOfTournaments = new ArrayList<>();

        tournamentList.forEach(e->{
            if(e.getFederation().equals(federation)){
                listOfTournaments.add(e);
            }
        });

        tournamentAdapter.updateData(listOfTournaments);
    }

    private class loading_balkan_federation extends AsyncTask<Void ,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document documentSRB = null;
            Document documentBIH = null;
            Document documentCRO = null;
            Document documentMNE = null;
            Document documentMKD = null;
            tournamentList.clear();
            try {
                documentSRB = Jsoup.connect("https://chess-results.com/fed.aspx?lan=1&fed=SRB").get();

                for(Element row : documentSRB.select("table.CRs2 tr ")){
                    if(row.select(".CRc").text().equals("")){
                        continue;
                    }else{
                        final String ticker = row.select(".CRc").text();
                        final String tournamentName = row.select("td:nth-of-type(2)").text();
                        final String tournamentDetailChange = row.select(".CRnowrap").text();

                        Element linkElement = row.select("a").first();
                        final String link = linkElement.attr("abs:href");

                        //Log.d("link",ticker + " " + link);


                        TournamentModel tm = new TournamentModel();
                        tm.setTicker(Integer.parseInt(ticker.trim()));
                        tm.setTournamentName(tournamentName);
                        tm.setTournamentDetailChange(tournamentDetailChange);
                        tm.setTournamentLink(link);
                        tm.setFederation("SRB");

                        tournamentList.add(tm);


                    }


                }




            } catch (IOException e) {
                Toast.makeText(getContext(),"Failed to connect to chess results !",Toast.LENGTH_LONG).show();
            }
            try {
                documentBIH = Jsoup.connect("https://chess-results.com/fed.aspx?lan=1&fed=BIH").get();

                for(Element row : documentBIH.select("table.CRs2 tr ")){
                    if(row.select(".CRc").text().equals("")){
                        continue;
                    }else{
                        final String ticker = row.select(".CRc").text();
                        final String tournamentName = row.select("td:nth-of-type(2)").text();
                        final String tournamentDetailChange = row.select(".CRnowrap").text();

                        Element linkElement = row.select("a").first();
                        final String link = linkElement.attr("abs:href");

                        //Log.d("link",ticker + " " + link);


                        TournamentModel tm = new TournamentModel();
                        tm.setTicker(Integer.parseInt(ticker.trim()));
                        tm.setTournamentName(tournamentName);
                        tm.setTournamentDetailChange(tournamentDetailChange);
                        tm.setTournamentLink(link);
                        tm.setFederation("BIH");

                        tournamentList.add(tm);


                    }


                }




            } catch (IOException e) {
                Toast.makeText(getContext(),"Failed to connect to chess results !",Toast.LENGTH_LONG).show();
            }

            try {
                documentCRO = Jsoup.connect("https://chess-results.com/fed.aspx?lan=1&fed=CRO").get();

                for(Element row : documentCRO.select("table.CRs2 tr ")){
                    if(row.select(".CRc").text().equals("")){
                        continue;
                    }else{
                        final String ticker = row.select(".CRc").text();
                        final String tournamentName = row.select("td:nth-of-type(2)").text();
                        final String tournamentDetailChange = row.select(".CRnowrap").text();

                        Element linkElement = row.select("a").first();
                        final String link = linkElement.attr("abs:href");

                        //Log.d("link",ticker + " " + link);


                        TournamentModel tm = new TournamentModel();
                        tm.setTicker(Integer.parseInt(ticker.trim()));
                        tm.setTournamentName(tournamentName);
                        tm.setTournamentDetailChange(tournamentDetailChange);
                        tm.setTournamentLink(link);
                        tm.setFederation("CRO");

                        tournamentList.add(tm);


                    }


                }




            } catch (IOException e) {
                Toast.makeText(getContext(),"Failed to connect to chess results !",Toast.LENGTH_LONG).show();
            }

            try {
                documentMNE = Jsoup.connect("https://chess-results.com/fed.aspx?lan=1&fed=MNE").get();

                for(Element row : documentMNE.select("table.CRs2 tr ")){
                    if(row.select(".CRc").text().equals("")){
                        continue;
                    }else{
                        final String ticker = row.select(".CRc").text();
                        final String tournamentName = row.select("td:nth-of-type(2)").text();
                        final String tournamentDetailChange = row.select(".CRnowrap").text();

                        Element linkElement = row.select("a").first();
                        final String link = linkElement.attr("abs:href");

                        //Log.d("link",ticker + " " + link);


                        TournamentModel tm = new TournamentModel();
                        tm.setTicker(Integer.parseInt(ticker.trim()));
                        tm.setTournamentName(tournamentName);
                        tm.setTournamentDetailChange(tournamentDetailChange);
                        tm.setTournamentLink(link);
                        tm.setFederation("MNE");

                        tournamentList.add(tm);


                    }


                }




            } catch (IOException e) {
                Toast.makeText(getContext(),"Failed to connect to chess results !",Toast.LENGTH_LONG).show();
            }

            try {
                documentMKD = Jsoup.connect("https://chess-results.com/fed.aspx?lan=1&fed=MKD").get();

                for(Element row : documentMKD.select("table.CRs2 tr ")){
                    if(row.select(".CRc").text().equals("")){
                        continue;
                    }else{
                        final String ticker = row.select(".CRc").text();
                        final String tournamentName = row.select("td:nth-of-type(2)").text();
                        final String tournamentDetailChange = row.select(".CRnowrap").text();

                        Element linkElement = row.select("a").first();
                        final String link = linkElement.attr("abs:href");

                        //Log.d("link",ticker + " " + link);


                        TournamentModel tm = new TournamentModel();
                        tm.setTicker(Integer.parseInt(ticker.trim()));
                        tm.setTournamentName(tournamentName);
                        tm.setTournamentDetailChange(tournamentDetailChange);
                        tm.setTournamentLink(link);
                        tm.setFederation("MKD");

                        tournamentList.add(tm);


                    }


                }




            } catch (IOException e) {
                Toast.makeText(getContext(),"Failed to connect to chess results !",Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }
}

