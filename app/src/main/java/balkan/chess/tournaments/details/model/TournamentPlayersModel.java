package balkan.chess.tournaments.details.model;

public class TournamentPlayersModel {

    private String number ;
    private String title;
    private String playerName;
    private String fideId;
    private String federation;
    private String rating;

    public TournamentPlayersModel(){
        number=  "";
        title=  "";
        playerName=  "";
        fideId=  "";
        federation=  "";
        rating=  "";
    }

    public TournamentPlayersModel(String number, String title, String name, String fideId, String federation, String rating) {
        this.number = number;
        this.title = title;
        this.playerName = name;
        this.fideId = fideId;
        this.federation = federation;
        this.rating = rating;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public String getFideId() {
        return fideId;
    }

    public void setFideId(String fideId) {
        this.fideId = fideId;
    }

    public String getFederation() {
        return federation;
    }

    public void setFederation(String federation) {
        this.federation = federation;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "TournamentPlayersModel{" +
                "number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", name='" + playerName + '\'' +
                ", fideId='" + fideId + '\'' +
                ", federation='" + federation + '\'' +
                ", rating='" + rating + '\'' +
                '}'+"\n";
    }
}
