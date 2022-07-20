package balkan.chess.tournaments.details.model;

public class TournamentDetailsModel {


    private String leftSide;
    private String rightSide;

    public TournamentDetailsModel(){
        leftSide = "";
        rightSide = "";
    }

    public String getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(String leftSide) {
        this.leftSide = leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }

    public void setRightSide(String rightSide) {
        this.rightSide = rightSide;
    }


    @Override
    public String toString() {
        return "TournamentDetailsModel{" +
                "leftSide='" + leftSide + '\'' +
                ", rightSide='" + rightSide + '\'' +
                '}'+"\n";
    }
}
