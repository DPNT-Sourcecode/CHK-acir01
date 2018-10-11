package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;

public class CheckoutSolution {
    //offers table
    private HashMap<String, Skus> priceOffersTable;

    public CheckoutSolution ()
    {
        priceOffersTable = new HashMap<?,?>(){
            
        }
    }

    // input 2B, 3A, etc...
    public Integer checkout(String skus) {
        //get amount
        //get item
        //check if item have special offers


    }

    private class Skus {
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

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setSpecialAmount(int specialAmount) {
            this.specialAmount = specialAmount;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setSpecial(boolean special) {
            isSpecial = special;
        }

        public void setSpecialPrice(int specialPrice) {
            this.specialPrice = specialPrice;
        }

        public int getTotalPrice() {
            int totalPrice = 0;
            //is have special price
            if (isSpecial) {
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
