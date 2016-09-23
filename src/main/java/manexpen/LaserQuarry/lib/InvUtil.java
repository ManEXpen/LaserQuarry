package manexpen.LaserQuarry.lib;

import manexpen.LaserQuarry.tileentity.TileMachineBase;
import net.minecraft.item.ItemStack;

/**
 * Created by ManEXpen on 2016/07/25.
 */
public class InvUtil {

    public static void setInvItem(final ItemStack addItem, TileMachineBase tile) {


        for (int i = 0; i < tile.itemStacks.length; i++) {
            try {
                ItemStack stack = tile.itemStacks[i];

                //まず最初に指されている配列がnullか？とインスタンスが同じかを検証
                if (addItem != null && stack != null && stack.getItem().getClass() == addItem.getItem().getClass() &&
                        addItem.getItemDamage() == stack.getItemDamage()) {

                    //格納されているスタックサイズが64なら次の要素へ
                    if (64 == tile.itemStacks[i].stackSize) continue;

                    //現在の数と加えたとき64を溢れないかチェック
                    if ((stack.stackSize + addItem.stackSize) - 64 <= 0) {
                        stack.stackSize += addItem.stackSize;
                    } else {
                        //溢れたら現在の場所に詰め込めるだけ詰め込んであふれた分はsetInvItemでどこかへ詰め込み
                        int diff = (stack.stackSize + addItem.stackSize) - 64;
                        stack.stackSize += addItem.stackSize - diff;
                        LogHelper.info("recur setInv");
                        setInvItem(new ItemStack(addItem.getItem(), diff, addItem.getItem().getDamage(addItem)), tile);
                    }
                    tile.markDirty();
                    return;

                    //要素がnullならそこに詰め込み
                } else if (stack == null) {
                    tile.itemStacks[i] = addItem;
                    tile.markDirty();
                    return;
                }
            } catch (NullPointerException e) {
                LogHelper.warn("ぬるぽ: setInvItem");
            }
        }
    }
}
