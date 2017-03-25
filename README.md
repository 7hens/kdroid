# KDroid

## 万能的 KAdapter

用 KDroid 创建一个 Adapter 有多简单？

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


