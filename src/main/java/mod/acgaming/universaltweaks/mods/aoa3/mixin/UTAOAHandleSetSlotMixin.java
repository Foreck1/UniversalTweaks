package mod.acgaming.universaltweaks.mods.aoa3.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import nc.container.processor.ContainerSorptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import sonar.fluxnetworks.common.core.ContainerCore;

// Courtesy of jchung01
@Mixin(value = NetHandlerPlayClient.class)
public class UTAOAHandleSetSlotMixin
{
    @Shadow
    private Minecraft client;

    @WrapWithCondition(method = "handleSetSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;putStackInSlot(ILnet/minecraft/item/ItemStack;)V", ordinal = 1))
    private boolean utAOAHandleSetSlotIfAllowed(Container instance, int slotID, ItemStack stack)
    {
        if (!UTConfigMods.AOA.utFixPlayerTickInInventorylessGui) return true;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAOAHandleSetSlot ::: Check inventory-less GUI (from AOA playerTick)");
        return !((Loader.isModLoaded("fluxnetworks") && client.player.openContainer instanceof ContainerCore) || (Loader.isModLoaded("nuclearcraft") && client.player.openContainer instanceof ContainerSorptions));
    }
}