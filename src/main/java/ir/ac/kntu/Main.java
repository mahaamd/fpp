package ir.ac.kntu;

import java.util.HashMap;

import java.util.Scanner;


public class Main {
    public static void readInput(Scanner s,String input,HashMap<String,Integer> variables) {
        //String[] args = {};
        input = input.trim();
        input = input.substring(1);
        variables.put(input.trim(),s.nextInt());
    }

    public static void isValid(String sub) {
        int sum = 0,from = 0,to;
        String[] args ={};
        sub = sub+"+";
        for (int i = 0; i < sub.length(); i++) {
            if(sub.charAt(0) == '-'|| sub.charAt(0) == '+') {
                continue;
            }
            if(sub.charAt(i) == '-' || sub.charAt(i) == '+') {
                to = i;
                if (sub.substring(from, to).matches("(-|[+])?[a-zA-Z]+(-|[+])?")) {
                    sum++;
                } else if (sub.substring(from, to).matches("(-|\\+)?\\d+(-|\\+)?")) {
                    sum++;
                }
                from = to;

                if (sum == 0) {
                    System.out.println("Fpp: Wrong input");
                    main(args);
                }
                sum = 0;
            }
        }
    }

    public static void printOutput(HashMap<String,Integer> variables,String input) {
        input = input.trim();
        if(variables.get(input.substring(1).trim())!=null && variables.size()>=1) {
            System.out.println(variables.get(input.substring(1).trim()));
        }else {
            System.out.println(input.substring(1).trim()+" is not defined");
        }
    }

    public static HashMap<String,Integer> substitute(HashMap<String,Integer>variables,String input) {
        //String[] args = {};
        String[] subInput = input.split("=");
        for (int i = 0; i < subInput.length; i++) {
            subInput[i] = subInput[i].trim();
        }
        if(input.matches("\\s*[A-Za-z]+\\s*=\\s*[-]?\\d+\\s*")) {
            variables.put(subInput[0],Integer.parseInt(subInput[1]));
            return variables;
        }
        if(input.matches("\\s*[A-Za-z]+\\s*=\\s*[-]?[a-zA-Z]+\\s*")) {
            if(input.contains("-") && variables.get(subInput[1].substring(1)) != null) {
                variables.put(subInput[0],- variables.get(subInput[1].substring(1)));
            }else if(!input.contains("-") && variables.get(subInput[1])!=null){
                variables.put(subInput[0], variables.get(subInput[1]));
            }else {
                System.out.println(subInput[1] + " is not defined");
            }
            return variables;
        }
        //isValid(subInput[1]);
        calculatingCombination(variables,input);
        return variables;
    }

    public static HashMap<String,Integer> calculatingCombination(HashMap<String,Integer>variables,String input) {
        String[] args = {};
        int sum;
        String[]subInput = input.split("\\s+");
        String inputWithoutSpace = "";
        for (String string : subInput) {
            inputWithoutSpace += string;
        }
        String[] equalSeprated = inputWithoutSpace.split("=");
        String help = equalSeprated[1];
        isValid(help);
        String[] check = help.split("[+|-]");
        for (String string : check) {
            if (string.matches("[a-zA-Z]+") && variables.get(string) == null) {
                System.out.println(string + " is not defined");
                main(args);
            }
        }
        sum = calculateSubParts(variables, help);
        variables.put(equalSeprated[0],sum);
        return variables;
    }

    public static int calculateSubParts(HashMap<String,Integer> variables,String sub) {
        int sum = 0,from = 0,to;
        sub = sub+"+";
        for (int i = 0; i < sub.length(); i++) {
            if(sub.charAt(i) == '-' || sub.charAt(i) == '+') {
                to=i;
                if(sub.substring(from, to).matches("-[a-zA-Z]+")) {
                    sum += -variables.get(sub.substring(from + 1, to));
                }else if(sub.substring(from, to).matches("\\+?[a-zA-Z]+")) {
                    if(sub.substring(from, to).contains("+")) {
                        sum +=variables.get(sub.substring(from + 1, to));
                    }else {
                        sum +=variables.get(sub.substring(from, to));
                    }
                }else if(sub.substring(from,to).matches("(-|\\+)?\\d+")) {
                    if(sub.substring(from, to).contains("+")) {
                        sum +=Integer.parseInt(sub.substring(from + 1, to));
                    }else {
                        sum+=Integer.parseInt(sub.substring(from,to));
                    }
                }
                from = to;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        String input;
        HashMap <String,Integer> variables = new HashMap<>();
        while (true) {
            input = scanner.nextLine();
            if(input.matches("\\s*[>]\\s*[a-zA-z]+\\s*")) {    //checking input validation
                readInput(scanner2,input,variables);

            }else if(!input.matches("exit_0") && !input.matches("\\s*[<]\\s*[a-zA-Z]+\\s*")
                &&!input.matches("\\s*[a-zA-Z]+\\s*=\\s*.[^@#$%^&*!]+")
                &&!input.matches("\\s*[>]\\s*[a-zA-Z]+\\s*")) {
                System.out.println("Fpp: Wrong input");
            }
            if(input.matches("\\s*[<]\\s*[a-zA-z]+\\s*")) {

                printOutput(variables, input);
            }
            if(input.matches("\\s*[a-zA-Z]+\\s*=\\s*.[^@#$%^&*!]+")) {
                variables = substitute(variables,input);
            }
            if(input.equals("exit_0")) {
                System.exit(0);
            }
        }
    }
}