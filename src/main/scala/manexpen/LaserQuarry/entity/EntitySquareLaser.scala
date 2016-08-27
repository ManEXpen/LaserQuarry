package manexpen.LaserQuarry.entity

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
  * Created by ManEXpen on 2016/08/28.
  */
class EntitySquareLaser(var world: World, var cornerPosX: Int, var cornerPosZ: Int, var endCornerPosX: Int, var endCornerPosZ: Int) extends Entity(world: World) with ILaserEntity {
  def this(world: World) = this(world, 0, 0, 0, 0)

  private val texture = new ResourceLocation("laser", "texture/entities/laserRed.png")


  override def onUpdate(): Unit = super.onUpdate()

  override def getTexture: ResourceLocation = texture

  override def writeEntityToNBT(p_70014_1_ : NBTTagCompound): Unit = {}

  override def entityInit(): Unit = {
    this.preventEntitySpawning = false
    this.noClip = true
  }

  override def readEntityFromNBT(p_70037_1_ : NBTTagCompound): Unit = {}
}
