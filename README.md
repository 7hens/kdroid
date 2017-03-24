# KDroid

## 万能的 KAdapter

用 KDroid 创建一个 Adapter 有多简单？

首先，创建一个 Adapter 代理：

```kotlin
val adapter = KAdapter.singleLayout(dataList, R.layout.item) {
    object : HolderView<Pair<String, String>>(it) {
        override fun convert(data: Pair<String, String>, position: Int) {
            vTitle.text = data.first
            vDescription.text = data.second
        }
    }
}
```

然后，听说你需要 ListView 的 Adapter？
```kotlin
listView.adapter = adapter.listAdapter()
```

还是说，需要 RecyclerView 的 Adapter？
```kotlin
recyclerView.adapter = adapter.recyclerAdapter()
```

ViewPager 的 Adapter？
```
viewPager.adapter = adapter.pagerAdapter()
```


