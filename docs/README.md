# KDroid 

[![license](https://img.shields.io/github/license/7hens/KDroid.svg)](https://github.com/7hens/KDroid/blob/master/LICENSE)
[![kotlin](https://img.shields.io/badge/kotlin-1.2.60-blue.svg)](https://github.com/7hens/KDroid/blob/master/build.gradle)
[![android support](https://img.shields.io/badge/android_support-27.1.1-blue.svg)](https://github.com/7hens/KDroid/blob/master/build.gradle)

KDroid 是用一个用 Kotlin 写的轻量级 Android 库。

<img src="docs/res/bird.png" width="256"/>

KDroid 目前有以下几个库：

- [VAdapter](#VAdapter)：打造简单通用的 Adapter
- [Moc](#Moc)：生产随机的 mock 数据
- [Stetho-NoOp](#Stetho-NoOp)：Stetho 的发布版
- [UPay](#UPay): 调用微信支付、支付宝（Rx）

使用之前，请先配置下项目的根目录的 build.gradle 文件：

```groovy

buildscript {
    repositories {
        jcenter()
        google()
        maven { url 'https://dl.bintray.com/7hens/maven/' }
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven { url 'https://dl.bintray.com/7hens/maven/' }
    }
}
```

![VAdapter](docs/res/2018-06-06_14-14-26.gif)

## VAdapter

[![vadapter](https://img.shields.io/bintray/v/7hens/maven/vadapter.svg)](https://bintray.com/7hens/maven/vadapter)

简单说来，这个库就是用来创建通用的 Adapter 的。

在 module 的 build.gradle 中添加依赖：

```groovy
implementation 'cn.thens.kdroid:vadapter:0.2.0'

// 需要导入 RxJava2
implementation "io.reactivex.rxjava2:rxjava:2.1.6"
implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
```

下面是 VAdapter 里面常用的几个类和 View 的对应关系。

| VAdapter | For View |
| ------- | --------- |
| `VRecyclerAdapter` | RecyclerView |
| `VListAdapter` | ListView, GridView, Spinner |
| `VPagerAdapter` | ViewPager |
| `VFragmentPagerAdater` | ViewPager |

### VAdapter 的简单用法

这里已 VRecyclerAdapter 为例，其他的 Adapter 用法类似。

**1) 首先，创建一个 VAdapter：**

```kotlin
val vAdapter = VRecyclerAdapter.create<YaData>(R.layout.view_item) { data, position ->
    vTitle.text = data.title
    vDescription.text = data.description
}
// 或者
val vAdapter = object : VRecyclerAdapter<YaData>() {
    override fun createHolder(viewGroup: ViewGroup, viewType: Int): VAdapter.Holder<YaData> {
        return viewGroup.inflate(R.layout.view_item).toHolder { data, position ->
            vTitle.text = data.title
            vDescription.text = data.description
        }
    }
}
```

**2) 初始化 RecyclerView，并传入 VAdapter  对象**

```kotlin
recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.adapter = vAdapter
```

**3) 在得到数据之后，再进行填充，并刷新视图：**

```kotlin
vAdapter.refill(dataSet)
```

### VAdapter 的高级用法

下面是 vadapter 库里面的几个主要类的继承关系图：

```plain
MutableList<D>
|-- VAdapter<D>
|   |-- VBaseAdapter<D>
|   |   |-- VListAdapter<D>
|   |   |-- VPagerAdaper<D>
|   |-- VRecyclerAdapter<D>
|-- VFragmentPagerAdapter
```

可以看到它们都继承了 MutableList。因此我们可以对 VAdapter 进行列表一样的增删改操作。但操作完成之后需要调用 VAdapter.notifyChanged() 来刷新视图。

下面是一个示例：

```kotlin
fun onFetchData(dataSet: List<YaData>) {
    vAdapter.addAll(dataSet)
    vAdapter.removeAt(0)
    vAdapter.notifyChanged()
}
```

> VAdapter.refill() 里面已经调用了 notifyChanged()，无需再次调用。

> 关于 RecyclerView 的 BUG：Inconsistency detected. Invalid item position。解决方案见 StackOverflow： [30220771](https://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position)，[31759171](https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in)

## Moc

[![moc](https://img.shields.io/bintray/v/7hens/maven/moc.svg)](https://bintray.com/7hens/maven/moc)

Moc 库用来生产随机的 mock 数据。

在 module 的 build.gradle 中添加依赖：

```groovy
implementation 'cn.thens.kdroid:moc:0.1.10'

// 需要导入 Gson
implementation 'com.google.code.gson:gson:2.8.2'
```

**Moc 通过注解来表示要生成的随机数据，并通过调用 Moc.create() 来创建实例：**

```kotlin
data class YaData(
        @MocBool(false) val isFollowed: Boolean,
        @MocString("mockedTitle", "Celavee", "Balabala") val title: String,
        @MocString("cool", "too") val description: String,
        @MocString("dd", "ee", "tt") val detail: String
)

val data: YaData = Moc.create(YaData::class.java)
```

Moc 中含有的注解：
- MocBool：生成随机的 boolean
- MocString：生成随机的 String
- MocInt：生成随机的 int
- MocLong：生成随机的 long
- MocFloat：生成随机的 float
- MocDouble：生成随机的 double
- MocList：生存随机大小的 ArrayList

## Stetho-NoOp

[![stetho-no-op](https://img.shields.io/bintray/v/7hens/maven/stetho-no-op.svg)](https://bintray.com/7hens/maven/stetho-no-op)

Stetho 是一个调试工具，但官方并没有提供其发布版，于是我就补了一个。

在 module 的 build.gradle 中添加依赖

```groovy
releaseImplementation "cn.thens.kdroid:sthetho-no-op:0.1.10"

debugImplementation "com.facebook.stetho:stetho:${stetho_version}"
debugImplementation "com.facebook.stetho:stetho-okhttp3:${stetho_version}"
```
## UPay

[![upay](https://img.shields.io/bintray/v/7hens/maven/upay.svg)](https://bintray.com/7hens/maven/upay)

UPay 是一个集合支付宝和微信支付的轻量级的工具库。

在 module 的 build.gradle 中添加依赖

```groovy
implementation "cn.thens.kdroid:upay:0.1.10"

// 导入其他依赖库
implementation 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:1.1.6'
implementation "io.reactivex.rxjava2:rxjava:2.1.6"
implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
implementation 'com.google.code.gson:gson:2.8.2'
```

在 AndroidManifest.xml 中声明权限

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

在 AndroidManifest.xml 中注册 Activity

```xml
<!-- 支付宝支付 -->
<activity
    android:name="com.alipay.sdk.app.H5PayActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:exported="false"
    android:screenOrientation="behind"/>

<activity
    android:name="com.alipay.sdk.auth.AuthActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:exported="false"
    android:screenOrientation="behind"/>

<!-- 微信支付 -->
<activity
    android:name="cn.thens.kdroid.upay.WeChatPay$CallbackActivity"
    android:configChanges="orientation|keyboardHidden|navigation|screenSize"
    android:launchMode="singleTop"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" />

<activity-alias
    android:name=".wxapi.WXPayEntryActivity"
    android:exported="true"
    android:targetActivity="cn.thens.kdroid.upay.WeChatPay$CallbackActivity" />
```

### 支付宝支付

支付宝支付和微信支付是类似的，调用 pay() 都返回 `Observable<PayResult>`。

得到 PayResult 后，可以通过 code 的值来判断是否支付成功。
- `PayResult.SUCCESS` 支付成功
- `PayResult.CANCEL` 支付取消
- `PayResult.FAIL` 支付失败

```kotlin
fun alipay(orderInfo: String) {
    Alipay.pay(activity, orderInfo)
        .doOnNext { payResult ->
            if (payResult.code == PayResult.SUCCESS) {
            }
        }
        .subscribe()
}
```

### 微信支付

微信需要先在 Application 里初始化。

```kotlin
class App : Application() {
    init {
        WeChatPay.initialize(this, appId)
    }
}
```

```kotlin
fun weChatPay(orderInfo: String) {
    WeChatPay.pay(activity, orderInfo)
        .doOnNext { payResult ->
            if (payResult.code == PayResult.SUCCESS) {
            }
        }
        .subscribe()
}
```

## ChangeLog

