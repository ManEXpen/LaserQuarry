package manexpen.LaserQuarry.entity;

import cpw.mods.fml.common.registry.IThrowableEntity;
import manexpen.LaserQuarry.LQItemBlockList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by manex on 2016/10/07.
 */
public class EntityEmeraldGunBullet extends Entity implements IProjectile, IThrowableEntity {


    protected int xTile = -1;
    protected int yTile = -1;
    protected int zTile = -1;
    protected Block inTile = null;
    protected int inData = 0;
    protected boolean inGround = false;
    public int canBePickedUp = 0;
    public int arrowShake = 0;
    public Entity shootingEntity;
    protected int ticksInGround;
    protected int ticksInAir = 0;
    protected double damage = 2.0D;
    protected int knockbackStrength;

    public EntityEmeraldGunBullet(World world) {
        super(world);
    }

    public EntityEmeraldGunBullet(World xWorld, EntityLivingBase user, float y) {
        super(xWorld);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = user;

        if (user instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(user.posX, user.posY + (double) user.getEyeHeight(), user.posZ, user.rotationYaw, user.rotationPitch);
        this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, y * 1.5F, 1.0F);
    }

    @Override
    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(x * x + z * z);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float i = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) i) * 180.0D / Math.PI);
        }

        Block block1 = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

        if (block1 instanceof BlockAir) {
            block1.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB vec3 = block1.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

            if (vec3 != null && vec3.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.inGround) {
            Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
            int vec31 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

            if (block == this.inTile && vec31 == this.inData) {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200) {
                    this.setDead();
                }
            } else {
                this.inGround = false;
                this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            ++this.ticksInAir;
            Vec3 var18 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 var19 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(var18, var19, false, true, true);
            var18 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            var19 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null) {
                var19 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;

            for (l = 0; l < list.size(); ++l) {
                Entity f2 = (Entity) list.get(l);

                if (f2.canBeCollidedWith() && (f2 != this.shootingEntity || this.ticksInAir >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB f3 = f2.boundingBox.expand((double) f1, (double) f1, (double) f1);
                    MovingObjectPosition f4 = f3.calculateIntercept(var18, var19);

                    if (f4 != null) {
                        double damagesource = var18.distanceTo(f4.hitVec);

                        if (damagesource < d0 || d0 == 0.0D) {
                            entity = f2;
                            d0 = damagesource;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
                EntityPlayer var20 = (EntityPlayer) movingobjectposition.entityHit;

                if (var20.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(var20)) {
                    movingobjectposition = null;
                }
            }

            float var21;
            float var22;

            if (movingobjectposition != null) {
                if (movingobjectposition.entityHit != null) {
                    var21 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int var23 = MathHelper.ceiling_double_int((double) var21 * this.damage);

                    if (this.getIsCritical()) {
                        var23 += this.rand.nextInt(var23 / 2 + 2);
                    }

                    DamageSource var24 = null;

                    if (this.shootingEntity == null) {
                        var24 = causeSimpleArrowDamage(this, this);
                    } else {
                        var24 = causeSimpleArrowDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                        movingobjectposition.entityHit.setFire(5);
                    }

                    if (movingobjectposition.entityHit.attackEntityFrom(var24, (float) var23)) {
                        if (movingobjectposition.entityHit instanceof EntityLiving) {
                            EntityLiving entityliving = (EntityLiving) movingobjectposition.entityHit;

                            if (!this.worldObj.isRemote) {
                                entityliving.setArrowCountInEntity(entityliving.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0) {
                                var22 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (var22 > 0.0F) {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579D / (double) var22, 0.1D, this.motionZ * (double) this.knockbackStrength * 0.6000000238418579D / (double) var22);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
                                Enchantment.thorns.func_151367_b((EntityLivingBase) this.shootingEntity, entityliving, this.rand.nextInt());
                            }

                        }

                        this.onHit(movingobjectposition.entityHit);
                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    } else {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
                    this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
                    this.motionX = (double) ((float) (movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = (double) ((float) (movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = (double) ((float) (movingobjectposition.hitVec.zCoord - this.posZ));
                    var21 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double) var21 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double) var21 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double) var21 * 0.05000000074505806D;
                    this.playSoundOnHit();
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (!(this.inTile instanceof BlockAir)) {
                        inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
                    }
                }
            }

            if (this.getIsCritical()) {
                for (l = 0; l < 4; ++l) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double) l / 4.0D, this.posY + this.motionY * (double) l / 4.0D, this.posZ + this.motionZ * (double) l / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            var21 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var21) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float var25 = 0.99F;
            f1 = 0.05F;

            if (this.isInWater()) {
                var22 = 0.25F;
                var25 = 0.8F;
                this.spawnParticleInWater();
            }

            var22 = 0.25F;
            this.motionX *= (double) var25;
            this.motionY *= (double) var25;
            this.motionZ *= (double) var25;
            this.motionY -= (double) f1;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }


    }

    protected void playSoundOnHit() {
        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
    }

    protected void spawnParticleInWater() {
        float f3 = 0.25F;

        for (int j1 = 0; j1 < 4; ++j1) {
            this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f3, this.posY - this.motionY * (double) f3, this.posZ - this.motionZ * (double) f3, this.motionX, this.motionY, this.motionZ);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(new ItemStack(Items.emerald, 1))) {
                flag = false;
            }

            if (flag) {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getShadowSize() {
        return 0.0F;
    }


    public void setDamage(double par1) {
        this.damage = par1;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setKnockbackStrength(int par1) {
        this.knockbackStrength = par1;
    }

    @Override
    public boolean canAttackWithItem() {
        return false;
    }

    public void setIsCritical(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte) (b0 & -2));
        }
    }

    public boolean getIsCritical() {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }

    public void onHit(Entity target) {
        if (target instanceof EntityVillager) {
            target.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) this.shootingEntity), 100.0F);
            this.worldObj.createExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1.5F, false);
            this.itemDrop();
        }

        this.setDead();
    }

    public void itemDrop() {
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 20; ++i) {
                EntityItem item = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(LQItemBlockList.strangeMeat));
                EntityItem em = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.emerald));
                item.motionX = item.motionX * 5.0D + this.rand.nextGaussian();
                item.motionY = item.motionY * 5.0D + this.rand.nextGaussian();
                item.motionZ = item.motionZ * 5.0D + this.rand.nextGaussian();
                this.worldObj.spawnEntityInWorld(item);

                if (this.rand.nextInt(5) == 0) {
                    this.worldObj.spawnEntityInWorld(em);
                }
            }
        }
    }

    @Override
    public Entity getThrower() {
        return null;
    }

    @Override
    public void setThrower(Entity entity) {
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte) 0);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        this.xTile = tag.getShort("xTile");
        this.yTile = tag.getShort("yTile");
        this.zTile = tag.getShort("zTile");

        this.inTile = Block.getBlockById(tag.getIntArray("inTile")[0]);
        this.inTile.damageDropped(tag.getIntArray("inTile")[1]);

        this.inData = tag.getByte("inData") & 255;
        this.arrowShake = tag.getByte("shake") & 255;
        this.inGround = tag.getByte("inGround") == 1;

        if (tag.hasKey("damage")) {
            this.damage = tag.getDouble("damage");
        }

        if (tag.hasKey("pickup")) {
            this.canBePickedUp = tag.getByte("pickup");
        } else if (tag.hasKey("player")) {
            this.canBePickedUp = tag.getBoolean("player") ? 1 : 0;
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setShort("xTile", (short) this.xTile);
        tag.setShort("yTile", (short) this.yTile);
        tag.setShort("zTile", (short) this.zTile);

        int blockid = Block.getIdFromBlock(this.inTile);
        int damage = this.inTile.getDamageValue(worldObj, this.xTile, this.yTile, this.zTile);
        tag.setIntArray("inTile", new int[]{blockid, damage});

        tag.setByte("inData", (byte) this.inData);
        tag.setByte("shake", (byte) this.arrowShake);
        tag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        tag.setByte("pickup", (byte) this.canBePickedUp);
        tag.setDouble("damage", this.damage);
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float dir, float par8) {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= (double) f2;
        y /= (double) f2;
        z /= (double) f2;
        x += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
        y += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
        z += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) par8;
        x *= (double) dir;
        y *= (double) dir;
        z *= (double) dir;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    public static DamageSource causeSimpleArrowDamage(EntityEmeraldGunBullet entitySimpleArrow, Entity par1Entity) {
        return (new EntityDamageSourceIndirect("arrow", entitySimpleArrow, par1Entity)).setProjectile();
    }
}
