package com.love.testmod.tile;

import com.love.testmod.TestMod;
import com.love.testmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TestMod.MODID);

    public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, Supplier<BlockEntityType.Builder<T>> builder) {
        return BLOCK_ENTITY_TYPES.register(name, () -> builder.get().build(null));
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TestBlockEntity>> TEST_BLOCK_ENTITY = register(
            "test_block_entity", () -> BlockEntityType.Builder.of(TestBlockEntity::new, ModBlocks.TEST_BLOCK.get())
    );
}
