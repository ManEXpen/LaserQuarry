package manexpen.LaserQuarry;

import manexpen.LaserQuarry.api.ClassKinds;
import manexpen.LaserQuarry.api.ToClearify;
import manexpen.LaserQuarry.block.BlockMachineLaserQuarry;
import manexpen.LaserQuarry.block.BlockReinforcedGlass;
import manexpen.LaserQuarry.entity.EntityRedLine;
import manexpen.LaserQuarry.entity.EntitySquareLaser;
import manexpen.LaserQuarry.item.ItemAreaSetter;
import manexpen.LaserQuarry.item.ItemSpawnDebug;
import manexpen.LaserQuarry.item.ItemsSimple;
import manexpen.LaserQuarry.render.RenderRedLine;
import manexpen.LaserQuarry.render.RenderSquareLaser;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LQItemBlockList {
    /*Block Fields*/
    public static BlockMachineLaserQuarry quarry;
    public static BlockReinforcedGlass reinforcedGlass;

    /*Items Fields*/
    public static ItemAreaSetter areaSetter;
    public static ItemSpawnDebug debugger;
    public static ItemsSimple.ItemCirculationLaserGen circLaserGen;
    public static ItemsSimple.ItemChipSet chipSet;
    public static ItemsSimple.ItemHighLevelConductor highLevelConductor;
    public static ItemsSimple.ItemLowLevelConductor lowLevelConductor;
    public static ItemsSimple.ItemOctaRedCrystal octaRedCrystal;
    public static ItemsSimple.ItemPositionFixinger positionFixinger;

    /*Class Fields*/
    @ToClearify(ClassKinds.TILEENTITY)
    public static Class tileLaserQuarryClass = TileLaserQuarry.class;
    @ToClearify(value = ClassKinds.ENTITYRENDER, needToReg = EntitySquareLaser.class)
    public static Class sqLaserRenderer = RenderSquareLaser.class;
    @ToClearify(value = ClassKinds.ENTITYRENDER, needToReg = EntityRedLine.class)
    public static Class redLineRenderer = RenderRedLine.class;
    @ToClearify(ClassKinds.ENTITY)
    public static Class sqLaser = EntitySquareLaser.class;
}
