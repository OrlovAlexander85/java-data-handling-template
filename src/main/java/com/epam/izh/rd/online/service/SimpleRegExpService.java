package com.epam.izh.rd.online.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String text = "";
        Path path = Paths.get("src/main/resources/sensitive_data.txt");
        try {
            text = Files.readAllLines(path).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mask = "$1 **** **** $2";
        Pattern p = Pattern.compile("([0-9]{4})\\s[0-9]{0,9}\\s[0-9]{0,9}\\s[0-9]{0,9}([0-9]{4})");
        Matcher matcher = p.matcher(text);
        if (matcher.find()){
            return matcher.replaceAll(mask);
        }


        return text;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String text = "";
        Path path = Paths.get("src/main/resources/sensitive_data.txt");
        try {
            text = Files.readAllLines(path).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern paymentPattern = Pattern.compile("(\\$\\{payment_amount})");
        Pattern balancePattern = Pattern.compile("(\\$\\{balance})");
        text = text.replaceAll(paymentPattern.pattern(),String.valueOf((int)paymentAmount))
                .replaceAll(balancePattern.pattern(), String.valueOf((int)balance));
        return text;
    }

}
