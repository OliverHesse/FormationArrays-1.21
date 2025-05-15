package net.Lucent.ArrayFormations.formation;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

//setup separately to potentially allow for bigger numbers
public class QiContainer {

    private final BigDecimal MAX_CAPACITY;
    private BigDecimal currentQi;

    private final BigDecimal PERCENTAGE_BIG_DECIMAL = new BigDecimal(100);


    public QiContainer(String maxQi){
        MAX_CAPACITY =  new BigDecimal(maxQi);
        currentQi = new BigDecimal(maxQi);
    }

    public void setQiAmount(String amount){
        currentQi = MAX_CAPACITY.min(new BigDecimal(amount));
    }

    public BigDecimal getQi(){
        return currentQi;
    }

    public boolean isEmpty(){
         return currentQi.compareTo(BigDecimal.ZERO) == 0;


    }
    //Strings have higher precision
    public boolean drainQi(String amount){
        BigDecimal amountBig = new BigDecimal(amount);
        if(amountBig.compareTo(currentQi) > 0) return false;
        currentQi =  BigDecimal.ZERO.max(currentQi.subtract(new BigDecimal(amount)));
        return true;
    }

    private BigDecimal qiDifference(){
        return MAX_CAPACITY.subtract(currentQi);
    }

    //only refills qi if it can do it without overflowing
    public boolean regenQiNoOverflow(String amount){
        BigDecimal amountBig = new BigDecimal(amount);
        if(amountBig.compareTo(qiDifference()) > 0) return false;
        regenQi(amount);
        return true;

    }


    public void regenQi(String amount){
        BigDecimal amountBig = new BigDecimal(amount);
        if(amountBig.compareTo(BigDecimal.ZERO) == 0) return;
        currentQi = MAX_CAPACITY.min(currentQi.add(amountBig));
    }


    //could be made more efficient
    public int currentQiPercentage(){
        BigDecimal percentage =  currentQi.divide(MAX_CAPACITY,2,RoundingMode.FLOOR).multiply(PERCENTAGE_BIG_DECIMAL);
        System.out.println("percentage: "+percentage);
        return percentage.intValue();
    }



}
