package whocraft.tardis_refined.common.blockentity.door;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

public class BulkHeadDoorBlockEntity extends BlockEntity implements BlockEntityTicker<BulkHeadDoorBlockEntity> {

    public BulkHeadDoorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.BULK_HEAD_DOOR.get(), blockPos, blockState);
    }


    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, BulkHeadDoorBlockEntity blockEntity) {
        if (!blockState.getValue(BulkHeadDoorBlock.LOCKED)) {
            Player player = level.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2.5f, false);
            if (player != null) {
                if (!blockState.getValue(BulkHeadDoorBlock.OPEN)) {
                    openDoor(level,blockPos,blockState);
                }
            } else {
                if (blockState.getValue(BulkHeadDoorBlock.OPEN)) {
                    closeDoor(level,blockPos,blockState);
                }
            }
        }
    }

    public void openDoor(Level level, BlockPos blockPos, BlockState blockState) {
        level.playSound(null, blockPos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1, 1);
        level.setBlock(blockPos, blockState.setValue(BulkHeadDoorBlock.OPEN, true), 2);
    }

    public void closeDoor(Level level, BlockPos blockPos, BlockState blockState) {
        level.playSound(null, blockPos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1, 1);
        level.setBlock(blockPos, blockState.setValue(BulkHeadDoorBlock.OPEN, false), 2);
    }
}
