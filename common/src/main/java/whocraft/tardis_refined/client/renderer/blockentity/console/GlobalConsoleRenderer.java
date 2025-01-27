package whocraft.tardis_refined.client.renderer.blockentity.console;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleModelCollection;
import whocraft.tardis_refined.client.model.blockentity.console.ConsoleUnit;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.Objects;

public class GlobalConsoleRenderer implements BlockEntityRenderer<GlobalConsoleBlockEntity>, BlockEntityRendererProvider<GlobalConsoleBlockEntity> {

    private static final Vec3 crystalHolo = new Vec3(0.3f, -1.725, 0.655);
    private static final Vec3 crystalHoloColor = new Vec3(1f, 0.64f, 0f);

    private static final Vec3 initiativeHolo = new Vec3(-1.23, -1.225, 1.775F);
    private static final Vec3 initiativeHoloColor = new Vec3(0, 0.8f, 1f);


    public GlobalConsoleRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(GlobalConsoleBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        BlockState blockstate = blockEntity.getBlockState();
        ConsoleTheme theme = blockstate.getValue(GlobalConsoleBlock.CONSOLE);

        ConsoleUnit consoleModel = ConsoleModelCollection.getInstance().getConsoleModel(theme);
        consoleModel.renderConsole(blockEntity, Objects.requireNonNull(blockEntity.getLevel()), poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(consoleModel.getTexture(blockEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        if(blockEntity.pattern() != null && blockEntity.pattern().patternTexture().emissive()) {
            consoleModel.renderConsole(blockEntity, Objects.requireNonNull(blockEntity.getLevel()), poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(consoleModel.getTexture(blockEntity, true))), 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
        poseStack.popPose();

        if (theme == ConsoleTheme.CRYSTAL) {
            renderHoloShell(crystalHolo,270, blockEntity, poseStack, bufferSource, packedLight, crystalHoloColor);
        }

        if (theme == ConsoleTheme.INITIATIVE) {
            renderHoloShell(initiativeHolo, -30 + 180, blockEntity, poseStack, bufferSource, packedLight, initiativeHoloColor);
        }
    }

    private void renderHoloShell(Vec3 offset, int rotation, GlobalConsoleBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Vec3 color) {
        if (blockEntity.getLevel().random.nextInt(20) != 0) {
            poseStack.pushPose();
            TardisClientData reactions = TardisClientData.getInstance(blockEntity.getLevel().dimension());
            var model = ShellModelCollection.getInstance().getShellModel(reactions.getShellTheme());
            model.setDoorOpen(false);

            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.translate(0, blockEntity.getLevel().random.nextFloat() * 0.01, 0);
            poseStack.scale(0.1f, 0.1f, 0.1f);
            if (reactions.isFlying()) {
                poseStack.mulPose(Vector3f.YP.rotationDegrees(((blockEntity.getLevel().getGameTime() % 360)) * 25f));
            } else {
                poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation % 360));
            }

            ShellPattern pattern = reactions.shellPattern();
            model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(pattern.exteriorDoorTexture().texture())), packedLight, OverlayTexture.NO_OVERLAY, (float) color.x, (float) color.y, (float) color.z, 0.25f);
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(GlobalConsoleBlockEntity blockEntity) {
        return true;
    }

    @Override
    public BlockEntityRenderer<GlobalConsoleBlockEntity> create(BlockEntityRendererProvider.Context context) {
        return new GlobalConsoleRenderer(context);
    }
    
}
