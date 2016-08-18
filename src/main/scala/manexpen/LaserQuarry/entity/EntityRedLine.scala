package manexpen.LaserQuarry.entity

import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

/**
  * Created by ManEXpen on 2016/08/03.
  */
class EntityRedLine(var world: World, var startPosX: Double, var endPosX: Double, var startPosY: Double, var endPosY: Double, var startPosZ: Double, var endPosZ: Double) extends Entity(world: World) with ILaserEntity {

  def this(world: World) = this(world, 0, 0, 0, 0, 0, 0)

  setPositionAndRotation(startPosX, startPosY, startPosZ, 0.0F, 0.0F)
  setSize(1, 256)


  val texture = new ResourceLocation("laser", "textures/entities/laserRed.png")


  def setPositions(startPosX: Double, endPosX: Double, startPosY: Double, endPosY: Double, startPosZ: Double, endPosZ: Double): Unit = {
    this.startPosX = startPosX
    this.endPosX = endPosX
    this.startPosY = startPosY
    this.endPosY = endPosY
    this.startPosZ = startPosZ
    this.endPosZ = endPosZ
    setPositionAndRotation(this.startPosX, this.startPosY, this.startPosZ, 0.0F, 0.0F)
  }

  override def onUpdate(): Unit = {
    super.onUpdate()
  }

  override def getTexture: ResourceLocation = texture

  override def getBrightnessForRender(p_70070_1_ : Float): Int = 210

  override def writeEntityToNBT(tagCompound: NBTTagCompound): Unit = {
  }

  override def readEntityFromNBT(tagCompound: NBTTagCompound): Unit = {
  }

  override def entityInit(): Unit = {
    this.preventEntitySpawning = false
    this.noClip = true
  }

  override def onEntityUpdate(): Unit = {
    super.onEntityUpdate()
  }

}
