package manexpen.LaserQuarry.api

/**
  * Created by ManEXpen on 2016/08/21.
  */

/*
* 2次元ポジションデータ
* 保持用クラス*/
class PosData2Dim(var x1: Int, var z1: Int, var x2: Int, var z2: Int) {
  def this(posData: PosData2Dim) = {
    this(posData.x1, posData.z1, posData.x2, posData.z2)
  }

  override def toString: String = {
    this.x1 + ": First X Pos" + this.z1 + ": First Z Pos" + this.x2 + ": Second X Pos" + this.z2 + ": Second Z Pos"
  }

}
