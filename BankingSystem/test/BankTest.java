package test;

import account.Account;
import bank.Bank;
import bank.CentralBank;
import bank.SavingBank;
import exceptions.funcException.WithdrawException;
import exceptions.ioExceptions.MenuInputException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankTest {
    private static Scanner scanner = new Scanner(System.in);
    private static Bank bank = new Bank();
    public static void main(String[] args) {

        CentralBank centralBank = CentralBank.getInstance();
        // 예금 계좌와 적금 계좌 생성
        Bank bank = new Bank();
        SavingBank bank2 = new SavingBank();
        ArrayList<Account> accountList = new ArrayList<>();
        boolean isSetUp = true;
        while(true){
            try {
                System.out.println("---일반 예금 계좌를 생성합니다.---");
                accountList.add(bank.createAccount());
                isSetUp = true;
                break;
            } catch (InputMismatchException e) {
                isSetUp = false;
                continue;
            }
        }
        while(true){
            try {
                System.out.println("---적금 계좌를 생성합니다.---");
                accountList.add(bank2.createAccount());
                isSetUp = true;
                break;
            } catch (InputMismatchException e) {
                isSetUp = false;
                continue;
            }
        }
        centralBank.setAccountList(accountList);
        while (isSetUp) {
            try {
                System.out.println("\n1. 계좌 목록 | 2. 출금 | 3. 입금 | 4. 송금 | 5. 종료");
                int menuNo = scanner.nextInt();
                switch (menuNo) {
                    case 1:
                        int sizeOfBank = centralBank.getAccountList().size();
                        for (int i = 0; i < sizeOfBank; i++) {
                            centralBank.getAccountList().get(i).getAccountInfo();
                        }
                        break;
                    case 2:
                        bank2.withdraw();
                        break;
                    case 3:
                        bank2.deposit();
                        break;
                    case 4:
                        bank2.transfer();
                        break;
                    case 5:
                        isSetUp = false;
                        break;
                }
            }catch (InputMismatchException ie){
                System.out.println(new MenuInputException("1~5까지의 숫자만 입력해주세요.").getMessage());
                scanner.next();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("뱅킹 프로그램을 종료합니다.");
    }
}
