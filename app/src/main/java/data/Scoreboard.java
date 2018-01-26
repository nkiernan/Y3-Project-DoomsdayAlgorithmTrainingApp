package data;

import io.realm.RealmObject;

public class Scoreboard extends RealmObject {
    private String mode;
    private int score;
    private String time;

    public Scoreboard(){}

    public Scoreboard(String mode, int score, String time) {
        this.mode = mode;
        this.score = score;
        this.time = time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
                "mode='" + mode + '\'' +
                ", score=" + score +
                ", time='" + time + '\'' +
                '}';
    }
}
