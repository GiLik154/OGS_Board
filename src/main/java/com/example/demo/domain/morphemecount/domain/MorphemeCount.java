package com.example.demo.domain.morphemecount.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class MorphemeCount {
    @Id
    private String word;

    private long count = 0L;

    private int percent = 0;

    protected MorphemeCount() {
    }

    public MorphemeCount(String word) {
        this.word = word;
    }

    public void increaseCount(long totalBoardCount) {
        this.count++;
        calculatePercent(totalBoardCount);
    }

    public void decreaseCount(long totalBoardCount) {
        this.count--;
        calculatePercent(totalBoardCount);
    }

    // 어떻게 동작하는지를 메서드명에 표출하면 안되고, 무엇을 할거다. 라고 표현해야함. 너무 디테일하게 표현하면 안됨.

    public void calculatePercent(long totalBoardCount) {
        if ((100.0 * this.count / totalBoardCount) >= 100) {
            this.percent = 100;
        } else if ((100.0 * this.count / totalBoardCount) <= 0) {
            this.percent = 0;
        } else {
            this.percent = (int) (100.0 * this.count / totalBoardCount);
        }
    }
}
