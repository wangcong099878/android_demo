# TheBase

#### 介绍
主要以[QMUI](https://github.com/Tencent/QMUI_Android)封装的一个基础框架。
写了一个[Gank](http://gank.io/)客户端用来测试此框架。

#### 预览

[如何写一个常用的主界面（ViewPager+Tab）](https://gitee.com/theoneee/TheBase/blob/master/app/src/main/java/the/one/demo/ui/gank/fragment/IndexFragment.java)

![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155654_b46fccb5_2286054.png "S90312-151521.png")


[如何写一个常用的TitleBar+Tab+ViewPager布局](https://gitee.com/theoneee/TheBase/blob/master/app/src/main/java/the/one/demo/ui/gank/fragment/CategoryFragment.java)

![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155709_cc65ed3d_2286054.png "S90312-151529.png")


[如何写一个常用的数据显示界面](https://gitee.com/theoneee/TheBase/blob/master/app/src/main/java/the/one/demo/ui/gank/fragment/GankFragment.java)

![输入图片说明](https://images.gitee.com/uploads/images/2019/0312/155923_cdb5f007_2286054.gif "99d6571a-1ce7-4f85-82bf-44dcda06c846.gif")


下拉刷新？加载更多？空页面？网络错误页面?  统统都封装好了。

LIST? GRID? STAGGERED? 一句代码

```
  @Override
    protected int setType() {
        return isWelfare ? TYPE_STAGGERED : TYPE_LIST;
    }
```

什么？还要懒加载？只需要返回这个。

```
   @Override
    protected boolean onAnimationEndInit() {
        return false;
    }
```



#### 更多请结合Demo看代码
[下载地址](https://gitee.com/theoneee/TheBase/raw/master/app/release/app-release.apk)


#### 交流

QQ群： 761201022


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




