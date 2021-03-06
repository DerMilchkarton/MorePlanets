package stevekung.mods.moreplanets.module.planets.nibiru.client.renderer.entity.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.nibiru.client.model.ModelInfectedCreeper;
import stevekung.mods.moreplanets.module.planets.nibiru.client.renderer.entity.RenderInfectedCreeper;

@SideOnly(Side.CLIENT)
public class LayerInfectedCreeperCharge implements LayerRenderer<EntityCreeper>
{
    private final RenderInfectedCreeper render;
    private final ModelInfectedCreeper creeperModel = new ModelInfectedCreeper(2.0F);

    public LayerInfectedCreeperCharge(RenderInfectedCreeper render)
    {
        this.render = render;
    }

    @Override
    public void doRenderLayer(EntityCreeper entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity.getPowered())
        {
            boolean flag = entity.isInvisible();
            GlStateManager.depthMask(!flag);
            this.render.bindTexture(new ResourceLocation("textures/entity/creeper/creeper_armor.png"));
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = entity.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            GlStateManager.color(f1, f1, f1, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(1, 1);
            this.creeperModel.setModelAttributes(this.render.getMainModel());
            this.creeperModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag);
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}