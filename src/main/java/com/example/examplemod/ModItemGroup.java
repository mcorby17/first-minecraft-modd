package com.example.examplemod;

import com.example.examplemod.lists.BlockList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup extends ItemGroup {

    public ModItemGroup() {
        super("mods");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Item.BLOCK_TO_ITEM.get(BlockList.cow_block));
    }
}
