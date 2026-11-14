# Angel Launcher — Android project preview

This is an Android Studio starter project for the Angel Launcher interface. It is **not yet a playable Minecraft Java launcher** and it does not include a Minecraft runtime.

## Included in this preview
- Native Android app shell with dark navy / electric-blue Angel branding
- Home, Instances, Mods and Settings screens
- Create and save local instance profile names on-device
- Android phone and Chromebook-friendly layout

## Not implemented yet (required before a real game launch)
- Minecraft Java runtime integration and device-specific rendering/input support
- Microsoft OAuth login, token storage/refresh and entitlement checks
- Downloading game assets/libraries and version manifests
- Fabric / Forge / NeoForge installer integration
- Modrinth API integration and mod file management
- Resource-pack import and per-instance management
- Cosmetics rendering / account sync

The Play control is intentionally disabled. It must not pretend to launch Minecraft until a real runtime is integrated. Do not enter Microsoft credentials into this preview.

## Build with Android Studio
1. Install Android Studio with Android SDK Platform 35 and JDK 17.
2. Open this folder (`AngelLauncher-Android`) as an existing project.
3. Let Gradle sync and install the Android SDK components if prompted.
4. Connect an Android phone or use an Android-enabled Chromebook, then Run `app`.
5. To create an APK: **Build → Build Bundle(s) / APK(s) → Build APK(s)**. The output is normally under `app/build/outputs/apk/debug/`.

This environment did not have the Android SDK or Gradle installed, so an APK could not be compiled or verified here. The ZIP contains the editable Android Studio project source.
