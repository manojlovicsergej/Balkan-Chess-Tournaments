package balkan.chess.tournaments.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import balkan.chess.tournaments.model.TournamentModel;

@Dao
public interface TournamentDAO {

    @Insert
    void insertAll(TournamentModel... tournamentModels);

    @Query("DELETE FROM TOURNAMENTMODEL")
    void deleteAll();

    @Query("SELECT * FROM tournamentmodel")
    List<TournamentModel> getAllTournaments();

    @Query("DELETE FROM tournamentmodel WHERE tournamentLink=:id1")
    void deleteTournament(String id1);
}
