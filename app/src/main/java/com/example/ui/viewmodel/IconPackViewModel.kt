package com.example.ui.viewmodel

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.IconDataProvider
import com.example.data.local.CustomGlassIcon
import com.example.data.local.IconPackDatabase
import com.example.data.repository.IconPackRepository
import com.example.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class InstalledAppItem(
    val label: String,
    val packageName: String,
    val activityName: String,
    val isThemed: Boolean
)

enum class AppScreen(val title: String) {
    ICONS("Icons"),
    APPLY("Apply"),
    STUDIO("Studio"),
    WALLPAPERS("Wallpapers"),
    REQUEST("Request"),
    ABOUT("About")
}

class IconPackViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: IconPackRepository

    init {
        val db = IconPackDatabase.getDatabase(application)
        repository = IconPackRepository(db.iconPackDao())
    }

    // Navigation & General UI
    private val _currentScreen = MutableStateFlow(AppScreen.ICONS)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // Search and Filter in Showcase
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(IconCategory.ALL)
    val selectedCategory: StateFlow<IconCategory> = _selectedCategory.asStateFlow()

    private val _isCompactGrid = MutableStateFlow(false)
    val isCompactGrid: StateFlow<Boolean> = _isCompactGrid.asStateFlow()

    private val _inspectingIcon = MutableStateFlow<IconItem?>(null)
    val inspectingIcon: StateFlow<IconItem?> = _inspectingIcon.asStateFlow()

    val filteredIcons: StateFlow<List<IconItem>> = combine(
        _searchQuery,
        _selectedCategory
    ) { query, category ->
        IconDataProvider.icons.filter { icon ->
            val matchesQuery = query.isEmpty() ||
                icon.name.contains(query, ignoreCase = true) ||
                icon.componentName.contains(query, ignoreCase = true) ||
                icon.description.contains(query, ignoreCase = true)
            val matchesCategory = category == IconCategory.ALL || icon.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IconDataProvider.icons)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: IconCategory) {
        _selectedCategory.value = category
    }

    fun toggleGridDensity() {
        _isCompactGrid.value = !_isCompactGrid.value
    }

    fun inspectIcon(icon: IconItem?) {
        _inspectingIcon.value = icon
    }

    // Launchers
    private val _installedLaunchers = MutableStateFlow<Set<String>>(emptySet())
    val installedLaunchers: StateFlow<Set<String>> = _installedLaunchers.asStateFlow()

    fun checkInstalledLaunchers() {
        viewModelScope.launch(Dispatchers.IO) {
            val pm = getApplication<Application>().packageManager
            val installed = mutableSetOf<String>()
            for (launcher in IconDataProvider.supportedLaunchers) {
                try {
                    pm.getPackageInfo(launcher.packageName, 0)
                    installed.add(launcher.packageName)
                } catch (_: PackageManager.NameNotFoundException) {
                    // Not installed
                }
            }
            _installedLaunchers.value = installed
        }
    }

    // Apply Launcher
    fun applyToLauncher(context: Context, launcher: LauncherInfo) {
        val isInstalled = _installedLaunchers.value.contains(launcher.packageName)
        if (isInstalled) {
            try {
                // Direct icon pack intent depending on launcher
                val intent = Intent("com.novalauncher.THEME")
                intent.putExtra("token", "apply")
                intent.putExtra("type", "icon_pack")
                intent.putExtra("package", context.packageName)
                intent.setPackage(launcher.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                Toast.makeText(context, "Applying Liquid Glass to ${launcher.name}", Toast.LENGTH_SHORT).show()
                return
            } catch (_: Exception) {
                // Fallback to opening launcher
                try {
                    val launchIntent = context.packageManager.getLaunchIntentForPackage(launcher.packageName)
                    if (launchIntent != null) {
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(launchIntent)
                        Toast.makeText(context, "Opened ${launcher.name}. Follow steps in guide to apply.", Toast.LENGTH_LONG).show()
                        return
                    }
                } catch (_: Exception) {}
            }
        }
        // If not installed, open Play Store or browser
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(launcher.playStoreUrl))
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(marketIntent)
        } catch (_: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${launcher.packageName}"))
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(webIntent)
        }
    }

    // Glass Studio State
    val customIcons: StateFlow<List<CustomGlassIcon>> = repository.allCustomIcons
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _studioShape = MutableStateFlow(GlassShape.SQUIRCLE)
    val studioShape: StateFlow<GlassShape> = _studioShape.asStateFlow()

    private val _studioStyle = MutableStateFlow(IconDataProvider.glassStyles.first())
    val studioStyle: StateFlow<GlassStyleOption> = _studioStyle.asStateFlow()

    private val _studioOpacity = MutableStateFlow(0.75f)
    val studioOpacity: StateFlow<Float> = _studioOpacity.asStateFlow()

    private val _studioRefraction = MutableStateFlow(0.80f)
    val studioRefraction: StateFlow<Float> = _studioRefraction.asStateFlow()

    private val _studioGlyph = MutableStateFlow("🫧")
    val studioGlyph: StateFlow<String> = _studioGlyph.asStateFlow()

    fun setStudioShape(shape: GlassShape) { _studioShape.value = shape }
    fun setStudioStyle(style: GlassStyleOption) { _studioStyle.value = style }
    fun setStudioOpacity(opacity: Float) { _studioOpacity.value = opacity }
    fun setStudioRefraction(refraction: Float) { _studioRefraction.value = refraction }
    fun setStudioGlyph(glyph: String) { _studioGlyph.value = glyph }

    fun saveCustomGlassIcon(title: String) {
        viewModelScope.launch {
            val icon = CustomGlassIcon(
                title = title.ifBlank { "Glass Icon #${System.currentTimeMillis() % 1000}" },
                glyphName = _studioGlyph.value,
                styleName = _studioStyle.value.name,
                shapeName = _studioShape.value.name,
                opacity = _studioOpacity.value,
                refraction = _studioRefraction.value,
                primaryColorHex = "#${Integer.toHexString(_studioStyle.value.colorStart.value.toInt())}",
                secondaryColorHex = "#${Integer.toHexString(_studioStyle.value.colorEnd.value.toInt())}"
            )
            repository.saveCustomIcon(icon)
            Toast.makeText(getApplication(), "Custom icon saved to Studio!", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteCustomIcon(id: Long) {
        viewModelScope.launch {
            repository.deleteCustomIcon(id)
        }
    }

    // Wallpapers
    private val _selectedWallpaperForPreview = MutableStateFlow<WallpaperItem?>(null)
    val selectedWallpaperForPreview: StateFlow<WallpaperItem?> = _selectedWallpaperForPreview.asStateFlow()

    fun previewWallpaper(wallpaper: WallpaperItem?) {
        _selectedWallpaperForPreview.value = wallpaper
    }

    fun setAsDeviceWallpaper(context: Context, wallpaper: WallpaperItem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wm = WallpaperManager.getInstance(context)
                val bitmap: Bitmap = if (wallpaper.drawableRes != null) {
                    BitmapFactory.decodeResource(context.resources, wallpaper.drawableRes)
                } else {
                    // Generate a high quality 1080x1920 gradient bitmap
                    val bmp = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bmp)
                    val paint = Paint()
                    val shader = android.graphics.LinearGradient(
                        0f, 0f, 1080f, 1920f,
                        wallpaper.gradientColors.map { it.hashCode() }.toIntArray(),
                        null,
                        android.graphics.Shader.TileMode.CLAMP
                    )
                    paint.shader = shader
                    canvas.drawRect(0f, 0f, 1080f, 1920f, paint)
                    bmp
                }
                wm.setBitmap(bitmap)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Wallpaper applied successfully!", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Could not set wallpaper: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // App Scanner & Icon Request
    private val _installedApps = MutableStateFlow<List<InstalledAppItem>>(emptyList())
    val installedApps: StateFlow<List<InstalledAppItem>> = _installedApps.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _selectedMissingApps = MutableStateFlow<Set<String>>(emptySet())
    val selectedMissingApps: StateFlow<Set<String>> = _selectedMissingApps.asStateFlow()

    fun scanDeviceApps() {
        if (_isScanning.value) return
        _isScanning.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val pm = getApplication<Application>().packageManager
            val intent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val resolveInfos = pm.queryIntentActivities(intent, 0)
            val themedComponents = IconDataProvider.icons.map { it.componentName }.toSet()

            val apps = resolveInfos.mapNotNull { ri ->
                val pkg = ri.activityInfo.packageName
                if (pkg == getApplication<Application>().packageName) return@mapNotNull null
                val label = ri.loadLabel(pm).toString()
                val component = "$pkg/${ri.activityInfo.name}"
                val isThemed = themedComponents.any { it.contains(pkg) }
                InstalledAppItem(
                    label = label,
                    packageName = pkg,
                    activityName = ri.activityInfo.name,
                    isThemed = isThemed
                )
            }.distinctBy { it.packageName }.sortedWith(
                compareBy<InstalledAppItem> { it.isThemed }.thenBy { it.label }
            )

            _installedApps.value = apps
            _isScanning.value = false
        }
    }

    fun toggleMissingAppSelection(pkg: String) {
        val current = _selectedMissingApps.value.toMutableSet()
        if (current.contains(pkg)) {
            current.remove(pkg)
        } else {
            current.add(pkg)
        }
        _selectedMissingApps.value = current
    }

    fun selectAllMissingApps() {
        val missing = _installedApps.value.filter { !it.isThemed }.map { it.packageName }.toSet()
        _selectedMissingApps.value = missing
    }

    fun clearMissingAppsSelection() {
        _selectedMissingApps.value = emptySet()
    }

    fun sendIconRequest(context: Context) {
        val selected = _installedApps.value.filter { _selectedMissingApps.value.contains(it.packageName) }
        if (selected.isEmpty()) {
            Toast.makeText(context, "Please select at least one unthemed app to request", Toast.LENGTH_SHORT).show()
            return
        }

        val body = StringBuilder()
        body.append("=== Liquid Glass Icon Request ===\n\n")
        body.append("Device: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})\n")
        body.append("Total Apps Requested: ${selected.size}\n\n")
        selected.forEachIndexed { i, app ->
            body.append("${i + 1}. ${app.label}\n")
            body.append("   Package: ${app.packageName}\n")
            body.append("   Activity: ${app.activityName}\n")
            body.append("   Component: ComponentInfo{${app.packageName}/${app.activityName}}\n\n")
        }

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:support@liquidglassicons.com")
            putExtra(Intent.EXTRA_SUBJECT, "Liquid Glass Icon Request (${selected.size} apps)")
            putExtra(Intent.EXTRA_TEXT, body.toString())
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(emailIntent)
        } catch (_: Exception) {
            // Fallback: copy to clipboard or general share
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Liquid Glass Icon Request")
                putExtra(Intent.EXTRA_TEXT, body.toString())
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Icon Request"))
        }
    }
}
