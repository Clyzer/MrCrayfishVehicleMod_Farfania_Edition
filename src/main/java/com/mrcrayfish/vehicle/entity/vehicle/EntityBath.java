package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityPlane;
import net.minecraft.util.EnumParticleTypes;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityBath extends EntityPlane implements IEntityRaytraceable
{
    public EntityBath(World worldIn)
    {
        super(worldIn);
        this.setFuelConsumption(0.0F);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return null;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return null;
    }

    @Override
    public void updateVehicle()
    {
        if(this.isFlying() && this.getControllingPassenger() != null)
        {
            for(int i = 0; i < 4; i++)
            {
                world.spawnParticle(EnumParticleTypes.DRIP_WATER, posX - 0.25 + 0.5 * rand.nextGaussian(), posY + 0.5 * rand.nextGaussian(), posZ - 0.25 + 0.5 * rand.nextGaussian(), 0, 0, 0, 0);
            }
        }
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
    public EngineType getEngineType()
    {
        return EngineType.NONE;
    }

    @Override
    public boolean canBeColored()
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
