package mod.syconn.starwars.tileentity;

import javafx.geometry.BoundingBox;
import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.init.ModTileEntities;
import mod.syconn.starwars.util.helpers.TriggerHelper;
import net.minecraft.command.impl.TellRawCommand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class TileEntityBomb extends TileEntity implements ITickableTileEntity {

    private int countdown = 100;
    private boolean startCountdown = false;
    private PlayerEntity player;

    public TileEntityBomb() {
        super(ModTileEntities.BOMB);
    }

    @Override
    public void tick() {
        if (!world.isRemote && pos != null) {

            if (startCountdown == true) {

                if (countdown != 0) {
                    world.setBlockState(pos, getBlockState().with(Bomb.FLASH, !getBlockState().get(Bomb.FLASH)),3);
                    System.out.println("Flashing: " + getBlockState().get(Bomb.FLASH));
                    List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(pos).expand(5, 1, 5));

                    for (PlayerEntity player : players){
                        if (countdown <= 10) {
                            player.sendMessage(new TranslationTextComponent("Bomb Blows in " + countdown));
                        }
                    }


                }

                if (countdown == 0) {
                    TriggerHelper.createExplosion(world, pos);
                }

                countdown--;
            }
        }
    }

    public void start(PlayerEntity player){
        startCountdown = true;
        this.player = player;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.countdown = compound.getInt("countdown");
        this.startCountdown = compound.getBoolean("start");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("countdown", countdown);
        compound.putBoolean("start", startCountdown);
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.read(tag);
    }

    @Override
    public CompoundNBT getTileData() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }
}
