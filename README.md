# KDroid

[![Bintray](https://img.shields.io/bintray/v/7hens/maven/KDroid.svg)](https://bintray.com/7hens/maven/KDroid)
[![GitHub tag](https://img.shields.io/github/tag/7hens/KDroid.svg)](https://github.com/7hens/KDroid)

KDroid 是用一个 Kotlin 写的 Android 库。希望通过 KDroid 来使得 Android 的开发更加方便快捷。

> Kotlin 是一门运行于 JVM 上的便捷灵活的语言，它完全兼容 Java，所以不用担心兼容性问题。

## 导入 KDroid

```groovy
compile "org.chx.kdroid:kdroid:1.0.0"
```

## 万能的 KAdapter

### 用 KDroid 创建一个 Adapter 有多简单？

首先，创建一个 Adapter 委托：

```kotlin
val kAdapter = KAdapter.singleLayout(dataList, R.layout.item) { data, position ->
    vTitle.text = data.first
    vDescription.text = data.second
}
```

然后，听说你需要 ListView 的 Adapter？
```kotlin
listView.adapter = kAdapter.listAdapter()
```

还是说，需要 RecyclerView 的 Adapter？
```kotlin
recyclerView.adapter = kAdapter.recyclerAdapter()
```

ViewPager 的 Adapter？
```
viewPager.adapter = kAdapter.pagerAdapter()
```


