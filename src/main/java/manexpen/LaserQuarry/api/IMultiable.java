package manexpen.LaserQuarry.api;

/**
 * Created by ManEXpen on 2016/07/24.
 */
public interface IMultiable {
    int getStackSize();

    void setDispStackSize(int stackSize);

    double getProgress();

    double calcProgress(double progress);

    void setEnergy(int energy);
}
