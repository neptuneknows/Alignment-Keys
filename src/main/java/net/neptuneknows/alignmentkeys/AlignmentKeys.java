package net.neptuneknows.alignmentkeys;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class AlignmentKeys implements ModInitializer {
	public static final String MOD_ID = "alignmentkeys";
	KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(AlignmentKeys.MOD_ID, "custom_category")
	);
	float snapAngleYaw45 = 45.0f;
	float snapAnglePitch1 = 1.0f;

	KeyMapping clockwiseRotation = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate 45° Clockwise",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_PERIOD,
					this.CATEGORY
			));
	KeyMapping counterClockwiseRotation = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Yaw 45° Counterclockwise",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_COMMA,
					this.CATEGORY
			));
	KeyMapping rotatePitchUp = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"Rotate Pitch 1° Up",
					InputConstants.Type.KEYSYM,
					GLFW.GLFW_KEY_UP,
					this.CATEGORY
			));
	KeyMapping rotatePitchDown = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
				"Rotate Pitch 1° Down",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_DOWN,
				this.CATEGORY
			));
	public void rotateYaw45(Minecraft client, int direction) {
		if (client.player == null) return;

		float yaw = client.player.getYRot();
		yaw = (yaw % 360 + 360) % 360;

		float snap = Math.round(yaw / snapAngleYaw45) * snapAngleYaw45;
		snap += snapAngleYaw45 * direction;

		if (snap >= 360) snap -= 360;
		if (snap < 0) snap += 360;

		client.player.setYRot(snap);
	}
	public void rotatePitch1(Minecraft client, int direction) {
		if (client.player == null) return;

		float pitch = client.player.getXRot();
		pitch = (pitch % 360 + 360) % 360;

		float snap = Math.round(pitch / snapAnglePitch1) * snapAnglePitch1;
		snap += snapAnglePitch1 * direction;

		if (snap >= 360) snap -= 360;
		if (snap < 0) snap += 360;

		client.player.setXRot(snap);
	}

	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(clockwiseRotation.consumeClick()) {
				rotateYaw45(client, 1);
			}
			while(counterClockwiseRotation.consumeClick()) {
				rotateYaw45(client, -1);
			}
			while(rotatePitchUp.consumeClick()) {
				rotatePitch1(client, -1);
			}
			while(rotatePitchDown.consumeClick()) {
				rotatePitch1(client, 1);
			}
		});

	}
}