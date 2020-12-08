package com.mrcrayfish.vehicle.proxy;

import com.google.common.base.Optional;
import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.Buttons;
import com.mrcrayfish.controllable.client.Controller;
import com.mrcrayfish.vehicle.VehicleConfig;
import com.mrcrayfish.vehicle.client.ClientEvents;
import com.mrcrayfish.vehicle.client.ControllerEvents;
import com.mrcrayfish.vehicle.client.EntityRaytracer;
import com.mrcrayfish.vehicle.client.HeldVehicleEvents;
import com.mrcrayfish.vehicle.client.audio.MovingSoundHorn;
import com.mrcrayfish.vehicle.client.audio.MovingSoundHornRiding;
import com.mrcrayfish.vehicle.client.audio.MovingSoundVehicle;
import com.mrcrayfish.vehicle.client.audio.MovingSoundVehicleRiding;
import com.mrcrayfish.vehicle.client.gui.GuiEditVehicle;
import com.mrcrayfish.vehicle.client.gui.GuiStorage;
import com.mrcrayfish.vehicle.client.model.CustomLoader;
import com.mrcrayfish.vehicle.client.render.*;
import com.mrcrayfish.vehicle.client.render.tileentity.*;
import com.mrcrayfish.vehicle.client.render.vehicle.*;
import com.mrcrayfish.vehicle.common.entity.HeldVehicleDataHandler;
import com.mrcrayfish.vehicle.common.entity.SyncedPlayerData;
import com.mrcrayfish.vehicle.common.inventory.IStorage;
import com.mrcrayfish.vehicle.entity.EntityHelicopter;
import com.mrcrayfish.vehicle.entity.EntityPlane;
import com.mrcrayfish.vehicle.entity.EntityPoweredVehicle;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.entity.trailer.*;
import com.mrcrayfish.vehicle.entity.vehicle.*;
import com.mrcrayfish.vehicle.init.ModItems;
import com.mrcrayfish.vehicle.init.RegistrationHandler;
import com.mrcrayfish.vehicle.item.ItemKey;
import com.mrcrayfish.vehicle.item.ItemPart;
import com.mrcrayfish.vehicle.item.ItemSprayCan;
import com.mrcrayfish.vehicle.tileentity.*;
import com.mrcrayfish.vehicle.util.FluidUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Author: MrCrayfish
 */
public class ClientProxy implements Proxy
{
    public static final KeyBinding KEY_HORN = new KeyBinding("key.vehicle.horn", Keyboard.KEY_H, "key.categories.vehicle");
    public static final KeyBinding KEY_CYCLE_SEATS  = new KeyBinding("key.vehicle.cycle_seats", Keyboard.KEY_C, "key.categories.vehicle");

    public static boolean controllableLoaded = false;

    private static final WeakHashMap<UUID, Map<SoundType, ITickableSound>> SOUND_TRACKER = new WeakHashMap<>();
    
    @Override
    public void preInit()
    {
        /* Register Vehicles */
        registerVehicleRender(EntityATV.class, new RenderLandVehicleWrapper<>(new RenderATV()));
        registerVehicleRender(EntityDuneBuggy.class, new RenderLandVehicleWrapper<>(new RenderDuneBuggy()));
        registerVehicleRender(EntityGoKart.class, new RenderLandVehicleWrapper<>(new RenderGoKart()));
        registerVehicleRender(EntityShoppingCart.class, new RenderLandVehicleWrapper<>(new RenderShoppingCart()));
        registerVehicleRender(EntityBici.class, new RenderMotorcycleWrapper<>(new RenderBici()));
        registerVehicleRender(EntityBici2.class, new RenderMotorcycleWrapper<>(new RenderBici2()));
        registerVehicleRender(EntityMiniBike.class, new RenderMotorcycleWrapper<>(new RenderMiniBike()));
        registerVehicleRender(EntityBumperCar.class, new RenderLandVehicleWrapper<>(new RenderBumperCar()));
        registerVehicleRender(EntityJetSki.class, new RenderBoatWrapper<>(new RenderJetSki()));
        registerVehicleRender(EntitySpeedBoat.class, new RenderBoatWrapper<>(new RenderSpeedBoat()));
        registerVehicleRender(EntityAluminumBoat.class, new RenderBoatWrapper<>(new RenderAluminumBoat()));
        registerVehicleRender(EntitySmartCar.class, new RenderLandVehicleWrapper<>(new RenderSmartCar()));
        registerVehicleRender(EntitySmartCar2.class, new RenderLandVehicleWrapper<>(new RenderSmartCar2()));
        registerVehicleRender(EntityDeportive.class, new RenderLandVehicleWrapper<>(new RenderDeportive()));
        registerVehicleRender(EntityDeportive2.class, new RenderLandVehicleWrapper<>(new RenderDeportive2()));
        registerVehicleRender(EntityDeportive3.class, new RenderLandVehicleWrapper<>(new RenderDeportive3()));
        registerVehicleRender(EntityTaxi.class, new RenderLandVehicleWrapper<>(new RenderTaxi()));
        registerVehicleRender(EntityPolicial.class, new RenderLandVehicleWrapper<>(new RenderPolicial()));
        registerVehicleRender(EntityPeugeot.class, new RenderLandVehicleWrapper<>(new RenderPeugeot()));
        registerVehicleRender(EntityRenault.class, new RenderLandVehicleWrapper<>(new RenderRenault()));
        registerVehicleRender(EntityLimusina.class, new RenderLandVehicleWrapper<>(new RenderLimusina()));
        registerVehicleRender(EntityLawnMower.class, new RenderLandVehicleWrapper<>(new RenderLawnMower()));
        registerVehicleRender(EntityMoped.class, new RenderMotorcycleWrapper<>(new RenderMoped()));
        registerVehicleRender(EntitySportsPlane.class, new RenderPlaneWrapper<>(new RenderSportsPlane()));
        registerVehicleRender(EntitySportsPlane2.class, new RenderPlaneWrapper<>(new RenderSportsPlane2()));
        registerVehicleRender(EntityGolfCart.class, new RenderLandVehicleWrapper<>(new RenderGolfCart()));
        registerVehicleRender(EntityOffRoader.class, new RenderLandVehicleWrapper<>(new RenderOffRoader()));
        registerVehicleRender(EntityTractor.class, new RenderLandVehicleWrapper<>(new RenderTractor()));
        registerVehicleRender(EntityMiniBus.class, new RenderLandVehicleWrapper<>(new RenderMiniBus()));
        registerVehicleRender(EntityBus.class, new RenderLandVehicleWrapper<>(new RenderBus()));
        registerVehicleRender(EntityDirtBike.class, new RenderMotorcycleWrapper<>(new RenderDirtBike()));


        /* Register Mod Exclusive Vehicles */
        if(Loader.isModLoaded("cfm"))
        {
            registerVehicleRender(EntityCouch.class, new RenderLandVehicleWrapper<>(new RenderCouch()));
            registerVehicleRender(EntityBath.class, new RenderPlaneWrapper<>(new RenderBath()));
            registerVehicleRender(EntitySofacopter.class, new RenderHelicopterWrapper<>(new RenderCouchHelicopter()));
        }

        /* Register Trailers */
        registerVehicleRender(EntityVehicleTrailer.class, new RenderVehicleWrapper<>(new RenderVehicleTrailer()));
        registerVehicleRender(EntityStorageTrailer.class, new RenderVehicleWrapper<>(new RenderStorageTrailer()));
        registerVehicleRender(EntitySeederTrailer.class, new RenderVehicleWrapper<>(new RenderSeederTrailer()));
        registerVehicleRender(EntityFertilizerTrailer.class, new RenderVehicleWrapper<>(new RenderFertilizerTrailer()));
        registerVehicleRender(EntityFluidTrailer.class, new RenderVehicleWrapper<>(new RenderFluidTrailer()));

        /* Client Events */
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(new HeldVehicleEvents());
        MinecraftForge.EVENT_BUS.register(this);

        /* Tile Entity Special Renderer*/
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidExtractor.class, new FluidExtractorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuelDrum.class, new FuelDrumRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVehicleCrate.class, new VehicleCrateRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJack.class, new JackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasPump.class, new GasPumpRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGasPumpTank.class, new GasPumpTankRenderer());

        /* Key Bindings */
        ClientRegistry.registerKeyBinding(KEY_HORN);
        ClientRegistry.registerKeyBinding(KEY_CYCLE_SEATS);

        ModelLoaderRegistry.registerLoader(new CustomLoader());
    }

    private <T extends EntityVehicle & EntityRaytracer.IEntityRaytraceable, R extends AbstractRenderVehicle<T>> void registerVehicleRender(Class<T> clazz, RenderVehicleWrapper<T, R> wrapper)
    {
        RenderingRegistry.registerEntityRenderingHandler(clazz, manager -> new RenderEntityVehicle<>(manager, wrapper));
        VehicleRenderRegistry.registerRenderWrapper(clazz, wrapper);
    }

    @Override
    public void init()
    {
        IItemColor color = (stack, index) ->
        {
            if(index == 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey("color", Constants.NBT.TAG_INT))
            {
                return stack.getTagCompound().getInteger("color");
            }
            return -1;
        };
        RegistrationHandler.Items.getItems().forEach(item ->
        {
            if(item instanceof ItemSprayCan || item instanceof ItemKey || (item instanceof ItemPart && ((ItemPart) item).isColored()) || item == ModItems.MODELS)
            {
                Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, item);
            }
        });
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(resourceManager ->
        {
            FluidUtils.clearCacheFluidColor();
            EntityRaytracer.clearDataForReregistration();
        });
    }

    @Override
    public void postInit()
    {
        if(Loader.isModLoaded("controllable"))
        {
            controllableLoaded = true;
            MinecraftForge.EVENT_BUS.register(new ControllerEvents());
        }
    }

    @Override
    public void playVehicleSound(EntityPlayer player, EntityPoweredVehicle vehicle)
    {
        Minecraft.getMinecraft().addScheduledTask(() ->
        {
            Map<SoundType, ITickableSound> soundMap = SOUND_TRACKER.computeIfAbsent(vehicle.getUniqueID(), uuid -> new HashMap<>());
            if(vehicle.getRidingSound() != null && player.equals(Minecraft.getMinecraft().player))
            {
                ITickableSound sound = soundMap.get(SoundType.ENGINE_RIDING);
                if(sound == null || sound.isDonePlaying() || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
                {
                    sound = new MovingSoundVehicleRiding(player, vehicle);
                    soundMap.put(SoundType.ENGINE_RIDING, sound);
                    Minecraft.getMinecraft().getSoundHandler().playSound(sound);
                }
            }
            if(vehicle.getMovingSound() != null && !player.equals(Minecraft.getMinecraft().player))
            {
                ITickableSound sound = soundMap.get(SoundType.ENGINE);
                if(sound == null || sound.isDonePlaying() || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
                {
                    sound = new MovingSoundVehicle(vehicle);
                    soundMap.put(SoundType.ENGINE, sound);
                    Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundVehicle(vehicle));
                }
            }
            if(vehicle.getHornSound() != null && !player.equals(Minecraft.getMinecraft().player))
            {
                ITickableSound sound = soundMap.get(SoundType.HORN);
                if(sound == null || sound.isDonePlaying() || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
                {
                    sound = new MovingSoundHorn(vehicle);
                    soundMap.put(SoundType.HORN, sound);
                    Minecraft.getMinecraft().getSoundHandler().playSound(sound);
                }
            }
            if(vehicle.getHornRidingSound() != null && player.equals(Minecraft.getMinecraft().player))
            {
                ITickableSound sound = soundMap.get(SoundType.HORN_RIDING);
                if(sound == null || sound.isDonePlaying() || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
                {
                    sound = new MovingSoundHornRiding(player, vehicle);
                    soundMap.put(SoundType.HORN_RIDING, sound);
                    Minecraft.getMinecraft().getSoundHandler().playSound(sound);
                }
            }
        });
    }

    @Override
    public void playSound(SoundEvent soundEvent, BlockPos pos, float volume, float pitch)
    {
        ISound sound = new PositionedSoundRecord(soundEvent, SoundCategory.BLOCKS, volume, pitch, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F);
        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().getSoundHandler().playSound(sound));
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onFogDensity(EntityViewRenderEvent.FogDensity event)
    {
        /*if(event.getEntity().isInsideOfMaterial(ModMaterials.FUELIUM))
        {
            event.setDensity(0.5F);
        }
        else
        {
            event.setDensity(0.01F);
        }
        event.setCanceled(true);*/
    }

    @Override
    public void openVehicleEditWindow(int entityId, int windowId)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = player.getEntityWorld();
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof EntityPoweredVehicle)
        {
            EntityPoweredVehicle poweredVehicle = (EntityPoweredVehicle) entity;
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditVehicle(poweredVehicle.getVehicleInventory(), poweredVehicle, player));
            player.openContainer.windowId = windowId;
        }
    }

    @Override
    public void syncStorageInventory(int entityId, NBTTagCompound tagCompound)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof IStorage)
        {
            IStorage wrapper = (IStorage) entity;
            wrapper.getInventory().readFromNBT(tagCompound);
        }
    }

    @Override
    public void openStorageWindow(int entityId, int windowId)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        World world = player.getEntityWorld();
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof IStorage)
        {
            IStorage wrapper = (IStorage) entity;
            Minecraft.getMinecraft().displayGuiScreen(new GuiStorage(player.inventory, wrapper.getInventory()));
            player.openContainer.windowId = windowId;
        }
    }

    @Override
    public EntityPoweredVehicle.AccelerationDirection getAccelerationDirection(EntityLivingBase entity)
    {
        if(controllableLoaded)
        {
            if(Minecraft.getMinecraft().currentScreen != null)
            {
                return EntityPoweredVehicle.AccelerationDirection.NONE;
            }

            Controller controller = Controllable.getController();
            if(controller != null)
            {
                if(VehicleConfig.CLIENT.controller.useTriggers)
                {
                    if(controller.getRTriggerValue() != 0.0F && controller.getLTriggerValue() != 0.0F)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.CHARGING;
                    }
                    else if(controller.getRTriggerValue() != 0.0F && controller.getLTriggerValue() == 0.0F)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.FORWARD;
                    }
                    else if(controller.getLTriggerValue() != 0.0F && controller.getRTriggerValue() == 0.0F)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.REVERSE;
                    }
                }
                else
                {
                    boolean forward = controller.getButtonsStates().getState(Buttons.A);
                    boolean reverse = controller.getButtonsStates().getState(Buttons.B);
                    if(forward && reverse)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.CHARGING;
                    }
                    else if(forward)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.FORWARD;
                    }
                    else if(reverse)
                    {
                        return EntityPoweredVehicle.AccelerationDirection.REVERSE;
                    }
                }
            }
        }

        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        boolean forward = settings.keyBindForward.isKeyDown();
        boolean reverse = settings.keyBindBack.isKeyDown();
        if(forward && reverse)
        {
            return EntityPoweredVehicle.AccelerationDirection.CHARGING;
        }
        else if(forward)
        {
            return EntityPoweredVehicle.AccelerationDirection.FORWARD;
        }
        else if(reverse)
        {
            return EntityPoweredVehicle.AccelerationDirection.REVERSE;
        }

        return EntityPoweredVehicle.AccelerationDirection.fromEntity(entity);
    }

    @Override
    public EntityPoweredVehicle.TurnDirection getTurnDirection(EntityLivingBase entity)
    {
        if(controllableLoaded)
        {
            if(Minecraft.getMinecraft().currentScreen != null)
            {
                return EntityPoweredVehicle.TurnDirection.FORWARD;
            }

            Controller controller = Controllable.getController();
            if(controller != null)
            {
                if(controller.getLThumbStickXValue() > 0.0F)
                {
                    return EntityPoweredVehicle.TurnDirection.RIGHT;
                }
                if(controller.getLThumbStickXValue() < 0.0F)
                {
                    return EntityPoweredVehicle.TurnDirection.LEFT;
                }
                if(controller.isButtonPressed(Buttons.DPAD_RIGHT))
                {
                    return EntityPoweredVehicle.TurnDirection.RIGHT;
                }
                if(controller.isButtonPressed(Buttons.DPAD_LEFT))
                {
                    return EntityPoweredVehicle.TurnDirection.LEFT;
                }
            }
        }
        if(entity.moveStrafing < 0)
        {
            return EntityPoweredVehicle.TurnDirection.RIGHT;
        }
        else if(entity.moveStrafing > 0)
        {
            return EntityPoweredVehicle.TurnDirection.LEFT;
        }
        return EntityPoweredVehicle.TurnDirection.FORWARD;
    }

    @Override
    public float getTargetTurnAngle(EntityPoweredVehicle vehicle, boolean drifting)
    {
        EntityPoweredVehicle.TurnDirection direction = vehicle.getTurnDirection();
        if(vehicle.getControllingPassenger() != null)
        {
            if(controllableLoaded)
            {
                if(Minecraft.getMinecraft().currentScreen != null)
                {
                    return 0.0F;
                }

                Controller controller = Controllable.getController();
                if(controller != null)
                {
                    float turnNormal = controller.getLThumbStickXValue();
                    if(turnNormal != 0.0F)
                    {
                        float newTurnAngle = vehicle.turnAngle + ((vehicle.getMaxTurnAngle() * -turnNormal) - vehicle.turnAngle) * 0.15F;
                        if(Math.abs(newTurnAngle) > vehicle.getMaxTurnAngle())
                        {
                            return vehicle.getMaxTurnAngle() * direction.getDir();
                        }
                        return newTurnAngle;
                    }
                }
            }

            if(direction != EntityPoweredVehicle.TurnDirection.FORWARD)
            {
                float amount = direction.getDir() * vehicle.getTurnSensitivity();
                if(drifting)
                {
                    amount *= 0.45F;
                }
                float newTurnAngle = vehicle.turnAngle + amount;
                if(Math.abs(newTurnAngle) > vehicle.getMaxTurnAngle())
                {
                    return vehicle.getMaxTurnAngle() * direction.getDir();
                }
                return newTurnAngle;
            }
        }

        if(drifting)
        {
            return vehicle.turnAngle * 0.95F;
        }
        return vehicle.turnAngle * 0.75F;
    }

    @Override
    public boolean isDrifting()
    {
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                if(controller.isButtonPressed(Buttons.RIGHT_BUMPER))
                {
                    return true;
                }
            }
        }
        return Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
    }

    @Override
    public boolean isHonking()
    {
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                if(controller.isButtonPressed(Buttons.RIGHT_THUMB_STICK))
                {
                    return true;
                }
            }
        }
        return ClientProxy.KEY_HORN.isKeyDown();
    }

    @Override
    public EntityPlane.FlapDirection getFlapDirection()
    {
        boolean flapUp = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
        boolean flapDown = Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                flapUp |= controller.isButtonPressed(Buttons.RIGHT_BUMPER);
                flapDown |= controller.isButtonPressed(Buttons.LEFT_BUMPER);
            }
        }
        return EntityPlane.FlapDirection.fromInput(flapUp, flapDown);
    }

    @Override
    public EntityHelicopter.AltitudeChange getAltitudeChange()
    {
        boolean flapUp = Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown();
        boolean flapDown = Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                flapUp |= controller.isButtonPressed(Buttons.RIGHT_BUMPER);
                flapDown |= controller.isButtonPressed(Buttons.LEFT_BUMPER);
            }
        }
        return EntityHelicopter.AltitudeChange.fromInput(flapUp, flapDown);
    }

    @Override
    public float getTravelDirection(EntityHelicopter vehicle)
    {
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                float xAxis = controller.getLThumbStickXValue();
                float yAxis = controller.getLThumbStickYValue();
                if(xAxis != 0.0F || yAxis != 0.0F)
                {
                    float angle = (float) Math.toDegrees(Math.atan2(-xAxis, yAxis)) + 180F;
                    return vehicle.rotationYaw + angle;
                }
            }
        }

        EntityPoweredVehicle.AccelerationDirection accelerationDirection = vehicle.getAcceleration();
        EntityPoweredVehicle.TurnDirection turnDirection = vehicle.getTurnDirection();
        if(vehicle.getControllingPassenger() != null)
        {
            if(accelerationDirection == EntityPoweredVehicle.AccelerationDirection.FORWARD)
            {
                return vehicle.rotationYaw + turnDirection.getDir() * -45F;
            }
            else if(accelerationDirection == EntityPoweredVehicle.AccelerationDirection.REVERSE)
            {
                return vehicle.rotationYaw + 180F + turnDirection.getDir() * 45F;
            }
            else
            {
                return vehicle.rotationYaw + turnDirection.getDir() * -90F;
            }
        }
        return vehicle.rotationYaw;
    }

    @Override
    public float getTravelSpeed(EntityHelicopter helicopter)
    {
        if(controllableLoaded)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                float xAxis = controller.getLThumbStickXValue();
                float yAxis = controller.getLThumbStickYValue();
                if(xAxis != 0.0F || yAxis != 0.0F)
                {
                    return (float) Math.min(1.0, Math.sqrt(Math.pow(xAxis, 2) + Math.pow(yAxis, 2)));
                }
            }
        }
        return helicopter.getAcceleration() != EntityPoweredVehicle.AccelerationDirection.NONE || helicopter.getTurnDirection() != EntityPoweredVehicle.TurnDirection.FORWARD ? 1.0F : 0.0F;
    }

    @Override
    public float getPower(EntityPoweredVehicle vehicle)
    {
        if(controllableLoaded && VehicleConfig.CLIENT.controller.useTriggers)
        {
            Controller controller = Controllable.getController();
            if(controller != null)
            {
                EntityPoweredVehicle.AccelerationDirection accelerationDirection = vehicle.getAcceleration();
                if(accelerationDirection == EntityPoweredVehicle.AccelerationDirection.FORWARD)
                {
                    return controller.getRTriggerValue();
                }
                else if(accelerationDirection == EntityPoweredVehicle.AccelerationDirection.REVERSE)
                {
                    return controller.getLTriggerValue();
                }
            }
        }
        return 1.0F;
    }

    @Override
    public void syncEntityFluid(int entityId, FluidStack stack)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity != null && entity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
        {
            IFluidHandler handler = entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            if(handler instanceof FluidTank)
            {
                FluidTank tank = (FluidTank) handler;
                tank.setFluid(stack);
            }
        }
    }

    @Override
    public void syncPlayerSeat(int entityId, int seatIndex, UUID uuid)
    {
        EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
        if(clientPlayer != null)
        {
            Entity entity = clientPlayer.world.getEntityByID(entityId);
            if(entity instanceof EntityVehicle)
            {
                EntityVehicle vehicle = (EntityVehicle) entity;
                vehicle.getSeatTracker().setSeatIndex(seatIndex, uuid);
            }
        }
    }

    @Override
    public void syncHeldVehicle(int entityId, NBTTagCompound tagCompound)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof EntityPlayer)
        {
            HeldVehicleDataHandler.setHeldVehicle((EntityPlayer) entity, tagCompound);
        }
    }

    @Override
    public void syncPlayerData(int entityId, int trailer, Optional<BlockPos> gasPumpPos)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            SyncedPlayerData.setTrailer(player, trailer);
            SyncedPlayerData.setGasPumpPos(player, gasPumpPos);
        }
    }

    @Override
    public void syncTrailer(int entityId, int trailer)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            SyncedPlayerData.setTrailer(player, trailer);
        }
    }

    @Override
    public void syncGasPumpPos(int entityId, Optional<BlockPos> gasPumpPos)
    {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(entityId);
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            SyncedPlayerData.setGasPumpPos(player, gasPumpPos);
        }
    }

    @Override
    public void spawnWheelParticle(BlockPos pos, IBlockState state, double x, double y, double z, Vec3d motion)
    {
        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;
        if(world != null)
        {
            world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, x, y, z, motion.x, motion.y, motion.z, Block.getStateId(state));
        }
    }

    private enum SoundType
    {
        ENGINE,
        ENGINE_RIDING,
        HORN,
        HORN_RIDING;
    }
}
