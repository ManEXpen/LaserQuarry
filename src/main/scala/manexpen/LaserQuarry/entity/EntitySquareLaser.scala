package manexpen.LaserQuarry.entity

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
  * Created by ManEXpen on 2016/08/28.
  */
class EntitySquareLaser(var world: World, var x: Int, var z: Int, var xLength: Int, var zLength: Int) extends Entity(world: World) with ILaserEntity {
  setPositionAndRotation(x, 256, z, 0, 0)
  setSize(1, 1)

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
