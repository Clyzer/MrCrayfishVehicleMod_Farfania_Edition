package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer.IEntityRaytraceable;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityBoat;
import net.minecraft.util.EnumParticleTypes;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityJetSki extends EntityBoat implements IEntityRaytraceable
{
    public EntityJetSki(World worldIn)
    {
        super(worldIn);
        this.setMaxSpeed(15F);
        this.setTurnSensitivity(15);
        this.setSize(1.5F, 1.0F);
        this.setFuelConsumption(0.5F);
        this.setFuelPort(FuelPort.CAP);
        this.setMaxHealth(50F);
        this.setHealth(50F);
    }

    @Override
    public void createParticles()
    {
        if(state == State.ON_WATER)
        {
            if(this.getAcceleration() == AccelerationDirection.FORWARD)
            {
                for(int i = 0; i < 5; i++)
                {
                    this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D);
                }

                for(int i = 0; i < 5; i++)
                {
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, -this.motionX * 2.0D, 0.0D, -this.motionZ * 2.0D);
                }
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
        return EngineType.SMALL_MOTOR;
    }

    @Override
    public float getMinEnginePitch()
    {
        return 1.2F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 2.2F;
    }

    @Override
    public boolean canBeColored()
    {
        return true;
    }

    //TODO remove and add key support
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
