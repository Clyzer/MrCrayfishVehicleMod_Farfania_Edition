package com.mrcrayfish.vehicle.common;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.mrcrayfish.vehicle.Reference;
import com.mrcrayfish.vehicle.VehicleConfig;
import com.mrcrayfish.vehicle.common.entity.HeldVehicleDataHandler;
import com.mrcrayfish.vehicle.common.entity.SyncedPlayerData;
import com.mrcrayfish.vehicle.entity.EntityJack;
import com.mrcrayfish.vehicle.entity.EntityTrailer;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.init.ModBlocks;
import com.mrcrayfish.vehicle.init.ModSounds;
import com.mrcrayfish.vehicle.network.PacketHandler;
import com.mrcrayfish.vehicle.network.message.MessageThrowVehicle;
import com.mrcrayfish.vehicle.tileentity.TileEntityGasPump;
import com.mrcrayfish.vehicle.tileentity.TileEntityJack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import com.mrcrayfish.vehicle.init.ModItems;
import com.mrcrayfish.vehicle.util.CommonUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import com.mrcrayfish.vehicle.entity.EntityPoweredVehicle;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CommonEvents
{
    private static final List<String> IGNORE_ITEMS;
    private static final List<String> IGNORE_SOUNDS;
    private static final List<String> IGNORE_ENTITIES;

    static
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("body");
        builder.add("atv");
        builder.add("go_kart");
        IGNORE_ITEMS = builder.build();

        builder = ImmutableList.builder();
        builder.add("idle");
        builder.add("driving");
        IGNORE_SOUNDS = builder.build();

        builder = ImmutableList.builder();
        builder.add("vehicle_atv");
        builder.add("couch");
        builder.add("bath");
        IGNORE_ENTITIES = builder.build();
    }

    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_ITEMS.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onMissingSound(RegistryEvent.MissingMappings<SoundEvent> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<SoundEvent> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_SOUNDS.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onMissingEntity(RegistryEvent.MissingMappings<EntityEntry> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<EntityEntry> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_ENTITIES.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.EntityInteractSpecific event)
    {
        if(pickUpVehicle(event.getWorld(), event.getEntityPlayer(), event.getHand(), event.getTarget()))
        {
            event.setCanceled(true);
        }
    }

    public static boolean pickUpVehicle(World world, EntityPlayer player, EnumHand hand, Entity targetEntity)
    {
        if(hand == EnumHand.MAIN_HAND && !world.isRemote && player.isSneaking() && !player.isSpectator() && VehicleConfig.SERVER.pickUpVehicles)
        {
            if(!HeldVehicleDataHandler.isHoldingVehicle(player))
            {
                if(targetEntity instanceof EntityPoweredVehicle && !targetEntity.isBeingRidden() && !targetEntity.isDead){
                    ItemStack stack = player.getHeldItemMainhand();
                    EntityPoweredVehicle poweredVehicle = (EntityPoweredVehicle) targetEntity;
                    if (poweredVehicle.isKeyNeeded()){
                        if (!stack.isEmpty() && stack.getItem() == ModItems.KEY){
                            if(!poweredVehicle.getUniqueID().equals(CommonUtils.getItemTagCompound(stack).getUniqueId("vehicleId")))
                            {
                                return false;
                            }
                        }
                        else{
                            return false;
                        }
                    }
                }
                if(targetEntity instanceof EntityVehicle && !targetEntity.isBeingRidden() && !targetEntity.isDead)
                {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    String id = getEntityString(targetEntity);
                    if(id != null)
                    {
                        ((EntityVehicle) targetEntity).setTrailer(null);

                        tagCompound.setString("id", id);
                        targetEntity.writeToNBT(tagCompound);
                        HeldVehicleDataHandler.setHeldVehicle(player, tagCompound);

                        //Removes the entity from the world
                        world.removeEntity(targetEntity);

                        //Plays pick up sound
                        world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.PICK_UP_VEHICLE, SoundCategory.PLAYERS, 1.0F, 1.0F);

                        return true;
                    }
                }
            }
            else if(targetEntity instanceof EntityTrailer && !targetEntity.isBeingRidden() && !targetEntity.isDead)
            {
                NBTTagCompound tagCompound = HeldVehicleDataHandler.getHeldVehicle(player);
                if(tagCompound.hasNoTags())
                {
                    return false;
                }
                Entity vehicle = EntityList.createEntityFromNBT(tagCompound, world);
                if(vehicle instanceof EntityVehicle && ((EntityVehicle) vehicle).canMountTrailer())
                {
                    vehicle.setPositionAndRotation(targetEntity.posX, targetEntity.posY, targetEntity.posZ, targetEntity.rotationYaw, targetEntity.rotationPitch);

                    //Update held vehicle data
                    HeldVehicleDataHandler.setHeldVehicle(player, new NBTTagCompound());

                    //Plays place sound
                    world.spawnEntity(vehicle);
                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    vehicle.startRiding(targetEntity);

                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        if(event.getHand() == EnumHand.OFF_HAND) return;

        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        if(!world.isRemote)
        {
            if(event.getFace() == EnumFacing.UP)
            {
                if(HeldVehicleDataHandler.isHoldingVehicle(player))
                {
                    BlockPos pos = event.getPos();
                    TileEntity tileEntity = event.getWorld().getTileEntity(pos);
                    if(tileEntity instanceof TileEntityJack)
                    {
                        TileEntityJack jack = (TileEntityJack) tileEntity;
                        if(jack.getJack() == null)
                        {
                            NBTTagCompound tagCompound = HeldVehicleDataHandler.getHeldVehicle(player);
                            Entity entity = EntityList.createEntityFromNBT(tagCompound, world);
                            if(entity instanceof EntityVehicle)
                            {
                                //Updates the player capability
                                HeldVehicleDataHandler.setHeldVehicle(player, new NBTTagCompound());

                                entity.fallDistance = 0.0F;
                                entity.rotationYaw = (player.getRotationYawHead() + 90F) % 360.0F;

                                jack.setVehicle((EntityVehicle) entity);
                                if(jack.getJack() != null)
                                {
                                    EntityJack entityJack = jack.getJack();
                                    entityJack.updateRidden();
                                    entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                                }
                                world.spawnEntity(entity);
                                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            }
                        }
                        event.setCanceled(true);
                        return;
                    }
                }
            }

            if(player.isSneaking())
            {
                if(HeldVehicleDataHandler.isHoldingVehicle(player))
                {
                    Vec3d clickedVec = event.getHitVec();
                    if(clickedVec == null || event.getFace() != EnumFacing.UP)
                    {
                        event.setCanceled(true);
                        return;
                    }

                    NBTTagCompound tagCompound = HeldVehicleDataHandler.getHeldVehicle(player);
                    Entity entity = EntityList.createEntityFromNBT(tagCompound, world);
                    if(entity instanceof EntityVehicle)
                    {
                        MinecraftServer server = world.getMinecraftServer();
                        if(server != null && server.getEntityFromUuid(entity.getUniqueID()) == null)
                        {
                            server.addScheduledTask(() ->
                            {
                                //Updates the player capability
                                HeldVehicleDataHandler.setHeldVehicle(player, new NBTTagCompound());

                                //Sets the positions and spawns the entity
                                float rotation = (player.getRotationYawHead() + 90F) % 360.0F;
                                Vec3d heldOffset = ((EntityVehicle) entity).getProperties().getHeldOffset().rotateYaw((float) Math.toRadians(-player.getRotationYawHead()));

                                entity.setPositionAndRotation(clickedVec.x + heldOffset.x * 0.0625D, clickedVec.y, clickedVec.z + heldOffset.z * 0.0625D, rotation, 0F);
                                entity.fallDistance = 0.0F;

                                //Plays place sound
                                world.spawnEntity(entity);
                                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            });
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
        else if(HeldVehicleDataHandler.isHoldingVehicle(player))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getHand() == EnumHand.OFF_HAND) return;

        World world = event.getWorld();
        if(world.isRemote)
        {
            if(event instanceof PlayerInteractEvent.RightClickEmpty || event instanceof PlayerInteractEvent.RightClickItem)
            {
                EntityPlayer player = event.getEntityPlayer();
                if(HeldVehicleDataHandler.isHoldingVehicle(player))
                {
                    if(player.isSneaking())
                    {
                        PacketHandler.INSTANCE.sendToServer(new MessageThrowVehicle());
                    }
                    if(event.isCancelable())
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @Nullable
    private static String getEntityString(Entity entity)
    {
        ResourceLocation resourcelocation = EntityList.getKey(entity);
        return resourcelocation == null ? null : resourcelocation.toString();
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event)
    {
        Entity entity = event.getEntityLiving();
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            this.dropVehicle(player);
        }
    }

    private void dropVehicle(EntityPlayer player)
    {
        if(HeldVehicleDataHandler.isHoldingVehicle(player))
        {
            NBTTagCompound vehicleTag = HeldVehicleDataHandler.getHeldVehicle(player);
            HeldVehicleDataHandler.setHeldVehicle(player, new NBTTagCompound());
            Entity vehicle = EntityList.createEntityFromNBT(vehicleTag, player.world);
            if(vehicle instanceof EntityVehicle)
            {
                float rotation = (player.getRotationYawHead() + 90F) % 360.0F;
                Vec3d heldOffset = ((EntityVehicle) vehicle).getProperties().getHeldOffset().rotateYaw((float) Math.toRadians(-player.getRotationYawHead()));
                vehicle.setPositionAndRotation(player.posX + heldOffset.x * 0.0625D, player.posY + player.getEyeHeight() + heldOffset.y * 0.0625D, player.posZ + heldOffset.z * 0.0625D, rotation, 0F);
                player.world.spawnEntity(vehicle);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            EntityPlayer player = event.player;
            World world = player.world;
            if(!world.isRemote && player.isSneaking())
            {
                int trailerId = SyncedPlayerData.getTrailer(player);
                if(trailerId != -1)
                {
                    Entity entity = world.getEntityByID(trailerId);
                    if(entity instanceof EntityTrailer)
                    {
                        ((EntityTrailer) entity).setPullingEntity(null);
                    }
                    SyncedPlayerData.setTrailer(player, -1);
                }
            }

            if(!world.isRemote && player.isSpectator())
            {
                this.dropVehicle(player);
            }

            Optional<BlockPos> pos = SyncedPlayerData.getGasPumpPos(player);
            if(pos.isPresent())
            {
                TileEntity tileEntity = world.getTileEntity(pos.get());
                if(!(tileEntity instanceof TileEntityGasPump))
                {
                    SyncedPlayerData.setGasPumpPos(player, Optional.absent());
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event)
    {
        if(SyncedPlayerData.getGasPumpPos(event.getEntityPlayer()).isPresent())
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        if(state.getBlock() != ModBlocks.GAS_PUMP && SyncedPlayerData.getGasPumpPos(event.getEntityPlayer()).isPresent())
        {
            event.setCanceled(true);
        }
    }
}
