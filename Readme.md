###结构
- rxretrofit: android lib 
- volley: android lib

###比较
~~~
12-10 17:20: this is Time_v:197
12-10 17:20: this is onNext:com.rabel.rxretrofit.Bean.SongListBean$ResponseBean@6815921
12-10 17:20: this is Time_r:292
12-10 17:20: this is onCompleted
12-10 17:20: this is onNext:com.rabel.rxretrofit.Bean.SongListBean$ResponseBean@2b80415
12-10 17:20: this is Time_r:2066
12-10 17:20: this is onCompleted
12-10 17:20: this is Time_v:2105
~~~
#####结果
- Volley第一优于Retrofit
- Retrofit第二次其获取缓存的速度优于Volley

## 注：代码是整合网上的资源，若有侵权，望告知
