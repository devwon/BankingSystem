package bank;

import java.math.BigDecimal;

public class SavingInterestCalculator implements InterestCalculator{
    @Override
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;

        // 적금 계좌의 경우 잔액이 100만원 이상은 이자율이 50%, 그 외에는 1% 입니다.
        if(balance.compareTo(BigDecimal.valueOf(1000000)) >= 0)
            interest = BigDecimal.valueOf(0.5);
        else
            interest = BigDecimal.valueOf(0.01);
        return balance.multiply(interest);
    }
}
