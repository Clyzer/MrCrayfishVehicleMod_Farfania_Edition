package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.entity.vehicle.EntityBus;
import com.mrcrayfish.vehicle.util.RenderUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Author: MrCrayfish
 */
public class RenderBus extends AbstractRenderVehicle<EntityBus>
{
    @Override
    public SpecialModels getTowBarModel()
    {
        return SpecialModels.BIG_TOW_BAR;
    }

    @Override
    public void render(EntityBus entity, float partialTicks)
    {
        this.renderDamagedPart(entity, SpecialModels.BUS_BODY_3.getModel());

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, 0, -3);
            this.renderDamagedPart(entity, SpecialModels.BUS_BODY_2.getModel());
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, 0, -6);
            this.renderDamagedPart(entity, SpecialModels.BUS_BODY.getModel());
        }
        GlStateManager.popMatrix();

        //Render the handles bars
        GlStateManager.pushMatrix();
        {
            // Positions the steering wheel in the correct position
            GlStateManager.translate(-0.2825, 0.225, 1.0625);
            GlStateManager.rotate(-67.5F, 1, 0, 0);
            GlStateManager.translate(0, -0.02, 0);
            GlStateManager.scale(0.75, 0.75, 0.75);

            // Rotates the steering wheel based on the wheel angle
            float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 25F;
            GlStateManager.rotate(turnRotation, 0, 1, 0);

            RenderUtil.renderColoredModel(SpecialModels.GO_KART_STEERING_WHEEL.getModel(), ItemCameraTransforms.TransformType.NONE, entity.getColor());
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(EntityBus entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        if(entity.getControllingPassenger() == player)
        {
            float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 6F;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-65F - turnRotation);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-7F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-65F + turnRotation);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(7F);
        }
    }
}
