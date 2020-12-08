package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityMotorcycle;
import com.mrcrayfish.vehicle.entity.WheelType;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityBici2 extends EntityMotorcycle implements IEntityRaytraceable
{
    public EntityBici2(World worldIn)
    {
        super(worldIn);
        this.setMaxSpeed(9F);
        this.setTurnSensitivity(12);
        this.setFuelCapacity(1F);
        this.setFuelConsumption(0F);
        this.setCurrentFuel(1F);
        this.setMaxHealth(30F);
        this.setHealth(30F);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.BICI_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.BICI_ENGINE_STEREO;
    }
    
    @Override
    public SoundEvent getHornSound()
    {
    	return ModSounds.HORN_BICI_MONO;
    }
    
    @Override
    public SoundEvent getHornRidingSound()
    {
    	return ModSounds.HORN_BICI_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.NONE;
    }

    @Override
    public float getMinEnginePitch()
    {
        return 0.5F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.8F;
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
    public boolean shouldRenderFuelPort()
    {
        return false;
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
