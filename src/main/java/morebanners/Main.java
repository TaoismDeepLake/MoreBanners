package morebanners;

import morebanners.items.ModBanner;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.BuffRegistry;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.buffs.staticBuffs.SimplePotionBuff;
import necesse.inventory.item.Item;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

@ModEntry
public class Main {

    public static final String BANNEROFSPEED = "mbanner_bannerofspeed";
    public static final String BANNEROFDAMAGE = "mbanner_bannerofdamage";
    public static final String MBANNER_BANNEROFDEFENSE = "mbanner_bannerofdefense";

    public void init() {
        System.out.println("Registering more banners.");

        Buff test = BuffRegistry.registerBuff("test", new SimplePotionBuff(new ModifierValue[]{new ModifierValue(BuffModifiers.ARMOR_FLAT, 8)}));

        ItemRegistry.registerItem(BANNEROFSPEED,
                new ModBanner(Item.Rarity.UNCOMMON, 480, m -> BuffRegistry.Banners.SPEED, true), 250, true);
        ItemRegistry.registerItem(BANNEROFDAMAGE,
                new ModBanner(Item.Rarity.UNCOMMON, 480, m -> BuffRegistry.Banners.DAMAGE, true), 250, true);
//        ItemRegistry.registerItem(MBANNER_BANNEROFDEFENSE,
//                new ModBanner(Item.Rarity.UNCOMMON, 480, m -> BuffRegistry.Banners.DEFENSE, true), 250, true);
        ItemRegistry.registerItem(MBANNER_BANNEROFDEFENSE,
                new ModBanner(Item.Rarity.UNCOMMON, 480, m -> test, true), 250, true);
        //does not really seems to work
    }

    public void initResources() {
        // Sometimes your textures will have a black or other outline unintended under rotation or scaling
        // This is caused by alpha blending between transparent pixels and the edge
        // To fix this, run the preAntialiasTextures gradle task
        // It will process your textures and save them again with a fixed alpha edge color

    }

    public void postInit() {
        String GOLDBAR = "goldbar";
        Recipes.registerModRecipe(new Recipe(
                BANNEROFSPEED,
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("bannerofspeed", 1),
                        new Ingredient(GOLDBAR, 1),
                }
        ).showAfter("bannerofspeed"));

        Recipes.registerModRecipe(new Recipe(
                BANNEROFDAMAGE,
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("bannerofdamage", 1),
                        new Ingredient(GOLDBAR, 1),
                }
        ).showAfter("bannerofdamage"));

        Recipes.registerModRecipe(new Recipe(
                MBANNER_BANNEROFDEFENSE,
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                        new Ingredient("bannerofdefense", 1),
                        new Ingredient(GOLDBAR, 1),
                }
        ).showAfter("bannerofdefense"));
    }

}
