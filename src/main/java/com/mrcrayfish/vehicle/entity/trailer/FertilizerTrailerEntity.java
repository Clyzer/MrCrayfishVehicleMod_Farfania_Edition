package com.mrcrayfish.vehicle.entity.trailer;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.vehicle.VehicleConfig;
import com.mrcrayfish.vehicle.client.EntityRayTracer;
import com.mrcrayfish.vehicle.common.inventory.IStorage;
import com.mrcrayfish.vehicle.common.inventory.StorageInventory;
import com.mrcrayfish.vehicle.entity.TrailerEntity;
import com.mrcrayfish.vehicle.item.SprayCanItem;
import com.mrcrayfish.vehicle.network.PacketHandler;
import com.mrcrayfish.vehicle.network.message.MessageAttachTrailer;
import com.mrcrayfish.vehicle.network.message.MessageSyncInventory;
import com.mrcrayfish.vehicle.util.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class FertilizerTrailerEntity extends TrailerEntity implements IStorage
{
    private static final EntityRayTracer.RayTracePart CONNECTION_BOX = new EntityRayTracer.RayTracePart(createScaledBoundingBox(-7 * 0.0625, 6.2 * 0.0625, 6 * 0.0625, 7 * 0.0625, 8.4 * 0.0625F, 18 * 0.0625, 1.1));
    private static final Map<EntityRayTracer.RayTracePart, EntityRayTracer.TriangleRayTraceList> interactionBoxMapStatic = DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> {
        Map<EntityRayTracer.RayTracePart, EntityRayTracer.TriangleRayTraceList> map = new HashMap<>();
        map.put(CONNECTION_BOX, EntityRayTracer.boxToTriangles(CONNECTION_BOX.getBox(), null));
        return map;
    });

    private int inventoryTimer;
    private StorageInventory inventory;
    private BlockPos[] lastPos = new BlockPos[3];

    public FertilizerTrailerEntity(EntityType<? extends FertilizerTrailerEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.initInventory();
    }

    public boolean canBeColored()
    {
        return true;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger)
    {
        return false;
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand)
    {
        ItemStack heldItem = player.getItemInHand(hand);
        if((heldItem.isEmpty() || !(heldItem.getItem() instanceof SprayCanItem)) && player instanceof ServerPlayerEntity)
        {
            NetworkHooks.openGui((ServerPlayerEntity) player, this.getInventory(), buffer -> buffer.writeVarInt(this.getId()));
            return ActionResultType.SUCCESS;
        }
        return super.interact(player, hand);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(!level.isClientSide && VehicleConfig.SERVER.trailerInventorySyncCooldown.get() > 0 && this.inventoryTimer++ == VehicleConfig.SERVER.trailerInventorySyncCooldown.get())
        {
            this.inventoryTimer = 0;
            PacketHandler.instance.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new MessageSyncInventory(this.getId(), this.inventory));
        }
    }

    @Override
    public void onUpdateVehicle()
    {
        super.onUpdateVehicle();

        if(!level.isClientSide)
        {
            ItemStack fertilizer = this.getFertilizer();
            if(fertilizer.isEmpty() && this.getPullingEntity() instanceof StorageTrailerEntity)
            {
                fertilizer = this.getFertilizerFromStorage((StorageTrailerEntity) this.getPullingEntity());
            }
            if(!fertilizer.isEmpty())
            {
                Vector3d lookVec = this.getLookAngle();
                boolean applied = this.applyFertilizer(lookVec.yRot((float) Math.toRadians(90F)), 0);
                applied |= this.applyFertilizer(Vector3d.ZERO, 1);
                applied |= this.applyFertilizer(lookVec.yRot((float) Math.toRadians(-90F)), 2);
                if(applied) fertilizer.shrink(1);
            }
        }
    }

    private boolean applyFertilizer(Vector3d vec, int index)
    {
        Vector3d prevPosVec = new Vector3d(xo, yo + 0.25, zo);
        prevPosVec = prevPosVec.add(new Vector3d(0, 0, -1).yRot(-this.yRot * 0.017453292F));
        BlockPos pos = new BlockPos(prevPosVec.x + vec.x, prevPosVec.y, prevPosVec.z + vec.z);

        if(lastPos[index] != null && lastPos[index].equals(pos))
        {
            return false;
        }
        lastPos[index] = pos;

        BlockState state = level.getBlockState(pos);
        if(state.getBlock() instanceof IGrowable)
        {
            IGrowable growable = (IGrowable) state.getBlock();
            if(growable.isValidBonemealTarget(level, pos, state, false))
            {
                if(growable.isBonemealSuccess(level, random, pos, state))
                {
                    growable.performBonemeal((ServerWorld) level, random, pos, state);
                    level.levelEvent(2005, pos, 0);
                    return true;
                }
            }
        }
        return false;
    }

    private ItemStack getFertilizer()
    {
        for(int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if(!stack.isEmpty() && stack.getItem() instanceof BoneMealItem)
            {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack getFertilizerFromStorage(StorageTrailerEntity storageTrailer)
    {
        if(storageTrailer == null)
            return ItemStack.EMPTY;

        if(storageTrailer.getInventory() != null)
        {
            StorageInventory storage = storageTrailer.getInventory();
            for(int i = 0; i < storage.getContainerSize(); i++)
            {
                ItemStack stack = storage.getItem(i);
                if(!stack.isEmpty() && stack.getItem() instanceof BoneMealItem)
                {
                    return stack;
                }
            }

            if(storageTrailer.getPullingEntity() instanceof StorageTrailerEntity)
            {
                return this.getFertilizerFromStorage((StorageTrailerEntity) storageTrailer.getPullingEntity());
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound)
    {
        super.readAdditionalSaveData(compound);
        if(compound.contains("Inventory", Constants.NBT.TAG_LIST))
        {
            this.initInventory();
            InventoryUtil.readInventoryToNBT(compound, "Inventory", this.inventory);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound)
    {
        super.addAdditionalSaveData(compound);
        if(this.inventory != null)
        {
            InventoryUtil.writeInventoryToNBT(compound, "Inventory", this.inventory);
        }
    }

    private void initInventory()
    {
        StorageInventory original = this.inventory;
        this.inventory = new StorageInventory(this, 27);
        // Copies the inventory if it exists already over to the new instance
        if(original != null)
        {
            for(int i = 0; i < original.getContainerSize(); i++)
            {
                ItemStack stack = original.getItem(i);
                if(!stack.isEmpty())
                {
                    this.inventory.setItem(i, stack.copy());
                }
            }
        }
    }

    @Override
    protected void onVehicleDestroyed(LivingEntity entity)
    {
        super.onVehicleDestroyed(entity);
        if(inventory != null)
        {
            InventoryHelper.dropContents(level, this, inventory);
        }
    }

    @Override
    public StorageInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public double getHitchOffset()
    {
        return -17.0 * 1.1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Map<EntityRayTracer.RayTracePart, EntityRayTracer.TriangleRayTraceList> getStaticInteractionBoxMap()
    {
        return interactionBoxMapStatic;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<EntityRayTracer.RayTracePart> getApplicableInteractionBoxes()
    {
        return ImmutableList.of(CONNECTION_BOX);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean processHit(EntityRayTracer.RayTraceResultRotated result, boolean rightClick)
    {
        if(rightClick)
        {
            if(result.getPartHit() == CONNECTION_BOX)
            {
                PacketHandler.instance.sendToServer(new MessageAttachTrailer(this.getId(), Minecraft.getInstance().player.getId()));
                return true;
            }
        }
        return super.processHit(result, rightClick);
    }

    @Override
    public boolean isStorageItem(ItemStack stack)
    {
        return !stack.isEmpty() && stack.getItem() instanceof BoneMealItem;
    }

    @Override
    public ITextComponent getStorageName()
    {
        return this.getDisplayName();
    }
}
