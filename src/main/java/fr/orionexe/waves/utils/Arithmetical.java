package fr.orionexe.waves.utils;

public class Arithmetical {
    int number;

    public Arithmetical(int nb){
        number = nb;
    }

    boolean isBetween(int firstNumber, int secondNumber){
        if (firstNumber > secondNumber){
            if (firstNumber >= number && number >= secondNumber){
                return true;
            }
        }
        else {
            if (firstNumber <= number && number <= secondNumber){
                return true;
            }
        }
        return false;
    }
}
