package studio.blockops.vyom.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class SystemProperties {
	private static boolean useOnlySpringConfig = false;
	private static SystemProperties CONFIG;

	/**
	 * Returns the static config instance. If the config is passed as a Spring
	 * bean by the application this instance shouldn't be used This method is
	 * mainly used for testing purposes (Autowired fields are initialized with
	 * this static instance but when running within Spring context they replaced
	 * with the bean config instance)
	 */
	public static SystemProperties getDefault() {
		return useOnlySpringConfig ? null : getSpringDefault();
	}

	static SystemProperties getSpringDefault() {
		if (CONFIG == null) {
			CONFIG = new SystemProperties();
		}
		return CONFIG;
	}

	/**
	 * Marks config accessor methods which need to be called (for value
	 * validation) upon config creation or modification
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ValidateMe {
	};

	/**
	 * Used mostly for testing purposes to ensure the application refers only to
	 * the config passed as a Spring bean. If this property is set to true
	 * {@link #getDefault()} returns null
	 */
	public static void setUseOnlySpringConfig(boolean useOnlySpringConfig) {
		SystemProperties.useOnlySpringConfig = useOnlySpringConfig;
	}

	static boolean isUseOnlySpringConfig() {
		return useOnlySpringConfig;
	}

}
