import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import { VuesticPlugin } from 'vuestic-ui'
import 'vuestic-ui/dist/vuestic-ui.css'

import ArcoVue from '@arco-design/web-vue';
import ArcoVueIcon from '@arco-design/web-vue/es/icon';
import '@arco-design/web-vue/dist/arco.css';

const app = createApp(App)
installElementPlus(app)
app.use(router).use(VuesticPlugin).use(ArcoVue).use(ArcoVueIcon).mount('#app')