## 前端说明文档

#### 组件库

##### **element-plus + vuestic-ui + arco-design**

###### **element-plus**

[link](https://element-plus.gitee.io/zh-CN/component/border.html)

```
# installation
$ npm install element-plus --save
# or
$ yarn add element-plus

# add to vue prject
$ vue add element-plus
```

###### **vuestic-ui**

[link](https://vuestic.dev/en/getting-started/installation)

安装

```
npm install vuestic-ui
# or
yarn add vuestic-ui
```

完整引入

```
vue add vuestic-ui
```

###### **arco-design**

[link](https://arco.design/vue/docs/start)

安装

```
# npm
npm install --save-dev @arco-design/web-vue
# yarn
yarn add --dev @arco-design/web-vue 
```

完整引入

```
import { createApp } from 'vue'
import ArcoVue from '@arco-design/web-vue';
import App from './App.vue';
import '@arco-design/web-vue/dist/arco.css';

const app = createApp(App);
app.use(ArcoVue);
app.mount('#app');
```

