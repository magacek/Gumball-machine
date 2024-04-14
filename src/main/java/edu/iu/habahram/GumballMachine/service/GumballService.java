package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.GumballMachine;
import edu.iu.habahram.GumballMachine.model.GumballMachine2;
import edu.iu.habahram.GumballMachine.model.GumballMachineRecord;
import edu.iu.habahram.GumballMachine.model.IGumballMachine;
import edu.iu.habahram.GumballMachine.model.TransitionResult;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GumballService implements IGumballService{

    IGumballRepository gumballRepository;

    public GumballService(IGumballRepository gumballRepository) {
        this.gumballRepository = gumballRepository;
    }

    private TransitionResult transit(String id, Transition action) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = runTheMachine(machine, action);
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    private TransitionResult runTheMachine(IGumballMachine machine, Transition action) {
        switch (action) {
            case INSERT_QUARTER -> {
                return machine.insertQuarter();
            }
            case EJECT_QUARTER -> {
                return machine.ejectQuarter();
            }
            case TURN_CRANK -> {
                return machine.turnCrank();
            }
        }
        return null;
    }

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        return transit(id, Transition.INSERT_QUARTER);
    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {
        return transit(id, Transition.EJECT_QUARTER);
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {
        return transit(id, Transition.TURN_CRANK);
    }

    @Override
    public GumballMachineRecord refill(String id, int count) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record != null) {
            record.setCount(count);
            gumballRepository.save(record);
            return record;
        }
        return null; 
    }

    @Override
    public List<GumballMachineRecord> findAll() throws IOException {
        return gumballRepository.findAll();
    }

    @Override
    public GumballMachineRecord findById(String id) throws IOException {
        return gumballRepository.findById(id);
    }

    @Override
    public String save(GumballMachineRecord gumballMachineRecord) throws IOException {
        return gumballRepository.save(gumballMachineRecord);
    }
}