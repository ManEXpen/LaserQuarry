package manexpen.LaserQuarry.render;

import manexpen.LaserQuarry.tileentity.TileSuperMincer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.tileentity.TileEntity;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by manex on 2016/10/06.
 */
public class RenderSuperMincer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
        glPushMatrix();
        glTranslated(x + 0.5, y, z + 0.5);
        renderSuperMincer((TileSuperMincer) tileEntity, x, y, z, p_147500_8_);
        glPopMatrix();
    }

    private void renderSuperMincer(TileSuperMincer tile, double x, double y, double z, float par7) {
        EntityVillager entity = tile.getVillager();
        float mod = (float) tile.getRunTime();

        if (mod <= 20.0F) mod = 0.0F;

        if (entity != null) {
            float val;

            if (mod < 20.0F)
                val = mod * mod;
            else
                val = (float) Math.sin((double) mod) * 360.0F;


            glTranslatef(0.0F, 0.4F, 0.0F);

            if (entity.deathTime == 0) glRotatef(val, 0.0F, 1.0F, 0.0F);

            glTranslatef(0.0F, -mod / 80.0F, 0.0F);
            entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, par7);
        }
    }
}
