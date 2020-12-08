package com.mrcrayfish.vehicle.crafting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.entity.trailer.*;
import com.mrcrayfish.vehicle.entity.vehicle.*;
import com.mrcrayfish.vehicle.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: MrCrayfish
 */
public class VehicleRecipes
{
    public static final ImmutableMap<Class<? extends EntityVehicle>, VehicleRecipe> RECIPES;

    static
    {
        ImmutableMap.Builder<Class<? extends EntityVehicle>, VehicleRecipe> mapBuilder = ImmutableMap.builder();

        Builder builder;

        /* Aluminum Boat */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 80));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 10));
        mapBuilder.put(EntityAluminumBoat.class, builder.build());

        /* ATV */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 64));
        builder.addMaterial(new ItemStack(Blocks.IRON_BARS, 4));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 4, 15));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 6));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 8));
        mapBuilder.put(EntityATV.class, builder.build());

        /* Bumper Car */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 8));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 8));
        mapBuilder.put(EntityBumperCar.class, builder.build());

        /* Dirt Bike */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 2, EnumDyeColor.GRAY.getMetadata()));
        mapBuilder.put(EntityDirtBike.class, builder.build());

        /* Dune Buggy */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Blocks.CONCRETE, 16, EnumDyeColor.YELLOW.getMetadata()));
        builder.addMaterial(new ItemStack(Blocks.CONCRETE, 8, EnumDyeColor.BLUE.getMetadata()));
        builder.addMaterial(new ItemStack(Blocks.CONCRETE, 4, EnumDyeColor.RED.getMetadata()));
        mapBuilder.put(EntityDuneBuggy.class, builder.build());

        /* Go Kart */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 4));
        mapBuilder.put(EntityGoKart.class, builder.build());

        /* Golf Cart */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 128));
        builder.addMaterial(new ItemStack(Blocks.IRON_BARS, 4));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 8, 0));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 12));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        mapBuilder.put(EntityGolfCart.class, builder.build());

        /* Jet Ski */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 80));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 10));
        mapBuilder.put(EntityJetSki.class, builder.build());

        /* Lawn Mower */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 64));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 4, 15));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 8));
        mapBuilder.put(EntityLawnMower.class, builder.build());

        /* Bici */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 24));
        mapBuilder.put(EntityBici.class, builder.build());

        /* Bici 2 */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 24));
        mapBuilder.put(EntityBici2.class, builder.build());
        
        /* Mini Bike */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 24));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 2, 15));
        mapBuilder.put(EntityMiniBike.class, builder.build());

        /* Mini Bus */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 128));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 5, EnumDyeColor.GRAY.getMetadata()));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 9));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 12));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        mapBuilder.put(EntityMiniBus.class, builder.build());

        /* Bus
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 128));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 5, EnumDyeColor.GRAY.getMetadata()));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 9));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 12));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        mapBuilder.put(EntityBus.class, builder.build());*/

        /* Moped */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(Blocks.IRON_BARS, 2));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 4, 15));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 6));
        mapBuilder.put(EntityMoped.class, builder.build());

        /* Off-Roader */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 150));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 8, 15));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 6));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 12));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 24));
        mapBuilder.put(EntityOffRoader.class, builder.build());

        /* Shopping Cart */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 8));
        builder.addMaterial(new ItemStack(Blocks.IRON_BARS, 4));
        builder.addMaterial(new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        mapBuilder.put(EntityShoppingCart.class, builder.build());

        /* Smart Car */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 80));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 8, 15));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 6));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 8));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        mapBuilder.put(EntitySmartCar.class, builder.build());

        /* Smart Car 2 */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 30));
        mapBuilder.put(EntitySmartCar2.class, builder.build());

        /* Deportive */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 60));
        mapBuilder.put(EntityDeportive.class, builder.build());

        /* Deportive 2*/
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 120));
        mapBuilder.put(EntityDeportive2.class, builder.build());

        /* Deportive 3*/
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 80));
        mapBuilder.put(EntityDeportive3.class, builder.build());

        /* Taxi */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 40));
        mapBuilder.put(EntityTaxi.class, builder.build());

        /* Policial */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 70));
        mapBuilder.put(EntityPolicial.class, builder.build());

        /* Renault */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 70));
        mapBuilder.put(EntityRenault.class, builder.build());

        /* Peugeot */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 70));
        mapBuilder.put(EntityPeugeot.class, builder.build());

        /* Limusina */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 120));
        mapBuilder.put(EntityLimusina.class, builder.build());

        /* Speed Boat */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 80));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 8, 15));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 4));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 10));
        mapBuilder.put(EntitySpeedBoat.class, builder.build());

        /* Sports Plane */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 180));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 16));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 18));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 32));
        mapBuilder.put(EntitySportsPlane.class, builder.build());

        /* Sports Plane 2 */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 180));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 16));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 18));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 32));
        mapBuilder.put(EntitySportsPlane2.class, builder.build());

        /* Tractor */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 128));
        builder.addMaterial(new ItemStack(Blocks.WOOL, 4, 15));
        builder.addMaterial(new ItemStack(Items.REDSTONE, 8));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        mapBuilder.put(EntityTractor.class, builder.build());

        /* Vehicle Trailer */
        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 48));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 2));
        mapBuilder.put(EntityVehicleTrailer.class, builder.build());

        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 2));
        builder.addMaterial(new ItemStack(Blocks.CHEST));
        mapBuilder.put(EntityStorageTrailer.class, builder.build());

        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 42));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 8));
        mapBuilder.put(EntitySeederTrailer.class, builder.build());

        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 36));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 8));
        mapBuilder.put(EntityFertilizerTrailer.class, builder.build());

        builder = new Builder();
        builder.addMaterial(new ItemStack(Items.IRON_INGOT, 48));
        builder.addMaterial(new ItemStack(ModItems.PANEL, 16));
        builder.addMaterial(new ItemStack(Blocks.GLASS_PANE, 2));
        mapBuilder.put(EntityFluidTrailer.class, builder.build());

        if(Loader.isModLoaded("cfm"))
        {
            /* Bath */
            builder = new Builder();
            builder.addMaterial(new ItemStack(Item.getByNameOrId("cfm:bath_bottom"), 1));
            builder.addMaterial(new ItemStack(Items.NETHER_STAR, 1));
            mapBuilder.put(EntityBath.class, builder.build());

            /* Couch */
            builder = new Builder();
            builder.addMaterial(new ItemStack(Item.getByNameOrId("cfm:couch_jeb"), 1));
            builder.addMaterial(new ItemStack(Items.IRON_INGOT, 8));
            mapBuilder.put(EntityCouch.class, builder.build());

            /* Sofacopter */
            builder = new Builder();
            builder.addMaterial(new ItemStack(Item.getByNameOrId("cfm:couch"), 1, 14));
            builder.addMaterial(new ItemStack(Item.getByNameOrId("cfm:ceiling_fan")));
            builder.addMaterial(new ItemStack(Items.IRON_INGOT, 16));
            mapBuilder.put(EntitySofacopter.class, builder.build());
        }

        RECIPES = mapBuilder.build();
    }

    @Nullable
    public static VehicleRecipe getRecipe(Class<? extends Entity> clazz)
    {
        return RECIPES.get(clazz);
    }

    public static int getVehicleCount()
    {
        return RECIPES.size();
    }

    public static List<Class<? extends EntityVehicle>> getVehicleClasses()
    {
        return ImmutableList.copyOf(RECIPES.keySet());
    }

    public static class VehicleRecipe
    {
        private final ImmutableList<ItemStack> materials;

        public VehicleRecipe(Set<ItemStack> materialSet)
        {
            materials = ImmutableList.copyOf(materialSet);
        }

        public ImmutableList<ItemStack> getMaterials()
        {
            return materials;
        }
    }

    public static class Builder
    {
        private Set<ItemStack> materials = new LinkedHashSet<>();

        public void addMaterial(ItemStack stack)
        {
            materials.add(stack);
        }

        public VehicleRecipe build()
        {
            return new VehicleRecipe(materials);
        }
    }
}
