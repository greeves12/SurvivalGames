package com.tatemylove.SurvivalGames.Arena;

public class BaseArena {
    public static ArenaStates states;
    public static enum ArenaStates{
        Waiting, Started, Countdown, Ended
    }
    public static ArenaStates getStates(){
        return states;
    }
}
