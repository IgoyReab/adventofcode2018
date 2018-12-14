package nl.yogibear;

import lombok.Data;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
class RecipeTries {

    ArrayList<Integer> recipeList = new ArrayList<Integer>();
//        ArrayList<Integer> elveList = new ArrayList<Integer>();

    int currentRecipeElve1;
    int currentRecipeElve2;
    int currentIndexElve1;
    int currentIndexElve2;
    int foundIndex;

    public RecipeTries() {
        recipeList.add(3);
//            elveList.add(3);
        currentRecipeElve1 = 3;
        currentRecipeElve2 = 7;
        currentIndexElve1 = 0;
        currentIndexElve2 = 1;
        recipeList.add(7);
        foundIndex = 0;
//            elveList.add(7);

    }

    public void printRecipes() {
        System.out.println(recipeList);
    }

    public void addRecipes() {

        Integer sum = currentRecipeElve1 + currentRecipeElve2;

        String sSum = sum.toString();

        for (int x = 0; x < sSum.length(); x++) {
            recipeList.add(Integer.parseInt(String.valueOf(sSum.charAt(x))));
        }

        currentIndexElve1 += 1 + currentRecipeElve1;
        while ((currentIndexElve1 >= recipeList.size())) {
            currentIndexElve1 = (currentIndexElve1 - recipeList.size());
        }

        currentIndexElve2 += 1 + currentRecipeElve2;
        while ((currentIndexElve2 >= recipeList.size())) {
              currentIndexElve2 = (currentIndexElve2 - recipeList.size());
        }

        currentRecipeElve1 = recipeList.get(currentIndexElve1);
        currentRecipeElve2 = recipeList.get(currentIndexElve2);


//        printRecipes();
    }

    public String Calculate_result(int input) {
        String result = "";

        do {
            addRecipes();
        } while (recipeList.size() < input);

        int startIndex = recipeList.size() - (recipeList.size() - input  );

        do {
            addRecipes();
        } while (recipeList.size() < startIndex + 10);

        for (int x = startIndex; x < startIndex + 10; x++) {
            result = result + recipeList.get(x);
        }

        return result;


    }

    public boolean foundSequence(int iSize, int input) {

        boolean found = false;
        int count = 0;
        for (int x = iSize; x < recipeList.size(); x++) {
            String sCompare = "";
            for (int y = x - 6; y < x; y++) {
                sCompare = sCompare + recipeList.get(y).toString();
            }

 //           System.out.println("compare : " + sCompare + " with " + input);

            found = sCompare.equals(String.valueOf(input));

            if (found) {
                foundIndex = iSize - 6 + count;
                break;
            }
            count++;
        }

        return found;
    }


    public int find_result(int input) {
        int result = 0;


        do {
            addRecipes();
        } while (recipeList.size() < 6);

        int previousSize;

        do {
            previousSize = recipeList.size();
            addRecipes();

        } while (!(foundSequence(previousSize, input)));

        return this.getFoundIndex();
    }
}


public class Day_14 {

    final static int INPUT = 440231;

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        RecipeTries rtPart1 = new RecipeTries();

        System.out.println("The Answer is : " + rtPart1.Calculate_result(INPUT));

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        start = LocalTime.now();

        RecipeTries rtPart2 = new RecipeTries();

        System.out.println("The Answer is : " + rtPart2.find_result(INPUT));

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());



    }

}
