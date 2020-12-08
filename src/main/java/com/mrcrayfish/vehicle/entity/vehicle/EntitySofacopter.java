package com.mrcrayfish.vehicle.entity.vehicle;

import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.common.entity.PartPosition;
import com.mrcrayfish.vehicle.entity.EngineType;
import com.mrcrayfish.vehicle.entity.EntityHelicopter;
import com.mrcrayfish.vehicle.init.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class EntitySofacopter extends EntityHelicopter implements EntityRaytracer.IEntityRaytraceable
{
    public static final PartPosition BODY_POSITION = new PartPosition(0, 0, 0.0625, 0, 0, 0, 1);
    public static final PartPosition FUEL_PORT_POSITION = new PartPosition(-2, 1.75, 8.25, 0, 0, 0, 0.45);
    public static final PartPosition KEY_PORT_POSITION = new PartPosition(-9.25, 8, 5, 0, 0, 0, 0.8);

    public EntitySofacopter(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.setFuelCapacity(40000F);
        this.setFuelConsumption(0.5F);
        this.dataManager.set(COLOR, 11546150);
        this.setMaxHealth(50F);
        this.setHealth(50F);
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
        return EngineType.ELECTRIC_MOTOR;
    }

    @Override
    public boolean canBeColored()
    {
        return false;
    }
    
    @Override
    public boolean canMountTrailer()
    {
        return true;
    }
}
