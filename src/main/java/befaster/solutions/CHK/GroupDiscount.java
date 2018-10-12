package befaster.solutions.CHK;

import java.util.List;

public class GroupDiscount {

    public List<SKU> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(List<SKU> groupItems) {
        this.groupItems = groupItems;
    }

    public int getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(int groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getTriggerAmount() {
        return triggerAmount;
    }

    public void setTriggerAmount(int triggerAmount) {
        this.triggerAmount = triggerAmount;
    }

    private List<SKU> groupItems;

    private int groupPrice;

    private int triggerAmount;

}
