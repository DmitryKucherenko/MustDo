import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import javax.inject.Inject
import javax.inject.Singleton

/**
 * To request AutoStart permission based on [Brand].
 */
@Singleton
class AutoStartPermissionManager @Inject constructor(
     private val context: Context
) {

    /**
     * Request AutoStart permission based on [Brand] type.
     * Note-> No permission required for [Brand.OTHER].
     */
    fun requestAutoStartPermission() {
        when (Build.BRAND.uppercase().toEnum(Brand::name, Brand.OTHER)) {
            Brand.XIAOMI, Brand.REDMI -> xiaomiAutoStart()
            Brand.NOKIA -> nokiaAutoStart()
            Brand.LETV -> letvAutoStart()
            Brand.ASUS -> asusAutoStart()
            Brand.HONOR -> honorAutoStart()
            Brand.OPPO -> oppoAutoStart()
            Brand.VIVO -> vivoAutoStart()
            Brand.OTHER -> {}
        }
    }

    inline fun <reified T : Enum<T>> String.toEnum(value: (T) -> String, def: T): T {
        return enumValues<T>().firstOrNull { value(it) == this } ?: def
    }

    private enum class Brand {
        REDMI,
        XIAOMI,
        NOKIA,
        LETV,
        ASUS,
        HONOR,
        OPPO,
        VIVO,
        OTHER
    }

    /**
     * Request AutoStart permission for [Brand.XIAOMI] and [Brand.REDMI].
     */
    private fun xiaomiAutoStart() {
        if (isPackageExists(XIAOMI_MAIN)) {
            try {
                startAutoStartActivity(context, XIAOMI_MAIN, XIAOMI_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun startAutoStartActivity(
        context: Context,
        packageName: String,
        componentName: String
    ) {
        val intentAutoStartPage = Intent().apply {
            component = ComponentName(packageName, componentName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intentAutoStartPage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Request AutoStart permission for [Brand.NOKIA].
     */
    private fun nokiaAutoStart() {
        if (isPackageExists(NOKIA_MAIN)) {
            try {
                startAutoStartActivity(context, NOKIA_MAIN, NOKIA_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Request AutoStart permission for [Brand.LETV].
     */
    private fun letvAutoStart() {
        if (isPackageExists(LETV_MAIN)) {
            try {
                startAutoStartActivity(context, LETV_MAIN, LETV_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Request AutoStart permission for [Brand.ASUS].
     */
    private fun asusAutoStart() {
        if (isPackageExists(ASUS_MAIN)) {
            try {
                startAutoStartActivity(context, ASUS_MAIN, ASUS_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Return true if requested package exist false otherwise.
     */
    private fun isPackageExists(targetPackage: String): Boolean {
        val packages = context.packageManager.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName.equals(targetPackage)) return true
        }

        return false
    }

    /**
     * Request AutoStart permission for [Brand.HONOR].
     */
    private fun honorAutoStart() {
        if (isPackageExists(HONOR_MAIN)) {
            try {
                startAutoStartActivity(context, HONOR_MAIN, HONOR_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Request AutoStart permission for [Brand.OPPO].
     */
    private fun oppoAutoStart() {
        if (isPackageExists(OPPO_MAIN) || isPackageExists(OPPO_FALLBACK)) {
            try {
                startAutoStartActivity(context, OPPO_MAIN, OPPO_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()

                try {
                    startAutoStartActivity(context, OPPO_FALLBACK, OPPO_COMPONENT_FALLBACK)

                } catch (e: Exception) {
                    e.printStackTrace()

                    try {
                        startAutoStartActivity(context, OPPO_MAIN, OPPO_COMPONENT_FALLBACK_A)

                    } catch (exx: Exception) {
                        exx.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * Request AutoStart permission for [Brand.VIVO].
     */
    private fun vivoAutoStart() {
        if (isPackageExists(VIVO_MAIN) || isPackageExists(VIVO_FALLBACK)) {
            try {
                startAutoStartActivity(context, VIVO_MAIN, VIVO_COMPONENT)

            } catch (e: Exception) {
                e.printStackTrace()

                try {
                    startAutoStartActivity(context, VIVO_FALLBACK, VIVO_COMPONENT_FALLBACK)

                } catch (e: Exception) {
                    e.printStackTrace()

                    try {
                        startAutoStartActivity(context, VIVO_MAIN, VIVO_COMPONENT_FALLBACK_A)

                    } catch (exx: Exception) {
                        exx.printStackTrace()
                    }
                }
            }


        }
    }
    private companion object BrandPackage {

        // Xiaomi
        private val XIAOMI_MAIN = "com.miui.securitycenter"
        private val XIAOMI_COMPONENT = "com.miui.permcenter.autostart.AutoStartManagementActivity"

        // Nokia
        private val NOKIA_MAIN = "com.evenwell.powersaving.g3"
        private val NOKIA_COMPONENT = "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"

        // Letv
        private val LETV_MAIN = "com.letv.android.letvsafe"
        private val LETV_COMPONENT = "com.letv.android.letvsafe.AutobootManageActivity"

        // ASUS ROG
        private val ASUS_MAIN = "com.asus.mobilemanager"
        private val ASUS_COMPONENT = "com.asus.mobilemanager.powersaver.PowerSaverSettings"

        // Honor
        private val HONOR_MAIN = "com.huawei.systemmanager"
        private val HONOR_COMPONENT = "com.huawei.systemmanager.optimize.process.ProtectActivity"

        // Oppo
        private val OPPO_MAIN = "com.coloros.safecenter"
        private val OPPO_FALLBACK = "com.oppo.safe"
        private val OPPO_COMPONENT = "com.coloros.safecenter.permission.startup.StartupAppListActivity"
        private val OPPO_COMPONENT_FALLBACK = "com.oppo.safe.permission.startup.StartupAppListActivity"
        private val OPPO_COMPONENT_FALLBACK_A = "com.coloros.safecenter.startupapp.StartupAppListActivity"

        // Vivo
        private val VIVO_MAIN = "com.iqoo.secure"
        private val VIVO_FALLBACK = "com.vivo.permissionmanager"
        private val VIVO_COMPONENT = "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
        private val VIVO_COMPONENT_FALLBACK = "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
        private val VIVO_COMPONENT_FALLBACK_A = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
    }
}




