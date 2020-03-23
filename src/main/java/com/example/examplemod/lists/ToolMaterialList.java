package com.example.examplemod.lists;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

/**
 * List of materials to make items with.
 */
public enum ToolMaterialList implements IItemTier {

    THAT_GOOD_STUFF(10.0f, 9.0f, 800, 3, 25, ItemList.bomb_item);

    int mMaxUses, mHarvestLvl, mEnchantability;
    float mEfficiency, mAttackDamage;
    Item mRepairMaterial;

    // Enchantability is how possible it is for the item to be improved through enchanting it
    // Harvest level determines what kind of blocks you can break with this material.
    //   Wood/Gold = 0 (?), Stone = 1, Iron = 2, Diamond = 3
    ToolMaterialList(float attackDamage, float efficiency, int maxUses, int harvestLvl,
                             int enchantability, Item repairMaterial)
    {
        this.mMaxUses = maxUses;
        this.mHarvestLvl = harvestLvl;
        this.mEnchantability = enchantability;
        this.mEfficiency = efficiency;
        this.mAttackDamage = attackDamage;
        this.mRepairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return mMaxUses;
    }

    @Override
    public float getEfficiency() {
        return mEfficiency;
    }

    @Override
    public float getAttackDamage() {
        return mAttackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return mHarvestLvl;
    }

    @Override
    public int getEnchantability() {
        return mEnchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(mRepairMaterial);
    }
}
