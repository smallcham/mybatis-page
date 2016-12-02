#mybatis-page

##作者
- 一线生

##说明
- 无需`count`，自动实现分页并返回分页信息，使用方法简单
- 可能是使用起来最方便的分页插件
- 包含了`mybatis-3.4.1` `mybatis-spring-1.3.0`不需要重复引入

###插件目前支持以下数据库
- `Mysql`
- `Oracle`

###配置方法
####Maven依赖
```xml
<dependency>
    <groupId>com.github.smallcham</groupId>
    <artifactId>mybatis-page-nodep</artifactId>
    <version>1.5-RELEASES</version>
</dependency>
```

####mybatis.xml配置文件, 在`configuration`标签内添加
```xml
<plugins>
    <plugin interceptor="FastPage">
    	<!-- 数据库类型（必须配至） -->
		<property name="type" value="MYSQL"/>
		<!-- 需要拦截的分页方法正则（必须配置） -->
		<property name="method" value=".*query*;.*page*"/>
		<!-- 每页显示多少条（可选配置） -->
		<property name="size" value="50"/>
	</plugin>
</plugins>
```
<b>注：</b>
- 数据库类型不区分大小写
- 每页显示多少条可以在代码中传参配置，也可以在xml文件中配置，如果都没有配则默认一页显示10条（`优先级从高到低为 代码配置 -> xml配置-> 默认配置`）即：如果代码中配置了size，则其它配置无效

###使用方法示例
productMapper接口
```js
List<Object> query(RowBounds rowBounds);
```

productService实现
```js
public Page<Product> query(Product product, int nowPage) {
    //nowPage为要查询的页数
    RowBounds rowBounds = Page.rowBounds(nowPage, product);
    List<Product> products = productMapper.query(rowBounds);
    return Page.asPage(rowBounds, products);
}
```
<b>注：`Page.rowBounds(nowPage, product)`支持`pageSize`参数, 即 `Page.rowBounds(nowPage, pageSize, product)`</b>

productMapper.xml SQL编写
```xml
<select id="query" resultMap="BaseResultMap" parameterType="com.github.smallcham.plugin.page.support.RowBounds">
SELECT
<include refid="Query_Column_List" />
FROM product p, product_type pt
<where>
  <if test="null != v.productCode and '' != v.productCode">
    AND p.product_code = #{v.productCode}
  </if>
  <if test="null != v.productName and '' != v.productName">
    AND p.product_name = #{v.productName}
  </if>
  <if test="null != v.productTypeCode and '' != v.productTypeCode">
    AND p.product_type_code = #{v.productTypeCode}
  </if>
  <if test="null != v.productState and '' != v.productState">
    AND p.product_state = #{v.productState}
  </if>
  AND p.product_type_code = pt.product_type_code
</where>
</select>
```
<b>需要注意的是，sql中的查询参数使用都需要在前面加`v.` 如：`#{v.productName}`，这是因为RowBounds对象中封装了传入的参数对象</b>

作者博客：  http://blog.csdn.net/a973893384

作者邮箱：  973893384@163.com
