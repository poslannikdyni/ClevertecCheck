package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.CustomException;
import main.java.ru.clevertec.check.to.OperationRequestTO;

import java.util.ArrayList;

import static main.java.ru.clevertec.check.util.Utility.saveFile;

public class CheckRunner {
    public static void main(String[] args) {
//        args = setDebugProgramArgument();

        OperationRequestTO request = null;
        try {
            OperationRequestBuilder requestBuilder = new OperationRequestBuilder();
            request = requestBuilder.fromArray(args);

            CheckRunnerFasade fasade = new CheckRunnerFasade();
            fasade.processingRequest(request);
        } catch (CustomException e) {
            try {
                var message = "ERROR" + System.lineSeparator() + e.getMessage();
                if (request.getSaveByPath() != null && request.getSaveByPath().isEmpty() == false)
                    saveFile(request.getSaveByPath(), message);

                System.out.println(message);
                System.out.println(e.getAdditionalInfo());
            } catch (Exception NOP) {
                System.out.println("INTERNAL SERVER ERROR");
            }
        }
    }

    private static String[] setDebugProgramArgument() {
        var argsList = new ArrayList<String>();
        argsList.add("3-1");
        argsList.add("2-5");
        argsList.add("5-1");
        argsList.add("5-2");
        argsList.add("discountCard=1111");
        argsList.add("balanceDebitCard=100");
        argsList.add("pathToFile=./src/main/resources/products.csv");
        argsList.add("saveToFile=result.csv");
        return argsList.toArray(new String[0]);
    }
}