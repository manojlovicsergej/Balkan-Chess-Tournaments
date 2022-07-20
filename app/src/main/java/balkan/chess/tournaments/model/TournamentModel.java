package balkan.chess.tournaments.model;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TournamentModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="ticker")
    private int ticker;

    @ColumnInfo(name="tournamentName")
    private String tournamentName;

    @ColumnInfo(name="tournamentDetailChange")
    private String tournamentDetailChange;

    @ColumnInfo(name="tournamentLink")
    private String tournamentLink;

    @ColumnInfo(name="federation")
    private String federation;


    public TournamentModel(){
        this.ticker = 0 ;
        this.tournamentLink = "";
        this.tournamentDetailChange="";
        this.tournamentName="";
        this.federation= "";
    }

    public TournamentModel(int ticker, String tournamentName, String tournamentDetailChange, String tournamentLink,String federation) {
        this.ticker = ticker;
        this.tournamentName = tournamentName;
        this.tournamentDetailChange = tournamentDetailChange;
        this.tournamentLink = tournamentLink;
        this.federation = federation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicker() {
        return ticker;
    }

    public void setTicker(int ticker) {
        this.ticker = ticker;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentDetailChange() {
        return tournamentDetailChange;
    }

    public void setTournamentDetailChange(String tournamentDetailChange) {
        this.tournamentDetailChange = tournamentDetailChange;
    }

    public String getFederation() {
        return federation;
    }

    public void setFederation(String federation) {
        this.federation = federation;
    }

    public String getTournamentLink() {
        return tournamentLink;
    }

    public void setTournamentLink(String tournamentLink) {
        this.tournamentLink = tournamentLink;
    }

    @Override
    public String toString() {
        return "TournamentModel{" +
                "ticker=" + ticker +
                ", tournamentName='" + tournamentName + '\'' +
                ", tournamentDetailChange='" + tournamentDetailChange + '\'' +
                ", tournamentLink='" + tournamentLink + '\'' +
                ", federation='" + federation + '\'' +
                '}'+"\n";
    }
}
