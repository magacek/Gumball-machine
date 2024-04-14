package edu.iu.habahram.GumballMachine.model;

public record TransitionRequest(String id, int count) {
    public int getCount() {
        return count;
    }
}
