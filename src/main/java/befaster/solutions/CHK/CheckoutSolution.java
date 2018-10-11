package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

public class CheckoutSolution {
    //offers table

    // input 2B, 3A, etc...
    public Integer checkout(String skus) {
        //get amount
        //get item
        //check if item have special offers


    }

    private class Skus
    {
        private int amount;

        // special amount
        private int specialAmount;

        // item
        private String item;

        private int price;
        // is special
        private boolean isSpecial;

        // special price
        private int specialPrice;

        public int getTotalPrice(){
            int totalPrice = 0;
            //is have special price
            if(isSpecial){
                //calculate amount of special bundles
                totalPrice = (amount / specialAmount) * specialPrice;
                //add left item with default price
                totalPrice += (amount % specialAmount) * price;
                return totalPrice;
            }
            //else return amount by price
            return amount * price;
        }
    }
}
