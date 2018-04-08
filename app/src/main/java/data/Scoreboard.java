package data;

import io.realm.RealmObject;

public class Scoreboard extends RealmObject {
    private String mode;
    private int score;
    private String time;

    public Scoreboard(){} // Empty constructor

    // Constructor used when saving scores
    public Scoreboard(String mode, int score, String time) {
        this.mode = mode;
        this.score = score;
        this.time = time;
    }

    // Get parameters required by ScoreboardsMode
    public String getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }
}
