dataSource {
    pooled = true
    dbCreate = "update"
    url = "jdbc:mysql://localhost/drupala"
    driverClassName = "com.mysql.jdbc.Driver"
dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    username = "badarib"
    password = "Badrinath1"
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}

   
    


