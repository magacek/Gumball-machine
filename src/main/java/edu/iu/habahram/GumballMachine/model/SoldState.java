package edu.iu.habahram.GumballMachine.model;

public class SoldState implements IState {
    IGumballMachine gumballMachine;

    public SoldState(IGumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public TransitionResult insertQuarter() {
        String message = "Please wait, we're already giving you a gumball";
        boolean succeeded = false;
        return new TransitionResult(succeeded, message, gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult ejectQuarter() {
        String message = "Sorry, you already turned the crank";
        boolean succeeded = false;
        return new TransitionResult(succeeded, message, gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult turnCrank() {
        String message = "Turning twice doesn't get you another gumball!";
        boolean succeeded = false;
        return new TransitionResult(succeeded, message, gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public TransitionResult dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.changeTheStateTo(GumballMachineState.NO_QUARTER);
        } else {
            gumballMachine.changeTheStateTo(GumballMachineState.OUT_OF_GUMBALLS);
        }
        String message = "A gumball comes rolling out the slot";
        boolean succeeded = true;
        return new TransitionResult(succeeded, message, gumballMachine.getTheStateName(), gumballMachine.getCount());
    }

    @Override
    public String getTheName() {
        return GumballMachineState.GUMBALL_SOLD.name();
    }
}
