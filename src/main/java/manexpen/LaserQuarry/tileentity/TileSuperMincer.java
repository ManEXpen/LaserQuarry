package manexpen.LaserQuarry.tileentity;

import manexpen.LaserQuarry.LQItemBlockList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

import java.util.List;
import java.util.Random;

/**
 * Created by manex on 2016/10/06.
 */
public class TileSuperMincer extends TileEntity {
    private EntityVillager villager;
    public int runTime, damageCount = 0;
    private Random rnd = new Random();

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == LQItemBlockList.superMincer) {
            if (villager != null) {
                runTime++;

                if (villager.deathTime > 0) {
                    villager.setDead();
                    villager = null;
                }
            } else {
                villager = getOnVillagerWalking();
                runTime = damageCount = 0;
            }

            if (runTime > 20) startMince();
        } else {
            villager = null;
            runTime = damageCount = 0;
        }
    }

    private EntityVillager getOnVillagerWalking() {
        AxisAlignedBB aa = AxisAlignedBB.getBoundingBox((double) this.xCoord, (double) this.yCoord, (double) this.zCoord, (double) this.xCoord, (double) (this.yCoord + 1), (double) this.zCoord).expand(1.5D, 1.0D, 1.5D);
        List list = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, aa);

        for (Object obj : list) {
            if (obj instanceof EntityVillager) return (EntityVillager) obj;
        }
        return null;
    }

    private void startMince() {
        ++this.damageCount;

        if (this.damageCount == 5 && this.villager != null) {
            EntityItem item = new EntityItem(this.worldObj, (double) this.xCoord, (double) this.yCoord - 1.5D, (double) this.zCoord, new ItemStack(LQItemBlockList.strangeMeat));
            EntityItem em = new EntityItem(this.worldObj, (double) this.xCoord, (double) this.yCoord - 1.5D, (double) this.zCoord, new ItemStack(Items.emerald));

            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(item);

                if (this.rnd.nextInt(3) == 0) {
                    this.worldObj.spawnEntityInWorld(em);
                }
            }

            this.villager.attackEntityFrom(DamageSource.cactus, 2.0F);
            this.damageCount = 0;
        }
    }

    public boolean isMachineTop() {
        return worldObj.getBlock(xCoord, yCoord + 1, zCoord) != LQItemBlockList.superMincer;
    }

    public void setVillager(EntityVillager villager) {
        this.villager = villager;
    }

    public EntityVillager getVillager() {
        return this.villager;
    }

    public int getRunTime() {
        return runTime;
    }
}
