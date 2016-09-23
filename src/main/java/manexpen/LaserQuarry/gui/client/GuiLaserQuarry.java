package manexpen.LaserQuarry.gui.client;

import manexpen.LaserQuarry.gui.container.LaserQuarryContainer;
import manexpen.LaserQuarry.gui.guiparts.LQGuiButton;
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
    private World worldObj;
    private EntityPlayer player;
    private int TestProgress, TestProgressS = 0;

    private LQGuiButton isDoNotify;


    public GuiLaserQuarry(int x, int y, int z, EntityPlayer player, World world) {
        super(new LaserQuarryContainer(x, y, z, player, world));
        this.x = x;
        this.y = y;
        this.z = z;
        tileLaserQuarry = (TileLaserQuarry) world.getTileEntity(x, y, z);
        this.xSize = 169;
        this.ySize = 159;
        this.player = player;
        this.worldObj = world;
    }

    @Override
    public void initGui() {
        super.initGui();
        int xStart = (this.width - this.xSize) / 2;
        int yStart = (this.height - this.ySize) / 2;
        this.isDoNotify = new LQGuiButton(0, xStart + 50, yStart + 79, 76, 7, "Notify when Work is Done.");
        this.buttonList.add(isDoNotify);
    }


    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int var1, int var2) {
        super.drawGuiContainerForegroundLayer(var1, var2);
        String s = String.valueOf(tileLaserQuarry.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 2, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        this.mc.renderEngine.bindTexture(GUI_RESOURCE_LOCATION);

        int xStart = (this.width - this.xSize) / 2;
        int yStart = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        this.drawTexturedModalRect(xStart + 37, yStart + 10, 0, 160, setTestProgress(), 6);
        this.drawTexturedModalRect(xStart + 37, yStart + 17, 0, 160, setTestProgress(), 6);
        this.drawTexturedModalRect(xStart + 7, yStart + 6, 170, 0, 12, setTestProgressS());
        this.drawTexturedModalRect(xStart + 151, yStart + 6, 170, 0, 12, setTestProgressS());
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void mouseClicked(int x, int y, int mouseButtonType) {
        super.mouseClicked(x, y, mouseButtonType);
        System.out.println(x + ":" + y + ":" + mouseButtonType);
    }

    public int setTestProgressS() {
        if (TestProgressS == 39) {
            TestProgressS = 0;
            return 0;
        }
        return TestProgressS++;
    }

    public int setTestProgress() {
        if (TestProgress == 98) {
            TestProgress = 0;
            return TestProgress;
        }
        return TestProgress++;
    }
}
