package ru.gb.lesson6.data;

import java.io.Serializable;

public class InitRepo implements Serializable {

    public InitRepo(boolean flagInitRepo) {
        this.flagInitRepo = flagInitRepo;
    }

    public InitRepo() {
    }

    private boolean flagInitRepo;

    public boolean isFlagInitRepo() {
        return flagInitRepo;
    }

    public void setFlagInitRepo(boolean flagInitRepo) {
        this.flagInitRepo = flagInitRepo;
    }

}
