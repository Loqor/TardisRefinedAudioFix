package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.ArrayList;
import java.util.List;

// TODO: Reimplement datapack system.
public class TardisDesktops {

    public static List<DesktopTheme> DESKTOPS = new ArrayList<>();

    public static final DesktopTheme DEFAULT_OVERGROWN_THEME = registerDesktop(new DesktopTheme("default_overgrown" ,new ResourceLocation(TardisRefined.MODID, "cave/cave_generation_one"), "Overgrown", new BlockPos(8,2,8), true));

    public static final DesktopTheme FACTORY_THEME = registerDesktop(new DesktopTheme("factory", new ResourceLocation(TardisRefined.MODID, "desktop/factory"), "Factory", new BlockPos(8,2,8), true));

    public static DesktopTheme registerDesktop(DesktopTheme theme) {
        DESKTOPS.add(theme);
        return theme;
    }

    public static DesktopTheme getDesktopThemeById(String id) {
        return DESKTOPS.stream().filter(theme -> theme.id == id).findAny().get();
    }

}