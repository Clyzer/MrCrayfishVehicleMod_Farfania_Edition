package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityLandVehicle;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityOffRoader extends EntityLandVehicle implements EntityRaytracer.IEntityRaytraceable
{
    public EntityOffRoader(World worldIn)
    {
        super(worldIn);
        this.setSize(2F, 1F);
        this.setMaxSpeed(18F);
        this.setFuelCapacity(25000F);
        this.setMaxHealth(50F);
        this.setHealth(50F);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.SPEED_BOAT_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.SPEED_BOAT_ENGINE_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.LARGE_MOTOR;
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
    public void applyEntityCollision(Entity entityIn)
    {
        if(this.isBeingRidden()){
            if(entityIn.isBeingRidden())
            {
                for(Entity passenger : this.getPassengers()){
                    this.applyDamageEntity(passenger, this.getSpeed() / 2);
                }
                applyBumperCollision(entityIn);
            }
        }
    }

    private void applyBumperCollision(Entity entity)
    {
        entity.motionX += vehicleMotionX * 2;
        entity.motionZ += vehicleMotionZ * 2;
        world.playSound(null, this.posX, this.posY, this.posZ, ModSounds.VEHICLE_CRASH, SoundCategory.NEUTRAL, 1.0F, 0.6F + 0.1F * this.getNormalSpeed());
        this.currentSpeed *= 0.25F;
    }

    @Override
    public boolean canBeColored()
    {
        return true;
    }

    @Override
    public boolean canMountTrailer()
    {
        return true;
    }
}
