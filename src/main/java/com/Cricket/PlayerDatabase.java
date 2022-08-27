package com.Cricket;

import java.util.*;

class AverageComparator implements Comparator<PlayerDetails> {
    @Override
    public int compare (PlayerDetails o1, PlayerDetails o2) {
        return Double.compare(o1.getRuns()/(double)o1.getPlayedMatch(), o2.getRuns()/(double)o2.getPlayedMatch());
    }
}
public class PlayerDatabase{
    Set<PlayerDetails> playerSet = new HashSet<>(20);
    Set<PlayerDetails> teamSet = new HashSet<>(11);
    static int numberOfBowlers = 0, numberOfWicketKeepers = 0;

    static Scanner sc = new Scanner(System.in);
    //Display the list of 20 players in tabular format
    public void displayAllList(){
        List<PlayerDetails> playerList= new ArrayList<>(playerSet);
        Collections.sort(playerList);
        System.out.println("ID\tName\tMatch Played\tTotal Runs\tWicket taken\tDucks\tPlayer Type");
        for(PlayerDetails p : playerList) {
            p.toString();
        }
    }

    //Update method to modify the player information
    public void updatePlayerInfo() throws PlayerNotFoundException {
        System.out.println("Enter the Player name for update :  ");
        String name = sc.nextLine();
        name = name.equals("") ? sc.nextLine() : name;
        PlayerDetails p = findPlayerByName(name);
        if(p == null) {
            throw new PlayerNotFoundException("No such Player exists!");
        }
        while (true) {
            System.out.println("Choose the option to be updated : 1. Match Played\n2. Total Runs\n" +
                    "3. Total Wickets\n4. Total Ducks\n5. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    int playedMatch = sc.nextInt();
                    p.setPlayedMatch(playedMatch);
                    break;
                case 2:
                    int runs = sc.nextInt();
                    p.setRuns(runs);
                    break;
                case 3:
                    int wickets = sc.nextInt();
                    p.setWickets(wickets);
                    break;
                case 4:
                    int ducks = sc.nextInt();
                    p.setDucks(ducks);
                    break;
                case 5: return;
                default:
                    System.out.println("Invalid choice!!!");
            }

        }

    }

    //method to display Final Team
    public void displayTeamList() throws PlayerNotFoundException {
        addFinalTeamPlayers();
        List<PlayerDetails> teamList= new ArrayList<>(teamSet);
        Collections.sort(teamList);
        System.out.println("ID\tName\tMatch Played\tTotal Runs\tWicket taken\tDucks\tPlayer Type");
        for(PlayerDetails p : teamList) {
            p.toString();
        }
    }

    public void addPlayer() throws DuckMatchException {
        if(playerSet.size() >= 20){
            System.out.println("You cant add more than 20 players.");
        }
        else{
            if(numberOfBowlers < 3){
                System.out.println("NOTE: Atleast three BOWLERS must be a part of the 20-member squad");
                System.out.println("Please enter a Bowler's details : ");
                playerSet.add(userInput());
            } else if (numberOfWicketKeepers<1) {
                System.out.println("Enter the Wicket Keeper : ");
                playerSet.add((userInput()));
            } else {
                playerSet.add(userInput());
            }
        }
    }
    public PlayerDetails findPlayerByName(String name){
        for(PlayerDetails p : playerSet) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public void addFinalTeamPlayers() throws PlayerNotFoundException{
        System.out.println("Enter the number of bowlers to be added in the final team : ");
        int n = sc.nextInt();
        if(n > numberOfBowlers){
            throw new PlayerNotFoundException("Not Enough Bowlers in the 20-member squad!!");
        }

        List<PlayerDetails> playerList = new ArrayList<>(playerSet);
        Collections.sort(playerList,new AverageComparator());

        playerList.stream().filter(player -> player.getPlayerType().equalsIgnoreCase("Bowler")).forEach(bowler ->
        {
            if(n <= numberOfBowlers) {
                teamSet.add(bowler);
                playerList.remove(bowler);
            }
        });
        playerList.stream().forEach(player ->
        {
            int numberOfKeepers = 0;
            if(player.getPlayerType().equalsIgnoreCase("Wicketkeeper")){
                if(numberOfKeepers <= 1) {
                    teamSet.add(player);
                }
            }
            teamSet.add(player);
        });
    }

    public PlayerDetails userInput() throws DuckMatchException{
        System.out.println("Enter the id : ");
        int id = sc.nextInt();
        System.out.println("Enter the player name : ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter the Matches played : ");
        int matches = sc.nextInt();
        System.out.println("Enter the runs scored : ");
        int runs = sc.nextInt();
        System.out.println("Enter the number of wickets : ");
        int wickets = sc.nextInt();
        System.out.println("Enter the number of ducks : ");
        int ducks = sc.nextInt();
        System.out.println("Enter the Player type : ");
        String type = sc.next();
        if(type.equalsIgnoreCase("Bowler")){
            numberOfBowlers++;
        }
        if(type.equalsIgnoreCase("wicketkeeper")){
            numberOfWicketKeepers++ ;
        }
        if(ducks > matches || (ducks == matches && runs > 0)){
            throw new DuckMatchException("Invalid statistics");
        }
        return new PlayerDetails(id,name,matches,runs,wickets,ducks,type);
    }

    public static void main(String[] args) throws PlayerNotFoundException, DuckMatchException {
        PlayerDatabase admin = new PlayerDatabase();

        while(true){
            System.out.println("Select the options : " );
            System.out.println("1. Display All Player List");
            System.out.println("2. Update Player Information");
            System.out.println("3. Display the Team");
            System.out.println("4. Add New Player");
            System.out.println("5. Exit");
            int choice = sc.nextInt();
            switch (choice){
                case 1: admin.displayAllList(); break;
                case 2: admin.updatePlayerInfo(); break;
                case 3: admin.displayTeamList();break;
                case 4: admin.addPlayer();break;
                case 5 : return;
                default:
                    System.out.println("Invalid choice!!!");
                    sc.close();
            }

        }

    }

}
