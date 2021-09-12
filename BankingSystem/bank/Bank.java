package bank;

import account.Account;
import account.SavingAccount;
import exceptions.funcException.DepositException;
import exceptions.funcException.TransferException;
import exceptions.funcException.UnknownException;
import exceptions.funcException.WithdrawException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");
    private HashMap<String, InterestCalculator> interestCalculators = new HashMap<>();

    public Bank(){
        interestCalculators.put("N", new BasicInterestCalculator());
        interestCalculators.put("S", new SavingInterestCalculator());
    }
    // 뱅킹 시스템의 기능들
    public void withdraw() throws WithdrawException {
        // 계좌번호 입력
        Account account = null;
        while(true){
            String accNo = askInput("\n출금하시려는 계좌번호를 입력하세요.", "");
            try {
                account = findAccount(accNo);
                if(account.getCategory().equals("S")) {
                    new SavingBank().withdraw((SavingAccount) account);
                }
                break;
            }catch (UnknownException | WithdrawException ee) {
                System.out.println(ee.getMessage());
                return;
            }catch (Exception e){
                System.out.println(e.getMessage());
                break;
            }
        }
        // 출금처리
        while(true){
            try{
                BigDecimal amount = askInput("\n출금할 금액을 입력하세요.", BigDecimal.ZERO);
                BigDecimal result;
                BigDecimal interest = interestCalculators.get(account.getCategory()).getInterest(account.getBalance());
                result = account.withdraw(amount);
                System.out.printf("\n출금액 %s원과 이자 %s원이 정상적으로 출금되었습니다.\n", df.format(result), df.format(interest));
                break;
            }catch(WithdrawException we) {
                throw we;
            }catch (Exception e){
                throw new WithdrawException(e.getMessage());
            }
        }
    }

    public void deposit() throws DepositException, UnknownException{
        // 입금 메서드 구현
        Account account;
        while(true){
            String accNo = askInput("\n입금하시려는 계좌번호를 입력하세요.", "");
            account = findAccount(accNo);
            if(account != null){
                break;
            }
        }

        // 입금처리
        BigDecimal amount = askInput("\n입금할 금액을 입력하세요.", BigDecimal.ZERO);
        BigDecimal result;
        try{
            result = account.deposit(amount);
            System.out.printf("\n입금액 %s원이 정상적으로 입금되었습니다.", df.format(result));
        }catch (DepositException de){
            throw de;
        }catch (Exception e){
            throw new DepositException(e.getMessage());
        }
    }

    public Account createAccount() throws InputMismatchException {
        // 계좌 생성하는 메서드 구현
        try {
            // 계좌번호 채번
            // 계좌번호는 "0002"+증가한 seq 포맷을 가진 번호입니다.
            String accNo = String.format(new DecimalFormat("0000").format(++seq));
            String owner = askInput("\n소유주명을 입력해주세요.", "");
            BigDecimal amount = askInput("\n최초 입금액을 입력해주세요.", BigDecimal.ZERO);
            Account account = new Account(accNo, owner, amount);
            CentralBank.getInstance().getAccountList().add(account);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        }catch (InputMismatchException ime){
            if(seq > 0) seq--;
            throw ime;
        }catch (Exception e){
            throw e;
        }
    }

    public Account findAccount(String accNo) throws UnknownException {
        // 계좌리스트에서 찾아서 반환하는 메서드 구현
        Account account = null;
        for (Account value : CentralBank.getInstance().getAccountList()) {
            if (value.getAccNo().equals(accNo) && value.isActive()) {
                account = value;
            }
        }
        if(account == null) throw new UnknownException("해당 계좌가 존재하지 않습니다.");
        return account;
    }

    public void transfer() throws InputMismatchException, TransferException, UnknownException{
        // 송금 메서드 구현
        Account accountFrom;
        Account accountTo;
        boolean isFromSavingAccount = false;
        while(true){
            try {
                accountFrom = findAccount(askInput("\n송금하시려는 계좌번호를 입력해주세요.", ""));
                if(accountFrom == null) {

                } else if (accountFrom.getCategory().equals("S")) {
                    // 적금 계좌에서의 송금을 미지원할 경우 아래 주석 해제 필요
//                    System.out.println("\n적금 계좌에서의 송금은 출금을 이용해주세요.");
//                    continue;
                    isFromSavingAccount = true;
                }
                accountTo = findAccount(askInput("\n어느 계좌번호로 보내시려나요?", ""));
                if (accountTo.equals(accountFrom)) {
                    System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
                    continue;
                } else if (accountTo.getCategory().equals("S")) {
                    System.out.println("\n적금 계좌로는 송금이 불가합니다.");
                    continue;
                } else {
                    break;
                }
            }catch (InputMismatchException ime){
                throw ime;
            }catch (Exception e){
                throw e;
            }
        }
        try {
            BigDecimal amount = askInput("\n송금할 금액을 입력하세요.", BigDecimal.ZERO);
            // 적금계좌로부터의 송금이면 체크 필요
            if(isFromSavingAccount) new SavingBank().withdraw((SavingAccount) accountFrom);
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
        }catch (WithdrawException | DepositException wde){
            throw new TransferException(wde.getMessage());
        }catch (Exception e){
            throw new TransferException(e.getMessage());
        }
    }

    public <T extends Object> T askInput(String msg, T obj){
        Object input;
        while(true) {
            try {
                System.out.println(msg);
                if(obj instanceof BigDecimal){
                    input = scanner.nextBigDecimal();
                }else{
                    input = scanner.next();
                }
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println(obj.getClass().toString() +" 형식으로 입력해주세요.");
                continue;
            }
            if (input instanceof BigDecimal && (((BigDecimal) input).scale() > 0 || ((BigDecimal) input).signum() < 0)) {
                // BigDecimal 인 경우 양의 정수인지 체크
                System.out.println("양의 정수만 입력해주세요.");
                continue;
            }
            return (T)input;
        }
    }
}
