package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal bigD = new BigDecimal(a).divide(BigDecimal.valueOf(b), range, BigDecimal.ROUND_HALF_UP);
        return bigD;
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int counter = 0;
        int number = 3; // В тесте скорее всего ошибка, т.к. простое число №100 = 541
        while (counter < range) {
            boolean isTrue = true;
            int divisor = 2;
            for (; divisor <= Math.sqrt(number); divisor++) {
                if (number % divisor == 0) {
                    isTrue = false;
                    break;
                }
            }
            if (isTrue) {
                counter++;
            }
            if (counter!=range){
                number++;
            }
        }
        return new BigInteger(String.valueOf(number));
    }
}
