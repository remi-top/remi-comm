```yaml
remi:
  files-server:
    # 文件上传模式，local 本地，qiniu 七牛云，aliyunOss 阿里云OSS，minio MinIO
    type: local
    local:
      # 本地存储目录
      directory: E:/data/free-fs/upload
      # 生产环境请填写公网地址或者域名
      endPoint: http://127.0.0.1:8081
      # 反向代理地址，如nginx代理，配置nginx代理地址
      #nginxUrl:
    #如配置七牛，需在QiniuAutoConfigure.java配置类中指定自己的七牛云机房区域
    qiniu:
      accessKey: xxxxxxxxxxxxxxxxxxxxxxxxx
      secretKey: xxxxxxxxxxxxxxxxxxx
      bucket: free-dh
      path: xxxxxxxxxxxxxxxxxxx
    #aliyunOss配置
    aliyunOss:
      accessKey: xxxxxxxxxxxxxxxxxxxxx
      secretKey: xxxxxxxxxxxxxxxxxxxx
      endPoint: oss-cn-hangzhou.aliyuncs.com
      bucket: free-fs
    #minio配置
    minio:
      accessKey: dfgfdg23423dfgdfgfd211
      secretKey: dinghao2222111333@!
      endpoint: http://127.0.0.1:9000
      bucket: test
  #文件预览地址，这里配置的是kkfileview的部署地址，https://kkfileview.keking.cn/
  preview:
    endpoint: http://127.0.0.8888/onlinePreview
```