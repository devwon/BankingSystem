package bank;

import java.math.BigDecimal;

public class BasicInterestCalculator implements InterestCalculator{
    @Override
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;

        // 예금 계좌의 경우 잔액이 1000만원 이상은 이자율이 50%,
        //  500만원 이상은 7%, 100만원 이상은 4%, 1만원 이상은 2%, 그 외에는 1% 입니다.
        if(balance.compareTo(BigDecimal.valueOf(10000000))>=0)
            interest = BigDecimal.valueOf(0.5);
        else if(balance.compareTo(BigDecimal.valueOf(5000000))>=0)
            interest = BigDecimal.valueOf(0.07);
        else if(balance.compareTo(BigDecimal.valueOf(1000000))>=0)
            interest = BigDecimal.valueOf(0.04);
        else if(balance.compareTo(BigDecimal.valueOf(10000))>=0)
            interest = BigDecimal.valueOf(0.02);
        else
            interest = BigDecimal.valueOf(0.01);
        return balance.multiply(interest);
    }
}
