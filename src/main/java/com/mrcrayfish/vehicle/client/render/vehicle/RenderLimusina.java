package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderVehicle;
import com.mrcrayfish.vehicle.entity.vehicle.EntityLimusina;
import com.mrcrayfish.vehicle.util.RenderUtil;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Author: MrCrayfish
 */
public class RenderLimusina extends AbstractRenderVehicle<EntityLimusina>
{
    @Override
    public void render(EntityLimusina entity, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {
        	GlStateManager.translate(0.0, 0.025, 0.0);
        	GlStateManager.rotate(180F, 0, 1F, 0);
        	this.renderDamagedPart(entity, SpecialModels.LIMUSINA_BODY.getModel());
        }
        GlStateManager.popMatrix();

        //Render the handles bars
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0.17, -0.235, 0.27);
            GlStateManager.rotate(-67.5F, 1, 0, 0);
            GlStateManager.translate(0, -0.02, 0);
            GlStateManager.scale(0.25, 0.25, 0.25);

            float wheelAngle = entity.prevRenderWheelAngle + (entity.renderWheelAngle - entity.prevRenderWheelAngle) * partialTicks;
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 25F;
            GlStateManager.rotate(turnRotation, 0, 1, 0);

            RenderUtil.renderColoredModel(SpecialModels.GO_KART_STEERING_WHEEL.getModel(), ItemCameraTransforms.TransformType.NONE, entity.getColor());
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void applyPlayerModel(EntityLimusina entity, EntityPlayer player, ModelPlayer model, float partialTicks)
    {
        int index = entity.getSeatTracker().getSeatIndex(player.getUniqueID());
        if(index != -1)
        {
            if(index == 2 || index == 3 || index == 4)
            {
            	model.bipedBody.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedBodyWear.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedHead.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedHeadwear.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedLeftArm.offsetZ = -0.3F;
            	model.bipedLeftArm.offsetX = -0.3F;
            	model.bipedLeftArmwear.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedRightArm.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedRightArm.offsetZ = 0.3F;
            	model.bipedRightArm.offsetX = 0.3F;
            	model.bipedRightArmwear.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90);
            	model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedRightLeg.offsetZ = -0.3F;
            	model.bipedRightLeg.offsetX = 0.1F;
            	model.bipedRightLegwear.rotateAngleX = (float) Math.toRadians(-90);
            	model.bipedRightLegwear.rotateAngleY = (float) Math.toRadians(90);
            	model.bipedRightLegwear.offsetZ = -0.3F;
            	model.bipedRightLegwear.offsetX = 0.1F;
            	model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(90);
            	model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-90);
            	model.bipedLeftLeg.offsetZ = 0.1F;
            	model.bipedLeftLeg.offsetX = -0.1F;
            	model.bipedLeftLegwear.rotateAngleX = (float) Math.toRadians(90);
            	model.bipedLeftLegwear.rotateAngleY = (float) Math.toRadians(-90);
            	model.bipedLeftLegwear.offsetZ = 0.1F;
            	model.bipedLeftLegwear.offsetX = -0.1F;
            }
            else
            {
            	model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
            	model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(10F);
            	model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
            	model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-10F);
            }
        }
    }
}
