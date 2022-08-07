package banking;

import java.sql.*;
import java.util.Scanner;
import java.util.Random;

import static banking.DataBase.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static int chooseAct;
    static int chooseAction;
    static String inputNumber;
    static boolean stop;
    static boolean stop2 = true;
    static int id;
    static String n1;
    static String num1;
    static String num2;
    static String num3;
    static String num4;
    static String n2;
    static String n3;
    static String n4;
    static String n5;
    static String n6;
    static String n7;
    static String n8;
    static String n9;
    private String number;
    private String pin;
    private int balance;
    static String cardNumber = "";
    static String bin = "400000";
    static String accountId;
    static String pinCode = "";
    static String cardNumberInput;
    static String pinCodeInput;
    private static String url;
    static Random random = new Random();
    static int a1;
    static int a2;
    static int a3;
    static int a4;
    static int a5;
    static int a6;
    static int a7;
    static int a8;
    public static boolean connectStop;
    static int a9;
    static int sum;
    static int add;
    static int transferBalance;
    static String cNumber;
    static boolean stops;
    static int checkSum;


    public Main() {
        this.number = cardCreate();
        this.pin = createPin();
        this.balance = 0;
    }
    public Main(String number, String pin, int balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }
    public static void main(String[] args) {
        DataBase.createNewDataBase(args[1]);
        Main main = new Main();
        main.constructor();
    }

    public static void message() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public void constructor() {
        this.stop = true;
        while(stop) {
            message();
            this.chooseAct = scanner.nextInt();
            switch (chooseAct) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    break;
            }
        }
    }
    public String cardCreate() {
        n1 = String.valueOf(random.nextInt(10));
        n2 = String.valueOf(random.nextInt(10));
        n3 = String.valueOf(random.nextInt(10));
        n4 = String.valueOf(random.nextInt(10));
        n5 = String.valueOf(random.nextInt(10));
        n6 = String.valueOf(random.nextInt(10));
        n7 = String.valueOf(random.nextInt(10));
        n8 = String.valueOf(random.nextInt(10));
        n9 = String.valueOf(random.nextInt(10));
        accountId = n1 + n2 + n3 + n4 + n5 + n6 +n7 + n8 + n9;
        checkSum();
        cardNumber = bin + accountId + checkSum;
        return bin + accountId + checkSum;
    }
    public String createPin() {
        this.num1 = String.valueOf(random.nextInt(10));
        this.num2 = String.valueOf(random.nextInt(10));
        this.num3 = String.valueOf(random.nextInt(10));
        this.num4 = String.valueOf(random.nextInt(10));
        this.pinCode = num1 + num2 + num3 + num4;
        return num1 + num2 + num3 + num4;
    }
    public static void createNewAccount() {
        Main account = new Main();
        System.out.println("\nYour card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
        DataBase.insert(account);
    }
    public static Main login() {
        System.out.println("\nEnter your card number:");
        inputNumber = scanner.next();

        System.out.println("Enter your PIN:");
        String inputPin = scanner.next();

        Main account = DataBase.getAccount(inputNumber, inputPin);
        if (account != null) {
            System.out.println("\nYou have successfully logged in!");
            Main main = new Main();
            main.constructorV2();
            return account;
        }
        System.out.println("\nWrong card number or PIN!\n");
        return null;
    }
    public static void cabinet() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }
    public void balance() {
        System.out.println("Balance: " + returnBalance());
    }
    public void logout() {
        System.out.println("You have successfully logged out!");
        stop2 = false;
    }
    public void exit() {
        System.out.println("\nBye!");
        stop2 = false;
        stop = false;
        connectStop = false;
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        scanner.close();

    }
    public String getNumber() {
        return number;
    }
    public String getPin() {
        return pin;
    }
    public int getBalance() {
        return balance;
    }
    public void checkSum() {
        stops = true;
        checkSum = 0;
         a1 = Integer.parseInt(n1);
         a2 = Integer.parseInt(n2);
         a3 = Integer.parseInt(n3);
         a4 = Integer.parseInt(n4);
         a5 = Integer.parseInt(n5);
         a6 = Integer.parseInt(n6);
         a7 = Integer.parseInt(n7);
         a8 = Integer.parseInt(n8);
         a9 = Integer.parseInt(n9);
        sum = needA(a1) + a2 + needA(a3) + a4 + needA(a5) + a6 + needA(a7) + a8 + needA(a9) + 8;
         while (stops) {
             if ((sum + checkSum) % 10 == 0) {
                 stops = false;
             } else {
                 checkSum++;
             }
         }
    }
    public int needA (int a) {
        if (a * 2 > 9) {
            a *= 2;
            a -= 9;
        } else if (a * 2 < 9) {
            a *= 2;
        }
        return a;
    }
    public long needL (long a) {
        if (a * 2 > 9) {
            a *= 2;
            a -= 9;
        } else if (a * 2 < 9) {
            a *= 2;
        }
        return a;
    }
    public static void addIncome() {
        Main account = new Main();
        add = 0;
        add = scanner.nextInt();
        insertBalance(account, add);
        System.out.println("Income was added!");
    }

    public void transfer() {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        cNumber = scanner.next();
        String a = cNumber.replace("400000", "");
        long u = Long.parseLong(cNumber);
        long b = Long.parseLong(a);
        long lastNumber = b % 10;
        long c1 = (b % 100) / 10;
        long d = (b % 1000) / 100;
        long e = (b % 10000) / 1000;
        long l = (b % 100000) / 10000;
        long m = (b % 1000000) / 100000;
        long n = (b % 10000000) / 1000000;
        long f = (b % 100000000) / 10000000;
        long g = (b % 1000000000) / 100000000;
        long k = (b % 10000000000L) / 1000000000;
        long first = (u % 10000000000000000L) / 1000000000000000L;
        long cSum = needL(c1) + d + needL(e) + l + needL(m) + n + needL(f) + g + needL(k) + needL(first);
        if ((cSum + lastNumber) % 10 != 0) {
            System.out.println("Probably you made a mistake in the card number. Please try again");
        } else if (getCardNumber(cNumber)) {
            System.out.println("Enter how much money you want to transfer:");
            transferBalance = scanner.nextInt();
            if (transferBalance > returnBalance()) {
                System.out.println("Not enough money!");
            } else {
                balanceNow(transferBalance);
                balanceAnother(transferBalance);
                System.out.println("Success!");
            }
        } else {
            System.out.println("Such a card does not exist.");
        }


    }

    public void constructorV2() {
        while(stop2) {
            cabinet();
            int inputAction = scanner.nextInt();
            switch (inputAction) {
                case 1:
                    balance();
                    break;
                case 2:
                    addIncome();
                    break;
                case 3:
                    transfer();
                    break;
                case 4:
                    deleteAccount();
                    break;
                case 5:
                    logout();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    break;

            }
        }
    }
}