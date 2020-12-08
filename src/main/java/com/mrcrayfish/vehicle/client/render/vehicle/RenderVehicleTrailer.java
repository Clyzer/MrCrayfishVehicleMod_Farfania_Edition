package com.mrcrayfish.vehicle.client.render.vehicle;

import com.mrcrayfish.vehicle.client.SpecialModels;
import com.mrcrayfish.vehicle.client.render.AbstractRenderTrailer;
import com.mrcrayfish.vehicle.entity.trailer.EntityVehicleTrailer;

import net.minecraft.client.renderer.GlStateManager;

/**
 * Author: MrCrayfish
 */
public class RenderVehicleTrailer extends AbstractRenderTrailer<EntityVehicleTrailer>
{
    @Override
    public void render(EntityVehicleTrailer entity, float partialTicks)
    {
        GlStateManager.pushMatrix();
        {
        	this.renderDamagedPart(entity, SpecialModels.TRAILER_BIG_BODY.getModel());
        }
        GlStateManager.popMatrix();
        this.renderWheel(entity, false, -11.5F * 0.0625F, -0.37F, -5.5F * 0.0625F, 1.5F, partialTicks);
        this.renderWheel(entity, true, 11.5F * 0.0625F, -0.37F, -5.5F * 0.0625F, 1.5F, partialTicks);
    }
}
