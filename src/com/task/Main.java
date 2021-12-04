package com.task;

import java.util.ArrayList;
import java.util.Collections;

abstract class bicycle {
    /* Сломан ли велосипед */
    boolean needsRepair = false;
    /* Износ велосипеда */
    double detailsFatigue = 0;

    /* Создаёт, то есть покупает новый велосипед */
    bicycle() {
        System.out.println("Congrats! You bought a new bicycle!");
    }

    /* Ремонтирует велосипеды
    *  Чем больше велосипед, тем дольше его ремонтировать*/
    abstract void repairBicycle();

    /* Протереть (Почистить) велосипед
    *  Уменьшет шанс скорой поломки велосипеда (detailsFatigue)
    *  Чем больше велосипед, тем больше времени уходит на данной занятие */
    abstract void maintainBicycle();

    /* Покататься на велосипеде
    *  Увеличивает износ велосипеда (detailsFatigue)
    *  С определённой вероятностью во время катаний велосипед может сломаться
    *  Катания на сломанном велосипеде не допускаются */
    abstract void goForRide();

}

class oneWheeledBicycle extends bicycle {

    @Override
    void repairBicycle() {
        System.out.println("Fixing it...");
        try {
            Utils.processOperation(3);
        } catch (InterruptedException ignored) { }
        needsRepair = false;
        detailsFatigue = 0;
        System.out.println("Can't wait to ride this again!");
    }

    @Override
    void maintainBicycle() {
        System.out.println("Cleaning it up...");
        try {
            Utils.processOperation(1);
        } catch (InterruptedException ignored) { }
        detailsFatigue = 0;
        System.out.println("Now it's as good as it was");
    }

    @Override
    void goForRide() {
        if (needsRepair) {
            System.out.println("Bad news, it's broken\nTime to visit a workshop or buy a new one");
            return;
        }
        System.out.println("What a good day to ride a bicycle!");
        if (detailsFatigue < 0.2)
            detailsFatigue += 0.02;
        if (Utils.getRandomChance(0.25 + detailsFatigue)) {
            System.out.println("Oh damn, did you hear that crackle?");
            needsRepair = true;
        }
    }

}

class twoWheeledBicycle extends bicycle {

    @Override
    void repairBicycle() {
        System.out.println("Fixing it...");
        try {
            Utils.processOperation(5);
        } catch (InterruptedException ignored) { }
        needsRepair = false;
        detailsFatigue = 0;
        System.out.println("We fixed it!");
    }

    @Override
    void maintainBicycle() {
        System.out.println("Cleaning it up...");
        try {
            Utils.processOperation(2);
        } catch (InterruptedException ignored) { }
        detailsFatigue = 0;
        System.out.println("Shines like a new!");
    }

    @Override
    void goForRide() {
        if (needsRepair) {
            System.out.println("It's just a crap of metal\nWorkshop is on the left");
            return;
        }
        System.out.println("Really good weather to ride a bicycle!");
        if (detailsFatigue < 0.30)
            detailsFatigue += 0.007;
        if (Utils.getRandomChance(0.20 + detailsFatigue)) {
            System.out.println("Oh no, it seems you have to wait with the riding...");
            needsRepair = true;
        }
    }

}

class threeWheeledBicycle extends bicycle {

    @Override
    void repairBicycle() {
        System.out.println("Fixing it...");
        try {
            Utils.processOperation(10);
        } catch (InterruptedException ignored) { }
        needsRepair = false;
        detailsFatigue = 0;
        System.out.println("Strong as caterpillar again!");
    }

    @Override
    void maintainBicycle() {
        System.out.println("Cleaning it up...");
        try {
            Utils.processOperation(4);
        } catch (InterruptedException ignored) { }
        detailsFatigue = 0;
        System.out.println("Maybe wipe it down more often?");
    }

    @Override
    void goForRide() {
        if (needsRepair) {
            System.out.println("Not sure if you want to injure yourself\nWorkshop is your way");
            return;
        }
        System.out.println("Another good day to ride a bicycle!");
        if (detailsFatigue < 0.35)
            detailsFatigue += 0.004;
        if (Utils.getRandomChance(0.15 + detailsFatigue)) {
            System.out.println("Damn dude, it doesn't go anymore!");
            needsRepair = true;
        }
    }

}

class Utils {

    static boolean getRandomChance(double chance) {
        return Math.random() <= chance;
    }

    static void processOperation(int duration) throws InterruptedException {
        Thread.sleep(duration * 1000);
    }
    /* Сортирует велосипеды в порядке уменьшения */
    static void sortByDiameter(ArrayList<bicycle> lst) {
        boolean go = true;
        while (go) {
            go = false;
            for (int i = 1; i < lst.size(); i++) {
                if ((lst.get(i) instanceof threeWheeledBicycle && (lst.get(i-1) instanceof twoWheeledBicycle || lst.get(i-1) instanceof oneWheeledBicycle))
                || (lst.get(i) instanceof twoWheeledBicycle && lst.get(i-1) instanceof oneWheeledBicycle)) {
                    Collections.swap(lst, i, i - 1);
                    go = true;
                }
            }
        }
    }

}

class WorkShop {
    /* Мастерская, ремонтирует любой велосипед из возможных */
    static void repair(bicycle Bicycle) {
        System.out.println("Happy to see you in workshop!");
        Bicycle.repairBicycle();
    }
}

public class Main {

    public static void main(String[] args) {
        oneWheeledBicycle bike = new oneWheeledBicycle();
        for (int i = 0; i < 10; i++) {
            bike.goForRide();
        }
        WorkShop.repair(bike);

        ArrayList<bicycle> lstBicycle = new ArrayList<>();
        lstBicycle.add(bike);
        lstBicycle.add(new oneWheeledBicycle());
        lstBicycle.add(new twoWheeledBicycle());
        lstBicycle.add(new threeWheeledBicycle());
        lstBicycle.add(new oneWheeledBicycle());
        lstBicycle.add(new threeWheeledBicycle());

        /* Починить только одноколёсные велосипеды */
        for (bicycle Bicycle : lstBicycle) {
            if (Bicycle instanceof oneWheeledBicycle) Bicycle.repairBicycle();
        }

        /* До сортировки */
        for (bicycle Bicycle : lstBicycle) {
            if (Bicycle instanceof oneWheeledBicycle) System.out.print("2");
            if (Bicycle instanceof twoWheeledBicycle) System.out.print("3");
            if (Bicycle instanceof threeWheeledBicycle) System.out.print("4");
        }

        System.out.println();

        Utils.sortByDiameter(lstBicycle);

        /* До после */
        for (bicycle Bicycle : lstBicycle) {
            if (Bicycle instanceof oneWheeledBicycle) System.out.print("1");
            if (Bicycle instanceof twoWheeledBicycle) System.out.print("2");
            if (Bicycle instanceof threeWheeledBicycle) System.out.print("3");
        }

    }
}
