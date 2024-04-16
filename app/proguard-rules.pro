# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.zero.accounthelper.http.** { *;}
-keep class com.zero.accounthelper.utils.** { *;}
-keep class com.zero.accounthelper.impl.** { *;}
-keep class com.zero.accounthelper.bean.** { *;}
-keep class com.zero.accounthelper.adapter.** { *;}
# Gson
-keep class com.google.gson.stream.** { *; }
# Java8 emulation library is used in Java 1.7 API clients and not present on Android.
-dontnote jdk.internal.reflect.**
-dontwarn jdk.internal.reflect.**
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
-keep class org.json.**{*;}
-keep class androidx.core.app.CoreComponentFactory { *; }

################################ Gson ################################
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes Signature
-keepattributes *Annotation*

# 保持 Gson 类的所有成员和方法不被混淆
-keep class com.google.gson.** { *; }
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**
# 保持 TypeToken 类的所有成员和方法不被混淆
-keep class com.google.gson.reflect.TypeToken { *; }
 # 使用R8全模式，对未保留的类剥离通用签名。挂起函数被包装在使用类型参数的continuation中。
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 # 如果不保留，R8完整模式将从返回类型中剥离通用签名。
 -if interface * { @retrofit2.http.* public *** *(...); }
 -keep,allowoptimization,allowshrinking,allowobfuscation class <3>

 # 在R8全模式下，对未保留的类剥离通用签名。
 -keep,allowobfuscation,allowshrinking class retrofit2.Response
# 如果您的实体类使用了 @SerializedName 注解，请确保该注解的成员不被混淆
-keepattributes SerializedName
##---------------End: proguard configuration for Gson  ----------
################################ Gson ################################
#okhttp3.x
################################ retrofit ################################
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
# Retrofit
-keep class retrofit2.** { *; }
-keepclassmembers class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
# OkHttp
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**

# Gson
-keep class com.google.gson.stream.** { *; }
# Platform calls Class.forName on types which do not exist on Android to determine platform.
# Java8 emulation library is used in Java 1.7 API clients and not present on Android.
-dontnote jdk.internal.reflect.**
-dontwarn jdk.internal.reflect.**
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
################################ retrofit ################################

################################ okhttp ################################
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
################################ okhttp ################################

#-dontwarn com.android.org.conscrypt.SSLParametersImpl
#-dontwarn lombok.bytecode.PoolConstantsApp
#-dontwarn lombok.bytecode.PostCompilerApp
#-dontwarn lombok.bytecode.PreventNullAnalysisRemover
#-dontwarn lombok.bytecode.SneakyThrowsRemover
#-dontwarn lombok.core.Main$LicenseApp
#-dontwarn lombok.core.Main$VersionApp
#-dontwarn lombok.core.PublicApiCreatorApp
#-dontwarn lombok.core.configuration.ConfigurationApp
#-dontwarn lombok.core.handlers.SneakyThrowsAndCleanupDependencyInfo
#-dontwarn lombok.core.runtimeDependencies.CreateLombokRuntimeApp
#-dontwarn lombok.delombok.DelombokApp
#-dontwarn lombok.eclipse.handlers.HandleAccessors
#-dontwarn lombok.eclipse.handlers.HandleBuilder
#-dontwarn lombok.eclipse.handlers.HandleBuilderDefault
#-dontwarn lombok.eclipse.handlers.HandleCleanup
#-dontwarn lombok.eclipse.handlers.HandleConstructor$HandleAllArgsConstructor
#-dontwarn lombok.eclipse.handlers.HandleConstructor$HandleNoArgsConstructor
#-dontwarn lombok.eclipse.handlers.HandleConstructor$HandleRequiredArgsConstructor
#-dontwarn lombok.eclipse.handlers.HandleData
#-dontwarn lombok.eclipse.handlers.HandleDelegate
#-dontwarn lombok.eclipse.handlers.HandleEqualsAndHashCode
#-dontwarn lombok.eclipse.handlers.HandleExtensionMethod
#-dontwarn lombok.eclipse.handlers.HandleFieldDefaults
#-dontwarn lombok.eclipse.handlers.HandleFieldNameConstants
#-dontwarn lombok.eclipse.handlers.HandleGetter
#-dontwarn lombok.eclipse.handlers.HandleHelper
#-dontwarn lombok.eclipse.handlers.HandleJacksonized
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleCommonsLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleCustomLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleFloggerLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleJBossLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleJulLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleLog4j2Log
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleLog4jLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleSlf4jLog
#-dontwarn lombok.eclipse.handlers.HandleLog$HandleXSlf4jLog
#-dontwarn lombok.eclipse.handlers.HandleNonNull
#-dontwarn lombok.eclipse.handlers.HandlePrintAST
#-dontwarn lombok.eclipse.handlers.HandleSetter
#-dontwarn lombok.eclipse.handlers.HandleSneakyThrows
#-dontwarn lombok.eclipse.handlers.HandleStandardException
#-dontwarn lombok.eclipse.handlers.HandleSuperBuilder
#-dontwarn lombok.eclipse.handlers.HandleSynchronized
#-dontwarn lombok.eclipse.handlers.HandleToString
#-dontwarn lombok.eclipse.handlers.HandleUtilityClass
#-dontwarn lombok.eclipse.handlers.HandleVal
#-dontwarn lombok.eclipse.handlers.HandleValue
#-dontwarn lombok.eclipse.handlers.HandleWith
#-dontwarn lombok.eclipse.handlers.HandleWithBy
#-dontwarn lombok.eclipse.handlers.singulars.EclipseGuavaMapSingularizer
#-dontwarn lombok.eclipse.handlers.singulars.EclipseGuavaSetListSingularizer
#-dontwarn lombok.eclipse.handlers.singulars.EclipseGuavaTableSingularizer
#-dontwarn lombok.eclipse.handlers.singulars.EclipseJavaUtilListSingularizer
#-dontwarn lombok.eclipse.handlers.singulars.EclipseJavaUtilMapSingularizer
#-dontwarn lombok.eclipse.handlers.singulars.EclipseJavaUtilSetSingularizer
#-dontwarn lombok.installer.Installer$CommandLineInstallerApp
#-dontwarn lombok.installer.Installer$CommandLineUninstallerApp
#-dontwarn lombok.installer.Installer$GraphicalInstallerApp
#-dontwarn lombok.installer.eclipse.AngularIDELocationProvider
#-dontwarn lombok.installer.eclipse.EclipseLocationProvider
#-dontwarn lombok.installer.eclipse.JbdsLocationProvider
#-dontwarn lombok.installer.eclipse.MyEclipseLocationProvider
#-dontwarn lombok.installer.eclipse.RhcrLocationProvider
#-dontwarn lombok.installer.eclipse.RhdsLocationProvider
#-dontwarn lombok.installer.eclipse.STS4LocationProvider
#-dontwarn lombok.installer.eclipse.STSLocationProvider
#-dontwarn lombok.javac.handlers.HandleAccessors
#-dontwarn lombok.javac.handlers.HandleBuilder
#-dontwarn lombok.javac.handlers.HandleBuilderDefault
#-dontwarn lombok.javac.handlers.HandleBuilderDefaultRemove
#-dontwarn lombok.javac.handlers.HandleBuilderRemove
#-dontwarn lombok.javac.handlers.HandleCleanup
#-dontwarn lombok.javac.handlers.HandleConstructor$HandleAllArgsConstructor
#-dontwarn lombok.javac.handlers.HandleConstructor$HandleNoArgsConstructor
#-dontwarn lombok.javac.handlers.HandleConstructor$HandleRequiredArgsConstructor
#-dontwarn lombok.javac.handlers.HandleData
#-dontwarn lombok.javac.handlers.HandleDelegate
#-dontwarn lombok.javac.handlers.HandleEqualsAndHashCode
#-dontwarn lombok.javac.handlers.HandleExtensionMethod
#-dontwarn lombok.javac.handlers.HandleFieldDefaults
#-dontwarn lombok.javac.handlers.HandleFieldNameConstants
#-dontwarn lombok.javac.handlers.HandleGetter
#-dontwarn lombok.javac.handlers.HandleHelper
#-dontwarn lombok.javac.handlers.HandleJacksonized
#-dontwarn lombok.javac.handlers.HandleLog$HandleCommonsLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleCustomLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleFloggerLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleJBossLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleJulLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleLog4j2Log
#-dontwarn lombok.javac.handlers.HandleLog$HandleLog4jLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleSlf4jLog
#-dontwarn lombok.javac.handlers.HandleLog$HandleXSlf4jLog
#-dontwarn lombok.javac.handlers.HandleNonNull
#-dontwarn lombok.javac.handlers.HandlePrintAST
#-dontwarn lombok.javac.handlers.HandleSetter
#-dontwarn lombok.javac.handlers.HandleSneakyThrows
#-dontwarn lombok.javac.handlers.HandleStandardException
#-dontwarn lombok.javac.handlers.HandleSuperBuilder
#-dontwarn lombok.javac.handlers.HandleSuperBuilderRemove
#-dontwarn lombok.javac.handlers.HandleSynchronized
#-dontwarn lombok.javac.handlers.HandleToString
#-dontwarn lombok.javac.handlers.HandleUtilityClass
#-dontwarn lombok.javac.handlers.HandleVal
#-dontwarn lombok.javac.handlers.HandleValue
#-dontwarn lombok.javac.handlers.HandleWith
#-dontwarn lombok.javac.handlers.HandleWithBy
#-dontwarn lombok.javac.handlers.singulars.JavacGuavaMapSingularizer
#-dontwarn lombok.javac.handlers.singulars.JavacGuavaSetListSingularizer
#-dontwarn lombok.javac.handlers.singulars.JavacGuavaTableSingularizer
#-dontwarn lombok.javac.handlers.singulars.JavacJavaUtilListSingularizer
#-dontwarn lombok.javac.handlers.singulars.JavacJavaUtilMapSingularizer
#-dontwarn lombok.javac.handlers.singulars.JavacJavaUtilSetSingularizer
#-dontwarn org.apache.harmony.xnet.provider.jsse.SSLParametersImpl

-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
-keep class com.android.org.conscrypt.** { *; }
-keep class org.repackage.** {*;}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}
-keep public class com.nineoldandroids.** {*;}

# support-v4
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }