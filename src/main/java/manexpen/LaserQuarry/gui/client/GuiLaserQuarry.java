package manexpen.LaserQuarry.gui.client;

import manexpen.LaserQuarry.gui.container.LaserQuarryContainer;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class GuiLaserQuarry extends GuiContainer {
    private static final ResourceLocation GUI_RESOURCE_LOCATION = new ResourceLocation("laser", "textures/gui/laserQuarry.png");

    private int x, y, z;
    private TileLaserQuarry tileLaserQuarry;

    public GuiLaserQuarry(int x, int y, int z, EntityPlayer player, World world) {
        super(new LaserQuarryContainer(x, y, z, player, world));
        this.x = x;
        this.y = y;
        this.z = z;
        tileLaserQuarry = (TileLaserQuarry) world.getTileEntity(x, y, z);
        this.xSize = 176;
        this.ySize = 165;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        super.keyTyped(p_73869_1_, p_73869_2_);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int var1, int var2) {
        super.drawGuiContainerForegroundLayer(var1, var2);
        String s = String.valueOf(tileLaserQuarry.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x62a48);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        this.mc.renderEngine.bindTexture(GUI_RESOURCE_LOCATION);
        int xStart = (this.width - this.xSize) / 2;
        int yStart = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        this.drawTexturedModalRect(50, 50, 176, 134, 30, 31);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        System.out.println(i + ":" + j + ":" + k);
    }
}
