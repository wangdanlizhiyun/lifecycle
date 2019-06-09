# 监听生命周期的lifecycle，使用方法比官方lifecycle简单并且支持的场景更多，支持android.app.Activity，android.app.Dialog等，支持多级组件的生命周期自动传递，子模块先执行，支持自动解绑
 
#用法
maven { url 'https://jitpack.io' }
implementation 'com.github.wangdanlizhiyun:lifecycle:3.0.0'
  
  ```
  让业务组件  如A继承LifecycleListener接口进行标示,用@OnCreate等注解标注要在监听到的生命周期时执行的方法，注意必须是无参方法

  业务组件与生命周期之间的关联通过业务组件的扩展函数bind方法A() bind this，参数支持如下：
  1.android.app.Application  只包含onstart
  2.android.support.v4.app.FragmentActivity
  3.android.app.Activity
  4.android.app.Fragment
  5.android.support.v4.app.Fragment（包含android.support.v4.app.DialogFragment）
  6.android.app.Dialog 只有oncreate和onDestroy即分别对应显示和隐藏
  7.其他context如view内的context
  8.View类型，
  
  对于java类由于不能调用扩展函数方法bind就得换成ManagerRetriever.INSTANCE.get(this).addListener(new A());,参数同上
    解绑定依然是自动化的，使用者无需考虑。注意对于参数为Application或者在子线程调用时就不会自动解绑，处理逻辑同glide
    
    对于业务组件的子组件无需手动绑定即可自动跟随夫业务组件的生命周期。
    如presenter里的子业务组件的生命周期无需使用者手写大量繁琐的生命周期传递方法
    如果是view组件则子view自动跟随夫view的生命周期
    两种情况可以同时存在并且多层嵌套
   
  ```
  ###TODO：1.免除其他场景的 bind 方法调用 
    
 