package org.johnnymod.holdmyguns.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = HeldItemRenderer.class, priority = 500)
public class HMIVPBPatchMixin {

    // Shadows the private method so we can call it directly,
    // bypassing HMI's call-site redirect in renderHand
    @Shadow(remap = false)
    private void method_3228(
            AbstractClientPlayerEntity player,
            float tickProgress,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack item,
            float equipProgress,
            MatrixStack matrices,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            int light
    ) { throw new AssertionError(); }

    // Ignore Red-highlighted methods, they are just the deobfuscated method signatures for the target method
    @WrapOperation(
            method = "method_22976(FLnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_746;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/class_759;method_3228(Lnet/minecraft/class_742;FFLnet/minecraft/class_1268;FLnet/minecraft/class_1799;FLnet/minecraft/class_4587;Lnet/minecraft/class_11659;I)V"
            )
    )
    private void patchHMIForVPB(
            HeldItemRenderer instance,
            AbstractClientPlayerEntity player,
            float tickProgress,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack item,
            float equipProgress,
            MatrixStack matrices,
            OrderedRenderCommandQueue orderedRenderCommandQueue,
            int light,
            Operation<Void> original
    ) {
        if (isVpbItem(item)) {
            this.method_3228(
                    player, tickProgress, pitch, hand,
                    swingProgress, item, equipProgress,
                    matrices, orderedRenderCommandQueue, light
            );
        } else {
            original.call(
                    instance, player, tickProgress, pitch, hand,
                    swingProgress, item, equipProgress,
                    matrices, orderedRenderCommandQueue, light
            );
        }
    }

    private static boolean isVpbItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Identifier id = Registries.ITEM.getId(stack.getItem());
        return "pointblank".equals(id.getNamespace());
    }
}