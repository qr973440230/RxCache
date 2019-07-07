# RxCache
# 使用方式 Tag请看最新release
Add it in your root build.gradle at the end of repositories:
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency
	dependencies {
	        implementation 'com.github.qr973440230:RxCache:Tag'
	}

# 模仿RxCache源码，实现的6种策略
1.Default
  默认模式：先加载缓存，再加载网络
2.NetworkPriority
  网络优先模式：优先加载网络，网络加载失败再加载缓存
3.CachePriority
  缓存优先模式: 优先加载缓存，无缓存时加载网络
4.OnlyNetwork
  仅网络模式：只加载网络
5.OnlyCache
  仅缓存模式：只加载缓存
6.CacheControl
  缓存控制模式：优先加载缓存，无缓存或者缓存超时(添加LifeCache注解控制)后加载网络数据

# @ProviderKey 注解
提供ProviderKey和上述策略
# @LifeCache注解 
提供存活时间,该注解只对CacheControl模式有效


