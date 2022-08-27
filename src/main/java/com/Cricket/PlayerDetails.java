package com.Cricket;

public class PlayerDetails implements Comparable<PlayerDetails>{
    private int id;
    private String name;
    private int playedMatch;
    private int runs;
    private int wickets;
    private int ducks;
    private String playerType;


    public PlayerDetails(int id, String name, int playedMatch, int runs, int wickets, int ducks, String playerType) {
        this.id = id;
        this.name = name;
        this.playedMatch = playedMatch;
        this.runs = runs;
        this.wickets = wickets;
        this.ducks = ducks;
        this.playerType = playerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayedMatch() {
        return playedMatch;
    }

    public void setPlayedMatch(int playedMatch) {
        this.playedMatch = playedMatch;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getDucks() {
        return ducks;
    }

    public void setDucks(int ducks) {
        this.ducks = ducks;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    @Override
    public String toString(){
        return id +
                "\t" + name +
                "\t" + playedMatch +
                "\t" + runs +
                "\t" + wickets +
                "\t" + ducks +
                "\t" + playerType;
    }

    @Override
    public int compareTo(PlayerDetails p) {
        return this.name.compareTo(p.getName());
    }
}
