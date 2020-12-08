package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.common.Seat;
import com.mrcrayfish.vehicle.entity.VehicleProperties;
import com.mrcrayfish.vehicle.entity.vehicle.EntityBici2;
import com.mrcrayfish.vehicle.util.RenderUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

/**
 * Author: MrCrayfish
 */
public class RenderBici2 extends AbstractRenderVehicle<EntityBici2>
{
    @Override
    public void render(EntityBici2 entity, float partialTicks)
    {
        this.renderDamagedPart(entity, SpecialModels.BICI_BODY_2.getModel());

        //Render the handles bars
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, 0, 10.5 * 0.0625);
            GlStateManager.rotate(-22.5F, 1, 0, 0);

            float wheelScale = 2.3F;
            float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 25F;

            GlStateManager.rotate(turnRotation, 0, 1, 0);
            GlStateManager.rotate(22.5F, 1, 0, 0);
            GlStateManager.translate(0, 0, -10.5 * 0.0625);

            this.renderDamagedPart(entity, SpecialModels.BICI_HANDLE_BAR_2.getModel());

            if(entity.hasWheels())
            {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translate(0, -0.6 + 2.2 * 0.0625, 13.4 * 0.0625);
                    float frontWheelSpin = entity.prevFrontWheelRotation + (entity.frontWheelRotation - entity.prevFrontWheelRotation) * partialTicks;
                    if(entity.isMoving())
                    {
                        GlStateManager.rotate(-frontWheelSpin, 1, 0, 0);
                    }
                    GlStateManager.scale(wheelScale, wheelScale, wheelScale);
                    GlStateManager.rotate(180F, 0, 1, 0);
                    IBakedModel model = RenderUtil.getWheelModel(entity);
                    if(model != null)
                    {
                        RenderUtil.renderColoredModel(model, ItemCameraTransforms.TransformType.NONE, entity.getWheelColor());
                    }
                }
                GlStateManager.popMatrix();
            }
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(EntityBici2 entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        int index = entity.getSeatTracker().getSeatIndex(player.getUniqueID());
        if(index != -1)
        {
            if(index == 0)
            {
                float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
                float wheelAngleNormal = wheelAngle / 45F;
                float turnRotation = wheelAngleNormal * 8F;
                model.bipedHead.offsetY = -0.15F;
                model.bipedHead.offsetZ = -0.3F;
                model.bipedHeadwear.offsetY = -0.15F;
                model.bipedHeadwear.offsetZ = -0.3F;
                model.bipedBody.offsetY = -0.2F;
                model.bipedBody.offsetZ = -0.3F;
                model.bipedBody.rotateAngleX = 0.5F;
                model.bipedBodyWear.offsetY = -0.2F;
                model.bipedBodyWear.offsetZ = -0.3F;
                model.bipedBodyWear.rotateAngleX = 0.5F;
                model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-40F - turnRotation);
                model.bipedRightArmwear.rotateAngleX = (float) Math.toRadians(-40F - turnRotation);
                model.bipedRightArm.offsetZ = -0.1F * wheelAngleNormal + -0.3F;
                model.bipedRightArm.offsetY = -0.3F;
                model.bipedRightArmwear.offsetZ = -0.1F * wheelAngleNormal + -0.3F;
                model.bipedRightArmwear.offsetY = -0.3F;
                model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-40F + turnRotation);
                model.bipedLeftArmwear.rotateAngleX = (float) Math.toRadians(-40F + turnRotation);
                model.bipedLeftArm.offsetZ = 0.1F * wheelAngleNormal + -0.3F;
                model.bipedLeftArm.offsetY = -0.3F;
                model.bipedLeftArmwear.offsetZ = 0.1F * wheelAngleNormal + -0.3F;
                model.bipedLeftArmwear.offsetY = -0.3F;
                model.bipedRightLegwear.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedRightLegwear.rotateAngleY = (float) Math.toRadians(30F);
                model.bipedRightLegwear.offsetY = -0.4F;
                model.bipedRightLegwear.offsetZ = 0.02F;
                model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(30F);
                model.bipedRightLeg.offsetY = -0.4F;
                model.bipedRightLeg.offsetZ = 0.02F;
                model.bipedLeftLegwear.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedLeftLegwear.rotateAngleY = (float) Math.toRadians(-30F);
                model.bipedLeftLegwear.offsetY = -0.4F;
                model.bipedLeftLegwear.offsetZ = 0.02F;
                model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-30F);
                model.bipedLeftLeg.offsetY = -0.4F;
                model.bipedLeftLeg.offsetZ = 0.02F;
            }
            if(index == 1)
            {
                model.bipedHead.offsetY = -0.4F;
                model.bipedHeadwear.offsetY = -0.4F;
                model.bipedBody.offsetY = -0.4F;
                model.bipedBodyWear.offsetY = -0.4F;
                model.bipedRightArm.offsetY = -0.3F;
                model.bipedLeftArm.offsetY = -0.4F;
                model.bipedRightArmwear.offsetY = -0.4F;
                model.bipedLeftArmwear.offsetY = -0.4F;
                model.bipedRightLegwear.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedRightLegwear.rotateAngleY = (float) Math.toRadians(30F);
                model.bipedRightLegwear.offsetY = -0.4F;
                model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(30F);
                model.bipedRightLeg.offsetY = -0.4F;
                model.bipedLeftLegwear.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedLeftLegwear.rotateAngleY = (float) Math.toRadians(-30F);
                model.bipedLeftLegwear.offsetY = -0.4F;
                model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-25F);
                model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-30F);
                model.bipedLeftLeg.offsetY = -0.4F;
            }
        }
    }

    @Override
    public void applyPlayerRender(EntityBici2 entity, EntityPlayer player, float partialTicks)
    {
        int index = entity.getSeatTracker().getSeatIndex(player.getUniqueID());
        if(index != -1)
        {
            VehicleProperties properties = entity.getProperties();
            Seat seat = properties.getSeats().get(index);
            Vec3d seatVec = seat.getPosition().addVector(0, properties.getAxleOffset() + properties.getWheelOffset(), 0).scale(properties.getBodyPosition().getScale());
            seatVec = new Vec3d(-seatVec.x, seatVec.y, seatVec.z);
            seatVec = seatVec.scale(0.0625);
            double scale = 32.0 / 30.0;
            double offsetX = seatVec.x * scale;
            double offsetY = (seatVec.y + player.getYOffset()) * scale + 24 * 0.0625; //Player is 2 blocks high tall but renders at 1.8 blocks tall
            double offsetZ = -seatVec.z * scale;
            GlStateManager.translate(offsetX, offsetY, offsetZ);
            float currentSpeedNormal = (entity.prevCurrentSpeed + (entity.currentSpeed - entity.prevCurrentSpeed) * partialTicks) / entity.getMaxSpeed();
            float turnAngleNormal = (entity.prevTurnAngle + (entity.turnAngle - entity.prevTurnAngle) * partialTicks) / 45F;
            GlStateManager.rotate(turnAngleNormal * currentSpeedNormal * 20F, 0, 0, 1);
            GlStateManager.translate(-offsetX, -offsetY, -offsetZ);
        }
    }
}
