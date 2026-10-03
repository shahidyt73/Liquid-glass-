package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.model.*
import com.example.ui.theme.*

object IconDataProvider {

    val icons: List<IconItem> = listOf(
        // System & Core
        IconItem(
            id = "phone",
            name = "Phone",
            category = IconCategory.SYSTEM,
            drawableRes = R.drawable.icon_phone,
            componentName = "com.google.android.dialer/com.google.android.dialer.extensions.GoogleDialtactsActivity",
            colorStart = Color(0xFF203E6A),
            colorEnd = Color(0xFF0A1526),
            accentColor = Color(0xFF38BDF8),
            glyphEmoji = "📞",
            glyphSymbolName = "phone",
            description = "Dialer & Call Manager with glass reflection"
        ),
        IconItem(
            id = "camera",
            name = "Camera",
            category = IconCategory.SYSTEM,
            drawableRes = R.drawable.icon_camera,
            componentName = "com.google.android.GoogleCamera/com.android.camera.CameraLauncher",
            colorStart = Color(0xFF5B21B6),
            colorEnd = Color(0xFF0F172A),
            accentColor = Color(0xFFC084FC),
            glyphEmoji = "📷",
            glyphSymbolName = "photo_camera",
            description = "High dynamic range glass optics shutter"
        ),
        IconItem(
            id = "settings",
            name = "Settings",
            category = IconCategory.SYSTEM,
            drawableRes = R.drawable.icon_settings,
            componentName = "com.android.settings/com.android.settings.Settings",
            colorStart = Color(0xFF374151),
            colorEnd = Color(0xFF111827),
            accentColor = Color(0xFF9CA3AF),
            glyphEmoji = "⚙️",
            glyphSymbolName = "settings",
            description = "System configurations & preferences"
        ),
        IconItem(
            id = "browser",
            name = "Browser",
            category = IconCategory.SYSTEM,
            drawableRes = R.drawable.icon_browser,
            componentName = "com.android.chrome/com.google.android.apps.chrome.Main",
            colorStart = Color(0xFF0369A1),
            colorEnd = Color(0xFF082F49),
            accentColor = Color(0xFF38BDF8),
            glyphEmoji = "🌐",
            glyphSymbolName = "language",
            description = "Web exploration with oceanic glass caustics"
        ),
        IconItem(
            id = "gallery",
            name = "Gallery",
            category = IconCategory.MEDIA,
            drawableRes = R.drawable.icon_gallery,
            componentName = "com.google.android.apps.photos/com.google.android.apps.photos.home.HomeActivity",
            colorStart = Color(0xFFC2410C),
            colorEnd = Color(0xFF431407),
            accentColor = Color(0xFFFB923C),
            glyphEmoji = "🌄",
            glyphSymbolName = "image",
            description = "Photo & video canvas with sunset prism"
        ),
        IconItem(
            id = "music",
            name = "Music",
            category = IconCategory.MEDIA,
            drawableRes = R.drawable.icon_music,
            componentName = "com.google.android.apps.youtube.music/com.google.android.apps.youtube.music.activities.MusicActivity",
            colorStart = Color(0xFFE11D48),
            colorEnd = Color(0xFF4C0519),
            accentColor = Color(0xFFF43F5E),
            glyphEmoji = "🎵",
            glyphSymbolName = "music_note",
            description = "Audio acoustic waves with crimson luminescence"
        ),
        IconItem(
            id = "messages",
            name = "Messages",
            category = IconCategory.COMMUNICATION,
            drawableRes = R.drawable.icon_messages,
            componentName = "com.google.android.apps.messaging/com.google.android.apps.messaging.ui.ConversationListActivity",
            colorStart = Color(0xFF059669),
            colorEnd = Color(0xFF064E3B),
            accentColor = Color(0xFF34D399),
            glyphEmoji = "💬",
            glyphSymbolName = "chat",
            description = "Direct chat bubbles in frosted emerald"
        ),
        IconItem(
            id = "files",
            name = "Files",
            category = IconCategory.TOOLS,
            drawableRes = R.drawable.icon_files,
            componentName = "com.google.android.apps.nbu.files/com.google.android.apps.nbu.files.home.HomeActivity",
            colorStart = Color(0xFFCA8A04),
            colorEnd = Color(0xFF713F12),
            accentColor = Color(0xFFFACC15),
            glyphEmoji = "📁",
            glyphSymbolName = "folder",
            description = "Storage organizer with amber refraction"
        ),
        IconItem(
            id = "calendar",
            name = "Calendar",
            category = IconCategory.PRODUCTIVITY,
            drawableRes = R.drawable.icon_calendar,
            componentName = "com.google.android.calendar/com.android.calendar.AllInOneActivity",
            colorStart = Color(0xFF0284C7),
            colorEnd = Color(0xFF0C4A6E),
            accentColor = Color(0xFF38BDF8),
            glyphEmoji = "📅",
            glyphSymbolName = "calendar_today",
            description = "Schedule timeline with cyan highlight"
        ),
        IconItem(
            id = "clock",
            name = "Clock",
            category = IconCategory.TOOLS,
            drawableRes = R.drawable.icon_clock,
            componentName = "com.google.android.deskclock/com.android.deskclock.DeskClock",
            colorStart = Color(0xFF0D9488),
            colorEnd = Color(0xFF134E4A),
            accentColor = Color(0xFF2DD4BF),
            glyphEmoji = "⏰",
            glyphSymbolName = "schedule",
            description = "Analog timepiece with ticking fluid rim"
        ),
        IconItem(
            id = "maps",
            name = "Maps",
            category = IconCategory.TOOLS,
            drawableRes = R.drawable.icon_maps,
            componentName = "com.google.android.apps.maps/com.google.android.maps.MapsActivity",
            colorStart = Color(0xFF16A34A),
            colorEnd = Color(0xFF14532D),
            accentColor = Color(0xFF4ADE80),
            glyphEmoji = "🗺️",
            glyphSymbolName = "map",
            description = "Navigation radar & topographic glass pin"
        ),
        IconItem(
            id = "calculator",
            name = "Calculator",
            category = IconCategory.TOOLS,
            drawableRes = R.drawable.icon_calculator,
            componentName = "com.google.android.calculator/com.android.calculator2.Calculator",
            colorStart = Color(0xFFD97706),
            colorEnd = Color(0xFF78350F),
            accentColor = Color(0xFFFBBF24),
            glyphEmoji = "🧮",
            glyphSymbolName = "calculate",
            description = "Computational matrix with gold specular arc"
        ),
        IconItem(
            id = "weather",
            name = "Weather",
            category = IconCategory.TOOLS,
            drawableRes = R.drawable.icon_weather,
            componentName = "com.google.android.apps.weather/com.google.android.apps.weather.WeatherActivity",
            colorStart = Color(0xFF0284C7),
            colorEnd = Color(0xFF0F172A),
            accentColor = Color(0xFF60A5FA),
            glyphEmoji = "⛅",
            glyphSymbolName = "cloud",
            description = "Atmospheric forecast & moisture refraction"
        ),
        IconItem(
            id = "notes",
            name = "Notes",
            category = IconCategory.PRODUCTIVITY,
            drawableRes = R.drawable.icon_notes,
            componentName = "com.google.android.keep/com.google.android.keep.activities.BrowseActivity",
            colorStart = Color(0xFFEAB308),
            colorEnd = Color(0xFF854D0E),
            accentColor = Color(0xFFFDE047),
            glyphEmoji = "📝",
            glyphSymbolName = "edit_note",
            description = "Quick thoughts & parchment in glass"
        ),
        IconItem(
            id = "mail",
            name = "Mail",
            category = IconCategory.COMMUNICATION,
            drawableRes = R.drawable.icon_mail,
            componentName = "com.google.android.gm/com.google.android.gm.ConversationListActivityGmail",
            colorStart = Color(0xFFDC2626),
            colorEnd = Color(0xFF450A0A),
            accentColor = Color(0xFFF87171),
            glyphEmoji = "✉️",
            glyphSymbolName = "mail",
            description = "Electronic correspondence with glowing flap"
        ),
        IconItem(
            id = "store",
            name = "Play Store",
            category = IconCategory.SYSTEM,
            drawableRes = R.drawable.icon_store,
            componentName = "com.android.vending/com.android.vending.AssetBrowserActivity",
            colorStart = Color(0xFF047857),
            colorEnd = Color(0xFF064E3B),
            accentColor = Color(0xFF34D399),
            glyphEmoji = "🛍️",
            glyphSymbolName = "shopping_bag",
            description = "App marketplace in iridescent prism glass"
        ),

        // Extended Rich Liquid Glass Catalogue
        IconItem(
            id = "contacts",
            name = "Contacts",
            category = IconCategory.COMMUNICATION,
            componentName = "com.google.android.contacts/com.android.contacts.activities.PeopleActivity",
            colorStart = Color(0xFF2563EB),
            colorEnd = Color(0xFF1E3A8A),
            accentColor = Color(0xFF60A5FA),
            glyphEmoji = "👥",
            glyphSymbolName = "person",
            description = "Address book in translucent indigo glass"
        ),
        IconItem(
            id = "recorder",
            name = "Sound Recorder",
            category = IconCategory.TOOLS,
            componentName = "com.google.android.apps.recorder/com.google.android.apps.recorder.MainActivity",
            colorStart = Color(0xFFBE123C),
            colorEnd = Color(0xFF4C0519),
            accentColor = Color(0xFFFB7185),
            glyphEmoji = "🎙️",
            glyphSymbolName = "mic",
            description = "Voice waveforms in crimson crystal"
        ),
        IconItem(
            id = "youtube",
            name = "YouTube",
            category = IconCategory.MEDIA,
            componentName = "com.google.android.youtube/com.google.android.youtube.HomeActivity",
            colorStart = Color(0xFFE11D48),
            colorEnd = Color(0xFF450A0A),
            accentColor = Color(0xFFF43F5E),
            glyphEmoji = "▶️",
            glyphSymbolName = "play_arrow",
            description = "Video streaming in glossy ruby bubble"
        ),
        IconItem(
            id = "spotify",
            name = "Spotify",
            category = IconCategory.MEDIA,
            componentName = "com.spotify.music/com.spotify.music.MainActivity",
            colorStart = Color(0xFF15803D),
            colorEnd = Color(0xFF052E16),
            accentColor = Color(0xFF22C55E),
            glyphEmoji = "🎧",
            glyphSymbolName = "graphic_eq",
            description = "Audio waves in bioluminescent neon emerald"
        ),
        IconItem(
            id = "telegram",
            name = "Telegram",
            category = IconCategory.COMMUNICATION,
            componentName = "org.telegram.messenger/org.telegram.ui.LaunchActivity",
            colorStart = Color(0xFF0284C7),
            colorEnd = Color(0xFF075985),
            accentColor = Color(0xFF38BDF8),
            glyphEmoji = "✈️",
            glyphSymbolName = "send",
            description = "Paper glider soaring through ice glass"
        ),
        IconItem(
            id = "whatsapp",
            name = "WhatsApp",
            category = IconCategory.COMMUNICATION,
            componentName = "com.whatsapp/com.whatsapp.HomeActivity",
            colorStart = Color(0xFF059669),
            colorEnd = Color(0xFF064E3B),
            accentColor = Color(0xFF34D399),
            glyphEmoji = "📱",
            glyphSymbolName = "phone_in_talk",
            description = "Encrypted messaging in vibrant jade glass"
        ),
        IconItem(
            id = "discord",
            name = "Discord",
            category = IconCategory.GOOGLE_SOCIAL,
            componentName = "com.discord/com.discord.main.MainActivity",
            colorStart = Color(0xFF4F46E5),
            colorEnd = Color(0xFF1E1B4B),
            accentColor = Color(0xFF818CF8),
            glyphEmoji = "🎮",
            glyphSymbolName = "sports_esports",
            description = "Community controller in royal violet obsidian"
        ),
        IconItem(
            id = "github",
            name = "GitHub",
            category = IconCategory.TOOLS,
            componentName = "com.github.android/com.github.android.ui.MainActivity",
            colorStart = Color(0xFF334155),
            colorEnd = Color(0xFF0F172A),
            accentColor = Color(0xFFE2E8F0),
            glyphEmoji = "🐙",
            glyphSymbolName = "code",
            description = "Octocat repository in smoked crystal"
        ),
        IconItem(
            id = "twitter",
            name = "X / Twitter",
            category = IconCategory.GOOGLE_SOCIAL,
            componentName = "com.twitter.android/com.twitter.android.MainActivity",
            colorStart = Color(0xFF1E293B),
            colorEnd = Color(0xFF020617),
            accentColor = Color(0xFFF8FAFC),
            glyphEmoji = "𝕏",
            glyphSymbolName = "alternate_email",
            description = "Minimalist dark crystal pulse"
        ),
        IconItem(
            id = "reddit",
            name = "Reddit",
            category = IconCategory.GOOGLE_SOCIAL,
            componentName = "com.reddit.frontpage/com.reddit.frontpage.MainActivity",
            colorStart = Color(0xFFEA580C),
            colorEnd = Color(0xFF7C2D12),
            accentColor = Color(0xFFFB923C),
            glyphEmoji = "🤖",
            glyphSymbolName = "forum",
            description = "Snoo beacon in molten liquid amber"
        ),
        IconItem(
            id = "instagram",
            name = "Instagram",
            category = IconCategory.GOOGLE_SOCIAL,
            componentName = "com.instagram.android/com.instagram.mainactivity.MainActivity",
            colorStart = Color(0xFFC026D3),
            colorEnd = Color(0xFF4A044E),
            accentColor = Color(0xFFF472B6),
            glyphEmoji = "📷",
            glyphSymbolName = "camera_alt",
            description = "Sunset gradient lens in chromatic dispersion"
        ),
        IconItem(
            id = "drive",
            name = "Google Drive",
            category = IconCategory.PRODUCTIVITY,
            componentName = "com.google.android.apps.docs/com.google.android.apps.docs.app.NewMainProxyActivity",
            colorStart = Color(0xFF0284C7),
            colorEnd = Color(0xFF16A34A),
            accentColor = Color(0xFFFACC15),
            glyphEmoji = "🔺",
            glyphSymbolName = "cloud",
            description = "Prismatic triangle cloud storage"
        ),
        IconItem(
            id = "notion",
            name = "Notion",
            category = IconCategory.PRODUCTIVITY,
            componentName = "notion.id/notion.id.MainActivity",
            colorStart = Color(0xFF374151),
            colorEnd = Color(0xFF111827),
            accentColor = Color(0xFFF9FAFB),
            glyphEmoji = "📓",
            glyphSymbolName = "auto_stories",
            description = "Workspace monolith in cut obsidian"
        ),
        IconItem(
            id = "security",
            name = "Security Shield",
            category = IconCategory.SYSTEM,
            componentName = "com.google.android.gms/com.google.android.gms.security.SecuritySettingsActivity",
            colorStart = Color(0xFF0D9488),
            colorEnd = Color(0xFF115E59),
            accentColor = Color(0xFF2DD4BF),
            glyphEmoji = "🛡️",
            glyphSymbolName = "shield",
            description = "Encrypted barrier with neon glow"
        ),
        IconItem(
            id = "compass",
            name = "Compass",
            category = IconCategory.TOOLS,
            componentName = "com.google.android.apps.compass/com.google.android.apps.compass.MainActivity",
            colorStart = Color(0xFF475569),
            colorEnd = Color(0xFF0F172A),
            accentColor = Color(0xFF38BDF8),
            glyphEmoji = "🧭",
            glyphSymbolName = "explore",
            description = "Directional gyro in liquid floating dial"
        ),
        IconItem(
            id = "wallet",
            name = "Wallet",
            category = IconCategory.LIFESTYLE,
            componentName = "com.google.android.apps.walletnfcrel/com.google.android.apps.wallet.main.MainActivity",
            colorStart = Color(0xFF1E3A8A),
            colorEnd = Color(0xFF172554),
            accentColor = Color(0xFF60A5FA),
            glyphEmoji = "💳",
            glyphSymbolName = "credit_card",
            description = "Digital passes in translucent card glass"
        ),
        IconItem(
            id = "fitness",
            name = "Fitness & Health",
            category = IconCategory.LIFESTYLE,
            componentName = "com.google.android.apps.fitness/com.google.android.apps.fitness.welcome.WelcomeActivity",
            colorStart = Color(0xFFE11D48),
            colorEnd = Color(0xFF881337),
            accentColor = Color(0xFFFB7185),
            glyphEmoji = "❤️",
            glyphSymbolName = "favorite",
            description = "Vitality rings in energetic pulse glass"
        ),
        IconItem(
            id = "home",
            name = "Smart Home",
            category = IconCategory.LIFESTYLE,
            componentName = "com.google.android.apps.chromecast.app/com.google.android.apps.chromecast.app.DiscoveryActivity",
            colorStart = Color(0xFFD97706),
            colorEnd = Color(0xFF78350F),
            accentColor = Color(0xFFFBBF24),
            glyphEmoji = "🏠",
            glyphSymbolName = "home",
            description = "Ambient iot controller with warm glass hearth"
        )
    )

    val supportedLaunchers: List<LauncherInfo> = listOf(
        LauncherInfo(
            id = "nova",
            name = "Nova Launcher",
            packageName = "com.teslacoilsw.launcher",
            playStoreUrl = "market://details?id=com.teslacoilsw.launcher",
            isDirectApplySupported = true,
            accentColor = Color(0xFFF97316),
            popularityBadge = "Most Popular",
            guideStep = "Nova Settings > Look & feel > Icon style > Icon theme > Select Liquid Glass"
        ),
        LauncherInfo(
            id = "lawnchair",
            name = "Lawnchair",
            packageName = "ch.deletescape.lawnchair",
            playStoreUrl = "market://details?id=ch.deletescape.lawnchair",
            isDirectApplySupported = true,
            accentColor = Color(0xFF10B981),
            popularityBadge = "Open Source",
            guideStep = "Home Settings > Theme > Icon Pack > Select Liquid Glass"
        ),
        LauncherInfo(
            id = "niagara",
            name = "Niagara Launcher",
            packageName = "bitpit.launcher",
            playStoreUrl = "market://details?id=bitpit.launcher",
            isDirectApplySupported = true,
            accentColor = Color(0xFF38BDF8),
            popularityBadge = "Modern Minimal",
            guideStep = "Niagara Settings > Look > Icon pack > Select Liquid Glass"
        ),
        LauncherInfo(
            id = "smart",
            name = "Smart Launcher 6",
            packageName = "ginlemon.flowerfree",
            playStoreUrl = "market://details?id=ginlemon.flowerfree",
            isDirectApplySupported = true,
            accentColor = Color(0xFFA855F7),
            popularityBadge = "Smart Categorization",
            guideStep = "Smart Preferences > Global appearance > Icon appearance > Icon pack"
        ),
        LauncherInfo(
            id = "action",
            name = "Action Launcher",
            packageName = "com.actionlauncher.playstore",
            playStoreUrl = "market://details?id=com.actionlauncher.playstore",
            isDirectApplySupported = true,
            accentColor = Color(0xFFEF4444),
            popularityBadge = "Quicktheme Ready",
            guideStep = "Action Settings > Appearance > Icon pack > Select Liquid Glass"
        ),
        LauncherInfo(
            id = "microsoft",
            name = "Microsoft Launcher",
            packageName = "com.microsoft.launcher",
            playStoreUrl = "market://details?id=com.microsoft.launcher",
            isDirectApplySupported = true,
            accentColor = Color(0xFF0284C7),
            popularityBadge = "Productivity Pick",
            guideStep = "Launcher Settings > Home screen > Icon appearance > Icon pack"
        ),
        LauncherInfo(
            id = "hyperion",
            name = "Hyperion Launcher",
            packageName = "projekt.launcher",
            playStoreUrl = "market://details?id=projekt.launcher",
            isDirectApplySupported = true,
            accentColor = Color(0xFFEC4899),
            popularityBadge = "Customization",
            guideStep = "Hyperion Settings > Iconography > Icon pack > Select Liquid Glass"
        ),
        LauncherInfo(
            id = "apex",
            name = "Apex Launcher",
            packageName = "com.anddoes.launcher",
            playStoreUrl = "market://details?id=com.anddoes.launcher",
            isDirectApplySupported = true,
            accentColor = Color(0xFFEAB308),
            popularityBadge = "Classic",
            guideStep = "Apex Settings > Theme settings > Icon pack > Select Liquid Glass"
        )
    )

    val curatedWallpapers: List<WallpaperItem> = listOf(
        WallpaperItem(
            id = "ai_liquid_glass",
            name = "Liquid Glass Aurora",
            category = "Chromatic Fluid",
            drawableRes = R.drawable.img_wallpaper_liquid,
            isAiGenerated = true,
            resolutionLabel = "4K Super AMOLED"
        ),
        WallpaperItem(
            id = "obsidian_nebula",
            name = "Obsidian Glass Caustic",
            category = "Minimal AMOLED",
            gradientColors = listOf(Color(0xFF08090D), Color(0xFF0E1A2F), Color(0xFF1E293B), Color(0xFF060910)),
            resolutionLabel = "Ultra HD Dynamic"
        ),
        WallpaperItem(
            id = "prismatic_dispersion",
            name = "Prismatic Glass Prism",
            category = "Refraction",
            gradientColors = listOf(Color(0xFF1E1B4B), Color(0xFF4338CA), Color(0xFF06B6D4), Color(0xFF090D1A)),
            resolutionLabel = "4K Ultra HD"
        ),
        WallpaperItem(
            id = "emerald_dew",
            name = "Emerald Bioluminescence",
            category = "Organic Glass",
            gradientColors = listOf(Color(0xFF022C22), Color(0xFF065F46), Color(0xFF10B981), Color(0xFF061412)),
            resolutionLabel = "OLED Pure Black"
        ),
        WallpaperItem(
            id = "cyber_twilight",
            name = "Cyberpunk Glass Waves",
            category = "Neon Dark",
            gradientColors = listOf(Color(0xFF1E1035), Color(0xFF701A75), Color(0xFFF43F5E), Color(0xFF0D0614)),
            resolutionLabel = "4K HDR"
        ),
        WallpaperItem(
            id = "solar_flare",
            name = "Molten Glass Horizon",
            category = "Warm Sunset",
            gradientColors = listOf(Color(0xFF2E1065), Color(0xFF9A3412), Color(0xFFEA580C), Color(0xFF0C0714)),
            resolutionLabel = "OLED Deep Contrast"
        )
    )

    val glassStyles: List<GlassStyleOption> = listOf(
        GlassStyleOption(
            id = "crystal",
            name = "Crystal Frosted",
            colorStart = Color(0x6638BDF8),
            colorEnd = Color(0x220F172A),
            rimHighlight = Color(0xEEBAE6FD),
            glowColor = Color(0x5538BDF8)
        ),
        GlassStyleOption(
            id = "bubble",
            name = "Liquid Bubble",
            colorStart = Color(0x8806B6D4),
            colorEnd = Color(0x33083344),
            rimHighlight = Color(0xFF67E8F9),
            glowColor = Color(0x6606B6D4)
        ),
        GlassStyleOption(
            id = "aurora",
            name = "Prismatic Aurora",
            colorStart = Color(0x77C084FC),
            colorEnd = Color(0x333B0764),
            rimHighlight = Color(0xFFF0ABFC),
            glowColor = Color(0x66C084FC)
        ),
        GlassStyleOption(
            id = "cyber",
            name = "Neon Cyber",
            colorStart = Color(0x77F43F5E),
            colorEnd = Color(0x334C0519),
            rimHighlight = Color(0xFFFDA4AF),
            glowColor = Color(0x66F43F5E)
        ),
        GlassStyleOption(
            id = "smoked",
            name = "Smoked Obsidian",
            colorStart = Color(0x66475569),
            colorEnd = Color(0x330F172A),
            rimHighlight = Color(0xFFCBD5E1),
            glowColor = Color(0x4494A3B8)
        ),
        GlassStyleOption(
            id = "emerald",
            name = "Emerald Caustic",
            colorStart = Color(0x7710B981),
            colorEnd = Color(0x33064E3B),
            rimHighlight = Color(0xFFA7F3D0),
            glowColor = Color(0x6610B981)
        )
    )
}
