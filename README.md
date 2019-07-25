# TheBase

#### 介绍
主要以[QMUI](https://github.com/Tencent/QMUI_Android)封装的一个基础框架。
写了一个[Gank](http://gank.io/)客户端用来测试此框架。

#### 使用

1.根build 里添加

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
        google()
        jcenter()
    }
}
```
2.app的build里dependencies下添加
```
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
implementation 'com.gitee.theoneee:TheBase:lastversion'
```
3.manifest里application指定  
```android:name="the.one.base.BaseApplication" ```

或者继承 BaseApplication再指定成自己的

style AppTheme继承BaseTheme
```<style name="AppTheme" parent="BaseTheme"/>```

#### 以上三个必须要！！！

#### 混淆

```
-dontwarn the.one.net.**
-dontwarn the.one.base.**
-keep class the.one.base.** { *; }
-keep class the.one.net.** { *; }


```

#### Demo
[下载地址](https://gitee.com/theoneee/TheBase/raw/master/app/release/app-release.apk)

#### 效果图

![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155923_cdb5f007_2286054.gif "99d6571a-1ce7-4f85-82bf-44dcda06c846.gif")

![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155654_b46fccb5_2286054.png "S90312-151521.png")![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155702_93c35928_2286054.png "S90312-151525.png")![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155709_cc65ed3d_2286054.png "S90312-151529.png")


#### 感谢

[QMUI](https://github.com/Tencent/QMUI_Android)

[Gank](http://gank.io/)

[KotlinGankApp](https://github.com/JayGengi/KotlinGankApp)

#### 第三方库

```
 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.40'
 'homhomlin.lib:sldinglayout:0.9.0'
 'com.github.bumptech.glide:glide:4.8.0'
 'com.jakewharton:butterknife:8.8.1'
 'org.greenrobot:eventbus:3.1.1'
 'com.belerweb:pinyin4j:2.5.1'
 'com.android.support:multidex:1.0.3'
 'com.github.tbruyelle:rxpermissions:0.10.2'
 'com.ms-square:expandableTextView:0.1.4'
 'com.github.chrisbanes:PhotoView:2.0.0'
 'com.orhanobut:logger:2.2.0'
 'com.wkp:StickLayout:1.0.6'
 'com.github.LuckSiege.PictureSelector:picture_library:v2.2.3'
 'top.androidman:superbutton:1.1.0'
```

还有一些为了自定义，集成到了项目里，所以并没有在依赖里出现，但是都保留原作者信息。


