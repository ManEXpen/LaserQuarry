package manexpen.LaserQuarry.api

import manexpen.LaserQuarry.tileentity.TileMachineBase

import scala.collection.mutable

/**
  * Created by ManEXpen on 2016/08/27.
  */
object TileMachineRegistry {
  var TileMachineList = new mutable.MutableList[TileMachineBase]

  def addMachine(machine: TileMachineBase): Unit = {
    TileMachineList = TileMachineList :+ machine
  }

  def delMachine(machine: TileMachineBase): Unit = {
    TileMachineList = TileMachineList.filter(_ != machine)
  }
}
