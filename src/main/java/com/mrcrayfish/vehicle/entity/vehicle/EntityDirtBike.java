package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityMotorcycle;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntityDirtBike extends EntityMotorcycle implements EntityRaytracer.IEntityRaytraceable
{
    public EntityDirtBike(World worldIn)
    {
        super(worldIn);
        this.setMaxSpeed(18F);
        this.setTurnSensitivity(10);
        this.setFuelCapacity(20000F);
        this.setFuelConsumption(0.35F);
        this.setMaxHealth(50F);
        this.setHealth(50F);
    }

    @Override
    public SoundEvent getMovingSound()
    {
        return ModSounds.DIRT_BIKE_ENGINE_MONO;
    }

    @Override
    public SoundEvent getRidingSound()
    {
        return ModSounds.DIRT_BIKE_ENGINE_STEREO;
    }

    @Override
    public EngineType getEngineType()
    {
        return EngineType.SMALL_MOTOR;
    }

    @Override
    public float getMinEnginePitch()
    {
        return 0.85F;
    }

    @Override
    public float getMaxEnginePitch()
    {
        return 1.5F;
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
    public boolean shouldShowEngineSmoke()
    {
        return true;
    }

    @Override
    public Vec3d getEngineSmokePosition()
    {
        return new Vec3d(-0.0625, 1.25, -1);
    }

    @Override
    public boolean canBeColored()
    {
        return true;
    }

    @Override
    public boolean shouldRenderEngine()
    {
        return true;
    }

    @Override
    public FuelPort getFuelPort()
    {
        return FuelPort.CAP;
    }

    @Override
    public boolean isLockable()
    {
        return true;
    }
    
    @Override
    public boolean canMountTrailer()
    {
        return true;
    }
}