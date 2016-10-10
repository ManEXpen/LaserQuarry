package manexpen.LaserQuarry;

import manexpen.LaserQuarry.api.ClassKinds;
import manexpen.LaserQuarry.api.ToClearify;
import manexpen.LaserQuarry.block.BlockMachineLaserQuarry;
import manexpen.LaserQuarry.block.BlockReinforcedGlass;
import manexpen.LaserQuarry.block.BlockSuperMincer;
import manexpen.LaserQuarry.entity.EntityEmeraldGunBullet;
import manexpen.LaserQuarry.entity.EntityRedLine;
import manexpen.LaserQuarry.entity.EntitySquareLaser;
import manexpen.LaserQuarry.item.ItemAreaSetter;
import manexpen.LaserQuarry.item.ItemEmeraldGun;
import manexpen.LaserQuarry.item.ItemSpawnDebug;
import manexpen.LaserQuarry.item.ItemsSimple;
import manexpen.LaserQuarry.render.RenderEmeraldBullet;
import manexpen.LaserQuarry.render.RenderRedLine;
import manexpen.LaserQuarry.render.RenderSquareLaser;
import manexpen.LaserQuarry.render.RenderSuperMincer;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import manexpen.LaserQuarry.tileentity.TileSuperMincer;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LQItemBlockList {
    /*Block Fields*/
    public static BlockMachineLaserQuarry quarry;
    public static BlockReinforcedGlass reinforcedGlass;
    public static BlockSuperMincer superMincer;

    /*Items Fields*/
    public static ItemAreaSetter areaSetter;
    public static ItemSpawnDebug debugger;
    public static ItemEmeraldGun itemEmeraldGun;
    public static ItemsSimple.ItemCirculationLaserGen circLaserGen;
    public static ItemsSimple.ItemChipSet chipSet;
    public static ItemsSimple.ItemHighLevelConductor highLevelConductor;
    public static ItemsSimple.ItemLowLevelConductor lowLevelConductor;
    public static ItemsSimple.ItemOctaRedCrystal octaRedCrystal;
    public static ItemsSimple.ItemPositionFixinger positionFixinger;
    public static ItemsSimple.ItemStrangeMeat strangeMeat;
    public static ItemsSimple.canVillagerMeat emptyCan, meatCan, emeraldCan, superEmeraldCan;


    /*Class Fields*/
    @ToClearify(ClassKinds.TILEENTITY)
    public static Class tileLaserQuarryClass = TileLaserQuarry.class;
    @ToClearify(ClassKinds.TILEENTITY)
    public static Class tileSuperMincer = TileSuperMincer.class;
    @ToClearify(value = ClassKinds.TILEENTITYRENDER, needToReg = TileSuperMincer.class)


    public static Class superMincerRenderer = RenderSuperMincer.class;
    @ToClearify(value = ClassKinds.ENTITYRENDER, needToReg = EntitySquareLaser.class)
    public static Class sqLaserRenderer = RenderSquareLaser.class;
    @ToClearify(value = ClassKinds.ENTITYRENDER, needToReg = EntityRedLine.class)
    public static Class redLineRenderer = RenderRedLine.class;
    @ToClearify(value = ClassKinds.ENTITYRENDER, needToReg = EntityEmeraldGunBullet.class)
    public static Class emeraldEunBulletRenderer = RenderEmeraldBullet.class;


    @ToClearify(ClassKinds.ENTITY)
    public static Class sqLaser = EntitySquareLaser.class;
    @ToClearify(ClassKinds.ENTITY)
    public static Class emeraldGunBullet = EntityEmeraldGunBullet.class;
}
