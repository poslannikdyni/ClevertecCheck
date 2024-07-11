package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.CustomException;

import java.util.ArrayList;

import static main.java.ru.clevertec.check.util.Utility.saveFile;

public class CheckRunner {
    public static String RESULT_CSV = "result.csv";

    public static void main(String[] args) {
//        args = setDebugProgramArgument();

       try {
           OperationRequestBuilder requestBuilder = new OperationRequestBuilder();
           var request = requestBuilder.fromArray(args);
           request.setSaveByPath(RESULT_CSV);

           CheckRunnerFasade fasade = new CheckRunnerFasade();
           fasade.processingRequest(request);
       }catch (CustomException e){
           try{
               var message = "ERROR" + System.lineSeparator()+e.getMessage();
               saveFile(RESULT_CSV, message);
               System.out.println(message);
               System.out.println(e.getAdditionalInfo());
           }catch (Exception NOP){
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
        return argsList.toArray(new String[0]);
    }
}