# SEproject_2021
### #软件工程课程设计项目



### 启动方式

#### 运行vue project源文件

1. 进入/SEproject_2021/front-end文件夹

2.  安装node.js & vue

```
% brew install node

% npm install vue
or
% npm install -g @vue/cli
```

3. 安装依赖包

| 依赖包              | 版本          |
| ------------------- | ------------- |
| core-js             | 3.6.5         |
| element-plus        | 1.0.2-beta.28 |
| v-calendar          | 3.0.0-alpha.6 |
| vue-router          | 4.0.0-0       |
| vuestic-ui          | 1.3.1         |
| arco-design/web-vue | 2.0.2         |

4. 运行

```
% npm install
% npm run serve
```



#### 运行打包后前端文件

1. 安装node.js & http-server

```
% brew install node

% npm install http-server
```

2. 进入文件夹/SEproject_2021/front-end/dist，运行

```
% http-server
```



#### 后端选择

##### 选用服务器源

无需更改



##### 本地运行jar包

（打包后的dist文件夹里改起来有点麻烦的样子，要跑本地jar包的话似乎在vue project源文件里跑比较好）

1. 进入/SEproject_2021/front-end/src/global.js，注释第一行，取消第二行注释

```javascript
// const URL = "http://localhost:8081"  //      <= 取消注释这行
const URL = "http://106.15.106.190:8081"//      <= 注释掉这行
export {
    URL
}
```

2. 进入/SEproject_2021/back-end/src/main/resources/application.yml，修改第86行upload-dir为本地一个可以读写的文件夹（或者直接注释掉86行后面增加一行）

```yaml
(82) file:
     ...
(86)   upload-dir: /home/user/backend   # 修改路径或者注释掉这行下面新增一条
```

3. 使用Intellij IDEA打开back-end文件夹，maven重新打包
4. 进入/SEproject_2021/back-end/target文件夹，命令行运行

```
% java -jar backend-test-0.0.1-SNAPSHOT.jar
```

