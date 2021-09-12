package account;

import bank.Bank;

import java.math.BigDecimal;

// SavingAccount는 Account에서 상속을 받습니다.
public class SavingAccount extends Account{
    // 적금 계좌 클래스의 속성은 목표금액 속성을 추가로 가집니다.
    private BigDecimal goalAmount = new BigDecimal(100000);

    public SavingAccount(){
        super();
        this.category = "S";
    }
    // 적금 계좌의 카테고리를 "S"로 만들어 줍니다.
    public SavingAccount(String accNo, String owner, BigDecimal balance, BigDecimal goalAmount) {
        super(accNo, owner, balance);
        this.goalAmount = goalAmount;
        this.category = "S";
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    @Override
    public void getAccountInfo() {
        System.out.printf("계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원 | 목표 금액: %s원\n",
                category, accNo, owner, Bank.df.format(balance), Bank.df.format(goalAmount));
    }
}
