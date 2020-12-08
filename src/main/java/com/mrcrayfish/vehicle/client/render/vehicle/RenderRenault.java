package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.entity.vehicle.EntityRenault;
import com.mrcrayfish.vehicle.util.RenderUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Author: MrCrayfish
 */
public class RenderRenault extends AbstractRenderVehicle<EntityRenault>
{
    @Override
    public void render(EntityRenault entity, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {
        	GlStateManager.translate(0.0, 0.33, 0.0);
        	GlStateManager.scale(1.8, 1.8, 1.8);
        	this.renderDamagedPart(entity, SpecialModels.RENAULT_BODY.getModel());
        }
        GlStateManager.popMatrix();

        //Render the handles bars
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0.48, 0.24, 0.3 + 7 * 0.0625);
            GlStateManager.rotate(-67.5F, 1, 0, 0);
            GlStateManager.translate(0, -0.02, 0);
            GlStateManager.scale(0.9, 0.9, 0.9);

            float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 25F;
            GlStateManager.rotate(turnRotation, 0, 1, 0);

            RenderUtil.renderColoredModel(SpecialModels.GO_KART_STEERING_WHEEL.getModel(), ItemCameraTransforms.TransformType.NONE, entity.getColor());
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(EntityRenault entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(10F);
        model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
        model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-10F);
        //model.bipedBodyWear.rotateAngleY = (float) Math.toRadians(-10);

        float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
        float wheelAngleNormal = wheelAngle / 45F;
        float turnRotation = wheelAngleNormal * 6F;
        if(entity.getControllingPassenger() == player){
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-80F - turnRotation);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-80F + turnRotation);
        }
    }
}
