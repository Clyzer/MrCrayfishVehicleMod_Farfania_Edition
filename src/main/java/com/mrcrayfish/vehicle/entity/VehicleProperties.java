package com.mrcrayfish.vehicle.entity;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.vehicle.VehicleConfig;
import com.mrcrayfish.vehicle.client.render.Wheel;
import com.mrcrayfish.vehicle.common.Seat;
import com.mrcrayfish.vehicle.common.entity.PartPosition;
import com.mrcrayfish.vehicle.entity.trailer.*;
import com.mrcrayfish.vehicle.entity.vehicle.*;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class VehicleProperties
{
    private static final Map<Class<? extends EntityVehicle>, VehicleProperties> PROPERTIES_MAP = new HashMap<>();

    public static void setProperties(Class<? extends EntityVehicle> clazz, VehicleProperties properties)
    {
        if(!PROPERTIES_MAP.containsKey(clazz) || VehicleConfig.CLIENT.debug.reloadVehiclePropertiesEachTick)
        {
            PROPERTIES_MAP.put(clazz, properties);
        }
    }

    public static VehicleProperties getProperties(Class<? extends EntityVehicle> clazz)
    {
        return PROPERTIES_MAP.get(clazz);
    }

    public static void register()
    {
        VehicleProperties properties;

        /* Aluminum Boat */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.0, 0.2, 1.1));
        properties.setFuelPortPosition(new PartPosition(-16.0, 3, -18, 0.0, -90.0, 0.0, 0.25));
        properties.setHeldOffset(new Vec3d(0.0, 0.0, 0.0));
        properties.setDisplayPosition(new PartPosition(1.0F));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.addSeat(new Seat(new Vec3d(-7, 8, -15), true));
        properties.addSeat(new Seat(new Vec3d(7, 6, -15)));
        properties.addSeat(new Seat(new Vec3d(-7, 6, 3)));
        properties.addSeat(new Seat(new Vec3d(7, 6, 3)));
        VehicleProperties.setProperties(EntityAluminumBoat.class, properties);

        /* ATV */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.5F);
        properties.setWheelOffset(4.375F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(0, 6.55, 5.0, -90, 0, 0, 0.35));
        properties.setKeyPortPosition(new PartPosition(-5, 4.5, 6.5, -45, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(4.0, 3.5, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -20.8));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.5F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 4.0F, 10.5F, 1.85F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 4.0F, 10.5F, 1.85F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 4.0F, -10.5F, 1.85F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 4.0F, -10.5F, 1.85F, true, true);
        properties.setFrontAxelVec(0, 10.5);
        properties.setRearAxelVec(0, -10.5);
        properties.addSeat(new Seat(new Vec3d(0, 5, -3), true));
        properties.addSeat(new Seat(new Vec3d(0, 5.5, -12)));
        VehicleProperties.setProperties(EntityATV.class, properties);

        /* Bumper Car */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.5F);
        properties.setWheelOffset(1.5F);
        properties.setBodyPosition(new PartPosition(1.2));
        properties.setFuelPortPosition(new PartPosition(-8.0, 6, -8.0, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(6.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.5F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 7.0F, 8.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 7.0F, 8.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 7.0F, -8.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 7.0F, -8.5F, 0.75F, false, true);
        properties.setFrontAxelVec(0, 8.5);
        properties.setRearAxelVec(0, -8.5);
        properties.addSeat(new Seat(new Vec3d(0, 1, -6), true));
        VehicleProperties.setProperties(EntityBumperCar.class, properties);

        /* Dirt Bike */
        properties = new VehicleProperties();
        properties.setAxleOffset(0.0F);
        properties.setWheelOffset(5.625F);
        properties.setBodyPosition(new PartPosition(1.0));
        properties.setEnginePosition(new PartPosition(0, 1, 0, 0, 180, 0, 0.85));
        properties.setFuelPortPosition(new PartPosition(1, 12.8, 3, 67.5, 180, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(-1, 12.8, 3, -22.5, 0, 0, 0.25));
        properties.setKeyPosition(new PartPosition(0, 0, 0, 0, 0, 0, 0.05));
        properties.setHeldOffset(new Vec3d(0.0, 3.5, 0.0));
        properties.setDisplayPosition(new PartPosition(1.4F));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.FRONT, 0.0F, 0.0F, 14.08F, 1.5F, 2.25F, 2.25F, false, false);
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.REAR, 0.0F, 0.0F, -11.61F, 1.5F, 2.25F, 2.25F, true, true);
        properties.setFrontAxelVec(0, 14.08);
        properties.setRearAxelVec(0, -11.61);
        properties.addSeat(new Seat(new Vec3d(0, 8, -2), true));
        properties.addSeat(new Seat(new Vec3d(0, 9, -9)));
        VehicleProperties.setProperties(EntityDirtBike.class, properties);

        /* Dune Buggy */
        properties = new VehicleProperties();
        properties.setAxleOffset(-2.3F);
        properties.setWheelOffset(2.5F);
        properties.setBodyPosition(new PartPosition(1.3));
        properties.setFuelPortPosition(new PartPosition(0, 3, -7.0, 0, 180, 0, 0.25));
        properties.setHeldOffset(new Vec3d(2.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.75F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 2.4F, -5.7F, 1.0F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 2.4F, -5.7F, 1.0F, true, true);
        properties.setFrontAxelVec(0, 5.3);
        properties.setRearAxelVec(0, -5.7);
        properties.addSeat(new Seat(new Vec3d(0, 2, -3), true));
        VehicleProperties.setProperties(EntityDuneBuggy.class, properties);

        /* Go Kart */
        properties = new VehicleProperties();
        properties.setAxleOffset(-2.5F);
        properties.setWheelOffset(3.45F);
        properties.setBodyPosition(new PartPosition(1.0));
        properties.setEnginePosition(new PartPosition(0, 2, -9, 0, 180, 0, 1.2));
        properties.setHeldOffset(new Vec3d(3.0D, 0.5D, 0.0D));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setTrailerOffset(new Vec3d(0D, -0.03125D, -0.375D));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 9.0F, 13.5F, 1.4F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 9.0F, 13.5F, 1.4F, false, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 9.0F, -8.5F, 1.4F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 9.0F, -8.5F, 1.4F, true, true);
        properties.setFrontAxelVec(0, 13.5);
        properties.setRearAxelVec(0, -8.5);
        properties.addSeat(new Seat(new Vec3d(0, -2, -1), true));
        VehicleProperties.setProperties(EntityGoKart.class, properties);

        /* Golf Cart */
        properties = new VehicleProperties();
        properties.setAxleOffset(-0.5F);
        properties.setWheelOffset(4.45F);
        properties.setBodyPosition(new PartPosition(1.15));
        properties.setFuelPortPosition(new PartPosition(-13, 3.5, -6, 0, -90, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(-8.5, 2.75, 8.5, -67.5, 0, 0, 0.5));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setHeldOffset(new Vec3d(1.5D, 2.5D, 0.0D));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.25F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 9.0F, 16.0F, 1.75F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 9.0F, 16.0F, 1.75F, false, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 9.0F, -12.5F, 1.75F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 9.0F, -12.5F, 1.75F, true, true);
        properties.setFrontAxelVec(0, 16.0);
        properties.setRearAxelVec(0, -12.5);
        properties.addSeat(new Seat(new Vec3d(5.5, 5, -6), true));
        properties.addSeat(new Seat(new Vec3d(-5.5, 5, -6)));
        properties.addSeat(new Seat(new Vec3d(5.5, 5, -15), 180F));
        properties.addSeat(new Seat(new Vec3d(-5.5, 5, -15), 180F));
        VehicleProperties.setProperties(EntityGolfCart.class, properties);

        /* Jet Ski */
        properties = new VehicleProperties();
        properties.setWheelOffset(2.75F);
        properties.setBodyPosition(new PartPosition(0.0, 0.0, 0.25, 0.0, 0.0, 0.0, 1.25));
        properties.setFuelPortPosition(new PartPosition(0.0, 9.25, 8.5, -90, 0, 0, 0.35));
        properties.setHeldOffset(new Vec3d(6.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.45F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addSeat(new Seat(new Vec3d(0, 5, 0), true));
        properties.addSeat(new Seat(new Vec3d(0, 5, -7)));
        VehicleProperties.setProperties(EntityJetSki.class, properties);

        /* Lawn Mower */
        properties = new VehicleProperties();
        properties.setAxleOffset(-2.0F);
        properties.setWheelOffset(2.85F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-4.50, 9.5, 4.0, 0, -90, 0, 0.2));
        properties.setKeyPortPosition(new PartPosition(-5, 4.5, 6.5, -45, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(12.0, -1.5, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -20.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.5F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 6.0F, 0.0F, 13.5F, 1.15F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 6.0F, 0.0F, 13.5F, 1.15F, false, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 5.0F, 0.8F, -10.7F, 1.55F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 5.0F, 0.8F, -10.7F, 1.55F, true, true);
        properties.setFrontAxelVec(0, 13.5);
        properties.setRearAxelVec(0, -10.7);
        properties.addSeat(new Seat(new Vec3d(0, 7, -9), true));
        VehicleProperties.setProperties(EntityLawnMower.class, properties);

        /* Bici */
        properties = new VehicleProperties();
        properties.setAxleOffset(-0.5F);
        properties.setWheelOffset(5.5F);
        properties.setBodyPosition(new PartPosition(1.05));
        properties.setEnginePosition(new PartPosition(0, 1, 2.5, 0, 180F, 0, 1.0));
        properties.setHeldOffset(new Vec3d(6.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.REAR, 0.0F, -12.4F, 2.3F, true, true);
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.FRONT, 0.0F, -2.5F + 1.7F * 0.0625F, 13.0F, 2.1F, true, false);
        properties.setFrontAxelVec(0, 13);
        properties.setRearAxelVec(0, -6.7);
        properties.addSeat(new Seat(new Vec3d(0, 7, -2), true));
        VehicleProperties.setProperties(EntityBici.class, properties);

        /* Bici 2 */
        properties = new VehicleProperties();
        properties.setAxleOffset(-0.5F);
        properties.setWheelOffset(5.5F);
        properties.setBodyPosition(new PartPosition(1.05));
        properties.setEnginePosition(new PartPosition(0, 1, 2.5, 0, 180F, 0, 1.0));
        properties.setHeldOffset(new Vec3d(6.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.REAR, 0.0F, -12.4F, 2.3F, true, true);
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.FRONT, 0.0F, -2.5F + 1.7F * 0.0625F, 13.0F, 2.1F, true, false);
        properties.setFrontAxelVec(0, 13);
        properties.setRearAxelVec(0, -6.7);
        properties.addSeat(new Seat(new Vec3d(0, 7, -2), true));
        properties.addSeat(new Seat(new Vec3d(0, 2, -11)));
        VehicleProperties.setProperties(EntityBici2.class, properties);

        /* Mini Bike */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(4.0F);
        properties.setBodyPosition(new PartPosition(1.05));
        properties.setEnginePosition(new PartPosition(0, 1, 2.5, 0, 180F, 0, 1.0));
        properties.setHeldOffset(new Vec3d(6.0, 0.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.REAR, 0.0F, -6.7F, 1.65F, true, true);
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.FRONT, 0.0F, -0.5F + 1.7F * 0.0625F, 13.0F, 1.65F, true, false);
        properties.setFrontAxelVec(0, 13);
        properties.setRearAxelVec(0, -6.7);
        properties.addSeat(new Seat(new Vec3d(0, 7, -2), true));
        VehicleProperties.setProperties(EntityMiniBike.class, properties);

        /* Mini Bus */
        properties = new VehicleProperties();
        properties.setAxleOffset(1.0F);
        properties.setWheelOffset(4.5F);
        properties.setBodyPosition(new PartPosition(1.3));
        properties.setFuelPortPosition(new PartPosition(-12.0, 8.0, -8.75, 0, -90, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(0, 6.75, 19.5, -67.5, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(0.0, 3.5, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -33.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.0F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 9.0F, 0.0F, 13.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 9.0F, 0.0F, 13.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 9.0F, 0.0F, -13.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 9.0F, 0.0F, -13.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.setFrontAxelVec(0, 14.5);
        properties.setRearAxelVec(0, -14.5);
        properties.addSeat(new Seat(new Vec3d(4.5, 2, 11), true));
        properties.addSeat(new Seat(new Vec3d(-4.5, 2, 11)));
        properties.addSeat(new Seat(new Vec3d(4.5, 2, -3)));
        properties.addSeat(new Seat(new Vec3d(-4.5, 2, -3)));
        properties.addSeat(new Seat(new Vec3d(4.5, 2, -15)));
        VehicleProperties.setProperties(EntityMiniBus.class, properties);

        /* Bus */
        properties = new VehicleProperties();
        properties.setAxleOffset(1.0F);
        properties.setWheelOffset(4.5F);
        properties.setBodyPosition(new PartPosition(1.3));
        properties.setFuelPortPosition(new PartPosition(-12.0, 8.0, -8.75, 0, -90, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(0, 6.75, 19.5, -67.5, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(0.0, 3.5, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -33.0));
        properties.setDisplayPosition(new PartPosition(1.0F));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 9.0F, 0.0F, 18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 9.0F, 0.0F, 18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 9.0F, 0.0F, -18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 9.0F, 0.0F, -18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 9.0F, 0.0F, -18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 9.0F, 0.0F, -18.5F, 1.5F, 1.9F, 1.9F, true, true);
        properties.setFrontAxelVec(0, 14.5);
        properties.setRearAxelVec(0, -14.5);
        properties.addSeat(new Seat(new Vec3d(4.5, 2, 11), true));
        properties.addSeat(new Seat(new Vec3d(-4.5, 2, 11)));
        properties.addSeat(new Seat(new Vec3d(4.5, 2, -3)));
        properties.addSeat(new Seat(new Vec3d(-4.5, 2, -3)));
        properties.addSeat(new Seat(new Vec3d(4.5, 2, -15)));
        VehicleProperties.setProperties(EntityBus.class, properties);

        /* Moped */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.0F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.2));
        properties.setFuelPortPosition(new PartPosition(-2.5, 4.2, -2.5, 0, -90, 0, 0.2));
        properties.setKeyPortPosition(new PartPosition(0, 10.8, 8.2, -112.5, 0, 0, 0.25));
        properties.setKeyPosition(new PartPosition(0, 0, 0, 0, 0, 0, 0.05));
        properties.setHeldOffset(new Vec3d(7.0, 2.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 1.5F));
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.REAR, 0.0F, -6.7F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.NONE, Wheel.Position.FRONT, 0.0F, -0.4F, 14.5F, 1.3F, true, false);
        properties.setFrontAxelVec(0, 14.5);
        properties.setRearAxelVec(0, -6.7);
        properties.addSeat(new Seat(new Vec3d(0, 4, -2), true));
        VehicleProperties.setProperties(EntityMoped.class, properties);

        /* Off Roader */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.0F);
        properties.setWheelOffset(5.4F);
        properties.setBodyPosition(new PartPosition(1.4));
        properties.setFuelPortPosition(new PartPosition(-12.0, 8.5, -6.5, 0, -90, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(0, 7, 6.2, -67.5, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(0.0, 3.5, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F, 1.0F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 10.0F, 14.5F, 2.25F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 10.0F, 14.5F, 2.25F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 10.0F, -14.5F, 2.25F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 10.0F, -14.5F, 2.25F, true, true);
        properties.setFrontAxelVec(0, 14.5);
        properties.setRearAxelVec(0, -14.5);
        properties.addSeat(new Seat(new Vec3d(5, 4, -3), true));
        properties.addSeat(new Seat(new Vec3d(-5, 4, -3)));
        properties.addSeat(new Seat(new Vec3d(5, 11.5, -14.5)));
        properties.addSeat(new Seat(new Vec3d(-5, 3.5, -18.9)));
        VehicleProperties.setProperties(EntityOffRoader.class, properties);

        /* Shopping Cart */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.0F);
        properties.setWheelOffset(2.0F);
        properties.setBodyPosition(new PartPosition(1.05));
        properties.setHeldOffset(new Vec3d(4.0, 9.25, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.45F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.NONE, 5.75F, -10.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.NONE, 5.75F, -10.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 4.0F, 9.5F, 0.75F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 4.0F, 9.5F, 0.75F, false, true);
        properties.setFrontAxelVec(0, 9.5);
        properties.setRearAxelVec(0, -10.5);
        properties.addSeat(new Seat(new Vec3d(0, 7, -4), true));
        VehicleProperties.setProperties(EntityShoppingCart.class, properties);

        /* Smart Car */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-9.0, 8.7, -12.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -24.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 7F, 12F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 7F, 12F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 7F, -12F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 7F, -12F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(0, 0.5, -2), true));
        VehicleProperties.setProperties(EntitySmartCar.class, properties);

        /* Smart Car 2 */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-9.0, 8.7, -15.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setKeyPortPosition(new PartPosition(5.5, 6.5, 8, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 7F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 7F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 7F, -18F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 7F, -18F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(0, 0.5, 2), true));
        properties.addSeat(new Seat(new Vec3d(0, 0.5, -8)));
        VehicleProperties.setProperties(EntitySmartCar2.class, properties);

        /* Deportive */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-13.0, 8.7, -15.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setKeyPortPosition(new PartPosition(10, 6.5, 8, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, 2), true));
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, -8)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, 2)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, -8)));
        VehicleProperties.setProperties(EntityDeportive.class, properties);

        /* Deportive 2 */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-13.0, 8.7, -15.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setKeyPortPosition(new PartPosition(10, 6.5, 8, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, 2), true));
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, -8)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, 2)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, -8)));
        VehicleProperties.setProperties(EntityDeportive2.class, properties);

        /* Deportive 3 */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setEnginePosition(new PartPosition(0, 9.5, 10, 0, 0, 0, 0.65));
        properties.setFuelPortPosition(new PartPosition(-13.0, 8.7, -15.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setKeyPortPosition(new PartPosition(10, 6.5, -2, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, -8), true));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, -8)));
        VehicleProperties.setProperties(EntityDeportive3.class, properties);

        /* Taxi */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(3.5F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-13.0, 8.7, -15.3, 0, -90, 0, 0.25));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setKeyPortPosition(new PartPosition(10, 6.5, 8, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 11F, 17F, 1.5F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 11F, -18F, 1.5F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, 2), true));
        properties.addSeat(new Seat(new Vec3d(-4.5, 0.5, -8)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, 2)));
        properties.addSeat(new Seat(new Vec3d(5.5, 0.5, -8)));
        VehicleProperties.setProperties(EntityTaxi.class, properties);

        /* Policial */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(4.8F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-15.4, 9.3, -23, 0, -90, 0, 0.25));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setKeyPortPosition(new PartPosition(1.9, 7.5, 6.8, -67.5, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 14F, 19.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 14F, 19.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-8, 1, -2), true));
        properties.addSeat(new Seat(new Vec3d(-8, 1, -16.5)));
        properties.addSeat(new Seat(new Vec3d(9, 1, -2)));
        properties.addSeat(new Seat(new Vec3d(9, 1, -16.5)));
        VehicleProperties.setProperties(EntityPolicial.class, properties);

        /* Peugeot */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(4.8F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-15.4, 9.3, -23, 0, -90, 0, 0.25));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setKeyPortPosition(new PartPosition(1.9, 7.5, 6.8, -67.5, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 14F, 19.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 14F, 19.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-8, 1, -2), true));
        properties.addSeat(new Seat(new Vec3d(-8, 1, -16.5)));
        properties.addSeat(new Seat(new Vec3d(9, 1, -2)));
        properties.addSeat(new Seat(new Vec3d(9, 1, -16.5)));
        VehicleProperties.setProperties(EntityPeugeot.class, properties);

        /* Renault */
        properties = new VehicleProperties();
        properties.setAxleOffset(-1.7F);
        properties.setWheelOffset(4.8F);
        properties.setBodyPosition(new PartPosition(1.25));
        properties.setFuelPortPosition(new PartPosition(-16.2, 9.9, -23, 0, -90, 0, 0.25));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setKeyPortPosition(new PartPosition(1.9, 7.5, 14.5, -90, 0, 0, 0.5));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 14F, 25.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 14F, 25.5F, 1.8F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 14F, -22.5F, 1.8F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-9, 1, 3), true));
        properties.addSeat(new Seat(new Vec3d(-9, 1, -10.5)));
        properties.addSeat(new Seat(new Vec3d(8, 1, 3)));
        properties.addSeat(new Seat(new Vec3d(8, 1, -10.5)));
        VehicleProperties.setProperties(EntityRenault.class, properties);

        /* Limusina */
        properties = new VehicleProperties();
        properties.setAxleOffset(0F);
        properties.setWheelOffset(1F);
        properties.setBodyPosition(new PartPosition(4));
        properties.setFuelPortPosition(new PartPosition(-5.5, 3.2, -20.5, 0, -90, 0, 0.08));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setHeldOffset(new Vec3d(3.0, 1.0, 0.0));
        properties.setKeyPortPosition(new PartPosition(1.1, 2.25, 5.2, -67.5, 0, 0, 0.20));
        properties.setKeyPosition(new PartPosition(1.1, -0.10, 5.2, 25.5, 0, 0, 0.05));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -31.5));
        properties.setDisplayPosition(new PartPosition(1.35F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 5F, 9.8F, 0.6F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 5F, 9.8F, 0.6F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 5F, -20F, 0.6F, false, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 5F, -20F, 0.6F, false, true);
        properties.setFrontAxelVec(0, 12);
        properties.setRearAxelVec(0, -12);
        properties.addSeat(new Seat(new Vec3d(-3, 1.4, 1.7), true));
        properties.addSeat(new Seat(new Vec3d(3, 1.4, 1.7)));
        properties.addSeat(new Seat(new Vec3d(-3, 1.4, -2.8)));
        properties.addSeat(new Seat(new Vec3d(-3, 1.4, -7.8)));
        properties.addSeat(new Seat(new Vec3d(-3, 1.4, -12.8)));
        properties.addSeat(new Seat(new Vec3d(-3, 1.4, -17.5)));
        properties.addSeat(new Seat(new Vec3d(3, 1.4, -17.5)));
        properties.addSeat(new Seat(new Vec3d(0, 1.4, -17.5)));
        VehicleProperties.setProperties(EntityLimusina.class, properties);

        /* Speed Boat */
        properties = new VehicleProperties();
        properties.setWheelOffset(2.5F);
        properties.setBodyPosition(new PartPosition(0.0, -0.03125, 0.6875, 1.0));
        properties.setFuelPortPosition(new PartPosition(0.0, 5.25, -20.5, -90.0, 0.0, 0.0, 0.65));
        properties.setHeldOffset(new Vec3d(6.0, -0.5, 0.0));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.65F, 0.0F, 0.0F, 0.0F, 1.25F));
        properties.addSeat(new Seat(new Vec3d(0, 0, 0), true));
        VehicleProperties.setProperties(EntitySpeedBoat.class, properties);

        /* Sports Plane */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0, 11 * 0.0625, -8 * 0.0625, 0, 0, 0, 1.8));
        properties.setFuelPortPosition(new PartPosition(-4.35, 4, -6, 0, -112.5, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(0, 3.75, 12.5, -67.5, 0, 0, 0.5));
        properties.setDisplayPosition(new PartPosition(0.85F));
        properties.addSeat(new Seat(new Vec3d(0, 6, 0), true));
        VehicleProperties.setProperties(EntitySportsPlane.class, properties);

        /* Sports Plane 2 */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0, 11 * 0.0625, -8 * 0.0625, 0, 0, 0, 1.8));
        properties.setFuelPortPosition(new PartPosition(-5.25, 4, -26, 0, -112.5, 0, 0.25));
        properties.setKeyPortPosition(new PartPosition(0, 3.75, 12.5, -67.5, 0, 0, 0.5));
        properties.setDisplayPosition(new PartPosition(0.85F));
        properties.addSeat(new Seat(new Vec3d(0, 6, 0), true));
        properties.addSeat(new Seat(new Vec3d(0, 6, -8)));
        properties.addSeat(new Seat(new Vec3d(0, 6, -16)));
        VehicleProperties.setProperties(EntitySportsPlane2.class, properties);

        /* Tractor */
        properties = new VehicleProperties();
        properties.setAxleOffset(-3.0F);
        properties.setWheelOffset(5.5F);
        properties.setBodyPosition(new PartPosition(1.0));
        properties.setEnginePosition(new PartPosition(0, 6, 8.775, 0, 0, 0, 0.85));
        properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
        properties.setFuelPortPosition(new PartPosition(-6.0, 9.5, -0.5, 0, -90, 0, 0.3));
        properties.setKeyPortPosition(new PartPosition(-2.75, 12, -1.75, -45.0, 0, 0, 0.5));
        properties.setHeldOffset(new Vec3d(0.0, 3.5, 0.0));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -24.5));
        properties.setDisplayPosition(new PartPosition(1.25F));
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 8.0F, 0.0F, 14.0F, 1.5F, 2.25F, 2.25F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 8.0F, 0.0F, 14.0F, 1.5F, 2.25F, 2.25F, true, true);
        properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 8.0F, 5.5F, -14.5F, 3.0F, 4.5F, 4.5F, true, true);
        properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 8.0F, 5.5F, -14.5F, 3.0F, 4.5F, 4.5F, true, true);
        properties.setFrontAxelVec(0, 14.0);
        properties.setRearAxelVec(0, -14.5);
        properties.addSeat(new Seat(new Vec3d(0, 9, -14), true));
        VehicleProperties.setProperties(EntityTractor.class, properties);

        /* Fertilizer Trailer */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.325, 0.0, 0.0, 0.0, 0.0, 1.1));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.35F));
        VehicleProperties.setProperties(EntityFertilizerTrailer.class, properties);

        /* Fluid Trailer */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.325, 0.0, 0.0, 0.0, 0.0, 1.1));
        properties.setHeldOffset(new Vec3d(0D, 3D, 0D));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.35F));
        VehicleProperties.setProperties(EntityFluidTrailer.class, properties);

        /* Seeder Trailer */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.325, 0.0, 0.0, 0.0, 0.0, 1.1));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.35F));
        VehicleProperties.setProperties(EntitySeederTrailer.class, properties);

        /* Storage Trailer */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.325, 0.0, 0.0, 0.0, 0.0, 1.1));
        properties.setTowBarPosition(new Vec3d(0.0, 0.0, -12.0));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.35F));
        VehicleProperties.setProperties(EntityStorageTrailer.class, properties);

        /* Vehicle Trailer */
        properties = new VehicleProperties();
        properties.setBodyPosition(new PartPosition(0.0, 0.325, 0.0, 0.0, 0.0, 0.0, 2.5));
        properties.setHeldOffset(new Vec3d(0D, 3D, 0D));
        properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 1.35F));
        VehicleProperties.setProperties(EntityVehicleTrailer.class, properties);

        if(Loader.isModLoaded("cfm"))
        {
            /* Bath */
            properties = new VehicleProperties();
            properties.setBodyPosition(new PartPosition(1.0));
            properties.setHeldOffset(new Vec3d(4.0, 3.5, 0.0));
            properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
            properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 1.5F));
            properties.addSeat(new Seat(new Vec3d(0, 0, 0), true));
            VehicleProperties.setProperties(EntityBath.class, properties);

            /* Sofa */
            properties = new VehicleProperties();
            properties.setAxleOffset(-1.5F);
            properties.setWheelOffset(5.0F);
            properties.setBodyPosition(new PartPosition(0, -0.0625, 0.1, 0, 0, 0, 1.0));
            properties.setFuelPortPosition(new PartPosition(0, 2, 8, 0, 0, 0, 0.5));
            properties.setHeldOffset(new Vec3d(2.0, 2.0, 0.0));
            properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
            properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.0F, 1.5F));
            properties.addWheel(Wheel.Side.LEFT, Wheel.Position.FRONT, 8.0F, 0.0625F, 7.0F, 1.75F, false, true);
            properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.FRONT, 8.0F, 0.0625F, 7.0F, 1.75F, false, true);
            properties.addWheel(Wheel.Side.LEFT, Wheel.Position.REAR, 8.0F, 0.0625F, -7.0F, 1.75F, true, true);
            properties.addWheel(Wheel.Side.RIGHT, Wheel.Position.REAR, 8.0F, 0.0625F, -7.0F, 1.75F, true, true);
            properties.setFrontAxelVec(0, 7.0);
            properties.setRearAxelVec(0, -7.0);
            properties.addSeat(new Seat(new Vec3d(0, 5, -1), true));
            VehicleProperties.setProperties(EntityCouch.class, properties);

            /* Sofacopter */
            properties = new VehicleProperties();
            properties.setBodyPosition(new PartPosition(0, 0, 0.0625, 0, 0, 0, 1));
            properties.setFuelPortPosition(new PartPosition(0.0, 1.5, 8.0, 0, 0, 0, 0.45));
            properties.setTrailerOffset(new Vec3d(0.0, 0.0, -1.0));
            properties.setKeyPortPosition(new PartPosition(-9.25, 8, 5, 0, 0, 0, 0.8));
            properties.setDisplayPosition(new PartPosition(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.25F));
            properties.addSeat(new Seat(new Vec3d(0, 5, 0), true));
            VehicleProperties.setProperties(EntitySofacopter.class, properties);
        }
    }

    private float axleOffset;
    private float wheelOffset;
    private Vec3d heldOffset = Vec3d.ZERO;
    private Vec3d towBarVec = Vec3d.ZERO;
    private Vec3d trailerOffset = Vec3d.ZERO;
    private List<Wheel> wheels;
    private PartPosition bodyPosition = PartPosition.DEFAULT;
    private PartPosition enginePosition;
    private PartPosition fuelPortPosition;
    private PartPosition fuelPortLidPosition;
    private PartPosition keyPortPosition;
    private PartPosition keyPosition;
    private PartPosition displayPosition = PartPosition.DEFAULT;
    private Vec3d frontAxelVec = null;
    private Vec3d rearAxelVec = null;
    private List<Seat> seats = new ArrayList<>();

    public void setAxleOffset(float axleOffset)
    {
        this.axleOffset = axleOffset;
    }

    public float getAxleOffset()
    {
        return axleOffset;
    }

    public void setWheelOffset(float wheelOffset)
    {
        this.wheelOffset = wheelOffset;
    }

    public float getWheelOffset()
    {
        return wheelOffset;
    }

    public void setHeldOffset(Vec3d heldOffset)
    {
        this.heldOffset = heldOffset;
    }

    public Vec3d getHeldOffset()
    {
        return heldOffset;
    }

    public void setTowBarPosition(Vec3d towBarVec)
    {
        this.towBarVec = towBarVec;
    }

    public Vec3d getTowBarPosition()
    {
        return towBarVec;
    }

    public void setTrailerOffset(Vec3d trailerOffset)
    {
        this.trailerOffset = trailerOffset;
    }

    public Vec3d getTrailerOffset()
    {
        return trailerOffset;
    }

    public void addWheel(Wheel.Side side, Wheel.Position position, float offsetX, float offsetZ, float scale, boolean particles, boolean render)
    {
        if(wheels == null)
        {
            wheels = new ArrayList<>();
        }
        wheels.add(new Wheel(side, position, 2.0F, scale, scale, scale, offsetX, 0F, offsetZ, particles, render));
    }

    public void addWheel(Wheel.Side side, Wheel.Position position, float offsetX, float offsetY, float offsetZ, float scale, boolean particles, boolean render)
    {
        if(wheels == null)
        {
            wheels = new ArrayList<>();
        }
        wheels.add(new Wheel(side, position, 2.0F, scale, scale, scale, offsetX, offsetY, offsetZ, particles, render));
    }

    public void addWheel(Wheel.Side side, Wheel.Position position, float offsetX, float offsetY, float offsetZ, float scaleX, float scaleY, float scaleZ, boolean particles, boolean render)
    {
        if(wheels == null)
        {
            wheels = new ArrayList<>();
        }
        wheels.add(new Wheel(side, position, 2.0F, scaleX, scaleY, scaleZ, offsetX, offsetY, offsetZ, particles, render));
    }

    @Nullable
    public List<Wheel> getWheels()
    {
        return wheels;
    }

    @Nullable
    public Wheel getFirstFrontWheel()
    {
        return wheels.stream().filter(wheel -> wheel.getPosition() == Wheel.Position.FRONT).findFirst().orElse(null);
    }

    @Nullable
    public Wheel getFirstRearWheel()
    {
        return wheels.stream().filter(wheel -> wheel.getPosition() == Wheel.Position.REAR).findFirst().orElse(null);
    }

    public void setBodyPosition(PartPosition bodyPosition)
    {
        this.bodyPosition = bodyPosition;
    }

    public PartPosition getBodyPosition()
    {
        return bodyPosition;
    }

    public void setEnginePosition(PartPosition enginePosition)
    {
        this.enginePosition = enginePosition;
    }

    public PartPosition getEnginePosition()
    {
        return enginePosition;
    }

    public void setFuelPortPosition(PartPosition fuelPortPosition)
    {
        this.fuelPortPosition = fuelPortPosition;
        this.fuelPortLidPosition = new PartPosition(
                fuelPortPosition.getX() - 6 * fuelPortPosition.getScale(),
                fuelPortPosition.getY(),
                fuelPortPosition.getZ() - 5 * fuelPortPosition.getScale(),
                fuelPortPosition.getRotX(),
                fuelPortPosition.getRotY() - 90,
                fuelPortPosition.getRotZ(),
                fuelPortPosition.getScale());
    }

    public PartPosition getFuelPortPosition()
    {
        return fuelPortPosition;
    }

    public void setFuelPortLidPosition(PartPosition fuelPortLidPosition)
    {
        this.fuelPortLidPosition = fuelPortLidPosition;
    }

    public PartPosition getFuelPortLidPosition()
    {
        return fuelPortLidPosition;
    }

    public void setKeyPortPosition(PartPosition keyPortPosition)
    {
        this.keyPortPosition = keyPortPosition;
        this.keyPosition = new PartPosition(keyPortPosition.getX(), keyPortPosition.getY(), keyPortPosition.getZ(), keyPortPosition.getRotX() + 90, 0, 0, 0.15);
    }

    public PartPosition getKeyPortPosition()
    {
        return keyPortPosition;
    }

    public void setKeyPosition(PartPosition keyPosition)
    {
        this.keyPosition = keyPosition;
    }

    public PartPosition getKeyPosition()
    {
        return keyPosition;
    }

    public void setDisplayPosition(PartPosition displayPosition)
    {
        this.displayPosition = displayPosition;
    }

    public PartPosition getDisplayPosition()
    {
        return displayPosition;
    }

    public void setFrontAxelVec(double x, double z)
    {
        this.frontAxelVec = new Vec3d(x, 0, z);
    }

    @Nullable
    public Vec3d getFrontAxelVec()
    {
        return frontAxelVec;
    }

    public void setRearAxelVec(double x, double z)
    {
        this.rearAxelVec = new Vec3d(x, 0, z);
    }

    @Nullable
    public Vec3d getRearAxelVec()
    {
        return rearAxelVec;
    }

    public void addSeat(Seat seat)
    {
        this.seats.add(seat);
    }

    public List<Seat> getSeats()
    {
        return ImmutableList.copyOf(this.seats);
    }
}
