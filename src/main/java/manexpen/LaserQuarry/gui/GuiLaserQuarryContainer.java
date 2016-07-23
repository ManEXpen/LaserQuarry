package manexpen.LaserQuarry.gui;

import manexpen.LaserQuarry.gui.container.LaserQuarryContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class GuiLaserQuarryContainer extends GuiContainer {
    private static final ResourceLocation GUI_RESOURCE_LOCATION = new ResourceLocation("laser", "textures/gui/laserQuarry.png");

    public GuiLaserQuarryContainer(int x, int y, int z) {
        super(new LaserQuarryContainer(x, y, z));
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        this.mc.renderEngine.bindTexture(GUI_RESOURCE_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
