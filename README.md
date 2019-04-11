# 监听生命周期的lifecycle
 
#用法
maven { url 'https://jitpack.io' }
compile 'com.github.wangdanlizhiyun:lifecycle:1.0.0'
  
  ```
  //继承 A : LifecycleListener
  //watch监听组件
  /** @see #with(android.app.Activity)
     * @see #with(android.app.Fragment)
     * @see #with(android.support.v4.app.Fragment)
     * @see #with(android.support.v4.app.FragmentActivity)
     */
    A().watch(this)
  ```
  
    
 