package manexpen.LaserQuarry.item;

import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.api.IHasRecipe;
import manexpen.LaserQuarry.entity.EntityEmeraldGunBullet;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by manex on 2016/10/06.
 */
public class ItemEmeraldGun extends ItemBow implements IHasRecipe {
    public ItemEmeraldGun() {
        setTextureName("laser:emeraldGun");
        setFull3D();
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int useCount) {
        int j = this.getMaxItemUseDuration(itemStack) - useCount;
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;

        if (flag || player.inventory.hasItem(Items.emerald)) {
            float f = (float) j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double) f < 0.1D) {
                return;
            }

            if (f > 1.0F) {
                f = 1.0F;
            }

            EntityEmeraldGunBullet entity = new EntityEmeraldGunBullet(world, player, 5.0F);

            if (f == 1.0F) {
                entity.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);

            if (k > 0) {
                entity.setDamage(entity.getDamage() + (double) k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

            if (l > 0) {
                entity.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                entity.setFire(100);
            }

            itemStack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.explode", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag) {
                entity.canBePickedUp = 2;
            } else {
                player.inventory.consumeInventoryItem(Items.emerald);
            }

            this.generateRandomParticles(player);

            if (!world.isRemote) {
                world.spawnEntityInWorld(entity);
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.emerald))
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));

        return itemStack;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon("laser:emeraldGun");
    }

    @Override
    public IIcon getItemIconForUseDuration(int par1) {
        return itemIcon;
    }

    private void generateRandomParticles(Entity entity) {
        for (int i = 0; i < 5; ++i) {
            double d0 = itemRand.nextGaussian() * 0.02D;
            double d1 = itemRand.nextGaussian() * 0.02D;
            double d2 = itemRand.nextGaussian() * 0.02D;
            entity.worldObj.spawnParticle("angryVillager", entity.posX + (double) (itemRand.nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + (double) (itemRand.nextFloat() * entity.height), entity.posZ + (double) (itemRand.nextFloat() * entity.width * 2.0F) - (double) entity.width, d0, d1, d2);
        }
    }

    @Override
    public void registerRecipe() {
        GameRegistry.addRecipe(new ItemStack(LQItemBlockList.itemEmeraldGun),
                "XY ",
                "ZXY",
                "AZY",
                'X', Items.quartz,
                'Y', Items.emerald,
                'Z', Items.iron_ingot,
                'A', LQItemBlockList.chipSet);
    }
}
