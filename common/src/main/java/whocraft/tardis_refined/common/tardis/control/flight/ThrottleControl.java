package whocraft.tardis_refined.common.tardis.control.flight;

import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.IControl;

public class ThrottleControl implements IControl {


    @Override
    public void onRightClick(TardisLevelOperator operator) {
        if (operator.getControlManager().isInFlight()) {
            operator.getControlManager().endFlight();
        } else {
            operator.getControlManager().beginFlight();
        }
    }

    @Override
    public void onLeftClick() {

    }
}