# Consumer ProGuard Rules for TRPOne Library
# These rules are automatically applied to apps that consume this library

# ============================================================================
# TRPOne Model Classes - Keep all model classes from obfuscation
# ============================================================================

# Keep all model classes and their members
-keep class com.tripian.one.api.**.model.** { *; }

# Keep all entity classes
-keep class com.tripian.one.api.users.model.** { *; }
-keep class com.tripian.one.api.trip.model.** { *; }
-keep class com.tripian.one.api.cities.model.** { *; }
-keep class com.tripian.one.api.pois.model.** { *; }
-keep class com.tripian.one.api.companion.model.** { *; }
-keep class com.tripian.one.api.favorites.model.** { *; }
-keep class com.tripian.one.api.reactions.model.** { *; }
-keep class com.tripian.one.api.bookings.model.** { *; }
-keep class com.tripian.one.api.offers.model.** { *; }
-keep class com.tripian.one.api.misc.model.** { *; }
-keep class com.tripian.one.api.tour.model.** { *; }
-keep class com.tripian.one.api.timeline.model.** { *; }

# Keep util classes that may be used by consumers
-keep class com.tripian.one.util.** { *; }

# ============================================================================
# Public API Classes
# ============================================================================

# Keep TRPRest (main facade) and its public methods
-keep class com.tripian.one.TRPRest { *; }
-keep class com.tripian.one.TRPRestBase { *; }

# ============================================================================
# Gson Serialization Rules
# ============================================================================

# Gson uses generic type information stored in a class file when working with fields
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# ============================================================================
# Retrofit Rules
# ============================================================================

# Retrofit does reflection on generic parameters
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded)
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore JSR 305 annotations for embedding nullability information
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when available
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not kept
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# ============================================================================
# OkHttp Rules
# ============================================================================

# JSR 305 annotations are for embedding nullability information
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved
-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt and other security providers are available
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ============================================================================
# Kotlin Coroutines Rules
# ============================================================================

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Same for CancellableContinuation
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

# These classes are only required by kotlinx.coroutines.debug.AgentPremain, which is only loaded when
# kotlinx-coroutines-debug is added
-dontwarn java.lang.instrument.ClassFileTransformer
-dontwarn sun.misc.SignalHandler
-dontwarn java.lang.instrument.Instrumentation
-dontwarn sun.misc.Signal

# ============================================================================
# Kotlin Serialization Rules
# ============================================================================

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep Serializers
-keep,includedescriptorclasses class com.tripian.one.**$$serializer { *; }
-keepclassmembers class com.tripian.one.** {
    *** Companion;
}
-keepclasseswithmembers class com.tripian.one.** {
    kotlinx.serialization.KSerializer serializer(...);
}
