package manexpen.LaserQuarry.render;

import manexpen.LaserQuarry.entity.EntityRedLine;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;


/**
 * Created by ManEXpen on 2016/08/02.
 */
public class RenderRedLine extends Render {

    ModelRedLine model = new ModelRedLine();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        EntityRedLine redLine = (EntityRedLine) entity;

        glPushMatrix();
        glPushAttrib(GL_ENABLE_BIT);

        glDisable(GL_LIGHTING);

        //これないと位置ずれする
        glTranslated(x, y, z);

        glTranslated(0.5, 0, 0.5);
        glScaled(2, 1, 2);

        bindEntityTexture(redLine);
        this.model.render(redLine, 0, 0, 0, 0, 0, 0.0625F);

        glPopAttrib();
        glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityRedLine redLine = (EntityRedLine) entity;
        return redLine.getTexture();
    }

    private static class ModelRedLine extends ModelBase {
        private ModelRenderer[] modelSide = new ModelRenderer[1];

        ModelRedLine() {
            modelSide[0] = new ModelRenderer(this, "side1");
            modelSide[0].addBox(0, 0, 0, 1, 4096, 1);
        }

        @Override
        public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
            modelSide[0].render(p_78088_7_);
        }
    }

}
