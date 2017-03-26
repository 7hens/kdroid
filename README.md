# KDroid

[![Bintray](https://img.shields.io/bintray/v/7hens/maven/kdroid.svg)](https://bintray.com/7hens/maven/kdroid)
[![license](https://img.shields.io/github/license/7hens/KDroid.svg)](https://github.com/7hens/KDroid/blob/master/LICENSE)

KDroid 是用一个用 Kotlin 写的轻量级 Android 库。希望通过 KDroid 来使得 Android 的开发更加方便快捷。

> Kotlin 是一门运行于 JVM 上的语言，完全兼容 Java，所以不用担心兼容性问题。

## 导入 KDroid

1) 在 module 的 build.gradle 中添加依赖

```groovy
compile "org.chx.kdroid:kdroid:1.0.2"
```

2) 在项目根目录的 build.gradle 中添加仓库 url

```groovy
allproject {
    repositories {
        jcenter()
        maven { url 'https://dl.bintray.com/7hens/maven/' }
    }
}
```

## 万能的 KAdapter

### 用 KDroid 创建一个 Adapter 有多简单？

首先，创建一个 Adapter 委托：

```kotlin
val adapter = KAdapter.singleLayout(dataList, R.layout.item) { data, position ->
    vTitle.text = data.first
    vDescription.text = data.second
}
```

然后，听说你需要 ListView 的 Adapter？

```kotlin
adapter.adapt(listView)
```

还是说，需要 RecyclerView 的 Adapter？

```kotlin
adapter.adapt(recyclerView)
```

ViewPager 的 Adapter？

```kotlin
adapter.adapt(viewPager)
```

如果你需要使用 Fragment 来填充 Adapter，你可以这么做：

```kotlin
listOf(FirstFragment::class, SecondedFragment::class).adapt(viewPager)
```


