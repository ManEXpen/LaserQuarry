package manexpen.LaserQuarry.render;

import manexpen.LaserQuarry.entity.EntitySquareLaser;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by ManEXpen on 2016/08/28.
 */
public class RenderSquareLaser extends Render {
    ModelSquareLaser model = new ModelSquareLaser();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        EntitySquareLaser sqLaser = (EntitySquareLaser) entity;
        model.init(sqLaser.xLength(), sqLaser.zLength());
        glPushMatrix();
        glPushAttrib(GL_ENABLE_BIT);

        glDisable(GL_LIGHTING);

        //これないと位置ずれする
        glTranslated(x, y, z);

        glTranslated(0.5, 0, 0.5);
        glScaled(2, 1, 2);

        bindEntityTexture(sqLaser);
        model.render(sqLaser, 0, 0, 0, 0, 0, 0.0625F);


        glPopAttrib();
        glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntitySquareLaser) entity).getTexture();
    }

    private static class ModelSquareLaser extends ModelBase {
        private ModelRenderer[] modelSide;

        public void init(int xLength, int zLength) {
            modelSide = new ModelRenderer[xLength + 4];
            modelSide[0] = new ModelRenderer(this, "side1");
            modelSide[1] = new ModelRenderer(this, "side2");
            modelSide[2] = new ModelRenderer(this, "side3");
            modelSide[3] = new ModelRenderer(this, "side4");

            //X,Y,Z,幅、高さ、奥行き
            modelSide[0].addBox(0.5F, 0.5F, 0.5F, 1, 16, 1);
            modelSide[1].addBox(xLength - 0.5F, 0.5F, 0.5F, 1, 16, 1);
            modelSide[2].addBox(0.5F, 0.5F, zLength - 0.5F, 1, 16, 1);
            modelSide[3].addBox(xLength - 0.5F, 0.5F, zLength - 0.5F, 1, 16, 1);
        }

        ModelSquareLaser() {
        }

        @Override
        public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
            for (int i = 0; i < 4; i++) {
                modelSide[i].render(p_78088_7_);
            }
        }
    }
}
