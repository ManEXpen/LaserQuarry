package manexpen.LaserQuarry.item;

import cofh.lib.util.helpers.EnergyHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import manexpen.LaserQuarry.api.EnumChatColor;
import manexpen.LaserQuarry.api.PosData2Dim;
import manexpen.LaserQuarry.entity.EntityRedLine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManEXpen on 2016/07/30.
 */
public class ItemAreaSetter extends ItemToolBase {
    private int posX1, posZ1, posX2, posZ2;


    private int useCount = 0;
    private static final int USE_ENERGY = 100;
    private static final int MAXENRGY = 5000;
    private static final int MAX_TRANSFER = 2000;
    private int storage;


    private ArrayList<EntityRedLine> foundLasers = new ArrayList<>();
    private PosData2Dim posData = new PosData2Dim(0, 0, 0, 0);


    public PosData2Dim getPosData() {
        return posData;
    }


    public ItemAreaSetter() {
        setTextureName("laser:areasetter");
        setMaxStackSize(1);
    }

    public ArrayList<EntityRedLine> getFoundLasers() {
        return foundLasers;
    }

    public void clearFoundLaserList() {
        foundLasers = null;
        foundLasers = new ArrayList<>();
        useCount = 0;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {

        if (itemStack.stackTagCompound == null) EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        int storage = itemStack.stackTagCompound.getInteger("Energy");


        if (storage > USE_ENERGY) {
            if (world.isRemote) {
                switch (useCount) {
                    case 0:
                        this.posX1 = x;
                        this.posZ1 = z;
                        particle(world, posX1, posZ1);
                        useCount++;
                        break;
                    case 1:
                        this.posX2 = x;
                        this.posZ2 = z;
                        this.posData = new PosData2Dim(posX1, posZ1, posX2, posZ2);
                        particle(world, posX1, posZ1);
                        particle(world, posX2, posZ2);
                        particle(world, posX1, posZ2);
                        particle(world, posX2, posZ1);
                        useCount++;
                        break;
                    case 2:
                        foundLasers.forEach(EntityRedLine::setDead);
                        foundLasers.clear();
                        this.posData = new PosData2Dim(0, 0, 0, 0);
                        useCount = 0;
                        break;
                }
                System.out.println(posX1 + " " + posX2);
                player.addChatComponentMessage(new ChatComponentText(EnumChatColor.AQUA + StatCollector.translateToLocal("laesr.chat.areasetter.setPos") + ": " + EnumChatColor.WHITE + "X= " + x + " Z= " + z));


                return true;
            } else {
                switch (useCount) {
                    case 0:
                        this.posX1 = x;
                        this.posZ1 = z;
                        break;
                    case 1:
                        this.posX2 = x;
                        this.posZ2 = z;
                        this.posData = new PosData2Dim(posX1, posZ1, posX2, posZ2);
                        break;
                }
            }
            this.storage = storage - USE_ENERGY;
            setEnergyToNBT(itemStack, this.storage);
        }

        return false;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean p_77624_4_) {
        if (itemStack.stackTagCompound == null) EnergyHelper.setDefaultEnergyTag(itemStack, 0);
        storage = itemStack.stackTagCompound.getInteger("Energy");
        list.add("Energy: " + storage);
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
        if (container.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(container, 0);

        int stored = container.stackTagCompound.getInteger("Energy");
        int receive = Math.min(maxReceive, Math.min(MAXENRGY - stored, MAX_TRANSFER));

        if (!simulate) {
            stored += receive;
            this.storage += receive;
            container.stackTagCompound.setInteger("Energy", stored);
        }
        return receive;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        if (container.stackTagCompound == null)
            EnergyHelper.setDefaultEnergyTag(container, 0);

        return container.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return MAXENRGY;
    }


    @SideOnly(Side.CLIENT)
    private void particle(World world, int x, int z) {
        EntityRedLine entityRedLine = new EntityRedLine(world, x, x, 0, 256, z, z);
        foundLasers.add(entityRedLine);
        world.spawnEntityInWorld(entityRedLine);
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

}

