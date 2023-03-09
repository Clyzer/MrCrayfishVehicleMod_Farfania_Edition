package com.mrcrayfish.vehicle.entity;

import com.mrcrayfish.vehicle.client.VehicleHelper;
import com.mrcrayfish.vehicle.common.entity.PartPosition;
import com.mrcrayfish.vehicle.init.ModSounds;
import com.mrcrayfish.vehicle.network.PacketHandler;
import com.mrcrayfish.vehicle.network.message.MessageDrift;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Author: MrCrayfish
 */
public abstract class LandVehicleEntity extends PoweredVehicleEntity
{
    private static final DataParameter<Boolean> DRIFTING = EntityDataManager.defineId(LandVehicleEntity.class, DataSerializers.BOOLEAN);

    public float drifting;
    public float additionalYaw;
    public float prevAdditionalYaw;

    @OnlyIn(Dist.CLIENT)
    public float frontWheelRotation;
    @OnlyIn(Dist.CLIENT)
    public float prevFrontWheelRotation;
    @OnlyIn(Dist.CLIENT)
    public float rearWheelRotation;
    @OnlyIn(Dist.CLIENT)
    public float prevRearWheelRotation;

    public LandVehicleEntity(EntityType<?> entityType, World worldIn)
    {
        super(entityType, worldIn);
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(DRIFTING, false);
    }

    @Override
    public void onUpdateVehicle()
    {
        super.onUpdateVehicle();
        this.updateWheels();
    }

    @Override
    public void updateVehicle()
    {
        this.prevAdditionalYaw = this.additionalYaw;
        this.prevFrontWheelRotation = this.frontWheelRotation;
        this.prevRearWheelRotation = this.rearWheelRotation;
        this.updateDrifting();
    }

    @Override
    public void onClientUpdate()
    {
        super.onClientUpdate();
        LivingEntity entity = (LivingEntity) this.getControllingPassenger();
        if(entity != null && entity.equals(Minecraft.getInstance().player))
        {
            boolean drifting = VehicleHelper.isDrifting();
            if(this.isDrifting() != drifting)
            {
                this.setDrifting(drifting);
                PacketHandler.instance.sendToServer(new MessageDrift(drifting));
            }
        }
    }

    @Override
    public void updateVehicleMotion()
    {
        float currentSpeed = this.currentSpeed;

        if(this.speedMultiplier > 1.0F)
        {
            this.speedMultiplier = 1.0F;
        }

        /* Applies the speed multiplier to the current speed */
        currentSpeed = currentSpeed + (currentSpeed * this.speedMultiplier);

        VehicleProperties properties = this.getProperties();
        if(properties.getFrontAxelVec() != null && properties.getRearAxelVec() != null)
        {
            AccelerationDirection acceleration = this.getAcceleration();
            if(acceleration == AccelerationDirection.CHARGING && this.charging)
            {
                PartPosition bodyPosition = properties.getBodyPosition();
                Vector3d frontAxel = properties.getFrontAxelVec().scale(0.0625F).scale(bodyPosition.getScale());
                Vector3d nextFrontAxel = frontAxel.yRot((this.turnAngle / 20F) * 0.017453292F);
                Vector3d deltaAxel = frontAxel.subtract(nextFrontAxel).yRot(-this.yRot * 0.017453292F);
                double deltaYaw = -this.turnAngle / 20F;
                this.yRot += deltaYaw;
                this.deltaYaw = (float) -deltaYaw;
                this.vehicleMotionX = (float) deltaAxel.x();
                if(!this.launching)
                {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08, 0));
                }
                this.vehicleMotionZ = (float) deltaAxel.z();
                return;
            }

            PartPosition bodyPosition = properties.getBodyPosition();
            Vector3d nextFrontAxelVec = new Vector3d(0, 0, currentSpeed / 20F).yRot(this.wheelAngle * 0.017453292F);
            nextFrontAxelVec = nextFrontAxelVec.add(properties.getFrontAxelVec().scale(0.0625).scale(bodyPosition.getScale()));
            Vector3d nextRearAxelVec = new Vector3d(0, 0, currentSpeed / 20F);
            nextRearAxelVec = nextRearAxelVec.add(properties.getRearAxelVec().scale(0.0625).scale(bodyPosition.getScale()));
            double deltaYaw = Math.toDegrees(Math.atan2(nextRearAxelVec.z - nextFrontAxelVec.z, nextRearAxelVec.x - nextFrontAxelVec.x)) + 90;
            if(this.isRearWheelSteering())
            {
                deltaYaw -= 180;
            }
            this.yRot += deltaYaw;
            this.deltaYaw = (float) -deltaYaw;

            Vector3d nextVehicleVec = nextFrontAxelVec.add(nextRearAxelVec).scale(0.5);
            nextVehicleVec = nextVehicleVec.subtract(properties.getFrontAxelVec().add(properties.getRearAxelVec()).scale(0.0625).scale(bodyPosition.getScale()).scale(0.5));
            nextVehicleVec = nextVehicleVec.scale(bodyPosition.getScale()).yRot((-this.yRot + 90) * 0.017453292F);

            float targetRotation = (float) Math.toDegrees(Math.atan2(nextVehicleVec.z, nextVehicleVec.x));
            float f1 = MathHelper.sin(targetRotation * 0.017453292F) / 20F * (currentSpeed > 0 ? 1 : -1);
            float f2 = MathHelper.cos(targetRotation * 0.017453292F) / 20F * (currentSpeed > 0 ? 1 : -1);
            this.vehicleMotionX = (-currentSpeed * f1);
            if(!launching)
            {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08, 0));
            }
            this.vehicleMotionZ = (currentSpeed * f2);
        }
        else
        {
            float f1 = MathHelper.sin(this.yRot * 0.017453292F) / 20F;
            float f2 = MathHelper.cos(this.yRot * 0.017453292F) / 20F;
            this.vehicleMotionX = (-currentSpeed * f1);
            if(!launching)
            {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08, 0));
            }
            this.vehicleMotionZ = (currentSpeed * f2);
        }
    }
    @Override
    protected void updateTurning()
    {
        if(this.level.isClientSide())
        {
            this.turnAngle = VehicleHelper.getTargetTurnAngle(this, this.isDrifting());
        }
        else
        {
            this.turnAngle = 0F;
        }

        this.wheelAngle = this.turnAngle * Math.max(0.45F, 1.0F - Math.abs(this.currentSpeed / 20F));

        VehicleProperties properties = this.getProperties();
        if(properties.getFrontAxelVec() == null || properties.getRearAxelVec() == null)
        {
            this.deltaYaw = this.wheelAngle * (this.currentSpeed / 30F) / 2F;
        }

        if(this.level.isClientSide)
        {
            this.targetWheelAngle = this.isDrifting() ? -35F * (this.turnAngle / (float) this.getMaxTurnAngle()) * this.getNormalSpeed() : this.wheelAngle - 35F * (this.turnAngle / (float) this.getMaxTurnAngle()) * drifting;
            this.renderWheelAngle = this.renderWheelAngle + (this.targetWheelAngle - this.renderWheelAngle) * (this.isDrifting() ? 0.35F : 0.5F);
        }
    }

    private void updateDrifting()
    {
        TurnDirection turnDirection = this.getTurnDirection();
        if(this.getControllingPassenger() != null && this.isDrifting())
        {
            if(turnDirection != TurnDirection.FORWARD)
            {
                AccelerationDirection acceleration = this.getAcceleration();
                if(acceleration == AccelerationDirection.FORWARD)
                {
                    this.currentSpeed *= 0.975F;
                }
                this.drifting = Math.min(1.0F, this.drifting + 0.025F);
            }
        }
        else
        {
            this.drifting *= 0.95F;
        }
        this.additionalYaw = 25F * this.drifting * (this.turnAngle / (float) this.getMaxTurnAngle()) * Math.min(this.getActualMaxSpeed(), this.getActualSpeed() * 2F);

        //Updates the delta yaw to consider drifting
        this.deltaYaw = this.wheelAngle * (this.currentSpeed / 30F) / (this.isDrifting() ? 1.5F : 2F);
    }

    public void updateWheels()
    {
        VehicleProperties properties = this.getProperties();
        double wheelCircumference = 24.0;
        double vehicleScale = properties.getBodyPosition().getScale();
        double speed = this.getSpeed();

        Wheel frontWheel = properties.getFirstFrontWheel();
        if(frontWheel != null && !this.charging)
        {
            double frontWheelCircumference = wheelCircumference * vehicleScale * frontWheel.getScaleY();
            double rotation = (speed * 16) / frontWheelCircumference;
            this.frontWheelRotation -= rotation * 20F;
        }

        Wheel rearWheel = properties.getFirstRearWheel();
        if(rearWheel != null)
        {
            double rearWheelCircumference = wheelCircumference * vehicleScale * rearWheel.getScaleY();
            double rotation = (speed * 16) / rearWheelCircumference;
            this.rearWheelRotation -= rotation * 20F;
        }
    }

    @Override
    public void createParticles()
    {
        if(this.canDrive())
        {
            super.createParticles();
        }
    }

    @Override
    protected void removePassenger(Entity passenger)
    {
        super.removePassenger(passenger);
        if(this.getControllingPassenger() == null)
        {
            this.yRot -= this.additionalYaw;
            this.additionalYaw = 0;
            this.drifting = 0;
        }
    }

    public void setDrifting(boolean drifting)
    {
        this.entityData.set(DRIFTING, drifting);
    }

    public boolean isDrifting()
    {
        return this.entityData.get(DRIFTING);
    }

    @Override
    protected float getModifiedAccelerationSpeed()
    {
        if(trailer != null)
        {
            if(trailer.getPassengers().size() > 0)
            {
                return super.getModifiedAccelerationSpeed() * 0.5F;
            }
            else
            {
                return super.getModifiedAccelerationSpeed() * 0.8F;
            }
        }
        return super.getModifiedAccelerationSpeed();
    }

    @Override
    public float getModifiedRotationYaw()
    {
        return this.yRot - this.additionalYaw;
    }

    public boolean isRearWheelSteering()
    {
        VehicleProperties properties = this.getProperties();
        return properties.getFrontAxelVec() != null && properties.getRearAxelVec() != null && properties.getFrontAxelVec().z < properties.getRearAxelVec().z;
    }

    @Override
    protected boolean canCharge()
    {
        return true;
    }

    public boolean canWheelie()
    {
        return true;
    }

    public void pushed(LandVehicleEntity entity)
    {
        if(entity instanceof LandVehicleEntity && this.isVehicle())
        {
            this.applyCollision(entity);
        }
    }

    private void applyCollision(LandVehicleEntity entity)
    {
        entity.setDeltaMovement(this.getDeltaMovement().add(this.vehicleMotionX * 2, 0, this.vehicleMotionZ * 2));
        level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_VEHICLE_IMPACT.get(), SoundCategory.NEUTRAL, 1.0F, 0.6F + 0.1F * this.getNormalSpeed());
        entity.currentSpeed *= 0.25F;
        
        this.setDeltaMovement(this.getDeltaMovement().add(-(this.vehicleMotionX * 2), 0, -(this.vehicleMotionZ * 2)));
        level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_VEHICLE_IMPACT.get(), SoundCategory.NEUTRAL, 1.0F, 0.6F + 0.1F * this.getNormalSpeed());
        this.currentSpeed *= 0.25F;
    }
}
