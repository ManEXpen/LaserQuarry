package manexpen.LaserQuarry.render;

import manexpen.LaserQuarry.entity.EntitySquareLaser;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ManEXpen on 2016/08/28.
 */
public class RenderSpuareLaser extends Render {
    ModelSquareLaser model = new ModelSquareLaser();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        EntitySquareLaser sqLaser = (EntitySquareLaser) entity;


    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntitySquareLaser) entity).getTexture();
    }

    private static class ModelSquareLaser extends ModelBase {
        private ModelRenderer[] modelSide;

        public void init(int xLength, int zLength) {
            modelSide = new ModelRenderer[xLength + zLength];
        }

        ModelSquareLaser() {
            modelSide[0] = new ModelRenderer(this, "side1");
            modelSide[0].addBox(0, 0, 0, 1, 4096, 1);
        }

        @Override
        public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
            modelSide[0].render(p_78088_7_);
        }
    }
}
