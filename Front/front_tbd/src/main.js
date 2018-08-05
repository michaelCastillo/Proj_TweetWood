import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import VueCharts from 'vue-chartjs'
//Routes
import router from './router'

//Components
import App from './App.vue'


Vue.use(Vuetify);
Vue.use(VueCharts);
Vue.config.productionTip = false;

router.beforeEach((to, from, next) => {
    if(to.matched.some((record) => record.meta.requiresAuth)){
        next({
            path: '/login',
            params: { nextUrl: to.fullPath }
        })
    } else{
        next()
    }
})

new Vue({
    render: h => h(App),
    router
}).$mount('#app')

