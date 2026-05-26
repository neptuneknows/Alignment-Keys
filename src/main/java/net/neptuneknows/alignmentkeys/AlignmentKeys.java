package net.neptuneknows.alignmentkeys;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.neptuneknows.alignmentkeys.config.MidnightlibConfig;

import static net.neptuneknows.alignmentkeys.config.MidnightlibConfig.customisableYawSnap;
import static net.neptuneknows.alignmentkeys.config.MidnightlibConfig.customisablePitchSnap;

public class AlignmentKeys implements ModInitializer {
	public static final String MOD_ID = "alignmentkeys";
	KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(AlignmentKeys.MOD_ID, "custom_category")
	);

	KeyMapping clockwiseRotation = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Yaw Clockwise",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_PERIOD,
					this.CATEGORY
			));
	KeyMapping counterClockwiseRotation = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Yaw Counterclockwise",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_COMMA,
					this.CATEGORY
			));
	KeyMapping rotatePitchUp = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Pitch Up",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_UP,
					this.CATEGORY
			));
	KeyMapping rotatePitchDown = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Pitch Down",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_DOWN,
				this.CATEGORY
			));
	KeyMapping rotateYaw180 = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Yaw 180°",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_R,
					this.CATEGORY
			));
	public void rotateYaw(Minecraft client, int direction) {
		if (client.player == null) return;

		float yaw = client.player.getYRot();
		yaw = (yaw % 360 + 360) % 360;

		float snap = Math.round(yaw / customisableYawSnap) * customisableYawSnap;
		snap += customisableYawSnap * direction;

		client.player.setYRot(snap);
	}
	public void rotateYaw180(Minecraft client, int direction) {
		if (client.player == null) return;

		float yaw = client.player.getYRot();
		yaw = (yaw % 360 + 360) % 360;

		float snap = (yaw /  180) * 180;
		snap += 180 * direction;

		client.player.setYRot(snap);

	}
	public void rotatePitch(Minecraft client, int direction) {
		if (client.player == null) return;

		float pitch = client.player.getXRot();

		float snap = Math.round(pitch / customisablePitchSnap) * customisablePitchSnap;
		snap += customisablePitchSnap * direction;

		snap = Math.clamp(snap, -90, 90);

		client.player.setXRot(snap);
	}

	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(clockwiseRotation.consumeClick()) {
				rotateYaw(client, 1);
			}
			while(counterClockwiseRotation.consumeClick()) {
				rotateYaw(client, -1);
			}
			while(rotatePitchUp.consumeClick()) {
				rotatePitch(client, -1);
			}
			while(rotatePitchDown.consumeClick()) {
				rotatePitch(client, 1);
			}
			while(rotateYaw180.consumeClick()) {
				rotateYaw180(client, 1);
			}
		});

		MidnightlibConfig.init("Alignment Keys", MidnightlibConfig.class);

	}
}