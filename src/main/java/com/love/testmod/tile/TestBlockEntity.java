package com.love.testmod.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.love.testmod.tile.ModBlockEntityTypes.TEST_BLOCK_ENTITY;

public class TestBlockEntity extends BlockEntity {

    public static Vec3 getPos() {
        return pos;
    }

    public static Vec3 pos = new Vec3(0, 0, 0);

    public TestBlockEntity(BlockPos pos, BlockState state) {
        super(TEST_BLOCK_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof TestBlockEntity tile){
            for(Entity entity : tile.getCaptureEntities()){
                if(entity instanceof Player){
                    pos = entity.getPosition(1);
                }
            }
        }
    }

    public List<Entity> getCaptureEntities() {
        return getLevel().getEntitiesOfClass(
                Entity.class,
                getAABBWithModifiers(), // 动态计算捕捉范围
                EntitySelector.ENTITY_STILL_ALIVE
        );
    }

    public AABB getAABBWithModifiers() {
        double x = getBlockPos().getX() + 0.5D;
        double y = getBlockPos().getY() + 0.5D;
        double z = getBlockPos().getZ() + 0.5D;
        return new AABB(
                x - 6.5D,
                y - 6.5D,
                z - 6.5D,
                x + 6.5D,
                y + 6.5D,
                z + 6.5D
        );
    }

   // 在这里从传递的 CompoundTag 读取值。
    /*@Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("targetPos ", Tag.TAG_LIST)) {
            ListTag list = tag.getList("targetPos ", Tag.TAG_DOUBLE);
            pos = new double[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pos[i] = list.getDouble(i);
            }
        }
    }

    // 在这里将值保存到传递的 CompoundTag 中。
    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        ListTag list = new ListTag();
        for (double value : pos) {
            list.add(DoubleTag.valueOf(value));
        }
        System.out.println(1);
        tag.put("targetPos ", list);
    }*/

    // 在这里创建一个更新标签。对于只有几个字段的方块实体，这可以只调用 #saveAdditional。
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // 在这里处理接收到的更新标签。默认实现在这里调用 #loadAdditional，
    // 因此如果你不打算做任何超出此范围的事情，则不需要重写此方法。
    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.handleUpdateTag(tag, registries);
    }

    // 在这里返回我们的数据包。此方法返回非空结果告诉游戏使用此数据包进行同步。
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // 数据包使用 #getUpdateTag 返回的 CompoundTag。存在 #create 的替代重载
        // 允许你指定自定义更新标签，包括省略客户端可能不需要的数据的能力。
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // 可选：在接收到数据包时运行一些自定义逻辑。
    // super/默认实现转发到 #loadAdditional。
    @Override
    public void onDataPacket(@NotNull Connection connection, @NotNull ClientboundBlockEntityDataPacket packet, HolderLookup.@NotNull Provider registries) {
        super.onDataPacket(connection, packet, registries);
        // 在这里执行你需要的任何操作。
    }
}
