package com.jelly.farmhelperv2.mixin.network;

import com.jelly.farmhelperv2.event.SpawnObjectEvent;
import com.jelly.farmhelperv2.event.SpawnParticleEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "handleParticles", at = @At(value = "HEAD"))
    public void handleParticles(S2APacketParticles packetIn, CallbackInfo ci) {
        SpawnParticleEvent event = new SpawnParticleEvent(
                packetIn.getParticleType(),
                packetIn.isLongDistance(),
                packetIn.getXCoordinate(),
                packetIn.getYCoordinate(),
                packetIn.getZCoordinate(),
                packetIn.getXOffset(),
                packetIn.getYOffset(),
                packetIn.getZOffset(),
                packetIn.getParticleArgs()
        );
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "handleSpawnObject", at = @At(value = "HEAD"))
    public void handleSpawnObject(S0EPacketSpawnObject packetIn, CallbackInfo ci) {
        SpawnObjectEvent event = new SpawnObjectEvent(
                packetIn.getEntityID(),
                packetIn.getX() / 32f,
                packetIn.getY() / 32f,
                packetIn.getZ() / 32f,
                packetIn.getSpeedX(),
                packetIn.getSpeedY(),
                packetIn.getSpeedZ(),
                packetIn.getYaw(),
                packetIn.getPitch(),
                packetIn.getType()
        );
        MinecraftForge.EVENT_BUS.post(event);
    }
}
