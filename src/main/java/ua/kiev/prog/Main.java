package ua.kiev.prog;

import ua.kiev.prog.Service.BankService;

public class Main {

    public static void main(String[] args) {

        BankService bs = new BankService();
        bs.openAccount("Vadym", "1111111");
        bs.depositAccount("Ivan", "UAH", 10000);
        bs.withdrawAccount("Petya", "UAH", 1200);
        bs.depositAccount("Oleg", "USD", 500);
        bs.depositAccount("Kolya", "EUR", 300);
        bs.withdrawAccount("Ivan", "EUR", 300);

        bs.openAccount("Kolya", "5555555");
        bs.depositAccount("Kolya", "UAH", 125);
        bs.withdrawAccount("Kolya", "UAH", 113);
        bs.depositAccount("Kolya", "USD", 500);
        bs.depositAccount("Kolya", "EUR", 900);
        bs.withdrawAccount("Kolya", "EUR", 899);


        bs.accountStatement("Petya");
        bs.accountStatement("Kolya");

        bs.accountBalance("Petya");
        bs.accountBalance("Kolya");


    }

}
