package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.GumballMachine;
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

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Gumball Machine not found with id: " + id);
        }
        IGumballMachine machine = new GumballMachine(record.getId(), record.getState(), record.getCount());
        TransitionResult result = machine.insertQuarter();
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Gumball Machine not found with id: " + id);
        }
        IGumballMachine machine = new GumballMachine(record.getId(), record.getState(), record.getCount());
        TransitionResult result = machine.ejectQuarter();
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Gumball Machine not found with id: " + id);
        }
        IGumballMachine machine = new GumballMachine(record.getId(), record.getState(), record.getCount());
        TransitionResult result = machine.turnCrank();
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public GumballMachineRecord refill(String id, int count) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        if (record == null) {
            throw new IllegalArgumentException("Gumball Machine not found with id: " + id);
        }
        record.setCount(record.getCount() + count);
        save(record);
        return record;
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
