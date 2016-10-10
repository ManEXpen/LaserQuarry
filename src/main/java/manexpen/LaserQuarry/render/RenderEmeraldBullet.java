package manexpen.LaserQuarry.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by manex on 2016/10/07.
 */
public class RenderEmeraldBullet extends Render {
    @Override
    public void doRender(Entity entity, double x, double y, double z, float par2, float par4) {
        IIcon icon = Items.emerald.getIconFromDamage(0);

        if (icon != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            this.bindEntityTexture(entity);
            Tessellator tessellator = Tessellator.instance;

            if (icon == ItemPotion.func_94589_d("bottle_splash")) {
                int i = PotionHelper.func_77915_a(((EntityPotion) entity).getPotionDamage(), false);
                float f2 = (float) (i >> 16 & 255) / 255.0F;
                float f3 = (float) (i >> 8 & 255) / 255.0F;
                float f4 = (float) (i & 255) / 255.0F;
                GL11.glColor3f(f2, f3, f4);
                GL11.glPushMatrix();
                this.renderEmerald(tessellator, ItemPotion.func_94589_d("overlay"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            this.renderEmerald(tessellator, icon);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    private void renderEmerald(Tessellator tesselator, IIcon icon) {
        float minU = icon.getMinU();
        float maxU = icon.getMaxU();
        float minV = icon.getMinV();
        float maxV = icon.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0F, 1.0F, 0.0F);
        tesselator.addVertexWithUV(-f5, -f6, 0.0D, minU, maxV);
        tesselator.addVertexWithUV(f4 - f5, f6, 0.0D, maxU, maxV);
        tesselator.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, maxU, minV);
        tesselator.addVertexWithUV(f5, f4 - f6, 0.0D, minU, minV);
        tesselator.draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return TextureMap.locationItemsTexture;
    }
}
