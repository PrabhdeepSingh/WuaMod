package com.prabhdeepsingh.wurmunlimited.mods.wuamod;

import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WuaMod implements WurmServerMod, Configurable, Initable, PreInitable {
	private boolean enableCustomWebInterface = true;
	private static Logger logger = Logger.getLogger(WuaMod.class.getName());

	@Override
	public void configure(Properties properties) {
		this.enableCustomWebInterface = Boolean.valueOf(properties.getProperty("enableCustomWebInterface", Boolean.toString(enableCustomWebInterface)));
		logger.log(Level.INFO, "enableCustomWebInterface: " + enableCustomWebInterface);
	}

	@Override
	public void preInit () {

	}

	@Override
	public void init() {
		if (enableCustomWebInterface) {
			try {

				if (WuaMod.class.getResource("/resources/WebInterface.class") == null) {
					logger.log(Level.WARNING, "WuaMod: Unable to find WebInterface.class");
					return;
				}

				if (WuaMod.class.getResource("/resources/WebInterfaceImpl$1.class") == null) {
					logger.log(Level.WARNING, "WuaMod: Unable to find WebInterfaceImpl$1.class");
					return;
				}

				if (WuaMod.class.getResource("/resources/WebInterfaceImpl.class") == null) {
					logger.log(Level.WARNING, "WuaMod: Unable to find WebInterfaceImpl.class");
					return;
				}

				HookManager.getInstance().getClassPool().makeClass(WuaMod.class.getResourceAsStream("/resources/WebInterface.class"));
				HookManager.getInstance().getClassPool().makeClass(WuaMod.class.getResourceAsStream("/resources/WebInterfaceImpl$1.class"));
				HookManager.getInstance().getClassPool().makeClass(WuaMod.class.getResourceAsStream("/resources/WebInterfaceImpl.class"));
			} catch (IOException | RuntimeException e) {
				logger.log(Level.WARNING, "WuaMod: " + e.toString());
				logger.log(Level.WARNING, "WuaMod: " + e.getMessage());

				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				logger.log(Level.WARNING, "WuaMod: " + errors.toString());
			}

		}
	}
}
