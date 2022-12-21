# short-link
# 一个单体的短链接生成和转发项目  
需要使用者自己配置数据源，数据库可以使用MySQL  
如果需要使用集群模式，可搭配使用 spring cloud 相关技术，但是需要新增 IdCenter 子类，子类要求能够处理集群模式下，雪花id冲突问题，比如
- 自适应的wordId,dataCenterId
- 集群模式时钟回拨产生的重复雪花id
