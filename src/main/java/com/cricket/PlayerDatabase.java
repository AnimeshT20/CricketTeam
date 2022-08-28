package com.cricket;

import java.util.*;
import java.util.stream.Collectors;

class AverageComparator implements Comparator<PlayerDetails> {
    @Override
    public int compare (PlayerDetails o1, PlayerDetails o2) {
        return Double.compare(o1.getRuns()/(double)o1.getPlayedMatch(), o2.getRuns()/(double)o2.getPlayedMatch());
    }
}
public class PlayerDatabase{
    static Set<PlayerDetails> playerSet = new HashSet<>(20);
    Set<PlayerDetails> teamSet = new HashSet<>(11);
    int numberOfBowlers = 0;
    int numberOfWicketKeepers = 0;
    static Scanner sc = new Scanner(System.in);
    //Display the list of 20 players in tabular format
    public void displayAllList(){
        List<PlayerDetails> playerList= new ArrayList<>(playerSet);
        Collections.sort(playerList);
        System.out.printf("ID\tName\t\t\tMatch-Played Total-Runs\tWickets\tDucks\tPlayer-Type%n");
        System.out.println("____________________________________________________________________________");

        for(PlayerDetails p : playerList) {
            System.out.printf(p.toString());

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
        System.out.printf("ID\tName\t\t\tMatch-Played Total-Runs\tWicket-taken Ducks\tPlayer-Type%n");
        System.out.println("____________________________________________________________________________");
        for(PlayerDetails p : teamList) {
            System.out.printf(p.toString());
        }
    }

    public void addPlayer() throws DuckMatchException {
        if(playerSet.size() >= 20){
            System.out.println("You cant add more than 20 players.");
        }
        else{
            if(numberOfBowlers < 3){
                System.out.println("NOTE: At least three BOWLERS must be a part of the 20-member squad");
                System.out.println("Please enter a Bowler's details : ");
                playerSet.add(userInput());
            } else if (numberOfWicketKeepers < 1) {
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

        if(n > PlayerDetails.totalBowlers && n < 3){
            throw new PlayerNotFoundException("Not Enough Bowlers in the 20-member squad!!");
        }

        int selectedCount = 0;
        List<PlayerDetails> playerList = new ArrayList<>(playerSet);
        Collections.sort(playerList,new AverageComparator());

        List<PlayerDetails> bowlerList = playerList.stream().filter(player -> player.getPlayerType().equalsIgnoreCase("Bowler")).limit(n)
                .collect(Collectors.toList());
        teamSet.addAll(bowlerList);
        playerList.removeAll(bowlerList);
        selectedCount += n;

        List<PlayerDetails> otherList = playerList.stream().limit((long)11-selectedCount)
                .collect(Collectors.toList());
        int keeperNumber = 0;
        for (PlayerDetails player: otherList){
            if(player.getPlayerType().equalsIgnoreCase("wicketkeeper") && keeperNumber < 1){
                keeperNumber++;
            }else if(player.getPlayerType().equalsIgnoreCase("wicketkeeper") && keeperNumber >= 1){
                otherList.remove(player);
                otherList.add((PlayerDetails) playerList.stream().skip((long)11-selectedCount+keeperNumber-1).limit(1));
            }
        }
        teamSet.addAll(otherList);

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
            numberOfBowlers += 1;
        }
        if(type.equalsIgnoreCase("wicketkeeper")){
            numberOfWicketKeepers += 1  ;
        }
        if(ducks > matches || (ducks == matches && runs > 0)){
            throw new DuckMatchException("Invalid statistics");
        }
        return new PlayerDetails(id,name,matches,runs,wickets,ducks,type);
    }

    public static void main(String[] args) throws PlayerNotFoundException, DuckMatchException {
        PlayerDatabase admin = new PlayerDatabase();
        playerSet.add(new PlayerDetails(1,"Shikhar Dhawan",400,70500,25,32,"Batsman"		 )  );
        playerSet.add(new PlayerDetails(2,"Virat Kohli",400,59500,25,32,"Batsman"	)	    );
        playerSet.add(new PlayerDetails(3,"Ravindra Jadeja",400,10500,25,32,"AllRounder"    ) );
        playerSet.add(new PlayerDetails(4,"Mohammed Shami",400,15500,25,32,"Bowler"	         ) );
        playerSet.add(new PlayerDetails(5,"Manish Pandey",400,13500,25,32,"Bowler"		 ) );
        playerSet.add(new PlayerDetails(6,"Jasprit Bumrah",400,38500,25,32,"Bowler"		 ) );
        playerSet.add(new PlayerDetails(19,"Mayank Agarwal",400,29500,25,32,"Batsman"	)	    );
        playerSet.add(new PlayerDetails(8,"Shreyas Iyer",400,91500,25,32,"Batsman"	)	    );
        playerSet.add(new PlayerDetails(9,"Yuzvendra Chahal",400,85500,25,32,"Bowler" 	)	    );
        playerSet.add( new PlayerDetails(10,"KL Rahul",400,75500,25,32,"WicketKeeper" ) );
        playerSet.add( new PlayerDetails(11,"Hardik Pandya",400,92500,25,32,"Batsman" 	)	    );
        playerSet.add( new PlayerDetails(12,"Kuldeep Yadav",400,16500,25,32,"Bowler"	)	    );
        playerSet.add( new PlayerDetails(13,"Shardul Thakur",400,7500,425,32,"Bowler" 	)	    );
        playerSet.add( new PlayerDetails(14,"Navdeep Saini",400,8500,425,32,"Bowler"	 )     );
        playerSet.add( new PlayerDetails(15,"Shubman Gill",400,72500,25,32,"Batsman" 		 ) );
        playerSet.add( new PlayerDetails(16,"Ajinkya Rahane",400,62500,25,32,"Batsman" 	)	    );
        playerSet.add( new PlayerDetails(17,"Ravichandran Ashwin",400,22500,25,32,"Bowler" 		 ) );
        playerSet.add( new PlayerDetails(18,"Umesh Yadav",400,32500,25,32,"Bowler" 		 ) );
        playerSet.add( new PlayerDetails(7,"MS Dhoni",400,98500,25,32,"WicketKeeper"  ) );
        playerSet.add( new PlayerDetails(20,"Hanuma Vihari",400,52500,25,32,"Batsman"));

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
                case 5: return;
                default:
                    System.out.println("Invalid choice!!!");
                    sc.close();
            }
        }
    }
}
