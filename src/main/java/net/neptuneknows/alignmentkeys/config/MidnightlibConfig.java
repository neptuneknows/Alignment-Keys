package net.neptuneknows.alignmentkeys.config;

import eu.midnightdust.lib.config.MidnightConfig;


public class MidnightlibConfig extends MidnightConfig {
    public static final String SLIDERS = "sliders";
    @Entry(category = SLIDERS, name = "Change yaw snap angle.",isSlider = true, min = 1, max = 360) public static int customisableYawSnap = 45;
    @Entry(category = SLIDERS, name = "Change pitch snap angle.",isSlider = true, min = 1, max = 180) public static int customisablePitchSnap = 1;
}
