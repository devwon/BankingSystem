package account;

import bank.Bank;
import exceptions.funcException.DepositException;
import exceptions.funcException.TransferException;
import exceptions.funcException.WithdrawException;

import java.math.BigDecimal;

public class Account {
    // 일반 계좌 클래스의 속성은 계좌종류(N: 예금계좌, S:적금계좌), 계좌번호, 소유자, 잔액, 활성화 여부 5가지 입니다.
    protected String category;         // 계좌 종류
    protected String accNo;              // 계좌번호
    protected String owner;              // 소유자
    protected BigDecimal balance;        // 잔액
    protected boolean isActive;          // 활성화 여부

    public Account() {
        // 일반 계좌의 활성화 여부를 True로, 계좌 종류를 "N"(NORMAL을 의미) 설정해줍니다.
        isActive = true;
        category = "N";
    }
    public Account(String accNo, String owner, BigDecimal balance) {
        this();
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
    }

    // 일반 계좌 클래스의 각 속성에 getter/setter를 제공합니다.
    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive(){
        return isActive;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void getAccountInfo(){
        // 계좌의 기본 정보를 아래 형태로 출력해줍니다.
        // 계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원
        System.out.printf("계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원\n",
                category, accNo, owner, Bank.df.format(balance));
    }

    public BigDecimal withdraw(BigDecimal amount) throws WithdrawException {
        // 출금액을 받아서 출금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        if(this.balance.compareTo(amount) < 0){
            throw new WithdrawException("잔액이 모자랍니다.");
        }else{
            this.balance = this.balance.subtract(amount);
        }
        return amount;
    }

    public BigDecimal deposit(BigDecimal amount) throws DepositException {
        try {
            this.balance = this.balance.add(amount);
        }catch (Exception e){
            throw new DepositException(e.getMessage());
        }
            return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (accNo == null) {
            if (other.accNo != null)
                return false;
        } else if (!accNo.equals(other.accNo)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accNo == null) ? 0 : accNo.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        return result;
    }
}
