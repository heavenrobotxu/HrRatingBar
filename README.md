# HrRatingBar
抛弃难用的系统自带Ratingbar，自己来写一个简单好用的吧 O(∩_∩)O~

# 简介
* 项目中总会使用到ratingbar这个控件，可是系统自带的使用起来真是很不方便，设置星星图片还要单独去创建资源文件，所以这里自己来实现一个，简单易用，仅xml文件就可以一次性搞定了。

# **Usage**
## **Installation**
**Using Gradle**

```gradle
compile 'com.xztot:xzratingbar:1.0.0'
```



现在你可以在XMl文件中直接使用了，就像这样
```xml
<com.hr.xz.hrratingbar.view.HrRatingBar
        android:id="@+id/my_rating_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:starTotalNum="5"
        app:starSelectedNum="3"
        app:starDefaultDrawable="@mipmap/star_default"
        app:starSelectedDrawable="@mipmap/star_selected"
        app:starHalfDrawable="@mipmap/star_half"
        app:isIndicator="true"
        app:starMargin="10dp"
        app:isShowHalf="true"
        app:starHeight="20dp"
        app:starWidth="20dp"
        />
```


**可使用的属性**

*starTotalNum* : 要显示的星星总数(默认为5)

*starSelectedNum* : 已选择星星数(默认为5)

*starDefaultDrawable* : 默认星星图标

*starHalfDrawable* : 半星图标

*starSelectedDrawable* : 被选中星星图标

*starMargin* : 星星之间间隔(可选，不设置的话默认为0)

*isIndicator* : 是否仅做展示不响应点击事件(默认为false)

*isShowHalf* : 是否显示半颗星(默认为false)

*starHeight* : 单个星星高度(可选，不设置的话按照图片为准)

*starWidth* : 单个星星宽度(可选，不设置的话按照图片为准)

**代码**
```java
mRb = (HrRatingBar) findViewById(R.id.my_rating_bar);
        mRb.setShowHalf(true);
        mRb.setRatingChangeListener(new HrRatingBar.OnRatingChangListener() {
            @Override
            public void onRatingChange(float rating) {
                Toast.makeText(MainActivity.this, rating + "", Toast.LENGTH_SHORT).show();
            }
        });
```

**注意**

*starMargin*属性在设置*layout_width="match_parent"*或者指定长度时会失效，当宽度为指定宽度时可以通过改变宽度的大小来间接的调整星星的间距。当宽度为自适应时*starMargin*有效，此时可以结合*starWidth*和*starMargin*来控制整个控件的宽度
