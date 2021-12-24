package util;

import java.util.Scanner;

public class ScannerUtil {
    
    private static void printMessage(String message) {
        System.out.println(message);
        System.out.print("> ");
    }
    
    public static int nextInt(Scanner scanner, String message) {
        printMessage(message);
        int temp = scanner.nextInt();
        
        return temp;
    }
    
    public static double nextDouble(Scanner scanner, String message) {
        printMessage(message);
        double temp = scanner.nextDouble();
        
        return temp;
    }
    
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        
        while(temp < min || temp > max) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextInt(scanner, message);
        }
        
        return temp;
        
    }
    
    public static double nextDouble(Scanner scanner, String message, double min, double max) {
        double temp = nextDouble(scanner, message);
        
        while(temp < min || temp > max) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextDouble(scanner, message);
        }
        
        return temp;
        
    }
    
    public static String nextLine(Scanner scanner, String message) {
        printMessage(message);
        String temp = scanner.nextLine();
        
        if(temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        
        return temp;
    }
    
    
    // email 입력받기
    public static String EnextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateEmail(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        
        return temp;
    }
    // tel 입력받기
    public static String telnextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateTel(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        return temp;
    }
    // 지점 tel 입력받기
    public static String btelnextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateBranchTel(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        return temp;
    }
    // 생년월일 입력받기
    public static String birthnextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateBirth(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        return temp;
    }
    
    // time 입력받기
    public static String timenextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateTime(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        return temp;
    }
    
    // yesNo
    public static String yesNonextLine(Scanner scanner, String message) {
        String temp = nextLine(scanner, message);
        
        while(!validateYesNo(temp)) {
            System.out.println("잘못 입력하셨습니다.");
            temp = nextLine(scanner, message);
        }
        return temp;
    }
    
    // email check
    public static boolean validateEmail(String s) {
        // 첫 번째 자리는 무조건 영대소문자 + 숫자 + @ + 영소문자 또는 숫자 + . + 영소문자 또는 숫자
        String regEx = new String("^[a-zA-Z]+\\w+@\\w+\\.\\w+$");
        
        if(s.matches(regEx)) {
            return true;
        }
        return false;
    }
    
    // 전화번호 check
    public static boolean validateTel(String s) {
        String regEx = new String("^01(0|1)-[1-9]\\d{3}-\\d{4}$");
        
        if(s.matches(regEx)) {
            return true;
        }
        return false;
    }
    // 지점 전화번호
    public static boolean validateBranchTel(String s) {
        String regEx = new String("^0[2-6][1-5]?-[1-9]\\d{2,3}-\\d{4}$");
        
        if(s.matches(regEx)) {
            return true;
        }
        return false;
    }
    
    // 생년월일 check
    public static boolean validateBirth(String s) {
        String regEx = new String("^(1|2)\\d{3}(0|1)\\d[0-3]\\d$");
        
        if(s.matches(regEx)) {
            return true;
        }
        return false;
    }
    
    // 시간대 check
    public static boolean validateTime(String s) {
        String regEx = new String("^[0-2]\\d[0-6]\\d");
        if(s.matches(regEx)) {
            return true;
        }
        return false;
    }
    
    // yesNo check
    public static boolean validateYesNo(String s) {
        if(s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("N")) {
            return true;
        }
        return false;
    }
    
    

}
