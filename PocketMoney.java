/////////////////////////////////////////////////////////////////////////////////////
// Class: PocketMoney
// Block Comment to explain your program and put your name in it
//
/////////////////////////////////////////////////////////////////////////////////////

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class PocketMoney implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> children;
    private HashMap<String, Float> chores;
    private HashMap<String, Float> goals;
    private HashMap<String, Float> money;

    // Constructor, getters, and setters
    public PocketMoney(ArrayList<String> children, HashMap<String, Float> chores, HashMap<String, Float> goals, HashMap<String, Float> money) {
        this.children = children;
        this.chores = chores;
        this.goals = goals;
        this.money = money;
    }

    public void saveData(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PocketMoney loadData(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (PocketMoney) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public HashMap<String, Float> getChores() {
        return chores;
    }

    public void setChores(HashMap<String, Float> chores) {
        this.chores = chores;
    }

    public HashMap<String, Float> getGoals() {
        return goals;
    }

    public void setGoals(HashMap<String, Float> goals) {
        this.goals = goals;
    }

    public HashMap<String, Float> getMoney() {
        return money;
    }

    public void setMoney(HashMap<String, Float> money) {
        this.money = money;
    }

    public static int showMenu(Scanner sc) {
        System.out.println("Pocket Money Management System");
        System.out.println("1. Add a child");
        System.out.println("2. Add a chore");
        System.out.println("3. Complete a chore");
        System.out.println("4. Check balance");
        System.out.println("5. Set a goal");
        System.out.println("6. Check goal");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        String inp = sc.nextLine();
        return Integer.parseInt(inp);
        
    }

    public void addChild(Scanner sc) {
        ArrayList<String> children = getChildren();
        System.out.print("Enter the name of the child: ");
        String name = sc.nextLine();
        // Add the child to the list of children
        children.add(name);
        setChildren(children);
        // Add the child to the money hashmap with 0 money
        HashMap<String, Float> money = getMoney();
        money.put(name, 0.0f);
        setMoney(money);
    }

    public void addChore(Scanner sc) {
        HashMap<String, Float> chores = getChores();
        System.out.print("Enter the name of the chore: ");
        String name = sc.nextLine();
        System.out.print("Enter the amount of money for the chore: ");
        String amount = sc.nextLine();
        // Add the chore to the hashmap of chores
        chores.put(name, Float.parseFloat(amount));
        setChores(chores);
    }

    public void completeChore(Scanner sc) {
        HashMap<String, Float> money = getMoney();
        HashMap<String, Float> chores = getChores();
        System.out.print("Enter the name of the child: ");
        String name = sc.nextLine();
        System.out.print("Enter the name of the chore: ");
        String chore = sc.nextLine();
        // Add the money to the child's money
        money.put(name, money.get(name) + chores.get(chore));
        setMoney(money);
    }

    public void checkBalance(Scanner sc) {
        HashMap<String, Float> money = getMoney();
        System.out.print("Enter the name of the child: ");
        String name = sc.nextLine();
        // Print the child's money
        System.out.println(name + " has $" + money.get(name));
    }

    public void setGoal(Scanner sc) {
        HashMap<String, Float> goals = getGoals();
        System.out.print("Enter the name of the child: ");
        String name = sc.nextLine();
        System.out.print("Enter the amount of the goal: ");
        String amount = sc.nextLine();
        // Add the goal to the hashmap of goals
        goals.put(name, Float.parseFloat(amount));
        setGoals(goals);
    }

    public void checkGoal(Scanner sc) {
        HashMap<String, Float> money = getMoney();
        HashMap<String, Float> goals = getGoals();
        System.out.print("Enter the name of the child: ");
        String name = sc.nextLine();
        // Print whether the child has reached their goal
        if (money.get(name) >= goals.get(name)) {
            System.out.println(name + " has reached their goal!");
        } else {
            System.out.println(name + " has not reached their goal.");
        }
        System.out.println("They have $" + money.get(name) + " out of their goal of $" + goals.get(name));
    }

    public static void main(String[] args) {
        // Load data
        PocketMoney pocketMoney = PocketMoney.loadData("pocketmoney.ser");
        if (pocketMoney == null) {
            // Initialize data
            ArrayList<String> children = new ArrayList<>();
            children.add("Alice");
            children.add("Bob");
            children.add("Charlie");
            pocketMoney = new PocketMoney(children,
                    new HashMap<>(Map.of("clean", 5.0f, "cook", 10.0f, "wash", 3.0f)),
                    new HashMap<>(Map.of("Alice", 100.0f, "Bob", 200.0f, "Charlie", 300.0f)),
                    new HashMap<>(Map.of("Alice", 0.0f, "Bob", 0.0f, "Charlie", 0.0f)));
        }
        // Create a scanner
        Scanner sc = new Scanner(System.in);
        //while running show menu and handle user input
        int choice = 0;
        while (choice != 7) {
            choice = showMenu(sc);
            if (choice == 1) {
                    // Add a child
                    pocketMoney.addChild(sc);
            } else if (choice == 2) {
                    // Add a chore
                    pocketMoney.addChore(sc);
            } else if (choice == 3) {
                    // Complete a chore
                    pocketMoney.completeChore(sc);
            } else if (choice == 4) {
                    // Check balance
                    pocketMoney.checkBalance(sc);
            } else if (choice == 5) {
                    // Set a goal
                    pocketMoney.setGoal(sc);
            } else if (choice == 6) {
                    // Check goal
                    pocketMoney.checkGoal(sc);
            } else if (choice != 7) { // 7 is exit
                System.out.println("Invalid choice");
            }

        }
        // Close the scanner
        sc.close();

        // Save data
        pocketMoney.saveData("pocketmoney.ser");
    }

}

