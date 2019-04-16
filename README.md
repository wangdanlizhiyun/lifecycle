# 监听生命周期的lifecycle，使用方法比官方lifecycle简单并且支持的场景更多
 
#用法
maven { url 'https://jitpack.io' }
compile 'com.github.wangdanlizhiyun:lifecycle:1.0.2'
  
  ```
  //让组件A继承 LifecycleListener
  //然后通过watch方法监听安卓生命周期组件，类似于glide用法
  /** @see #with(android.app.Activity)
     * @see #with(android.app.Fragment)
     * @see #with(android.support.v4.app.Fragment)
     * @see #with(android.support.v4.app.FragmentActivity)
     */
    A().watch(this)
  ```
  
    
 