package org.yates;

// Disclaimer: This code was in part from a project I did a few years ago for AP CS A, found online at https://replit.com/@JordanYates1

import java.util.ArrayList;
import java.util.Scanner;

class CLI {
    private static final char esc = (char) 27;
    private static final String escb = esc + "[";
    private static final String clear = escb + "2J";
    private static final String reset = escb + "0m";
    private static final String bold = escb + "1m";

    private static final String red = reset + escb + "31m";
    private static final String orange = reset + escb + "37m";
    private static final String yellow = reset + escb + "33m";

    private static final String green = reset + escb + "32m";

    private static final String blue = reset + escb + "34m";
    private static final String indigo = reset + escb + "36m";
    private static final String violet = reset + escb + "35m";

    private static final String choiceMsg = blue + bold + "\nChoose an option from the list below:\n";
    private static final String menu = choiceMsg + blue + "\t1) Add\n" + red + "\t2) Set\n\t3) Find\n" + blue + "\t4) Remove\n\t5) Clear\n\t6) Quit\n";
    private static final String addSubmenu = choiceMsg + blue + "\t1) Add to end\n\t2) Add at index\n\t3) Added to front\n"+ red +"\t4) Add all\n"+blue+"\t5) Back";
    private static final String removeSubmenu = choiceMsg + blue + "\t1) Remove from end\n\t2) Remove at index\n\t3) remove from front\n\t4) Remove all\n\t5) Back";


    private static final Scanner sca = new Scanner(System.in);

    private final String inputMsg = reset + bold + "Please, put your input below\n" + reset + "λ:";

    private static final String index = "\n -- INDEX -- \n";

    private final String[] st = {"This", "is", "your", "list"};
    private final LinkedList<String> strList = new LinkedList<>(st);

    public void header() {
        System.out.print(clear);
        System.out.println(red + " █ - "
                + orange + "█ - "
                + yellow + "█ - "
                + green + bold + "Linked-List CLI " + reset
                + blue + "- █ - "
                + indigo + "█ - "
                + violet + "█\n"
        );
        System.out.println(strList);
    }

    private String getString() {
        System.out.print(inputMsg);
        return sca.nextLine();
    }

    private int getInt() {
        System.out.print(inputMsg);
        int result;
        try {
            result = Integer.parseInt(sca.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(red + bold + "Whoops, ensure your input is correct." + reset);
            result = getInt();
        }
        return result;
    }

    private void append() {
        header();
        strList.add(getString());
    }

    private void addIndex() {
        header();
        System.out.println(index);
        int index = getInt();
        header();
        System.out.println();
        strList.add(index, getString());
    }

    private void addFront() {
        header();
        strList.add(0, getString());
    }

    private void removeFront() {
        header();
        strList.remove(0);
    }

    private void removeLast() {
        header();
        strList.remove(strList.size() - 1);
    }

    private void removeIndex() {
        header();
        System.out.println(index);
        int index = getInt();
        header();
        System.out.println();
        strList.remove(index);
    }

    private void addList() {
        ArrayList<String> arr = new ArrayList<>();
        while (true) {
            switch (getInt()) {
                case 1: break;
                case 2: strList.addAll(arr); return;
            }
        }
    }

    private void removeSubmenu() {
        while (true) {
            header();
            System.out.println(removeSubmenu);
            switch (getInt()) {
                case 1: removeLast(); break;
                case 2: removeIndex(); break;
                case 3: removeFront(); break;
                case 4: strList.clear(); break;
                case 5: return;
            }
        }
    }

    private void addSubmenu() {
        while (true) {
            header();
            System.out.println(addSubmenu);
            switch (getInt()) {
                case 1: append(); break;
                case 2: addIndex(); break;
                case 3: addFront(); break;
                case 4: addList(); break;
                case 5: return;
            }
        }
    }

    private void mainMenu() {
        while (true) {
            header();
            System.out.println(menu);
            switch (getInt()) {
                case 1: addSubmenu(); break;
                case 2, 3: break;
                case 4: removeSubmenu();
                case 5: strList.clear(); break;
                case 6: if (ynconfirm()) { return; } else { break; }
            }
        }
    }

    private boolean ynconfirm() {
        header();
        while (true) {
            System.out.println(blue + "Are you sure you would like to quit?\n\t1)" + red + " Yes\n\t" + blue + "2) No");
            switch (getInt()) {
                case 1: return true;
                case 2: return false;
                default: System.out.println("Choose either 1 (Yes) or 2 (No)"); break;
            }
        }

    }

    public void start() {
        mainMenu();
    }
}

public class Main {
    public static void main(String[] args) {
        // --- CLI
        CLI cli = new CLI();
        cli.start();
    }
}