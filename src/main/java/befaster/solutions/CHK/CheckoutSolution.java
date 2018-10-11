package befaster.solutions.CHK;

import java.util.HashMap;

public class CheckoutSolution {
    //offers table
    private HashMap<String, Skus> priceOffersTable;

    public CheckoutSolution ()
    {
        priceOffersTable = new HashMap<String,Skus>();
        Skus A = new Skus("A", 50, true, 3, 130);
        Skus B = new Skus("B", 30, true, 2, 45);
        Skus C = new Skus("C", 20, false, null, null);
        Skus D = new Skus("D", 15, false, null, null);
    }

    // input 2B, 3A, need to check out which kind of input
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

        public Skus(String item, int price, boolean isSpecial, Integer specialAmount, Integer specialPrice) {
            this.specialAmount = specialAmount;
            this.item = item;
            this.price = price;
            this.isSpecial = isSpecial;
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
