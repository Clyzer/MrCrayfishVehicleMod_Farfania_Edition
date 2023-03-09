package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.entity.LandVehicleEntity;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class Deportive2Entity extends LandVehicleEntity
{
    public Deportive2Entity(EntityType<? extends Deportive2Entity> type, World worldIn)
    {
        super(type, worldIn);
        this.setMaxSpeed(20F);
        this.setTurnSensitivity(12);
        //this.stepHeight = 1F;
        this.setFuelCapacity(30000F);
        this.setFuelConsumption(0.375F);
        this.setMaxHealth(100F);
        this.setHealth(100F);
    }

    @Override
    public SoundEvent getEngineSound()
    {
        return ModSounds.ENTITY_BUMPER_CAR_ENGINE.get();
    }

    @Override
    public float getMinEnginePitch()
    {
        return 0.8F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.6F;
    }

    @Override
    public boolean canBeColored()
    {
        return true;
    }

    @Override
    public boolean canTowTrailer()
    {
        return true;
    }

    @Override
    public boolean canMountTrailer()
    {
        return true;
    }
}
