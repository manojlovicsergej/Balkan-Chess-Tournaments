package balkan.chess.tournaments.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import balkan.chess.tournaments.model.TournamentModel;

@Database(entities = {TournamentModel.class},version = 1)
public abstract class TournamentDatabase extends RoomDatabase {

    public abstract TournamentDAO tournamentDAO();



}
