package befaster.solutions.CHK;

public class SpecialReducerItem {
    private String reduceTarget;
    private int reduceAmount;
    private int triggerAmount;

    public String getReduceTarget() {
        return reduceTarget;
    }

    public SpecialReducerItem(String reduceTarger, int reduceAmount, int triggerAmount) {
        this.reduceTarget = reduceTarger;
        this.reduceAmount = reduceAmount;
        this.triggerAmount = triggerAmount;
    }

    public int getReducedAmount(String target, int itemCount) {
        if (!reduceTarget.equals(target))
            return 0;

        return reduceAmount * (itemCount / triggerAmount);
    }
}
