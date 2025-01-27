package whocraft.tardis_refined.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.command.TardisRefinedCommand;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.patterns.ShellPatterns;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonBus {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (level instanceof ServerLevel serverLevel) {
            if (event.phase == TickEvent.Phase.END) {
                DelayedTeleportData.tick(serverLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onDatapack(AddReloadListenerEvent addReloadListenerEvent) {
        addReloadListenerEvent.addListener(ConsolePatterns.getReloadListener());

        addReloadListenerEvent.addListener(TardisDesktops.getReloadListener());
        addReloadListenerEvent.addListener(ShellPatterns.getReloadListener());

    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        TardisRefinedCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (MiscHelper.shouldStopItem(event.getEntity().getLevel(), player, event.getPos(), player.getMainHandItem())) {
                event.getLevel().destroyBlock(event.getPos(), true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        event.setCanceled(MiscHelper.shouldCancelBreaking(event.getPlayer().getLevel(), event.getPlayer(), event.getPos(), event.getState()));
    }

}