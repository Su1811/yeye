package Management;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {
    public Dictionary myList = new Dictionary();
    public boolean change = false;
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.print("The number of words you want to insert: ");
        System.out.println();

        int num = 0;
        boolean check = false;
        String a =sc.next();
        while(true) {
            for(int i=0;i<a.length();i++) {
                if(a.charAt(i) < '0' || a.charAt(i) > '9') {
                    System.out.print("Invalid number, please enter a positive integer number");
                    break;
                }
                if(i == a.length()-1) check = true;
            }
            if(check==true) break;
            a = sc.next();
        }
        for(int i=0;i<a.length();i++) {
            num*=10;
            num+= a.charAt(i)-'0';
        }
        String tmp = sc.nextLine();
        int i = 0;
        while (i != num) {
            insertWord();
            i++;
        }
    }

    public void insertFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("dictionaries.txt"));
        while (sc.hasNext()) {
            String readWord = sc.nextLine();
            Scanner token = new Scanner(readWord).useDelimiter("\t");
            Word newWord = new Word();
            newWord.setWord_target(token.next());
            newWord.setWord_explain(token.next());
            myList.word_list.add(newWord);
            token.close();
        }
    }

    public List<String> getStringList() throws FileNotFoundException {
        insertFromFile();
        List<String> result = new ArrayList<>();
        for (Word word : myList.word_list) {
            result.add(word.getWord_target());
        }
        return result;
    }

    public void dictionaryLookup() {
        System.out.println("The word you want to look up: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine().toLowerCase();
        boolean flag = false;
        for (Word check : myList.word_list) {
            if (check.getWord_target().equals(word)) {
                System.out.println(word + ": " + check.getWord_explain());
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("This word doesn't exist!");
            System.out.println("Please give me the meaning of the word: ");
            Word newWord = new Word();
            newWord.setWord_target(word);
            newWord.setWord_explain(sc.nextLine().toLowerCase());
            myList.word_list.add(newWord);
            System.out.println("Successful!");
        }
    }

    public void insertWord() {
        System.out.println("The word you want to insert: ");
        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine().toLowerCase();
        boolean flag = false;
        for (Word check : myList.word_list) {
            if (check.getWord_target().equals(word)) {
                System.out.println("This word already exists!");
                flag = true;
                break;
            }
        }
        if (!flag) {
            change = true;
            Word newWord = new Word();
            System.out.println("The meaning of the word: ");
            newWord.setWord_target(word);
            newWord.setWord_explain(sc.nextLine().toLowerCase());
            myList.word_list.add(newWord);
            System.out.println("Successful!");
        }
    }

    public void removeWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("The word you want to remove: ");
        String word = sc.nextLine().toLowerCase();
        boolean flag = false;
        for (Word check : myList.word_list) {
            if (check.getWord_target().equals(word)) {
                myList.word_list.remove(check);
                flag = true;
                break;
            }
        }
        if (flag) {
            change = true;
            System.out.println("Successful!");
        } else {
            System.out.println("This word doesn't exist!");
        }
    }

    public void editWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("The word you want to edit: ");
        String word = sc.nextLine().toLowerCase();
        boolean flag = false;
        for (Word check : myList.word_list) {
            if (check.getWord_target().equals(word)) {
                myList.word_list.remove(check);
                Word newWord = new Word();
                System.out.println("Edit to: ");
                newWord.setWord_target(sc.nextLine().toLowerCase());
                System.out.println("The meaning of the word: ");
                newWord.setWord_explain(sc.nextLine().toLowerCase());
                myList.word_list.add(newWord);
                System.out.println("Successful!");
                flag = true;
                change = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("This word doesn't exist!");
        }
    }

    public void dictionaryExportToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("dictionaries.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Word check : myList.word_list) {
            printWriter.println(check.getWord_target() + "\t" + check.getWord_explain());
        }
        printWriter.close();
    }
    public void dictionarySearch() {
        System.out.println("Enter some characters: ");
        String s;
        Scanner scanner = new Scanner(System.in);
        s = scanner.nextLine();
        System.out.println("All words starting with: " + s);
        myList.word_list.forEach((i) -> {
            int index = i.getWord_target().indexOf(s);
            if (index == 0) {
                System.out.println(i.getWord_target() + "\t|" + i.getWord_explain());
            }
        });
    }
    
}
