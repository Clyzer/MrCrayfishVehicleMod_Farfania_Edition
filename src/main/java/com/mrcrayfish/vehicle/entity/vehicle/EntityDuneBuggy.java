package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityLandVehicle;
import com.mrcrayfish.vehicle.entity.WheelType;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityDuneBuggy extends EntityLandVehicle implements IEntityRaytraceable
{
    public EntityDuneBuggy(World worldIn)
    {
        super(worldIn);
        this.setMaxSpeed(10);
        this.setSize(0.75F, 0.75F);
        this.stepHeight = 1F;
        this.setFuelCapacity(20000F);
        this.setMaxHealth(50F);
        this.setHealth(50F);
    }

    @Override
    public void entityInit()
    {
        super.entityInit();
        this.dataManager.set(WHEEL_TYPE, WheelType.PLASTIC.ordinal());
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.ELECTRIC_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.ELECTRIC_ENGINE_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.ELECTRIC_MOTOR;
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
    public boolean isLockable()
    {
        return false;
    }
    
    @Override
    public boolean canMountTrailer()
    {
        return true;
    }
}
