package com.love.testmod.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.love.testmod.tile.ModBlockEntityTypes.TEST_BLOCK_ENTITY;

public class TestBlockEntity extends BlockEntity {

    public static Vec3 getPos() {
        return pos;
    }

    private static Vec3 pos = new Vec3(0, 0, 0);

    private static void setState(int state){
        TestBlockEntity.state = state;
    }

    public static int getState() {
        return state;
    }

    private static int state = 0; // 0：待机 1：攻击 2：冷却

    private static int getTimer() {
        return timer;
    }

    private static void setTimer(int timer) {
        TestBlockEntity.timer = timer;
    }

    private static void addTimer(){
        TestBlockEntity.timer++;
    }

    private static int timer = 0; // 内部计时器

    public static float getFulling() {
        return fulling;
    }

    private static void setFulling(float fulling) {
        TestBlockEntity.fulling = fulling;
    }

    private static float fulling = 0; // 拉弓进度

    public TestBlockEntity(BlockPos pos, BlockState state) {
        super(TEST_BLOCK_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof TestBlockEntity tile){
            for(Entity entity : tile.getCaptureEntities()){
                if(entity instanceof Player){
                    pos = entity.getPosition(1);

                    if(state == 0) state++; // 转换状态

                    /*switch (state){
                        case 1: // 攻击
                            addTimer();
                            setFulling((float) getTimer() / 20);
                            break;

                        case 2: // 冷却

                            break;
                    }*/
                    System.out.println(entity);
                }
            }
        }
    }

    public List<Entity> getCaptureEntities() {
        return getLevel().getEntitiesOfClass(
                Entity.class,
                getAABBWithModifiers(),
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
}
