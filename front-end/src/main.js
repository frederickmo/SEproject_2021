import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import { VuesticPlugin } from 'vuestic-ui'
import 'vuestic-ui/dist/vuestic-ui.css'
import { URL } from './global'

// import store from "@src/store/store"



// import axios from 'axios'  //引入axios
// Vue.prototype.$http = axios


import ArcoVue from '@arco-design/web-vue';
import ArcoVueIcon from '@arco-design/web-vue/es/icon';
import '@arco-design/web-vue/dist/arco.css';

const app = createApp(App)
app.config.globalProperties.$URL=URL
installElementPlus(app)
app.use(router).use(VuesticPlugin).use(ArcoVue).use(ArcoVueIcon).mount('#app')

// Vue.use(Vuex)

// new Vue({
//     el: '#app',
//     router,
//     store,
//     components: { App },
//     template: '<App/>'
// })