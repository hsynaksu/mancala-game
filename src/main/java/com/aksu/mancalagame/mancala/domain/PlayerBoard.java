package com.aksu.mancalagame.mancala.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PlayerBoard {
    public static int PIT_COUNT = 6;
    private final List<Integer> smallPits;
    private Integer bigPit;

    public PlayerBoard(List<Integer> smallPits, Integer bigPit) {
        this.smallPits = new ArrayList<>(smallPits);
        this.bigPit = bigPit;
    }

    public PlayerBoard(Integer initialStoneCount) {
        this.smallPits = new ArrayList<>(Collections.nCopies(PIT_COUNT, initialStoneCount));
        this.bigPit = 0;
    }

    public void clearPit(int index) {
        smallPits.set(index, 0);
    }

    public void clearAllSmallPits() {
        Collections.fill(smallPits, 0);
    }

    public List<Integer> getSmallPits() {
        return smallPits;
    }

    public Integer getBigPit() {
        return bigPit;
    }

    public void setBigPit(Integer bigPit) {
        this.bigPit = bigPit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerBoard that = (PlayerBoard) o;
        return Objects.equals(getSmallPits(), that.getSmallPits()) && Objects.equals(getBigPit(), that.getBigPit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSmallPits(), getBigPit());
    }

}
